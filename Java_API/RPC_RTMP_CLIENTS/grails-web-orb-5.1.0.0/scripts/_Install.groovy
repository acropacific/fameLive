Ant.copy(todir: "${basedir}/web-app/WEB-INF/flex", overwrite: false) {
    fileset(dir: "${pluginBasedir}/src/flex")
}

Ant.copy(todir: "${basedir}/web-app/weborbassets", overwrite: false) {
    fileset(dir: "${pluginBasedir}/src/weborbassets")
}

Ant.copy(todir: "${basedir}/web-app/console", overwrite: false) {
    fileset(dir: "${pluginBasedir}/src/console")
}

Ant.copy(todir: "${basedir}/web-app", overwrite: false, file:"${pluginBasedir}/src/weborbconsole.html") {
}