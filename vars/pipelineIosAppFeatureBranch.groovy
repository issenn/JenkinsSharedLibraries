#!/usr/bin/env groovy

def call(Closure body={}) {
    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def XCODE_PROJECT_FILENAME = ""
    def XCODE_PROJECT_PATH = ""
    def XCODE_WORKSPACE_FILENAME = ""
    def XCODE_WORKSPACE_PATH = ""
    def XCODE_PROVISIONING_PROFILE_UUID
    def XCODE_PROVISIONINGPROFILES
    def changeLogSets

    pipeline {
    agent {
        label 'mac-mini3'
    }

        options {
            skipDefaultCheckout()
        }

        triggers {
            pollSCM('H * * * *')
        }

        environment {
            //LANG = "C.UTF-8"
            //LC_ALL = "en_US.UTF-8"
            //LANGUAGE = "en_US.UTF-8"
            UNITTESTING_STATE = 'false'
            TESTING_STATE = 'false'
            //App = 'HelloTalk_Binary'
            //REPO_NAME = "${App}"
            XCODE_NAME = 'Xcode latest(9.4.1)'
            XCODE_CONFIGURATION = "Release"
            XCODE_SDK = "iphoneos"
        }

        stages {
            stage('Check Branch/Tag') {
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
                        }
                    }
                }

                steps {
                    error "Don't know what to do with this branch or tag: ${env.BRANCH_NAME}"
                }
            }

            stage('Checkout SCM') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }

                steps {
                    script {
                        def scmVars = checkoutGithub()
                        env.GIT_URL = scmVars.GIT_URL
                        env.GIT_COMMIT = scmVars.GIT_COMMIT
                        env.GIT_PREVIOUS_SUCCESSFUL_COMMIT = scmVars.GIT_PREVIOUS_SUCCESSFUL_COMMIT
                        println(changelog(env.GIT_PREVIOUS_SUCCESSFUL_COMMIT, env.GIT_COMMIT))
                    }
                }
            }

            stage('Prepare') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }

                steps {
                    dir("${XCODE_WORKSPACE_PATH}") {
                        sh '/usr/local/bin/pod repo update && /usr/local/bin/pod install --verbose --no-repo-update'
                    }
                    script {
                        // REPO_NAME = repo_name()
                        REPO_NAME = 'HelloTalk_Binary'
                        if (fileExists("${WORKSPACE}/${REPO_NAME}.xcworkspace")) {
                            XCODE_WORKSPACE_FILENAME = "${REPO_NAME}"
                            XCODE_WORKSPACE_PATH = ""
                            XCODE_SCHEME = "${XCODE_WORKSPACE_FILENAME}"
                            XCODE_PROJECT_FILENAME = ""
                            XCODE_PROJECT_PATH = "${XCODE_PROJECT_FILENAME}"
                        } else {
                            XCODE_WORKSPACE_FILENAME = ""
                            XCODE_WORKSPACE_PATH = "${XCODE_WORKSPACE_FILENAME}"
                            if (fileExists("${WORKSPACE}/${REPO_NAME}.xcodeproj")) {
                                XCODE_PROJECT_FILENAME = "${REPO_NAME}"
                                XCODE_PROJECT_PATH = ""
                                XCODE_SCHEME = "${XCODE_PROJECT_FILENAME}"
                            } else {
                                XCODE_PROJECT_FILENAME = ""
                                XCODE_PROJECT_PATH = "${XCODE_PROJECT_FILENAME}"
                                XCODE_SCHEME = ""
                                error "XCODE_PROJECT_FILENAME and XCODE_WORKSPACE_FILENAME is not set!";
                            }
                        }

                        XCODE_PROVISIONING_PROFILE_UUID = xcode_provisioning_profile_value([key: ":UUID", filename: "${WORKSPACE}/PackageConfig/${REPO_NAME}_AdHoc.mobileprovision"])
                        XCODE_DEVELOPMENT_TEAM_ID = xcode_provisioning_profile_value([key: ":TeamIdentifier:0", filename: "${WORKSPACE}/PackageConfig/${REPO_NAME}_AdHoc.mobileprovision"])
                        XCODE_PROVISIONING_PROFILE_APPID = xcode_provisioning_profile_value([key: ":Entitlements:application-identifier", filename: "${WORKSPACE}/PackageConfig/${REPO_NAME}_AdHoc.mobileprovision"]) - "${XCODE_DEVELOPMENT_TEAM_ID}."
                        XCODE_PLATFORM = xcode_provisioning_profile_value([key: ":Platform:0", filename: "${WORKSPACE}/PackageConfig/${REPO_NAME}_AdHoc.mobileprovision"])

                        XCODE_PROVISIONINGPROFILES = install_provisioning_profile("${WORKSPACE}/PackageConfig", XCODE_DEVELOPMENT_TEAM_ID)
                    }
                }
            }

            stage('Archive') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }

                steps {
                    xcodeBuild allowFailingBuildResults: false,
                        appURL: "",
                        assetPackManifestURL: "",
                        buildDir: "${WORKSPACE}/build/Archives/${XCODE_CONFIGURATION}-${XCODE_SDK}",
                        buildIpa: false,
                        bundleID: "",
                        bundleIDInfoPlistPath: "",
                        cfBundleShortVersionStringValue: "",
                        cfBundleVersionValue: "",
                        changeBundleID: false,
                        cleanBeforeBuild: true,
                        cleanTestReports: true,
                        compileBitcode: true,
                        configuration: "${XCODE_CONFIGURATION}",
                        developmentTeamID: "${XCODE_DEVELOPMENT_TEAM_ID}",
                        developmentTeamName: "",
                        displayImageURL: "",
                        embedOnDemandResourcesAssetPacksInBundle: true,
                        fullSizeImageURL: "",
                        generateArchive: true,
                        interpretTargetAsRegEx: false,
                        ipaExportMethod: "ad-hoc",
                        ipaName: "",
                        ipaOutputDirectory: "",
                        keychainName: "",
                        keychainPath: "${HOME}/Library/Keychains/login.keychain-db",
                        keychainPwd: 'hellotalk',
                        logfileOutputDirectory: "",
                        manualSigning: true,
                        noConsoleLog: false,
                        onDemandResourcesAssetPacksBaseURL: "",
                        provideApplicationVersion: false,
                        provisioningProfiles: [
                            [
                                provisioningProfileAppId: "${XCODE_PROVISIONINGPROFILES[0].get('provisioningProfileAppId')}",
                                provisioningProfileUUID: "${XCODE_PROVISIONINGPROFILES[0].get('provisioningProfileUUID')}"
                            ],
                            [
                                provisioningProfileAppId: "${XCODE_PROVISIONINGPROFILES[1].get('provisioningProfileAppId')}",
                                provisioningProfileUUID: "${XCODE_PROVISIONINGPROFILES[1].get('provisioningProfileUUID')}"
                            ],
                            [
                                provisioningProfileAppId: "${XCODE_PROVISIONINGPROFILES[2].get('provisioningProfileAppId')}",
                                provisioningProfileUUID: "${XCODE_PROVISIONINGPROFILES[2].get('provisioningProfileUUID')}"
                            ]
                        ],
                        sdk: "${XCODE_SDK}",
                        symRoot: "",
                        target: "",
                        thinning: "",
                        unlockKeychain: true,
                        uploadBitcode: true,
                        uploadSymbols: true,
                        xcodeName: "${XCODE_NAME}",
                        xcodeProjectFile: "${XCODE_PROJECT_FILENAME}",
                        xcodeProjectPath: "${XCODE_WORKSPACE_PATH}",
                        xcodeSchema: "${XCODE_SCHEME}",
                        xcodeWorkspaceFile: "${XCODE_WORKSPACE_FILENAME}",
                        xcodebuildArguments: "-derivedDataPath ${WORKSPACE}/build/DerivedData/${XCODE_SCHEME} -destination generic/platform=${XCODE_PLATFORM}"
                }
            }

            stage('Sign and Export .ipa') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }

                steps {
                    exportIpa symRoot: "",
                        appURL: '',
                        archiveDir: "${WORKSPACE}/build/Archives/${XCODE_CONFIGURATION}-${XCODE_SDK}",
                        assetPackManifestURL: '',
                        compileBitcode: true,
                        developmentTeamID: "${XCODE_DEVELOPMENT_TEAM_ID}",
                        developmentTeamName: '',
                        displayImageURL: '',
                        fullSizeImageURL: '',
                        ipaExportMethod: 'ad-hoc',
                        ipaName: "",
                        ipaOutputDirectory: "${WORKSPACE}/build/IPA/${XCODE_CONFIGURATION}-${XCODE_SDK}",
                        keychainName: '',
                        keychainPath: "${HOME}/Library/Keychains/login.keychain-db",
                        keychainPwd: 'hellotalk',
                        manualSigning: true,
                        packResourcesAsset: true,
                        provisioningProfiles: [
                            [
                                provisioningProfileAppId: "${XCODE_PROVISIONINGPROFILES[0].get('provisioningProfileAppId')}",
                                provisioningProfileUUID: "${XCODE_PROVISIONINGPROFILES[0].get('provisioningProfileUUID')}"
                            ],
                            [
                                provisioningProfileAppId: "${XCODE_PROVISIONINGPROFILES[1].get('provisioningProfileAppId')}",
                                provisioningProfileUUID: "${XCODE_PROVISIONINGPROFILES[1].get('provisioningProfileUUID')}"
                            ],
                            [
                                provisioningProfileAppId: "${XCODE_PROVISIONINGPROFILES[2].get('provisioningProfileAppId')}",
                                provisioningProfileUUID: "${XCODE_PROVISIONINGPROFILES[2].get('provisioningProfileUUID')}"
                            ]
                        ],
                        resourcesAssetURL: '',
                        thinning: '<none>',
                        unlockKeychain: false,
                        uploadBitcode: true,
                        uploadSymbols: true,
                        xcodeName: "${XCODE_NAME}",
                        xcodeProjectFile: "${XCODE_PROJECT_FILENAME}",
                        xcodeProjectPath: "${XCODE_WORKSPACE_PATH}",
                        xcodeSchema: "${XCODE_SCHEME}",
                        xcodeWorkspaceFile: "${XCODE_WORKSPACE_FILENAME}"
                }
            }

            stage('Deploy') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }

                steps {
                    firPublish("${WORKSPACE}/build/IPA/${XCODE_CONFIGURATION}-${XCODE_SDK}/*.ipa")
                }
            }
        }
    }
}
