package com.famelive

import grails.plugin.asyncmail.AsynchronousMailService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class TestController {
    AsynchronousMailService asynchronousMailService
//    def asynchronousMailService
    def launchPlayer() {
        String streamUrl = params.streamUrl
        return render(view: 'player', model: [streamUrl: streamUrl])
//        return render('hhhhhhhh')
    }

    def index() {
        Map header = ["X-MC-Template": "test-mail-template|main", "X-MC-MergeVars": '{"VERIFICATION_CODE":"abcdef12345","COMPANY":["Intelligrape Software","FameLive"],"FROM_ADDRESS":"nOIDA","FROM_NAME":"xyz aNIL aGRAWAL","RECIPIENT":"xyz CUSTOMER","SENDER":"ADMIN"}']

        asynchronousMailService.sendMail {
            to "anil.agrawal@intelligrape.com"
            from "admn.famelive@gmail.com"
//            cc "admn.famelive@gmail.com"
//            bcc "admn.famelive@gmail.com"
            subject "Hello famelive"
            body 'this is some text'
            headers header
        }
        /* SendMandrillMail.sendMail ({
             to "anil.agrawal@intelligrape.com"
             from "admn.famelive@gmail.com"
             cc "admn.famelive@gmail.com"
             bcc "admn.famelive@gmail.com"
             subject "Hello John"
             body 'this is some text'
         })*/

//        render 'mail sent 1133'+SendMandrillMail.sendMail()
        return render('mail sent 500')
    }
}
