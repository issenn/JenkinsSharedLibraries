| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| apns_reconnect/project=push | [apns重连次数过多] | all(#3)>=10 | 3 | 0 |
| apns_write_fail/project=push | [apns write重连后还是失败] | all(#3)>=10 | 3 | 0 |
| curl_gcm_fail/project=push | [gcm curl失败] | all(#3)>=5 | 3 | 0 |
| gcm_return_error/project=push | [gcm返回错误] | all(#3)>=5 | 3 | 0 |
| gcmsender_proc_push_error/project=push | [gcm push处理出错] | all(#3)>=10 | 3 | 0 |
| gcmsender_proc_push_slow/project=push | [gcm push处理慢] | all(#3)>=10 | 3 | 0 |
| gcmsender_proc_push_timeout/project=push | [gcm push处理超时] | all(#3)>=10 | 3 | 0 |
| iossender_proc_push_error/project=push | [ios push处理出错] | all(#3)>=10 | 3 | 0 |
| iossender_proc_push_slow/project=push | [ios push处理慢] | all(#3)>=10 | 3 | 0 |
| iossender_proc_push_timeout/project=push | [ios push处理超时] | all(#3)>=10 | 3 | 0 |
| pigeon_sender_proc_push_error/project=push | [信鸽push处理出错] | all(#3)>=5 | 3 | 0 
| pigeon_sender_proc_push_slow/project=push | [信鸽push处理慢] | all(#3)>=10 | 3 | 0 |
| pigeon_sender_proc_push_timeout/project=push | [信鸽push处理超时] | all(#3)>=5 | 3 | 0 |
| proxy_ios_queue_push_fail/project=push | [iospush入队失败] | all(#3)>=10 | 3 | 0 |
| proxy_pigeon_queue_push_fail/project=push | [信鸽push入队失败] | all(#3)>=10 | 3 | 0 |
| proxy_proc_push_slow/project=push | [push处理延时大] | all(#3)>=20 | 3 | 0 |
| proxy_proc_push_timeout/project=push | [处理push超时] | all(#3)>=10 | 3 | 0 |
| proxy_push_gcm_queue/project=push | [gcmpush入队失败] | all(#3)>=10 | 3 | 0 |
| proxy_query_badge_fail/project=push | [查badge失败] | all(#3)>=10 | 3 | 0 |
| proxy_query_online_fail/project=push | [查在线状态失败] | all(#3)>=10 | 3 | 0 |
| proxy_query_pushsetting_fail/project=push | [查pushsetting失败] | all(#3)>=10 | 3 | 0 |
| proxy_recv_push_req_cmd8027/project=push | [push请求量太大] | all(#3)>=5000 | 3 | 0 |
| proxy_uc_return_error/project=push | [usercache返回错误] | all(#3)>=20 | 3 | 0 |
| proxy_update_badge_fail/project=push | [更新badge失败] | all(#3)>=20 | 3 | 0 |
| proxy_username_null/project=push | [pushtoken为空] | all(#3)>=20 | 3 | 0 |
| reinit_when_connect_fail/project=push | [connect失败过多重新初始化] | all(#3)>=3 | 3 | 0 |
| reinit_when_ssl_connect_fail/project=push | [ssl重连失败过多重新初始化] | all(#3)>=3 | 3 | 0 |

