grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

//grails.plugin.location.'common' = "./modules/common"

String buildFile = System.getProperty('buildModule')
if (buildFile?.equals("admin")) {
    println "X~~~~~~~~~~~~~ BUILDING ADMIN MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'admin' = "./modules/admin"
} else if (buildFile?.equals("api")) {
    println "X~~~~~~~~~~~~~ BUILDING API MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'api' = "./modules/api"
} else if (buildFile?.equals("social")) {
    println "X~~~~~~~~~~~~~ BUILDING SOCIAL MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'social' = "./modules/social"
} else if (buildFile?.equals("common")) {
    println "X~~~~~~~~~~~~~ BUILDING COMMON MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'common' = "./modules/common"
} else if (buildFile?.equals("web")) {
    println "X~~~~~~~~~~~~~ BUILDING WEB MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'web' = "./modules/web"
} else if (buildFile?.equals("stream")) {
    println "X~~~~~~~~~~~~~ BUILDING STREAM MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'stream' = "./modules/stream"
} else if (buildFile?.equals("chat")) {
    println "X~~~~~~~~~~~~~ BUILDING CHAT MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'chat' = "./modules/chat"
} else {
    /*println "X~~~~~~~~~~~~~ BUILDING CHAT AND API MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'api' = "./modules/api"
    grails.plugin.location.'chat' = "./modules/chat"
    grails.plugin.location.'admin' = "./modules/admin"
    println "X~~~~~~~~~~~~~ BUILDING STREAM MODULE ~~~~~~~~~~~~~X"
    grails.plugin.location.'stream' = "./modules/stream"*/
}

//grails -DbuildModule=frontend -Dserver.port=8070 run-app
//if (Environment.current == Environment.PRODUCTION) {}
//grails.plugin.location.'api' = "./modules/api"
//grails.plugin.location.'admin' = "./modules/admin"
//grails.plugin.location.'common' = "./modules/common"

//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

        // configure settings for the test-app JVM, uses the daemon by default
        test   : false,//[maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
        // configure settings for the run-app JVM
        run    : false,//[maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
        // configure settings for the run-war JVM
        war    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
        // configure settings for the Console UI JVM
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false
    // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        runtime 'mysql:mysql-connector-java:5.1.29'
        // runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'
        test "org.grails:grails-datastore-test-support:1.0-grails-2.4"
        compile 'joda-time:joda-time:2.3'
        compile 'org.pac4j:pac4j-oauth:1.5.0'
    }

    plugins {
        build ":tomcat:7.0.55"
        compile ":codenarc:0.22"
        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.7'
        compile ":asset-pipeline:1.9.6"

        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.5.5" // or ":hibernate:3.6.10.17"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        compile ":console:1.4.5"
    }
}
