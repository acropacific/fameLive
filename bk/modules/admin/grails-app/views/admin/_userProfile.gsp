<div class="row profile">
    <div class="col-md-3">
        <ul class="list-unstyled profile-nav">
            <li>
                <admin:displayUserImage imageName="${adminUserInformationVO?.adminUserProfileDto?.imageName}"/>
            </li>
        </ul>
    </div>

    <div class="col-md-9">
        <div class="row">
            <div class="col-md-8 profile-info">
                <h1>${adminUserInformationVO?.adminUserProfileDto?.username}</h1>

                <p>
                    Fame Name : ${adminUserInformationVO?.adminUserProfileDto?.fameName}
                </p>

                <p>
                    Fame Id :  ${adminUserInformationVO?.adminUserProfileDto?.fameId}
                </p>

            </div>
        </div>
    </div>
</div>