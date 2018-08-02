#!/usr/bin/env groovy

def call(Closure body={}) {
    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def XCODE_PROJECT_FILENAME
    def XCODE_PROJECT_PATH
    def XCODE_WORKSPACE_FILENAME
    def XCODE_WORKSPACE_PATH
    def XCODE_PROVISIONING_PROFILE_UUID
    def XCODE_PROVISIONINGPROFILES


pipeline {
    agent {
        label 'mac-mini'
    }

    environment {
        XCODE_NAME = 'Xcode latest(9.4.1)'
        XCODE_CONFIGURATION = "DailyBuild"
        XCODE_SDK = "iphoneos"
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
                    REPO_NAME = repo_name()
                    if (fileExists("${WORKSPACE}/${REPO_NAME}/${REPO_NAME}.xcworkspace")) {
                        XCODE_WORKSPACE_FILENAME = "${REPO_NAME}"
                        XCODE_WORKSPACE_PATH = "${XCODE_WORKSPACE_FILENAME}"
                        XCODE_SCHEME = "${XCODE_WORKSPACE_FILENAME}_DailyBuildScheme"
                        XCODE_PROJECT_FILENAME = ""
                        XCODE_PROJECT_PATH = "${XCODE_PROJECT_FILENAME}"
                    } else {
                        XCODE_WORKSPACE_FILENAME = ""
                        XCODE_WORKSPACE_PATH = "${XCODE_WORKSPACE_FILENAME}"
                        if (fileExists("${WORKSPACE}/${REPO_NAME}/${REPO_NAME}.xcodeproj")) {
                            XCODE_PROJECT_FILENAME = "${REPO_NAME}"
                            XCODE_PROJECT_PATH = "${XCODE_PROJECT_FILENAME}"
                            XCODE_SCHEME = "${XCODE_PROJECT_FILENAME}_DailyBuildScheme"
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

                    XCODE_PROVISIONINGPROFILES = install_provisioning_profile("${WORKSPACE}/PackageConfig")
                }

                /*
                echo "XCODE_WORKSPACE_FILENAME: ${XCODE_WORKSPACE_FILENAME}"
                echo "XCODE_WORKSPACE_PATH: ${XCODE_WORKSPACE_PATH}"
                echo "XCODE_PROJECT_FILENAME: ${XCODE_PROJECT_FILENAME}"
                echo "XCODE_PROJECT_PATH: ${XCODE_PROJECT_PATH}"
                echo "XCODE_PROVISIONING_PROFILE_UUID: ${XCODE_PROVISIONING_PROFILE_UUID}"
                echo "XCODE_DEVELOPMENT_TEAM_ID: ${XCODE_DEVELOPMENT_TEAM_ID}"
                echo "XCODE_PROVISIONING_PROFILE_APPID: ${XCODE_PROVISIONING_PROFILE_APPID}"
                echo "XCODE_PLATFORM: ${XCODE_PLATFORM}"
                */
            }
        }

        stage('Archive') {
            steps {

                // sh 'security unlock-keychain -p hellotalk /Users/mac/Library/Keychains/login.keychain-db'
                // sh 'security lock-keychain /Users/mac/Library/Keychains/login.keychain-db'
                // importDeveloperProfile 'f58c14ef-92ff-4a2a-844e-0bc1dfd4d0b7'
                // sh 'xcodebuild clean -project test.xcodeproj -scheme test -configuration DailyBuild'
                // sh 'xcodebuild archive -project test.xcodeproj -scheme test -configuration DailyBuild -sdk iphoneos -destination generic/platform=iOS -archivePath build/Archives/DailyBuild-iphoneos/test.xcarchive -derivedDataPath build/DerivedData/test'
                // sh 'xcodebuild -exportArchive -archivePath build/Archives/DailyBuild-iphoneos/test.xcarchive -exportPath build/IPA/DailyBuild-iphoneos/test.ipa -exportOptionsPlist ExportOptions.plist -sdk iphoneos'

                dir("${XCODE_WORKSPACE_PATH}") {
                    sh '/usr/local/bin/pod repo update && /usr/local/bin/pod install --verbose --no-repo-update'
                }

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

        stage('Test') {
            steps {
                sh 'echo "pass"'
            }
        }

        stage('artifacts') {
            steps {
                stash name: "stash-ipa", includes: "build/IPA/${XCODE_CONFIGURATION}-${XCODE_SDK}/*.ipa"
            }
        }

        stage('Deploy') {
            agent {
                label 'master'
            }

            steps {

                unstash "stash-ipa"

                sh "mv ${WORKSPACE}/build/IPA/${XCODE_CONFIGURATION}-${XCODE_SDK}/*.ipa /var/www/nginx/html/testing.hellotalk.com/ios/package/test-1.0-1.ipa"
            }
        }
    }
}
}
