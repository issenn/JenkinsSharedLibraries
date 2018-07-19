| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| recv_req_count/project=govoip | [go-voip recv_req_count 请求量降低请注意查看] | all(#3)<=3 | 3 | 5 |
| sendresp_count/project=govoip | [go-voip sendresp量掉零 请注意查看] | all(#3)<3 | 3 | 5 |
| sendresp_with_payload_count/project=govoip | [go-voip sendresp_with_payload_count 量掉零请注意查看] | all(#15)<2 | 3 | 5 |

