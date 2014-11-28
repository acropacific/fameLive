package com.famelive.streamManagement.wowza

import javax.xml.bind.annotation.*

/**
 * Created by anil on 27/10/14.
 */

@XmlRootElement(name = "Application")
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorType(XmlAccessType.NONE)

class Application {

    @XmlAttribute
    String id
    @XmlAttribute
    String href
    @XmlElement
    String appType
    @XmlElement
    boolean dvrEnabled
    @XmlElement
    boolean drmEnabled
    @XmlElement
    boolean transcoderEnabled
}
