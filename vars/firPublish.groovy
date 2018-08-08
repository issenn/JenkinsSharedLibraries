#!/usr/bin/env groovy

def call(String path) {
    script {
        sh "sudo /usr/local/bin/fir publish -T 9611b6a99d280463039cbb64b7eb24ca -c ${env.CHANGELOG} ${path}"
        // sh("sudo /usr/local/bin/fir publish -T db412ee63c68dcce0f9be05d0e40025c -c ${env.CHANGELOG} ${path}")
    }
}