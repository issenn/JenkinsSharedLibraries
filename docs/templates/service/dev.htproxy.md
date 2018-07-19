| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| htproxy_send_wns_req/project=htproxy | [htproxy请求量突变4000 可能导致用户连接不上] | diff(#1)>=4000 | 3 | 0 |
| htproxy_send_wns_req/project=htproxy | [htproxy请求量到达最大值 可能超过系统容量] | all(#3)>=100000 | 3 | 0 |
| htproxy_wait_resp_timeout_0/project=htproxy | [htproxy等待179超时量超过阈值] | all(#3)>=50 | 3 | 5 |
| htproxy_wait_resp_timeout_1/project=htproxy | [htproxy等待179超时量超过阈值] | all(#3)>=50 | 3 | 5 |
| htproxy_wait_resp_timeout_2/project=htproxy | [htproxy等待20超时量超过阈值] | all(#3)>=50 | 3 | 5 |
| req_is_too_large/project=htproxy | [htproxy超长请求量超过阈值] | all(#1)>=10 | 3 | 5 |
| resp_is_too_large/project=htproxy | [htproxy超长响应量超过阈值] | all(#1)>=10 | 3 | 5 |

