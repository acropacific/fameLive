package org.springframework.security.authentication.dao;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;


/**
 * Created by anil on 23/8/14.
 * <p/>
 * Reference : http://nofluffjuststuff.com/blog/burt_beckwith
 */
public class CustomDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private Object grailsApplication;
	private Object userCache;
	private Object preAuthenticationChecks;
	private Object postAuthenticationChecks;
	private Object authoritiesMapper;
	private Object hideUserNotFoundExceptions;

	//~ Static fields/initializers =====================================================================================

	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	//~ Instance fields ================================================================================================

	private PasswordEncoder passwordEncoder;

	private String userNotFoundEncodedPassword;

	private SaltSource saltSource;

	private UserDetailsService userDetailsService;

	public CustomDaoAuthenticationProvider() {
		setPasswordEncoder(new PlaintextPasswordEncoder());
	}

	/**
	 * The plaintext password used to perform {@link org.springframework.security.authentication.encoding.PasswordEncoder#isPasswordValid(String, String, Object)} on when the user is
	 * not found to avoid SEC-2056.
	 */
	public static String getUserNotFoundPassword() {
		return USER_NOT_FOUND_PASSWORD;
	}

	//~ Methods ========================================================================================================

	@SuppressWarnings("deprecation")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		Object salt = null;

		if (this.getSaltSource() != null) {
			salt = this.getSaltSource().getSalt(userDetails);
		}

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
		}
	}

	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.getUserDetailsService(), "A UserDetailsService must be set");
	}

	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;

		try {
			loadedUser = this.getUserDetailsService().loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			if (authentication.getCredentials() != null) {
				String presentedPassword = authentication.getCredentials().toString();
				getPasswordEncoder().isPasswordValid(getUserNotFoundEncodedPassword(), presentedPassword, null);
			}
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate passwords.
	 * If not set, the password will be compared as plain text.
	 * <p/>
	 * For systems which are already using salted password which are encoded with a previous release, the encoder
	 * should be of type {@code org.springframework.security.authentication.encoding.PasswordEncoder}. Otherwise,
	 * the recommended approach is to use {@code org.springframework.security.crypto.password.PasswordEncoder}.
	 *
	 * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder} types.
	 */
	public void setPasswordEncoder(Object passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

		if (passwordEncoder instanceof PasswordEncoder) {
			setPasswordEncoder((PasswordEncoder) passwordEncoder);
			return;
		}

		if (passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
			final org.springframework.security.crypto.password.PasswordEncoder delegate =
					(org.springframework.security.crypto.password.PasswordEncoder) passwordEncoder;
			setPasswordEncoder(new PasswordEncoder() {
				public String encodePassword(String rawPass, Object salt) {
					checkSalt(salt);
					return delegate.encode(rawPass);
				}

				public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
					checkSalt(salt);
					return delegate.matches(rawPass, encPass);
				}

				private void checkSalt(Object salt) {
					Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
				}
			});

			return;
		}

		throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
	}

	private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

		this.setUserNotFoundEncodedPassword(passwordEncoder.encodePassword(getUserNotFoundPassword(), null));
		this.passwordEncoder = passwordEncoder;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * The source of salts to use when decoding passwords. <code>null</code>
	 * is a valid value, meaning the <code>DaoAuthenticationProvider</code>
	 * will present <code>null</code> to the relevant <code>PasswordEncoder</code>.
	 * <p/>
	 * Instead, it is recommended that you use an encoder which uses a random salt and combines it with
	 * the password field. This is the default approach taken in the
	 * {@code org.springframework.security.crypto.password} package.
	 *
	 * @param saltSource to use when attempting to decode passwords via the <code>PasswordEncoder</code>
	 */
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	protected SaltSource getSaltSource() {
		return saltSource;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * The password used to perform {@link org.springframework.security.authentication.encoding.PasswordEncoder#isPasswordValid(String, String, Object)} on when the user is
	 * not found to avoid SEC-2056. This is necessary, because some {@link org.springframework.security.authentication.encoding.PasswordEncoder} implementations will short circuit if the
	 * password is not in a valid format.
	 */
	public String getUserNotFoundEncodedPassword() {
		return userNotFoundEncodedPassword;
	}

	public void setUserNotFoundEncodedPassword(String userNotFoundEncodedPassword) {
		this.userNotFoundEncodedPassword = userNotFoundEncodedPassword;
	}

	public Object getGrailsApplication() {
		return grailsApplication;
	}

	public void setGrailsApplication(Object grailsApplication) {
		this.grailsApplication = grailsApplication;
	}

	public Object getAuthoritiesMapper() {
		return authoritiesMapper;
	}

	public void setAuthoritiesMapper(Object authoritiesMapper) {
		this.authoritiesMapper = authoritiesMapper;
	}

	public Object getHideUserNotFoundExceptions() {
		return hideUserNotFoundExceptions;
	}

	public void setHideUserNotFoundExceptions(Object hideUserNotFoundExceptions) {
		this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
	}

	public void setUserCache(Object userCache) {
		this.userCache = userCache;
	}

	public void setPreAuthenticationChecks(Object preAuthenticationChecks) {
		this.preAuthenticationChecks = preAuthenticationChecks;
	}

	public void setPostAuthenticationChecks(Object postAuthenticationChecks) {
		this.postAuthenticationChecks = postAuthenticationChecks;
	}
}
