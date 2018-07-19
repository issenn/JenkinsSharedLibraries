| metric/tags | note | condition | max | P |
| - | - | - | - | - | - |
| proxy_push_gcm_queue/project=push | [moment相关推送 gcm请求量过低 请查看] | all(#10)<0 | 3 | 5 |
| proxy_push_ios_queue/project=push | [moment相关推送 iOS 请求量过低 请查看] | all(#10)<0 | 3 | 5 |
| proxy_push_pigeon_queue/project=push | [moment相关推送 xinge请求量过低 请查看] | all(#20)<0 | 3 | 5 |
| proxy_query_pushsetting_fail/project=push | [moment相关推送失败超过阈值] | all(#3)>=2 | 3 | 1 |

