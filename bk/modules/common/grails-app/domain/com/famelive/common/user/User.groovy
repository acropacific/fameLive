package com.famelive.common.user

import com.famelive.common.command.usernamagement.UserSearchCommand
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.followermanagement.Follow
import com.famelive.common.slotmanagement.Event
import com.famelive.common.util.FameLiveUtil

class User {

    transient springSecurityService

    String fameId
    String username
    String email
    String fameName
    String mobile
    String password
    String imageName
    Date dateCreated
    String verificationToken
    boolean isAccountVerified = true
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String forgotPasswordCode
    UserRegistrationType registrationType = UserRegistrationType.MANUAL
    String channel = FameLiveUtil.getRandomChannel()

    static transients = ['springSecurityService']

    static hasMany = [events: Event, linkedSocialAccounts: LinkedSocialAccount]

    static constraints = {
        username nullable: false, blank: false
        password nullable: false, blank: false
        email nullable: false, blank: false
        mobile nullable: true, blank: false
        imageName nullable: true
        fameName nullable: true
        forgotPasswordCode nullable: true
        channel nullable: true
    }

    static mapping = {
        password column: '`password`'
        fameId formula: "CONCAT('FAME00000',id)"
    }

    static namedQueries = {
        filteredList { UserSearchCommand userSearchCommand ->
            if (userSearchCommand.fameName) {
                ilike("fameName", "%${userSearchCommand.fameName}%")
            }
            if (userSearchCommand.email) {
                eq("email", userSearchCommand.email)
            }
            if (userSearchCommand.fameId) {
                ilike("fameId", "%${userSearchCommand.fameId}%")
            }
            if (userSearchCommand.registrationTypes) {
                'in'("registrationType", userSearchCommand.registrationTypes)
            }
        }
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role }
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        if (password) {
            password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
        }
    }

    List<LinkedSocialAccount> fetchActiveSocialAccount() {
        return this?.linkedSocialAccounts?.findAll { it.isActive } as List
    }

    List<User> fetchFollowers() {
        return Follow.fetchUserFollowers(this)
    }

    List<User> fetchFollowings() {
        return Follow.fetchUserFollowings(this)
    }

    List<User> fetchUnFollowers() {
        return Follow.fetchUserFollowers(this, false)
    }

    Long totalNumberOfFollowers() {
        return fetchFollowers()?.size()
    }

    Long totalNumberOfFollowings() {
        return fetchFollowings()?.size()
    }

    Long totalNumberOfUnFollowers() {
        return fetchUnFollowers()?.size()
    }

    String populateUserImagePath() {
        return this?.fameId
    }

    List<String> fetchFollowersChannels() {
        return Follow.getChannelsForPerformer(this)
    }

    //Customize login
    //http://www.objectpartners.com/2013/07/11/custom-authentication-with-the-grails-spring-security-core-plugin/
}
