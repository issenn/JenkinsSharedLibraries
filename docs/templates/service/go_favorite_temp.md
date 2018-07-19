| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| change_fav_count_in_db_failed/project=gofav | [go-favorite 更新DB中的计数失败请查看] | all(#1)>=5 | 3 | 5 |
| fav_rest_fav_count_failed/project=gofav | [go-favorite 重置Redis中的计数失败请查看] | all(#3)>=3 | 3 | 1 |
| favorite_recv_req/project=gofav | [go-favorite 进程接受的请求量掉零] | all(#10)<0 | 3 | 1 |
| mongodb_error_count/project=gofav | [go-favorite 进程访问mogon出错请立即查看] | all(#3)>=3 | 3 | 1 |
| reset_fav_count_in_db_failed/project=gofav | [go-favorite 重置DB中的计数失败请查看] | all(#1)>=5 | 3 | 5 |

