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

        "500"(view: '/error')

        "/" {
            if (System.getProperty('buildModule').equals("stream")) {
                controller = 'streamingAdmin'
                action = 'index'
            } else if (System.getProperty('buildModule').equals("admin")) {
                controller = 'admin'
                action = 'index'
            } else {
                "/index.gsp"
            }
        }
    }
}
