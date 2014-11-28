<%@ page import="com.famelive.streamManagement.admin.StreamingAdminPanelUIConstants; com.famelive.common.streamManagement.EventStreamInfo; com.famelive.common.streamManagement.WowzaChannel" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'as.jpg')}"/>
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    %{--<asset:stylesheet src="application.css"/>--}%
    %{--<asset:javascript src="application.js"/>--}%
    %{--<g:javascript src="jquery-1.9.1.js"/>
    <g:javascript src="jquery-ui-1.10.1.custom.js"/>--}%
    <script type="application/javascript" src="../js/jquery-1.9.1.js"></script>
    <script type="application/javascript" src="../js/jquery-ui-1.10.1.custom.js"></script>
    <link href="../css/googleFonts/googleFont_OpenSans.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet" type="text/css"/>

    <link href="../assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" type="text/css" href="../assets/plugins/clockface/css/clockface.css"/>
    <link rel="stylesheet" type="text/css" href="../assets/plugins/bootstrap-datepicker/css/datepicker.css"/>
    <link rel="stylesheet" type="text/css"
          href="../assets/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
    <link rel="stylesheet" type="text/css" href="../assets/plugins/bootstrap-colorpicker/css/colorpicker.css"/>
    <link rel="stylesheet" type="text/css" href="../assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"/>
    <link rel="stylesheet" type="text/css" href="../assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>

    <link href="../assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/pages/timeline.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/pages/portfolio.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/pages/profile.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="../assets/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="../assets/css/custom.css" rel="stylesheet" type="text/css"/>

    <link href="../assets/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css"/>

    <g:layoutHead/>
</head>

<body class="page-header-fixed">

<div class="header navbar navbar-fixed-top">
    <!-- BEGIN TOP NAVIGATION BAR -->
    <div class="header-inner">
        <!-- BEGIN LOGO -->
    <a class="navbar-brand" href="${createLink(controller: 'streamingAdmin', action: 'fetchChannels')}">
        <img src="../images/home.png" alt="Home" height="40px" width="40px" style="margin-top: -15px"
             class="img-responsive"/>
        </a>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <img src="../assets/img/menu-toggler.png" alt=""/>
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <ul class="nav navbar-nav pull-right">
            <!-- BEGIN NOTIFICATION DROPDOWN -->

            %{--PLACE Header Notification Bar Here--}%

            <!-- END NOTIFICATION DROPDOWN -->
            <!-- BEGIN INBOX DROPDOWN -->

            %{--Place header inbox bar here--}%

            <!-- END INBOX DROPDOWN -->
            <!-- BEGIN TODO DROPDOWN -->

            %{--Place header task bar here--}%

            <!-- END TODO DROPDOWN -->
            <!-- BEGIN USER LOGIN DROPDOWN -->
            <li class="dropdown user">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                   data-close-others="true">
                    <img alt="" src="../assets/img/avatar1_small.jpg"/>
                    <span class="username">
                        %{--${admin?.username}--}%
                    </span>
                    <i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="extra_profile.html">
                            <i class="fa fa-user"></i> My Profile
                        </a>
                    </li>
                    <li class="divider">
                    </li>
                    <li>
                        <g:link controller="logout" action="index">
                            <i class="fa fa-key"></i> Log Out
                        </g:link>
                    </li>
                </ul>
            </li>
            <!-- END USER LOGIN DROPDOWN -->
        </ul>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END TOP NAVIGATION BAR -->
</div>

<div class="clearfix">
</div>

<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div class="page-sidebar-wrapper">
        <div class="page-sidebar navbar-collapse collapse">
            <!-- add "navbar-no-scroll" class to disable the scrolling of the sidebar menu -->
            <!-- BEGIN SIDEBAR MENU -->
            <ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200">
                <li class="sidebar-toggler-wrapper">
                    <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                    <div class="sidebar-toggler hidden-phone">
                    </div>
                    <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                </li>
                %{--Place seacrh here--}%
                <br/>
                <li>
                    <g:link controller="streamingAdmin" action="fetchChannels" class="activeList">
                        <i class="fa fa-tasks"></i>
                        <span class="title">
                            Manage Channels
                        </span>
                        <span class="arrow ${StreamingAdminPanelUIConstants.MANAGE_CHANNEL_ACTIONS.contains(params.action) ? 'open' : ''}">
                        </span>
                    </g:link>
                    <ul class="sub-menu"
                        style="display: ${StreamingAdminPanelUIConstants.MANAGE_CHANNEL_ACTIONS.contains(params.action) ? 'block' : 'none'};">
                        <g:each in="${WowzaChannel.list()}" var="channel">
                            <li>
                                <g:link controller="streamingAdmin" action="fetchChannelDetails"
                                        params='[channelId: "${channel?.id}"]'>
                                    <i class="fa fa-bar-chart-o"></i>
                                    ${channel.name.toUpperCase()}
                                </g:link>
                            </li>
                        </g:each>

                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <i class="fa fa-barcode"></i>
                        <span class="title">
                            Live Streams Channels
                        </span>
                        <span class="arrow  ${StreamingAdminPanelUIConstants.LIVE_STREAMS_CHANNELS_ACTIONS.contains(params.action) ? 'open' : ''}">
                        </span>
                    </a>
                    <ul class="sub-menu"
                        style="display: ${StreamingAdminPanelUIConstants.LIVE_STREAMS_CHANNELS_ACTIONS.contains(params.action) ? 'block' : 'none'};">

                        <g:each in="${WowzaChannel.findAllByIsVODChannelAndIsActive(false, true)}" var="channel">
                            <li>
                                <g:link controller="streamingAdmin" action="fetchLiveStreamDetailsForChannel"
                                        params='[channelId: "${channel?.id}"]'>
                                    <i class="fa fa-bar-chart-o"></i>
                                    ${channel.name.toUpperCase()}
                                </g:link>
                            </li>
                        </g:each>
                    </ul>
                </li>
            </ul>
            <!-- END SIDEBAR MENU -->
        </div>
    </div>
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <div class="page-content">
            <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
            <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                            <h4 class="modal-title">Modal title</h4>
                        </div>

                        <div class="modal-body">
                            Widget settings form goes here
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn blue">Save changes</button>
                            <button type="button" class="btn default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
            <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
            <!-- BEGIN STYLE CUSTOMIZER -->

            <!-- END STYLE CUSTOMIZER -->
            <!-- BEGIN PAGE HEADER-->
            <div class="row">
                <div class="col-md-12">

                    <g:layoutBody/>
                </div>
            </div>
            <!-- END CONTENT -->
            <div class="footer" role="contentinfo"></div>

            <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt"
                                                                               default="Loading&hellip;"/></div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="footer-inner">
        2014 &copy; FameLive.
    </div>

    <div class="footer-tools">
        <span class="go-top">
            <i class="fa fa-angle-up"></i>
        </span>
    </div>
</div>

<script src="../assets/plugins/respond.min.js"></script>
<script src="../assets/plugins/excanvas.min.js"></script>

<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="../assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="../assets/plugins/jqvmap/jqvmap/jquery.vmap.js" type="text/javascript"></script>
<script src="../assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js" type="text/javascript"></script>
<script src="../assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js" type="text/javascript"></script>
<script src="../assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js" type="text/javascript"></script>
<script src="../assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js" type="text/javascript"></script>
<script src="../assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js" type="text/javascript"></script>
<script src="../assets/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js" type="text/javascript"></script>
<script src="../assets/plugins/flot/jquery.flot.min.js" type="text/javascript"></script>
<script src="../assets/plugins/flot/jquery.flot.resize.min.js" type="text/javascript"></script>
<script src="../assets/plugins/flot/jquery.flot.categories.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.pulsate.min.js" type="text/javascript"></script>
<script src="../assets/plugins/gritter/js/jquery.gritter.js" type="text/javascript"></script>
<!-- IMPORTANT! fullcalendar depends on jquery-ui-1.10.3.custom.min.js for drag & drop support -->
<script src="../assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.sparkline.min.js" type="text/javascript"></script>

<script type="text/javascript" src="../assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="../assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="../assets/plugins/clockface/js/clockface.js"></script>
<script type="text/javascript" src="../assets/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="../assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="../assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script type="text/javascript" src="../assets/plugins/jquery-mixitup/jquery.mixitup.min.js"></script>
<script type="text/javascript"
        src="../assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../assets/scripts/core/app.js" type="text/javascript"></script>
<script src="../assets/scripts/custom/index.js" type="text/javascript"></script>
<script src="../assets/scripts/custom/tasks.js" type="text/javascript"></script>
<script src="../assets/scripts/custom/components-pickers.js"></script>
<script src="../assets/scripts/custom/portfolio.js"></script>

<script src="../assets/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="../assets/scripts/ui-toastr.js" type="text/javascript"></script>

<script src="../js/famelive/FameLiveUtil.js"></script>
<script src="../js/famelive/StreamingAdminPanelHelper.js"></script>
<script>
    jQuery(document).ready(function () {
        App.init();
        ComponentsPickers.init();
        Portfolio.init();
        UIToastr.init();
        Index.init();
        Index.initJQVMAP(); // init index page's custom scripts
        Index.initCalendar(); // init index page's custom scripts
        Index.initCharts(); // init index page's custom scripts
        Index.initChat();
        Index.initMiniCharts();
        Index.initDashboardDaterange();
//        Index.initIntro();
        Tasks.initDashboardWidget();
    });
</script>

</body>
</html>
