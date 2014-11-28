<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title></title>
</head>

<body>
<g:form controller="genre" action="update" class="form-horizontal">
    <div class="form-body">
        <h3 class="form-section">Edit Genre</h3>


        <div class="form-group">
            <label class="col-md-3 control-label">Name</label>

            <div class="col-md-4">
                <g:textField name="name" class="form-control" value="${adminGenreDto?.name}"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 control-label">YouTube Channel Link</label>

            <div class="col-md-4">
                <g:textField name="youtubeLink" class="form-control" value="${adminGenreDto?.youtubeLink}"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-7">
                <div class="pull-right">
                    <g:hiddenField name="genreId" value="${adminGenreDto?.id}"/>
                    <g:link controller="genre" action="list" class="btn yellow">Back</g:link>
                    <g:submitButton name="Save" class="btn green"/>
                </div>
            </div>
        </div>
    </div>
</g:form>
</body>
</html>