/**
 * Created by anil on 27/9/14.
 */

class APIUrlMappings {

    static mappings = {
        "/api/auth/login" {
            controller = "authorization"
            action = "login"
        }

        "/api/auth/logout" {
            controller = "authorization"
            action = "logout"
        }

        "/api/$controller/$action" {}

    }
}