| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| get_account_cache_inter_err/project=gocrlogic | [chat record logic 从dbd获取聊天计数内部错误] | all(#3)>=10 | 3 | 5 |
| get_chat_record_cache_miss/project=gocrlogic | [chat record logic cache miss 突增请注意！] | all(#3)>=500 | 3 | 5 |
| get_chat_record_count/project=gocrlogic | [chat record logic 请求量下降 请注意查看] | all(#3)<=3000 | 3 | 5 |
| get_chat_record_count/project=gocrdbd | [get chat record dbd 请求量突增 请注意查看] | all(#3)>=500 | 3 | 5 |
| inc_chat_record_count/project=gocrlogic | [increase chat record logic 请求量下降 请注意查看] | all(#3)<=300 | 3 | 5 |
| inc_chat_record_count/project=gocrdbd | [increase chat record dbd 请求量下降 请注意查看] | all(#3)<=300 | 3 | 5 |
| inc_chat_record_count/project=gocragent | [increase chat record 请求量降低 请注意查看] | all(#3)<=300 | 3 | 5 |
| inc_chat_record_failed/project=gocragent | [increase chat record failed 增加 请注意查看] | all(#3)>=10 | 3 | 5 |
| inc_chat_record_inter_err/project=gocrlogic | [chat record logic 增加聊天计数内部错误] | all(#3)>=10 | 3 | 5 |
| set_chat_record_failed/project=gocragent | [写redis出错 请注意查看] | all(#3)>=10 | 3 | 5 |
| total_recv_req_count/project=gocrlogic | [chat record logic 总请求量下降 请注意查看] | all(#3)<=3000 | 3 | 5 |
| total_recv_req_count/project=gocrdbd | [chat record dbd 总请求量下降 请注意查看] | all(#3)<=300 | 3 | 5 |
| total_recv_req_count/project=gocragent | [总得请求量降低 请注意查看] | all(#3)<=300 | 3 | 5 |
| update_db_chat_count_err/project=gocrdbd | [chat record dbd 写SSDB 出错 请立即查看] | all(#3)>=10 | 3 | 5 |

