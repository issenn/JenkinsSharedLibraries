package io.issenn.devops.jenkins.pipeline.gradle

class GradleConstants implements Serializable {

    def abc
    GradleConstants() {}
/*
    def gradle(String command) {
        sh "set +x && ./gradlew ${command}"
    }

    def gradle_version() {
        gradle '-v'
    }
*/
}
