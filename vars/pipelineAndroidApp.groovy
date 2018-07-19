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

            stage('Build') {
                steps {
                    script {
                        if (env.BRANCH_NAME.startsWith('feature/')) {
                            pipelineAndroidApp.buildFeatureBranch()
                        } else if (env.BRANCH_NAME == 'develop') {
                            pipelineAndroidApp.buildDevelopBranch()
                        } else if (env.BRANCH_NAME == 'dev') {
                            pipelineAndroidApp.buildDevelopBranch()
                        } else if (env.BRANCH_NAME.startsWith('release/')) {
                            pipelineAndroidApp.buildReleaseBranch()
                        } else if (env.BRANCH_NAME == 'master') {
                            pipelineAndroidApp.buildMasterBranch()
                        } else if (env.BRANCH_NAME.startsWith('hotfix/')) {
                            pipelineAndroidApp.buildHotfixBranch()
                        } else {
                            error "Don't know what to do with this branch: ${env.BRANCH_NAME}"
                        }
                    }
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

void buildDevelopBranch() {
    echo "Develop branch"
    test()
    echo "---"
    pipelineAndroidApp.build(String buildTypes='release')
    // sonar()
    // javadoc()
    // deploy(env.JBOSS_TST)
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

void test() {
    echo "test"
}

void build(String buildTypes='') {
    echo "build"
    gradle "clean assemble${buildTypes}"
}

