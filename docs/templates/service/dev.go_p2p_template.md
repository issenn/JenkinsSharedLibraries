| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| build_push_packet_failed/project=gop2p | [go-p2p build push 报文失败超过阈值 请注意] | all(#3)>=3 | 3 | 5 |
| get_mc_failed_count/project=gop2p | [go-p2p 查询memcache失败超过阈值 请注意] | all(#5)>=10 | 3 | 5 |
| save_offline_failed_count/project=gop2p | [go-p2p 存储离线失败超过阈值 请注意] | all(#3)>=10 | 3 | 5 |
| total_recv_req_count/project=gop2p | [go-p2p 接收到的请求量掉零 请注意] | all(#1)<=10 | 3 | 1 |

