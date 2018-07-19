| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| query_account_cache_failed/project=gouseradapter | [cache-adapter 查询account cache失败升高 请立即查看] | all(#1)>=3 | 5 | 0 |  |
| query_base_cache_failed/project=gouseradapter | [cache-adapter 查询base cache失败升高 请立即查看] | all(#1)>=3 | 5 | 0 |  |
| total_recv_req_count/project=gouseradapter | [总请求量波动过大 请立即查看] | diff(#1)>=1000 | 5 | 0 |  |
