package com.famelive.streamManagement.api.command

import com.famelive.streamManagement.command.RequestCO

/**
 * Created by anil on 27/10/14.
 */
class APILoginResponseCO extends APIResponseCO {
    APILoginResponseCO(InputStream inputStream, RequestCO requestCO) {
        super(inputStream, requestCO)
    }

    Map data
}
