#!/usr/bin/env groovy

package io.issenn
/*
class gradleConstants implements Serializable {

    static void gradle(String command) {
        sh "set +x && ./gradlew ${command}"
    }

    static void gradle_version() {
        gradle '-v'
    }

}
*/

class gradleConstants {
  static def mvn() {
    return "123"
  }
}