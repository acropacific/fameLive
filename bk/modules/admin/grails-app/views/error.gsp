<!DOCTYPE html>
<html>
<head>
    <title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
    <meta name="layout" content="admin">
    <link href="./assets/css/pages/error.css" rel="stylesheet" type="text/css"/>
    <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
</head>

<body class="page-500-full-page">
<g:if env="development">
    <g:renderException exception="${exception}"/>
</g:if>
<g:else>
    <div class="row">
        <div class="col-md-12 page-500">
            <div class=" number">
                500
            </div>

            <div class=" details">
                <h3>Oops! Something went wrong.</h3>

                <p>
                    We are fixing it!<br/>
                    Please come back in a while.<br/><br/>
                </p>
            </div>
        </div>
    </div>
</g:else>
</body>
</html>
