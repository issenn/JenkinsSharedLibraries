#!/usr/bin/groovy

package io.issenn.jenkins.utils

import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def installHomebrew() {
    def command = """
    /usr/bin/ruby -e "\$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    """
    sh(returnStdout: true, script: command)
}

@NonCPS
def installMetarunnerOnHomebrew(String metarunner){
    // sh(script: "brew install ${metarunner}")
    sh(script: "brew bundle --file=Brewfile.rbenv")
}

@NonCPS
def installMetarunner(String metarunner){
    sh """ 
  git clone https://github.com/${metarunner}/${metarunner}.git ${HOME}/.${metarunner}
  cd ${HOME}/.${metarunner}
  src/configure --without-ssl && make -C src
  """
}

@NonCPS
def installVersion(String metarunner, String version, String configure_opts=null) {
    if (configure_opts) {
        sh "CONFIGURE_OPTS=\"${configure_opts}\" ${metarunner} install ${version}"
    } else {
        sh "${metarunner} install ${version}"
    }
}

@NonCPS
def deleteVersion(String metarunner, String version) {
    sh "${metarunner} uninstall -f ${version}"
    // File directory = new File("${HOME}/.${metarunner}/versions/${version}")
    // directory.deleteDir()
}

@NonCPS
def purgeAllVersions(String metarunner) {
    File directory = new File("${HOME}/.${metarunner}/versions/")

    directory.listFiles().each{
        it.deleteDir()
    }
}
