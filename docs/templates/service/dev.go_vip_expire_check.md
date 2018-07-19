| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| db_return_err/project=govipcheck | [govipcheck db失败超过阈值 请注意查看] | all(#1)>=3 | 3 | 1 |
| mc_return_err/project=govipcheck | [govipcheck 访问memcache 出错超过阈值 请注意查看] | all(#1)>=3 | 3 | 1 |

