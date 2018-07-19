| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| apns_total_recv_req_count/project=push_producer | [go-push-system apns接受到的请求低于阈值] | all(#3)<=500 | 3 | 1 |
| apns_total_succ_req_count/project=push_producer | [go-push-system apns 成功push到MQ的请求低于阈值] | all(#3)<=500 | 3 | 1 |
| gcm_total_recv_req_count/project=push_producer | [go-push-system gcm接受到的请求低于阈值] | all(#3)<=500 | 3 | 1 |
| gcm_total_succ_req_count/project=push_producer | [go-push-system gcm成功push到MQ请求低于阈值] | all(#3)<=500 | 3 | 1 |
| get_mc_failed_load_from_db_count/project=push_producer | [go-push-system 查询memcache失败超过阈值] | all(#3)>=30 | 3 | 1 |
| get_mc_failed_load_from_db_failed_count/project=push_producer | [go-push-system 从mysql中加载推送设置失败超过阈值] | all(#3)>=10 | 3 | 1 |
| invalid_android_token_count/project=push_producer | [go-push-system 无效android token 量超过阈值] | all(#3)>=30 | 3 | 1 |
| invalid_apns_token_count/project=push_producer | [go-push-system 无效iOS token 量超过阈值] | all(#3)>=100 | 3 | 1 |
| null_token_count/project=push_producer | [go-push-system null token 量超过阈值] | all(#3)>=1000 | 3 | 1 |
| otal_push_success_count/project=xinge | [go-push-xinge 发送到XINGE成功量低于阈值] | all(#3)<=60 | 3 | 1 |
| total_push_failed_count/project=apns | [go-push-apns 发送到APNS失败量超过阈值] | all(#3)>=1500 | 3 | 1 |
| total_push_failed_count/project=gcm | [go-push-gcm 发送到GCM失败量超过阈值] | all(#3)>=2000 | 3 | 1 |
| total_push_failed_count/project=xinge | [go-push-xinge 发送到XINGE失败量超过阈值] | all(#5)>=500 | 3 | 1 |
| total_push_message_count/project=push_producer | [go-push-system 成功publish到MQ的请求低于阈值] | all(#3)<=1500 | 3 | 1 |
| total_push_succ_count/project=apns | [go-push-apns 发送到APNS成功量低于阈值] | all(#3)<=500 | 3 | 1 |
| total_push_succ_count/project=gcm | [go-push-gcm 发送到GCM成功量低于阈值] | all(#3)<=500 | 3 | 1 |
| total_recv_req_count/project=push_producer | [go-push-system 收到的请求低于阈值] | all(#3)<=1500 | 3 | 1 |
| total_recv_req_count/project=apns | [go-push-apns 收到的请求低于阈值] | all(#3)<=500 | 3 | 1 |
| total_recv_req_count/project=gcm | [go-push-gcm 收到的请求低于阈值] | all(#3)<=500 | 3 | 1 |
| total_recv_req_count/project=xinge | [go-push-xinge 收到的请求低于阈值] | all(#3)<=40 | 3 | 1 |
| xinge_total_recv_req_count/project=push_producer | [go-push-system xinge接受到的请求低于阈值] | all(#3)<=40 | 3 | 1 |
| xinge_total_succ_req_count/project=push_producer | [go-push-system xinge成功push到MQ请求低于阈值] | all(#3)<=40 | 3 | 1 |

