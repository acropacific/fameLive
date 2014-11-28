<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>Create Genre</title>
</head>

<body>
<g:form controller="genre" action="save" class="form-horizontal">
    <div class="form-body">
        <h3 class="form-section">Create Genre</h3>

        <div class="form-group">
            <label class="col-md-3 control-label">Name</label>

            <div class="col-md-4">
                <g:textField name="name" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 control-label">YouTube Channel Link</label>

            <div class="col-md-4">
                <g:textField name="youtubeLink" class="form-control"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-7">
                <div class="pull-right">
                    <g:link controller="genre" action="list" class="btn yellow">Back</g:link>
                    <g:submitButton name="Create" class="btn green"/>
                </div>
            </div>
        </div>
    </div>
</g:form>
</body>
</html>