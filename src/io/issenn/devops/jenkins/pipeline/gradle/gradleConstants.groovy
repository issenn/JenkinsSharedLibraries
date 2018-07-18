#!/usr/bin/env groovy

package io.issenn.devops.jenkins.pipeline.gradleConstants

class gradleConstants implements Serializable {

    static gradle(String command) {
        sh "set +x && ./gradlew ${command}"
    }

    static gradle_version() {
        this.gradle '-v'
    }

}