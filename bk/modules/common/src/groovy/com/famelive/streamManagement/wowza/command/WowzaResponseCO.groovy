package com.famelive.streamManagement.wowza.command

import com.famelive.streamManagement.command.ResponseCO

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

/**
 * Created by anil on 27/10/14.
 */

@XmlAccessorType(XmlAccessType.NONE)
abstract class WowzaResponseCO extends ResponseCO {
    String restURI
}
