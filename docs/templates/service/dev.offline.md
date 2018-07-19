| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| db_agent_del_timeout/project=offline | [agent删除离线超时] | all(#3)>=10 | 3 | 0 |
| db_agent_delay_push_timeout/project=offline | [查从库是否同步超时] | all(#3)>=10 | 3 | 0 |
| db_agent_query_timeout/project=offline | [agent查询离线消息超时] | all(#3)>=10 | 3 | 0 |
| db_agent_recv_total_req/project=offline | [db_agent请求掉0] | all(#3)<=5 | 3 | 0 |
| db_agent_save_timeout/project=offline | [agent插入离线超时] | all(#3)>=10 | 3 | 0 |
| db_inter_recv_req/project=offline | [db_inter请求掉0] | all(#3)<=50 | 3 | 0 |
| db_sync_syncmc_timeout/project=offline | [更新mc中离线最大ID失败请立即查看] | all(#3)>=1 | 300 | 0 |
| db_worker_lastid_overflow_error/project=offline | [查离线ID超过最大值] | all(#3)>=1 | 3 | 0 |

