<div class="row mix-grid">
    <g:each in="${adminUserInformationVO?.adminUserProfileDto?.followers}" var="follower">
        <div class="col-md-2 col-sm-3 mix category_1 mix_all" style="display: block; opacity: 1;">
            <div class="mix-inner">
                <admin:displayFollowerImage imageName="${follower?.imageName}"/>

                <div class="mix-details">
                    <h4>${follower?.username}</h4>
                    <h6>${follower?.fameId}</h6>
                    <br/>
                    <g:link controller="admin" action="userProfile" class="btn dark"
                            params='[userId: "${follower?.id}"]'><i
                            class="fa fa-user"></i></g:link>
                </div>
            </div>
        </div>
    </g:each>
</div>