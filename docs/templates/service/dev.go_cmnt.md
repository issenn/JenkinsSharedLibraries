| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| clear_user_info_count/project=gocmnt | [gocmntlogic clear user info 请求量降低请注意查看] | all(#1)<=80 | 3 | 5 |
| clear_user_info_count/project=gocmntdbd | [gocmntdbd clear user info 请求量降低请注意查看] | all(#1)<=80 | 3 | 5 |
| clear_user_info_count/project=gocmntagent | [gocmntagent 总请求量降低请注意查看] | all(#1)<=80 | 3 | 5 |
| get_user_info_count/project=gocmnt | [gocmntlogic 请求量降低请注意查看] | all(#1)<=400 | 3 | 5 |
| get_user_info_count/project=gocmntdbd | [gocmntdbd请求量降低请注意查看] | all(#1)<=100 | 3 | 5 |
| get_user_moment_info_redis_failed/project=gocmnt | [gocmntlogic cache miss 量增加 请注意查看] | all(#1)>=1000 | 3 | 1 |
| total_recv_req_count/project=gocmntdbd | [gocmntdbd 总请求量降低请注意查看] | all(#1)<=200 | 3 | 5 |

