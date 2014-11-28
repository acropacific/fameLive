import com.famelive.admin.util.AdminMessagesUtil

class AdminBootStrap {
    def grailsApplication

    def init = { servletContext ->
        new AdminMessagesUtil().initializeMessageSource(grailsApplication);
    }

    def destroy = {
    }
}