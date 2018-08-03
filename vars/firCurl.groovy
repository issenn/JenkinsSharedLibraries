#!/usr/bin/env groovy

def call(String path, String bundleId, String type, String os) {
    script {
        api_token = "9611b6a99d280463039cbb64b7eb24ca"
        cmd1 = """curl -X "POST" "http://api.fir.im/apps" \
            -H "Content-Type: application/json" \
            -d "{\"type\":\"${os}\", \"bundle_id\":\"${bundleId}\", \"api_token\":\"${api_token}\"}" """
        cmd2 = """curl -F "key=db412ee63c68dcce0f9be05d0e40025c"              \
            -F "token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:TJUiJEdWnpgsptiQuUD7khx6PLY=:eyJzY29wZSI6InByby1hcHA6N2U2Nzc3NTI5OTg2YmNiNzQwN2MyNjdlODFhYjk4MmE0YjliNzY2YiIsImNhbGxiYWNrVXJsIjoiaHR0cDovL2FwaS5maXIuaW0vYXV0aC9xaW5pdS9jYWxsYmFjaz9wYXJlbnRfaWQ9NWI2MmFkYzY5NTlkNjk1MDNlM2Q3MjdhXHUwMDI2dGltZXN0YW1wPTE1MzMxOTQ1NjRcdTAwMjZzaWduPTNkZjcxXHUwMDI2dXNlcl9pZD01YjYyOWY4NjU0OGI3YTFhZGQ2YmYwZmYiLCJjYWxsYmFja0JvZHkiOiJrZXk9JChrZXkpXHUwMDI2ZXRhZz0kKGV0YWcpXHUwMDI2ZnNpemU9JChmc2l6ZSlcdTAwMjZmbmFtZT0kKGZuYW1lKVx1MDAyNm9yaWdpbj0kKHg6b3JpZ2luKVx1MDAyNm5hbWU9JCh4Om5hbWUpXHUwMDI2YnVpbGQ9JCh4OmJ1aWxkKVx1MDAyNnZlcnNpb249JCh4OnZlcnNpb24pXHUwMDI2aXNfdXNlX21xYz0kKHg6aXNfdXNlX21xYylcdTAwMjZjaGFuZ2Vsb2c9JCh4OmNoYW5nZWxvZylcdTAwMjZyZWxlYXNlX3R5cGU9JCh4OnJlbGVhc2VfdHlwZSlcdTAwMjZkaXN0cmlidXRpb25fbmFtZT0kKHg6ZGlzdHJpYnV0aW9uX25hbWUpXHUwMDI2c3VwcG9ydGVkX3BsYXRmb3JtPSQoeDpzdXBwb3J0ZWRfcGxhdGZvcm0pXHUwMDI2bWluaW11bV9vc192ZXJzaW9uPSQoeDptaW5pbXVtX29zX3ZlcnNpb24pXHUwMDI2dWlfcmVxdWlyZWRfZGV2aWNlX2NhcGFiaWxpdGllcz0kKHg6dWlfcmVxdWlyZWRfZGV2aWNlX2NhcGFiaWxpdGllcylcdTAwMjZ1aV9kZXZpY2VfZmFtaWx5PSQoeDp1aV9kZXZpY2VfZmFtaWx5KSIsImRlYWRsaW5lIjoxNTMzMTk4MTY0LCJ1cGhvc3RzIjpbImh0dHA6Ly91cC5xaW5pdS5jb20iLCJodHRwOi8vdXBsb2FkLnFpbml1LmNvbSIsIi1IIHVwLnFpbml1LmNvbSBodHRwOi8vMTgzLjEzMS43LjE4Il0sImdsb2JhbCI6ZmFsc2V9"             \
            -F "file=@${path}"            \
            -F "x:name=${bundleId}"             \
            -F "x:version=${versionName}"         \
            -F "x:build=${versionCode}"               \
            -F "x:release_type=${type}"   \
            -F "x:changelog=${env.CHANGELOG}"       \
            https://upload.qbox.me"""
        aaa = sh(returnStdout: true, script: "${cmd1}").trim()
        println(aaa)
        // bbb = sh(returnStdout: true, script: "${cmd2}").trim()
    }
}
