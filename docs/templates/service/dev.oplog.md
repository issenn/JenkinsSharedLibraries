| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| dbworker_imstate_queue_cnt/project=oplog | [imstate日志队列拥塞] | all(#3)>=1000 | 3 | 5 |
| dbworker_lbsstate_queue_cnt/project=oplog | [lbs_state日志队列拥塞] | all(#3)>=1000 | 3 | 5 |
| dbworker_login_queue_cnt/project=oplog | [login日志队列拥塞] | all(#3)>=1000 | 3 | 5 |
| dbworker_muc_queue_cnt/project=oplog | [muc日志队列堵住] | all(#3)>=1000 | 3 | 5 |
| dbworker_mysql_conn_fail/project=oplog | [mysql连接失败] | all(#3)>=5 | 3 | 5 |
| dbworker_mysql_execute_fail/project=oplog | [mysql操作失败] | all(#3)>=5 | 3 | 5 |
| dbworker_mysql_query_fail/project=oplog | [mysql查询失败] | all(#3)>=5 | 3 | 5 |
| dbworker_online_queue_cnt/project=oplog | [online日志队列堵住] | all(#3)>=1000 | 3 | 5 |
| dbworker_p2p_queue_cnt/project=oplog | [p2p日志队列堵住] | all(#3)>=800000 | 3 | 5 |
| dbworker_portrait_queue_cnt/project=oplog | [头像日志队列堵住] | all(#3)>=1000 | 3 | 5 |
| dbworker_proc_imstate_failed/project=oplog | [imstate日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_lbsstate_failed/project=oplog | [lbsstate日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_login_failed/project=oplog | [登录日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_online_failed/project=oplog | [在线日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_p2p_failed/project=oplog | [P2P日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_portrait_failed/project=oplog | [头像日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_relation_failed/project=oplog | [relation日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_slowproc_failed/project=oplog | [slowproc日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_proc_voip_failed/project=oplog | [voip日志入库失败] | all(#5)>=1 | 3 | 5 |
| dbworker_recv_online_req1/project=oplog | [online日志掉0] | all(#3)<=5 | 3 | 5 |
| dbworker_recv_p2p_req2/project=oplog | [p2p日志掉0] | all(#3)<=5 | 3 | 5 |

