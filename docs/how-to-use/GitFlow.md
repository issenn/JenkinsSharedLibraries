
### GitFlow

![architecture](./images/git-model@2x.png)

从 [Jenkinsfile_pipeline_library](http://gitlab.hellotalk.com/test-issenn/Jenkinsfile_pipeline_library) 获取Jenkinsfile，并提交到自己的代码仓库
```bash
git add Jenkinsfile
git commit -m 'Add Jenkinsfile'
git push
```

登录 [test-android](http://jenkins.hellotalk.com/job/test-android/) 启动构建任务