<html>
<head>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'as.jpg')}"/>
    <title><g:message code="springSecurity.login.title"/></title>
    <style type='text/css' media='screen'>
    </style>
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet"
          type="text/css"/>
    <link href="../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" type="text/css" href="../assets/plugins/select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="../assets/plugins/select2/select2-metronic.css"/>

    <link href="../assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="../assets/css/pages/login-soft.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/custom.css" rel="stylesheet" type="text/css"/>
</head>

<body class="login">

<div class="logo">
    <img src="../images/logo.png" alt=""/>
</div>

<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
        <h3 class="form-title">Login to your account</h3>

        <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
            <span>
                Enter any username and password.
            </span>
            <g:if test='${flash.message}'>
                <div class='login_message'>${flash.message}</div>
            </g:if>
        </div>

        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">Username</label>

            <div class="input-icon">
                <i class="fa fa-user"></i>
                <input type='text' class='form-control placeholder-no-fix text_' name='j_username' id='username'
                       autocomplete="off" placeholder="Username"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">Password</label>

            <div class="input-icon">
                <i class="fa fa-lock"></i>
                <input type='password' class='form-control placeholder-no-fix text_' name='j_password' id='password'
                       autocomplete="off" placeholder="Password"/>
            </div>
        </div>

    <div class="form-group" id="remember_me_holder">
        <input type='checkbox' class='form-control placeholder-no-fix text_' name='${rememberMeParameter}'
               id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
        <label class="control-label" for='remember_me'>Remember me</label>
    </div>


    <div class="form-actions">
            <button type="submit" class="btn blue pull-right">
                Login <i class="m-icon-swapright m-icon-white"></i>
            </button>
        </div>

        %{--Place Social Login here--}%

        %{--Place forgot password here--}%

        %{--Place create account here--}%
    </form>
    <!-- END LOGIN FORM -->
    <!-- BEGIN FORGOT PASSWORD FORM -->

    <!-- END FORGOT PASSWORD FORM -->
    <!-- BEGIN REGISTRATION FORM -->

    %{--Place registration form here--}%
    <!-- END REGISTRATION FORM -->
</div>



<script src="../assets/plugins/respond.min.js"></script>
<script src="../assets/plugins/excanvas.min.js"></script>
<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="../assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="../assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../assets/plugins/select2/select2.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/scripts/core/app.js" type="text/javascript"></script>
<script src="../assets/scripts/custom/login-soft.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
    jQuery(document).ready(function () {
        App.init();
        Login.init();
    });
</script>
<script type='text/javascript'>
    <!--
    (function () {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>
