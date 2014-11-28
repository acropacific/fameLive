grails.plugin.location.'common' = "../common"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies

    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        /*mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"*/
        mavenRepo "http://repo.spring.io/milestone/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.27'
    }

    plugins {
        compile ":platform-core:1.0.0"
        build(":release:3.0.1",
                ":rest-client-builder:1.0.3") {
            export = false
        }
        compile ":spring-security-core:2.0-RC4"
        compile ":spring-security-cas:2.0-RC1"
    }
}
