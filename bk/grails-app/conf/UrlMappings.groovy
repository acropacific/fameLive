class UrlMappings {

    static mappings = {

        "/api/$id?"(parseRequest: true) {
            controller = 'api'
            action = 'index'
        }

        "/api/$controller/$action" {}

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        /*"/" {
            controller = "login"
            action = "index"

        }*/
        /*"/"(controller: 'login')*/
//        "/"(view: "/index")
        "500"(view: '/error')
    }
}
