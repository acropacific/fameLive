<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>Create Event</title>
</head>

<body>
<g:uploadForm controller="event" action="save" class="form-horizontal">
    <div class="form-body">
        <h3 class="form-section">Create Event</h3>

        <div class="form-group">
            <label class="col-md-3 control-label">Event Name</label>

            <div class="col-md-4">
                <g:textField name="name" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Event Description</label>

            <div class="col-md-4">
                <g:textArea name="description" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Start Date Time</label>

            <div class="col-md-3">
                <div class="input-group input-medium date date-picker" data-date-format="dd/mm/yyyy"
                     data-date-start-date="+0d">
                    <g:textField name="startDate" class="form-control" readonly="readonly"/>
                    <span class="input-group-btn">
                        <button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
                    </span>
                </div>
            </div>

            <div class="col-md-3">
                <div class="input-group">
                    <g:textField name="startTime" class="form-control timepicker timepicker-24-no-seconds"
                                 readonly="readonly"/>
                    <span class="input-group-btn">
                        <button class="btn default" type="button"><i class="fa fa-clock-o"></i></button>
                    </span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Duration</label>

            <div class="col-md-4">
                <g:select from="${adminCreateEventDto?.duration}" optionKey="" name="duration" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Genre</label>

            <div class="col-md-4">
                <g:select from="${adminCreateEventDto?.adminFetchGenreDto?.genreDtos}" optionKey="id" optionValue="name"
                          name="genreId" class="form-control" noSelection="['': '-Select Genre-']"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Users</label>

            <div class="col-md-4">
                <g:select from="${adminCreateEventDto?.userDetails?.profileDtoList}" optionKey="id"
                          optionValue="fameName"
                          name="userId" class="form-control" noSelection="['': '-Select User-']"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Event Image</label>

            <div class="col-md-4">
                <input type="file" name="imageName" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-7">
                <div class="pull-right">
                    <g:link controller="event" action="list" class="btn yellow">Back</g:link>
                    <g:submitButton name="Create" class="btn green"/>
                </div>
            </div>
        </div>
    </div>
</g:uploadForm>
</body>
</html>