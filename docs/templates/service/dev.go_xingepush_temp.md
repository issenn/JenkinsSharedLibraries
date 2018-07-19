| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| failed_req_count/project=goxinge | [xinge_push 请求腾讯云失败超过阈值 请注意查看] | all(#1)>=15 | 3 | 1 |
| success_req_count/project=goxinge | [xinge_push请求腾讯云成功量下降，请注意查看] | all(#1)<=10 | 3 | 1 |
| total_recv_req_count/project=goxinge | [xinge_push 接收到的请求量下降 请注意] | all(#1)<=10 | 3 | 1 |

