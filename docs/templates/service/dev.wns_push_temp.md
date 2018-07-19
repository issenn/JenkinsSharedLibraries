| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| channel_post_success_count/project=wns_consumer | [推送到维纳斯成功的请求量降低 请查看] | all(#3)<4000 | 3 | 0 |
| post_failed_count/project=wns_consumer | [推送到维纳斯失败量升高 请查看] | all(#3)>10 | 3 | 0 |
| post_success_count/project=wns_consumer | [推送到维纳斯请求量过低 请查看] | all(#3)<4000 | 3 | 0 |

