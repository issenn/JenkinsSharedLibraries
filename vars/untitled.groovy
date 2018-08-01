            stage('entry - release') {
                environment {
                    buildTypes = "release"
                }
                when {
                    beforeAgent true
                    branch "release"
                }
                failFast false
                parallel {
                    stage('entry - china flavor - release') {
                        environment {
                            productFlavors = "china"
                        }
                        when {
                            beforeAgent true
                            environment name: 'CHINAPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - china flavor - release') {
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
                            stage('Prepare - china flavor - release') {
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
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - china flavor - release') {
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
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - china flavor - release') {
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
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - china flavor - release') {
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
                                steps {
                                    buildReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - china flavor - release') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('entry - google flavor - release') {
                        environment {
                            productFlavors = "google"
                        }
                        when {
                            beforeAgent true
                            environment name: 'GOOGLEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - google flavor - release') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('entry - HTPrivate flavor - release') {
                        environment {
                            productFlavors = "HTPrivate"
                        }
                        when {
                            beforeAgent true
                            environment name: 'HTPRIVATEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                }
            }