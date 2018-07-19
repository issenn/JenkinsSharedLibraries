| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| get_location_req_count/project=golbs | [golbs 获取用户位置请求量降低] | all(#3)<=10 | 3 | 5 |
| recv_req_count/project=golbs | [golbs 总的请求量降低接近零值] | all(#3)<=30 | 3 | 0 |
| refersh_location_req_count/project=golbs | [golbs 刷新地理位置请求量降低] | all(#3)<=100 | 3 | 5 |
| update_location_req_count/project=golbs | [golbs 更新地位置信息降低] | all(#3)<=10 | 3 | 5 |

