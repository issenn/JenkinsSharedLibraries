| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| interface_cmnt_proc_slow/project=moment | [inter访问comment进程延时大] | all(#3)>=30 | 3 | 5 |
| interface_cmnt_return_error/project=moment | [comment进程处理出错] | all(#3)>=30 | 3 | 0 |
| interface_cmnt_timeout/project=moment | [inter访问comment进程超时] | all(#3)>=20 | 3 | 0 |
| interface_mnt_proc_slow/project=moment | [inter访问momment进程延时大] | all(#3)>=1000 | 3 | 5 |
| interface_mnt_return_error/project=moment | [moment进程处理出错] | all(#3)>=30 | 3 | 0 |
| interface_mnt_timeout/project=moment | [inter访问moment进程超时] | all(#3)>=20 | 3 | 0 |
| interface_recv_cmnt_req/project=moment | [comment请求数掉0] | all(#3)<=100 | 3 | 0 |
| interface_recv_cmnt_req/project=moment | [comment请求量很大] | all(#3)>=50000 | 3 | 0 |
| interface_recv_mnt_req/project=moment | [momment请求数掉0] | all(#3)<=100 | 3 | 0 |
| interface_recv_mnt_req/project=moment | [momment请求量很大] | all(#3)>=50000 | 3 | 0 |
| postworker_momnet_mid_err/project=moment | [MntPostWorker 收到mid=1 错误帖文] | all(#1)>=1 | 3 | 0 |

