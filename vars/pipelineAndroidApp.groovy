#!/usr/bin/env groovy

def call(Closure body={}) {

    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent {
            label 'mac-mini'
        }

        environment {
            ANDROID_SDK_ROOT="${HOME}/Library/Android/sdk"
            ANDROID_HOME="${ANDROID_SDK_ROOT}"
        }

        stages {
            stage('Checkout') {
                steps {
                    checkoutGitlab()
                }
            }

            stage('Prepare') {
                steps {
                    script {
                        //gradle '-v'
                        gradle.version()

                        //def environment = new io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants(this)
                        //println(environment.repoName(this))
                    }
                }
            }

            stage('Build snapshot - feature/*') {
                when {
                    branch "feature/*"
                }
                steps {
                    buildFeatureBranch()
                }
            }

            stage('Build snapshot - develop') {
                when {
                    branch "develop"
                }
                steps {
                    buildDevelopBranch()
                }
            }

            stage('Build snapshot - release/*') {
                when {
                    branch "release/*"
                }
                steps {
                    buildReleaseBranch()
                }
            }

            stage('Build @ Prod - master') {
                when {
                    branch "master"
                }
                steps {
                    buildMasterBranch()
                }
            }

            stage('Build snapshot - hotfix/*') {
                when {
                    branch "hotfix/*"
                }
                steps {
                    buildHotfixBranch()
                }
            }

            stage('Build - error') {
                when {
                    not {
                        anyOf {
                            branch "feature/*"
                            branch "develop"
                            branch "release/*"
                            branch "master"
                            branch "hotfix/*"
                        }
                    }
                }
                steps {
                    error "Don't know what to do with this branch: ${env.BRANCH_NAME}"
                }
            }

            stage('artifacts') {
                steps {
                    echo "artifacts"
                }
            }

            stage('Deploy snapshot - feature/*') {
                when {
                    branch "feature/*"
                }
                steps {
                    deployHotfixBranch()
                }
            }

            stage('Test') {
                steps {
                    echo "Test"
                }
            }

        }
    }
}

/**
 * feature/* for feature branches; merge back into develop
 * develop for ongoing development work
 * test/*
 * release/* to prepare production releases; merge back into develop and tag master
 * master for production-ready releases
 * hotfix/* to patch master quickly; merge back into develop and tag master
 */

def buildFeatureBranch() {
    echo "Feature branch"
}

def buildDevelopBranch() {
    echo "Develop branch"
    test()
    build('release')
    // sonar()
    // javadoc()
    deploy(env.JBOSS_TST)
}

def buildReleaseBranch() {
    echo "Release branch"
}

def buildMasterBranch() {
    echo "Master branch"
}

def buildHotfixBranch() {
    echo "Hotfix branch"
}

def test(String buildTypes='') {
    echo "test"
    // "clean test"
}

def build(String buildTypes='') {
    echo "build"
    gradle "clean assemble${buildTypes}"
}

def deploy() {
    echo "deploy"
}