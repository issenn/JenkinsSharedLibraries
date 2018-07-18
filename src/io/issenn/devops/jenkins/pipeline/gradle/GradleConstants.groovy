package io.issenn.devops.jenkins.pipeline.gradle

/**
 * Constants for environment variables used by Pipeline scripts and by Jenkins
 */
class GradleConstants implements Serializable {

    def steps

    GradleConstants(steps) {
        this.steps = steps
    }

    def mvn(script) {
        script.sh "set +x && ./gradlew -v"
    }

    def gradle(def script, String command) {
        sh "set +x && ./gradlew ${command}"
    }

    def gradle_version() {
        gradle '-v'
    }
}