| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| mongodb_error_count/project=gomuc | [go-muc 连接mongo出现问题请立即查看] | all(#1)>=1 | 3 | 1 |
| total_recv_req_count/project=gomuc | [go-muc 请求量掉零请注意查看] | all(#3)<=30 | 3 | 1 |

