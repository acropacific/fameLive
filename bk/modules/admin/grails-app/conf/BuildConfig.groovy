grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.plugin.location.'common' = "../common"


grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

    repositories {
//        inherits true // Whether to inherit repository definitions from plugins

//        grailsPlugins()
//        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.spring.io/milestone/"
    }

    dependencies {
        compile 'com.cloudinary:cloudinary:1.0.14'
    }

    plugins {
        compile ":console:1.4.5"
        compile ":platform-core:1.0.0"
        build(":release:3.0.1",
                ":rest-client-builder:1.0.3") {
            export = false
        }
        compile ":spring-security-core:2.0-RC4"
        compile ":spring-security-cas:2.0-RC1"
    }
}
