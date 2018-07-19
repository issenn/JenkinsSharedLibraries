| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| batch_get_muc_msg_count/project= gommlogic | [gommlogic 批量获取群消息请求量降低请注意查看] | all(#3)<=100 | 3 | 5 |
| batch_set_hashmap_count/project=gommagent | [gommagent 批量设置hash请求量降低请注意查看] | all(#3)<=20 | 3 | 5 |
| get_msg_index_cache_miss/project=gommlogic | [gommlogic get msg index cache miss 量升高请注意查看] | all(#1)>=7000 | 3 | 5 |
| get_muc_msg_index_count/project=gommdbd | [gommdbd 查询消息的请求量降低请注意查看] | all(#3)<=30 | 3 | 5 |
| hashmap_not_exist/project=gommagent | [gommagent hash not exist 升高 请注意查看] | all(#1)>=25000 | 3 | 5 |
| inc_user_msg_count_failed/project=gommdbd | [gommdb hinc失败量升高请立即查看] | all(#1)>=1 | 3 | 1 |
| reload_msg_index_count/project=gommagent | [gommagent reload msg indes 量升高请注意查看] | all(#1)>=50 | 3 | 5 |
| RSS/project=redis-server | [redis 内存超过限制，请立即查看] | all(#3)>=8300 | 3 | 5 |
| set_key_value_count/project=gommagent | [gommagent 设置的key请求量降低请注意查看] | all(#3)<=20 | 3 | 5 |
| set_key_value_failed/project=gommagent | [gommagent 设置key失败量升高请注意查看] | all(#1)>=10 | 3 | 5 |
| store_muc_msg_count/project=gommlogic | [gommlogic 存储群消息请求量降低请注意查看] | all(#3)<=20 | 3 | 5 |
| store_muc_msg_count/project=gommdbd | [gommdbd 存储群消息总请求量降低请注意查看] | all(#3)<=20 | 3 | 5 |
| store_muc_msg_index_count/project=gommlogic | [gommlogic 存储群消息索引量降低 请注意查看] | all(#3)<=20 | 3 | 5 |
| store_muc_msg_index_count/project=gommdbd | [gommdbd 存储群消息索引请求量降低请注意查看] | all(#3)<=20 | 3 | 5 |
| total_recv_req_count/project=gommlogic | [总的请求量降低 请注意查看] | all(#3)<=200 | 3 | 5 |
| total_recv_req_count/project=gommagent | [gommagent 总请求量降低请注意查看] | all(#3)<=30 | 3 | 5 |
| total_recv_req_count/project=gommdbd | [gommdbd 总请求量降低请注意查看] | all(#3)<=200 | 3 | 5 |
