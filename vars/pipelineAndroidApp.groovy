#!/usr/bin/env groovy

def call(Closure body={}) {

    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent {
            node {
                label 'mac-mini'
                customWorkspace "workspace/${JOB_NAME}"
            }
        }

        environment {
            ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
            ANDROID_HOME = "${ANDROID_SDK_ROOT}"
            UNITTESTING = 'false'
            DebugBuildTypes = "Debug"
            ReleaseBuildTypes = "Release"
            ChinaProductFlavors = "China"
            GoogleProductFlavors = "Google"
            HTPrivateProductFlavors = "HTPrivate"
            //-PBUILD_NUMBER=${env.BUILD_NUMBER}
        }

        stages {
            stage('Branch and Tag - error') {
                when {
                    beforeAgent true
                    not {
                        anyOf {
                            branch "feature/*"
                            branch "develop"
                            //branch "test"
                            branch "release/*"
                            branch "master"
                            branch "hotfix/*"
                            buildingTag()
                        }
                    }
                }
                steps {
                    error "Don't know what to do with this branch or tag: ${env.BRANCH_NAME}"
                }
            }

            stage('Checkout SCM') {
                steps {
                    script {
                        def environment = new io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants(this)
                        //println(environment.repoName(this))
                        println(environment.BRANCH_NAME)
                        println(environment.JOB_NAME)
                    }
                    checkoutGitlab()
                }
            }

            stage('Prepare') {
                steps {
                    script {
                        //gradle '-v'
                        gradle.version()
                    }
                }
            }

            stage('Build snapshot - feature/*') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }
                steps {
                    buildFeatureBranch()
                }
            }

            stage('Build snapshot - develop') {
                when {
                    beforeAgent true
                    branch "develop"
                }
                failFast false
                parallel {
                    stage('china flavor - develop') {
                        stages {
                            stage('Unit Testing - develop') {
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(ReleaseBuildTypes, ChinaProductFlavors)
                                }
                            }
                            stage('Build - develop') {
                                steps {
                                    buildDevelopBranch(ReleaseBuildTypes, ChinaProductFlavors)
                                }
                            }
                            stage('artifacts - develop') {
                                steps {
                                    artifacts("${ChinaProductFlavors}${ReleaseBuildTypes}", "HelloTalk/build/outputs/apk/${ChinaProductFlavors}/${ReleaseBuildTypes}/HelloTalk-${ChinaProductFlavors}-${ReleaseBuildTypes}.apk")
                                }
                            }
                            stage('Deploy snapshot - develop') {
                                agent {
                                    label 'master'
                                }
                                steps {
                                    deployDevelopBranch("${ChinaProductFlavors}${ReleaseBuildTypes}", "HelloTalk/build/outputs/apk/${ChinaProductFlavors}/${ReleaseBuildTypes}/HelloTalk-${ChinaProductFlavors}-${ReleaseBuildTypes}.apk")
                                }
                            }
                            stage('Testing - develop') {
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }/*
                    stage('google flavor - feature/*') {
                        stages {
                            stage('') {
                                steps {
                                    buildDevelopBranch()
                                }
                            }
                        }
                    }*/
                }
            }

/*
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
            // artifacts
            stage('artifacts') {
                steps {
                    echo "artifacts"
                }
            }
            // Deploy
            stage('Deploy snapshot - feature/*') {
                when {
                    branch "feature/*"
                }
                steps {
                    deployFeatureBranch()
                }
            }

            stage('Deploy snapshot - develop') {
                when {
                    branch "develop"
                }
                steps {
                    deployDevelopBranch()
                }
            }

            stage('Deploy snapshot - release/*') {
                when {
                    branch "release/*"
                }
                steps {
                    deployReleaseBranch()
                }
            }

            stage('Deploy @ Prod - master') {
                when {
                    branch "master"
                }
                steps {
                    deployMasterBranch()
                }
            }

            stage('Deploy snapshot - hotfix/*') {
                when {
                    branch "hotfix/*"
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
*/

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

def unittestFeatureBranch() {
    echo "Feature branch - Unit Testing"
    //unittest(buildTypes, flavor)
}

def unittestDevelopBranch(String buildTypes='', String flavor='') {
    echo "Develop branch - Unit Testing"
    args = ((flavor ?: '') + (buildTypes ?: '')) ? (((flavor ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}

def buildFeatureBranch() {
    echo "Feature branch"
}

def buildDevelopBranch(String buildTypes='', String flavor='') {
    echo "Develop branch - Build"
    args = (flavor ?: '') + (buildTypes ?: '')
    build(args)
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

def deployFeatureBranch() {
    echo "Feature branch"
}

def deployDevelopBranch(String name, String path) {
    echo "Develop branch"
    deploy(name, path)
}

def deployReleaseBranch() {
    echo "Feature branch"
}

def deployMasterBranch() {
    echo "Feature branch"
}

def deployHotfixBranch() {
    echo "Feature branch"
}

def unittest(String args='') {
    echo "Unit Testing"
    gradle "clean test${args}"
}

def build(String args='') {
    echo "Build"
    gradle "clean assemble${args}"
}

def artifacts(String name, String path) {
    stash name: "${name}", includes: "${path}"
}


def deploy(String name, String path) {
    echo "deploy"
    unstash "${name}"
    //sh "mv ${WORKSPACE}/build/IPA/${XCODE_CONFIGURATION}-${XCODE_SDK}/*.ipa /var/www/nginx/html/testing.hellotalk.com/test-1.0-1.ipa"
}