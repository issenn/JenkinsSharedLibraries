| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| loghub_recv_req_0x1001_ret_err | [0x1001 ret error] | all(#3)>=30 | 3 | 5 |
| loghub_recv_req_0x1001_wns_err | [0x1001 wns error突增] | diff(#1)>=60 | 3 | 5 | 20:00-24:00 |
| loghub_recv_req_0x1001_wns_err | [0x1001 wns error(用户可能会连接失败)] | all(#3)>=50 | 3 | 0 |
| loghub_recv_req_0x1001_wns_err_7 | [0x1001 wns error7(用户可能会连接失败)] | all(#3)>=10 | 3 | 0 |
| loghub_recv_req_0x1003_ret_err | [0x1003 ret err] | all(#3)>=20 | 3 | 5 |
| loghub_recv_req_0x1003_wns_err | [0x1003 wns err(用户可能会连接失败)] | all(#3)>=10 | 3 | 0 |
| loghub_recv_req_0x1003_wns_err_7 | [0x1003 wns err7(用户可能会连接失败)] | all(#3)>=10 | 3 | 0 |
| loghub_recv_req_0x1005_ret_err | [0x1005 ret err] | all(#3)>=1000 | 3 | 5 |
| loghub_recv_req_0x1005_wns_err | [0x1005 wns err(用户可能会连接失败)] | all(#3)>=500 | 3 | 0 |
| loghub_recv_req_0x1005_wns_err_7 | [0x1005 wns err7(用户可能会连接失败)] | all(#3)>=100 | 3 | 0 |
| loghub_recv_req_reconnect_ret_err | [reconnect ret err] | all(#3)>=300 | 3 | 5 |
| loghub_recv_req_wns_connect_ret_err | [wns_connect ret err] | all(#3)>=500 | 3 | 5 |
| loghub_recv_req_wns_connect_status_ret_err | [wns_connect_status ret err] | all(#3)>=5000 | 3 | 5 |
| loghub_recv_req_wns_connect_status_wns_err | [wns_connect_status wns err] | all(#3)>=1200 | 3 | 5 |
| loghub_recv_req_wns_connect_status_wns_err_7 | [wns_connect_status wns err7(用户可能会连接失败)] | all(#3)>=2000 | 3 | 0 |

