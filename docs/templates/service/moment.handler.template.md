| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| found_face/project=mmh | [人脸识别数量少] | all(#5)==0 | 5 | 0 |
| moment_api_fail/project=mmh | [mmhAPI失败] | all(#4)>=2 | 2 | 0 |
| post_commet/project=mmh | [评论过滤数量少] | all(#5)<=1 | 2 | 0 |
| post_commet_fail/project=mmh | [评论过滤失败] | all(#2)>3 | 2 | 0 |
| post_moment/project=mmh | [帖子过滤数量少] | all(#5)<=1 | 2 | 0 |
| post_moment_fail/project=mmh | [帖子过滤失败] | all(#2)>5 | 2 | 0 |

