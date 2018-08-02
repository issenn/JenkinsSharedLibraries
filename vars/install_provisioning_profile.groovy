#!/usr/bin/env groovy

def call(path) {
    list = []
    for (file in xcode_provisioning_profile_file("${path}")) {
        uuid = xcode_provisioning_profile_value([key: ":UUID", filename: "${file}"])
        appid = xcode_provisioning_profile_value([key: ":Entitlements:application-identifier", filename: "${file}"]) - "${XCODE_DEVELOPMENT_TEAM_ID}."
        script {
            echo "${uuid}"
            sh "cp -f ${file} ${HOME}/Library/MobileDevice/'Provisioning Profiles'/${uuid}.mobileprovision"
        }
        list += [provisioningProfileAppId: "${appid}", provisioningProfileUUID: "${uuid}"]
    }
    return list
}