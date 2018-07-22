#!/usr/bin/env groovy

import org.apache.commons.lang3.StringUtils

def call(Closure body={}) {

    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent none

        options {
            skipDefaultCheckout()
        }

        triggers {
            pollSCM('H */4 * * 1-5')
        }

        environment {
            LANG = "C.UTF-8"
            LC_ALL = "en_US.UTF-8"
            LANGUAGE = "en_US.UTF-8"
            ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
            ANDROID_HOME = "${ANDROID_SDK_ROOT}"
            UNITTESTING_STATE = 'false'
            App = "HelloTalk"
            releaseBuildTypes = "release"
            debugBuildTypes = "debug"
            CHINAPRODUCTFLAVORS_STATE = 'true'
            chinaProductFlavors = "china"
            GOOGLEPRODUCTFLAVORS_STATE = 'true'
            googleProductFlavors = "google"
            HTPRIVATEPRODUCTFLAVORS_STATE = 'true'
            HTPrivateProductFlavors = "HTPrivate"
            //-PBUILD_NUMBER=${env.BUILD_NUMBER}
        }

        stages {
            stage('Branch and Tag - error') {
                agent {
                    node {
                        label 'master'
                        customWorkspace "workspace/${JOB_NAME}"
                    }
                }
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
/*
            stage('Checkout SCM') {
                agent {
                    node {
                        label 'mac-mini'
                        customWorkspace "workspace/${JOB_NAME}"
                    }
                }
                steps {
                    script {
                        def environment = new io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants(this)
                        //println(environment.repoName(this))
                        //println(environment.BRANCH_NAME)
                        //println(environment.JOB_NAME)
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

            stage('clean') {
                steps {
                    clean()
                }
            }
*/
            stage('Build entry - feature/*') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }
                steps {
                    buildFeatureBranch()
                }
            }

            stage('Build entry - develop') {
                when {
                    beforeAgent true
                    branch "develop"
                }
                failFast false
                parallel {
                    stage('china flavor - develop') {
                        agent {
                            node {
                                label 'mac-mini1'
                                customWorkspace "workspace/${JOB_NAME}"
                            }
                        }
                        environment {
                            ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                            ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                        }
                        when {
                            beforeAgent true
                            environment name: 'CHINAPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    clean()
                                }
                            }/*
                            stage('Unit Testing - china flavor - develop') {
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(releaseBuildTypes, chinaProductFlavors)
                                }
                            }
                            stage('Build - china flavor - develop') {
                                steps {
                                    buildDevelopBranch(releaseBuildTypes, chinaProductFlavors)
                                }
                            }
                            stage('Artifacts - china flavor - develop') {
                                steps {
                                    artifactsDevelopBranch(releaseBuildTypes, chinaProductFlavors)
                                }
                            }
                            stage('Deploy - china flavor - develop') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployDevelopBranch(releaseBuildTypes, chinaProductFlavors)
                                }
                            }
                            stage('Testing - china flavor - develop') {
                                steps {
                                    echo "Test"
                                }
                            }*/
                        }
                    }/*
                    stage('google flavor - develop') {
                        when {
                            beforeAgent true
                            environment name: 'GOOGLEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Unit Testing - google flavor - develop') {
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(releaseBuildTypes, googleProductFlavors)
                                }
                            }
                            stage('Build - google flavor - develop') {
                                steps {
                                    buildDevelopBranch(releaseBuildTypes, googleProductFlavors)
                                }
                            }
                            stage('Artifacts - google flavor - develop') {
                                steps {
                                    artifactsDevelopBranch(releaseBuildTypes, googleProductFlavors)
                                }
                            }
                            stage('Deploy - google flavor - develop') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployDevelopBranch(releaseBuildTypes, googleProductFlavors)
                                }
                            }
                            stage('Testing - google flavor - develop') {
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('HTPrivate flavor - develop') {
                        when {
                            beforeAgent true
                            environment name: 'HTPRIVATEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Unit Testing - HTPrivate flavor - develop') {
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(releaseBuildTypes, HTPrivateProductFlavors)
                                }
                            }
                            stage('Build - HTPrivate flavor - develop') {
                                steps {
                                    buildDevelopBranch(releaseBuildTypes, HTPrivateProductFlavors)
                                }
                            }
                            stage('Artifacts - HTPrivate flavor - develop') {
                                steps {
                                    artifactsDevelopBranch(releaseBuildTypes, HTPrivateProductFlavors)
                                }
                            }
                            stage('Deploy - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployDevelopBranch(releaseBuildTypes, HTPrivateProductFlavors)
                                }
                            }
                            stage('Testing - HTPrivate flavor - develop') {
                                steps {
                                    echo "Test"
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

def changeStringStyle(String str, boolean toCamel) {
    if(!str || str.size() <= 1)
        return str

    if(toCamel){
        String r = str.toLowerCase().split('_').collect{cc -> StringUtils.capitalize(cc)}.join('')
        return r
    } else {
        str = str[0].toLowerCase() + str[1..-1]
        return str.collect{cc -> ((char)cc).isUpperCase() ? '_' + cc.toLowerCase() : cc}.join('')
    }
}

def changeStringGradleStyle(String str) {
    if(!str || str.size() <= 1)
        return str

    return str[0].toUpperCase() + str[1..-1]
}

/**
 * feature/* for feature branches; merge back into develop
 * develop for ongoing development work
 * test/*
 * release/* to prepare production releases; merge back into develop and tag master
 * master for production-ready releases
 * hotfix/* to patch master quickly; merge back into develop and tag master
 */

def unittestFeatureBranch(String buildTypes='', String productFlavors='') {
    echo "Feature branch - Unit Testing"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}

def unittestDevelopBranch(String buildTypes='', String productFlavors='') {
    echo "Develop branch - Unit Testing"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}

def buildFeatureBranch() {
    echo "Feature branch"
}

def buildDevelopBranch(String buildTypes='', String productFlavors='') {
    echo "Develop branch - Build"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = (productFlavors ?: '') + (buildTypes ?: '')
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

def artifactsDevelopBranch(String buildTypes = '', String productFlavors = '') {
    echo "Develop branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    artifacts(name, path)
}


def deployFeatureBranch() {
    echo "Feature branch"
}

def deployDevelopBranch(String buildTypes = '', String productFlavors = '') {
    echo "Develop branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
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

def clean() {
    echo "Clean"
    gradle "clean"
}

def unittest(String args='') {
    echo "Unit Testing"
    gradle "test${args}"
}

def build(String args='') {
    echo "Build"
    gradle "assemble${args}"
}

def artifacts(String name, String path) {
    echo "stash '${name}' '${path}'"
    stash name: "${name}", includes: "${path}"
}

def deploy(String name, String path) {
    echo "unstash '${name}' '${path}'"
    unstash "${name}"
    //sh "mv ${WORKSPACE}/build/IPA/${XCODE_CONFIGURATION}-${XCODE_SDK}/*.ipa /var/www/nginx/html/testing.hellotalk.com/test-1.0-1.ipa"
}