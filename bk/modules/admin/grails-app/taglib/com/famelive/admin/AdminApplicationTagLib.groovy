package com.famelive.admin

class AdminApplicationTagLib {

    static namespace = "admin"
    def grailsApplication

    def displayEventImage = { attrs ->
        String imageName = attrs?.imageName
        Map map = grailsApplication.config.cloudinary.config
        String imageUrl = imageName ? map.baseUrl + "" + "/" + imageName + "." + map.mimeType : map.baseUrl + "" + map.folder + "/" + map.defaultEventImage
        out << "<img src='${imageUrl}' width='200px' height='200px' class='center-block img-thumbnail img-responsive'/>"
    }

    def displayUserImage = { attrs ->
        String imageName = attrs?.imageName
        Map map = grailsApplication.config.cloudinary.config
        String imageUrl = imageName ? map.baseUrl + "" + "/" + imageName + "." + map.mimeType : map.baseUrl + "" + map.folder + "/" + map.defaultUserImage
        out << "<img src='${imageUrl}' width='200px' height='200px' class='center-block img-thumbnail img-responsive'/>"
    }

    def displayFollowerImage = { attrs ->
        String imageName = attrs?.imageName
        Map map = grailsApplication.config.cloudinary.config
        String imageUrl = imageName ? map.baseUrl + "" + "/" + imageName + "." + map.mimeType : map.baseUrl + "" + map.folder + "/" + map.defaultUserImage
        out << "<img src='${imageUrl}' class='img-responsive' style='height: 140px !important;width: 142px !important;'/>"
    }
}
