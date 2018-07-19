| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| get_mnt_content_redis_failed/project=gomntlogic | [go-mnt 查询moment content cache miss 量升高 请注意查看] | all(#3)>400 | 3 | 0 |
| get_teach_and_learn_lang_faild/project=gomntlogic | [go-mnt 访问UserCache 出错 请立刻重启UserCache] | all(#1)>1 | 3 | 0 |
| get_user_info_count/project=gomntlogic | [go-mnt 查询用户moment信息请求量降低 请注意查看] | all(#3)<1700 | 3 | 0 |
| get_user_moment_info_redis_failed/project=gomntlogic | [go-mnt 查询用户moment cache miss 量增加 请注意查看] | all(#3)>1000 | 3 | 0 |
| mod_mnt_stat_inter_err/project=gomntlogic | [go-mnt 修改贴文状态错误量升高请注意查看] | all(#3)>1 | 3 | 0 |
| moment_lang_not_match/project=gomntdbd | [查询UserCache失败造成语言不匹配 立即重启UserCache] | all(#1)>=6 | 10 | 0 |
| nsq_mod_mnt_bucket_count/project=gomntlogic | [go-mnt nsq 修改贴文桶的状态请求量降低请查看] | all(#3)<4 | 3 | 0 |
| post_cmnt_req_count/project=gomntlogic | [go-mnt 发布评论数降低 请注意查看] | all(#3)<15 | 3 | 0 |
| post_like_req_count/project=gomntlogic | [go-mnt 点赞数降低 请注意查看] | all(#3)<60 | 3 | 0 |
| post_mnt_count/project=gomntlogic | [go-mnt 发布贴文量降低请注意查看] | all(#3)<9 | 3 | 0 |
| post_mnt_proc_slow/project=gomntlogic | [go-mnt 发布贴文慢处理量增多 请留意] | all(#3)>20 | 3 | 0 |
| query_index_mnt_count/project=gomntlogic | [go-mnt 查询特定贴文请求量降低] | all(#3)<10 | 3 | 0 |
| query_reveal_entry_count/project=gomntlogic | [go-mnt 查询用户入口请求量降低] | all(#3)<20 | 3 | 0 |
| restore_user_inter_err/project=gomntlogic | [go-mnt 恢复用户失败 请查看] | all(#3)>0 | 3 | 0 |
| total_recv_req_count/project=gomntlogic | [go-mnt 接收到的请求量下降 请立即查看!!] | all(#3)<2300 | 10 | 0 |
| update_redis_failed/project=gomntdbd | [go-mnt dbd 更新redis失败 请立即查看!!] | all(#3)>30 | 10 | 0 |

