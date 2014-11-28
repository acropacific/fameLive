<div class="row mix-grid">
    <g:each in="${adminUserInformationVO?.adminUserProfileDto?.followings}" var="following">
        <div class="col-md-2 col-sm-3 mix category_1 mix_all" style="display: block; opacity: 1;">
            <div class="mix-inner">
                <admin:displayFollowerImage imageName="${following?.imageName}"/>

                <div class="mix-details">
                    <h4>${following?.username}</h4>
                    <h6>${following?.fameId}</h6>
                    <br/>
                    <g:link controller="streamingAdmin" action="userProfile" class="btn dark"
                            params='[userId: "${following?.id}"]'><i
                            class="fa fa-user"></i></g:link>
                </div>
            </div>
        </div>
    </g:each>
</div>