| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| Innodb_buffer_pool_reads | [db读磁盘] | all(#3)>450 | 2 | 0 |
| Innodb_row_lock_current_waits | [db锁] | all(#3)>15 | 3 | 0 |
| Innodb_row_lock_waits | [db锁] | all(#2)>=0.6 | 1 | 0 |
| Questions | [查询量] | all(#3)>=2200 | 1 | 0 |
| Slow_queries | [慢查询] | all(#3)>=10 | 1 | 0 |
| Threads_connected | [db线程数] | all(#3)>=400 | 3 | 0 |

