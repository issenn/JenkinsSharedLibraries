| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| pushserver_xinge_s2s_failed_count/project=IM | [push 发送请求到李凌失败超过阈值] | all(#1)>=10 | 3 | 1 |
| pushserver_xinge_s2s_succ_count/project=IM | [push 发送请求到李凌成功数量小余阈值] | all(#1)>=100 | 3 | 1 |

