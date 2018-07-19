| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| mongodb_error_count/project=gomoment | [go-moment 进程连接mongo出错请立即处理] | all(#1)>=1 | 3 | 1 |
| total_recv_req_count/project=gomoment | [go-moment 请求量降为零 请注意查看] | all(#3)<=400 | 3 | 5 |

