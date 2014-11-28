<%@ page import="com.famelive.common.enums.slotmanagement.GenreStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title></title>
    <g:javascript src="jquery-1.9.1.js"/>
</head>

<body>
<div class="form-body">
    <div class="col-md-2 pull-right"><g:link controller="genre" action="create" class="btn green">
        Create Genre
        <i class="fa fa-pencil"></i>
    </g:link></div>

    <h3 class="form-section">Genres Details</h3>


    <div class="portlet box yellow">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>Genres
            </div>

            <div class="tools">
                <a href="javascript:;" class="collapse">
                </a>
            </div>
        </div>

        <div class="portlet-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Youtube Link</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <g:each in="${adminFetchGenreDto.genreDtos}" var="genre">
                        <tr>
                            <td>${genre.id}</td>
                            <td>${genre.name}</td>
                            <td>${genre?.youtubeLink}</td>
                            <td>${genre?.genreStatus?.value}</td>
                            <td><g:link controller="genre" action="edit"
                                        params='[genreId: "${genre?.id}"]' class="btn green">Update <i
                                        class="fa fa-edit"></i></g:link></td>
                            <td>
                                <g:if test="${genre.genreStatus == GenreStatus.PUBLISHED}">
                                    <g:link controller="genre" action="changeGenreStatus"
                                            class="unpublishGenre btn green"
                                            params='[genreId: "${genre?.id}", genreStatus: "${GenreStatus.UN_PUBLISHED}"]'>Un-Publish <i
                                            class="fa fa-file-o"></i></g:link>
                                </g:if>
                                <g:else>
                                    <g:link controller="genre" action="changeGenreStatus" class="publishGenre btn green"
                                            params='[genreId: "${genre?.id}", genreStatus: "${GenreStatus.PUBLISHED}"]'>Publish <i
                                            class="fa fa-file-text-o"></i></g:link>
                                </g:else>
                            </td>
                        </tr>
                    </g:each>
                </table>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    $(document).ready(function () {
        $(".unpublishGenre").click(function () {
            return confirm("Are you sure you want to publish the Genre?");
        })
        $(".publishGenre").click(function () {
            return confirm("Are you sure you want to Un-publish the Genre?");
        })
    })
</g:javascript>
</body>
</html>