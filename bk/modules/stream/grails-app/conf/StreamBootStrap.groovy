import com.famelive.streamManagement.StreamBootStrapService
import com.famelive.streamManagement.util.StreamMessagesUtil
import grails.plugin.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Created by anil on 16/10/14.
 */

class StreamBootStrap {
    StreamBootStrapService streamBootStrapService
    GrailsApplication grailsApplication
    def init = {
        new StreamMessagesUtil().initializeMessageSource(grailsApplication)
        //SpringSecurityUtils.clientRegisterFilter('streamCommonFilter', 0)
        SpringSecurityUtils.clientRegisterFilter('streamingSecurityFilter', 0)

        if (true) {
            println "inside streamManagement bootStrap"
        }
//        streamBootStrapService.bootStrapDummyEventData()
    }
    def destroy = {}
}
