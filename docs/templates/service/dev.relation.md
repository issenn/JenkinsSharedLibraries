| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| dbworker_exceed_day_limit/project=relation | [关注达到单日上限] | all(#5)>=10 | 3 | 0 |
| dbworker_exceed_max_limit/project=relation | [关注达到最大上限] | all(#5)>=10 | 3 | 0 |
| logic_db_timeout/relation=relation | [dbworker超时] | all(#3)>=10 | 3 | 0 |
| logic_follow_req1/relation=relation | [关注请求量过大] | all(#3)>=500 | 3 | 0 |
| logic_follow_req1/relation=relation | [关注请求量掉0] | all(#3)<=5 | 3 | 0 |
| logic_follow_ret_err/relation=relation | [关注出错] | all(#3)>=10 | 3 | 0 |
| logic_follow_slow/relation=relation | [关注请求处理慢] | all(#3)>=10 | 3 | 5 |
| logic_follow_timeout/relation=relation | [关注请求处理超时] | all(#3)>=10 | 3 | 0 |
| logic_get_all_following_detail_req14/relation=relation | [获取关注信息请求量过大] | all(#3)>=5000 | 3 | 5 |
| logic_get_all_following_detail_req14/relation=relation | [获取关注信息请求量掉0] | all(#3)<=5 | 3 | 0 |
| logic_get_all_following_detail_ret_err/relation=relation | [获取关注信息出错] | all(#3)>=10 | 3 | 0 |
| logic_get_all_following_detail_slow/relation=relation | [获取关注信息处理慢] | all(#3)>=10 | 3 | 5 |
| logic_get_all_following_detail_timeout/relation=relation | [获取关注信息处理超时] | all(#3)>=10 | 3 | 0 |
| logic_get_follower_detail_by_time_req9/relation=relation | [获取粉丝信息请求量过大] | all(#3)>=1000 | 3 | 0 |
| logic_get_follower_detail_by_time_req9/relation=relation | [获取粉丝信息请求量掉0] | all(#3)<=1 | 3 | 0 |
| logic_get_follower_detail_by_time_ret_err/relation=relation | [获取粉丝信息出错] | all(#3)>=10 | 3 | 0 |
| logic_get_follower_detail_by_time_slow/relation=relation | [获取粉丝信息处理慢] | all(#3)>=10 | 3 | 5 |
| logic_get_follower_detail_by_time_timeout/relation=relation | [获取粉丝信息处理超时] | all(#3)>=10 | 3 | 0 |
| logic_query_all_list_req13/relation=relation | [query all list请求量过大] | all(#3)>=5000 | 3 | 5 |
| logic_query_all_list_req13/relation=relation | [query all list请求处理慢] | all(#3)>=10 | 3 | 5 |
| logic_query_all_list_req13/relation=relation | [query all list请求量掉0] | all(#3)<=10 | 3 | 0 |
| logic_query_all_list_ret_err/relation=relation | [query all list出错] | all(#3)>=10 | 3 | 0 |
| logic_query_all_list_timeout/relation=relation | [query all list请求处理超时] | all(#3)>=10 | 3 | 0 |
| logic_query_is_friend_req12/relation=relation | [query is friend请求量过大] | all(#3)>=1000 | 3 | 5 |
| logic_query_is_friend_req12/relation=relation | [query is friend请求量掉0] | all(#3)<=1 | 3 | 0 |
| logic_query_is_friend_ret_err/relation=relation | [query is friend出错] | all(#3)>=10 | 3 | 0 |
| logic_query_is_friend_slow/relation=relation | [query is friend请求处理慢] | all(#3)>=10 | 3 | 5 |
| logic_query_is_friend_timeout/relation=relation | [query is friend请求处理超时] | all(#3)>=10 | 3 | 0 |
| logic_unfollow_req2/relation=relation | [取消关注请求量过大] | all(#3)>=500 | 3 | 0 |
| logic_unfollow_req2/relation=relation | [取消关注请求量掉0] | all(#3)<=1 | 3 | 0 |
| logic_unfollow_ret_err/relation=relation | [取消关注出错] | all(#3)>=10 | 3 | 0 |
| logic_unfollow_slow/relation=relation | [取消关注请求处理慢] | all(#3)>=10 | 3 | 5 |
| logic_unfollow_timeout/relation=relation | [取消关注请求处理超时] | all(#3)>=10 | 3 | 0 |
| logic_usercache_timeout/relation=relation | [usercache超时] all(#3)>=10 | 3 | 0 |

