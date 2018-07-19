| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| get_hide_uid_list_redis_failed/project=gomntlogic | [go-mnt Redis缓存失败升高请注意查看] | all(#3)>150 | 3 | 0 |
| get_his_mnt_count/project=gomntlogic | [go-mnt Redis缓存失败导致客户端重试请求量升高请注意查看] | all(#3)>6000 | 3 | 0 |
| get_his_mnt_proc_slow/project=gomntlogic | [go-mnt 查询历史贴文慢处理升高请注意查看] | all(#3)>200 | 3 | 0 |
| get_last_mid_proc_slow/project=gomntlogic | [go-mnt 获取最新的mid慢处理升高请注意查看] | all(#3)>600 | 3 | 0 |
| get_mnt_content_redis_failed/project=gomntlogic | [go-mnt 查询moment content cache miss 量升高 请注意查看] | all(#3)>18000 | 3 | 0 |
| get_mnt_creater_cache_miss/project=gomntlogic | [go-mnt 查询贴文创建者Redis缓存失败升高请注意查看] | all(#3)>20000 | 3 | 0 |
| get_not_share_list_redis_failed/project=gomntlogic | [go-mnt not share list Redis缓存失败升高请注意查看] | all(#3)>200 | 3 | 0 |
| get_teach_and_learn_lang_faild/project=gomntlogic | [go-mntloigic 访问UserCache失败 请立刻重启UserCache进程] | all(#1)>3 | 10 | 0 |
| total_recv_req_count/project=gomntlogic | [go-mnt 接收到的请求量暴增 请立即查看!!] | all(#3)>=17000 | 10 | 0 |
| total_recv_req_count/project=gomntlogic | [go-mnt 接收到的请求量下降 请立即查看!!] | all(#3)<500 | 10 | 0 |
| update_redis_failed/project=gomntdbd | [go-mnt dbd 更新redis失败 请立即查看!!] | all(#3)>30 | 10 | 0 |

