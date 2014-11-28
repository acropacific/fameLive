<div class="row">
    <div class="col-md-10">
        <div class="portlet sale-summary">
            <div class="portlet-title">
                <div class="caption">
                    Account Summary
                </div>
            </div>

            <div class="portlet-body">
                <ul class="list-unstyled">
                    <li>
                        <span class="sale-info">
                            Email Address <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.email}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Fame Id <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.fameId}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Fame Name <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.fameName}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            User Name <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.username}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Mobile Number <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.mobile}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Registered Through <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.registrationType}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Type <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${adminUserInformationVO?.adminUserProfileDto?.type}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Account Linked <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            <g:each in="${adminUserInformationVO?.adminUserProfileDto?.socialAccounts}"
                                    var="socialAccount">
                                ${socialAccount?.socialAccount}     <br>
                            </g:each>
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>