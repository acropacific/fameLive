<%@ page import="com.famelive.admin.constant.AdminConstants" %>
<div class="row profile">
    <div class="col-md-3">
        <ul class="list-unstyled profile-nav">
            <li>
                <admin:displayEventImage imageName="${adminEventVO?.imageName}"/>
            </li>
        </ul>
    </div>

    <div class="col-md-9">
        <div class="row">
            <div class="col-md-8 profile-info">
                <h1>${adminEventVO?.eventUId} - ${adminEventVO?.name}</h1>

                <p>
                    ${adminEventVO?.description}
                </p>

                <p>
                    Fame Id : ${adminEventVO?.user?.fameId}  <g:link controller="admin" action="userProfile"
                                                                     class="btn btn-xs purple"
                                                                     params='[userId: "${adminEventVO?.user?.id}"]'>View User Profile <i
                            class="fa fa-user"></i></g:link>
                </p>

                <p>
                    Fame Name : ${adminEventVO?.user?.fameName}
                </p>

                <p>
                    Start Date : ${adminEventVO?.startTime?.format(AdminConstants.DATE_TIME_FORMAT)}
                </p>

                <p>
                    End Date :  ${adminEventVO?.endTime?.format(AdminConstants.DATE_TIME_FORMAT)}
                </p>

                <p>
                    Duration :  ${adminEventVO?.duration} Minutes
                </p>

                <p>
                    Genre :  ${adminEventVO?.genre?.name}
                </p>

            </div>
        </div>
    </div>
</div>