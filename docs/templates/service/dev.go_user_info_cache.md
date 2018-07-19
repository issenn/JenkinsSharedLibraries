| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| get_user_account_cache_count/project=goaclogic | [user account cache 总得请求量降低 请注意查看] | all(#3)<=2000 | 3 | 5 |
| get_user_account_cache_miss/project=goaclogic | [user account cache miss 量增加] | all(#3)>=4500 | 3 | 5 |
| get_user_base_cache_count/roject=gobalogic | [user base logic 总得请求量降低 请注意查看] | all(#3)<=2000 | 3 | 5 |
| get_user_base_cache_miss/project=gobalogic | [user base cache miss 量增加] | all(#3)>=1500 | 3 | 5 |
| query_account_cache_failed/project=gocainter | [查询account cache 失败超过阈值] | all(#3)>=3800 | 3 | 5 |
| query_base_cache_failed/project=gocainter | [查询base cache 失败超过阈值] | all(#3)>=5 | 3 | 5 |
| unregister_user_db_err/project=goacdbd | [accont cache 注销用户失败] | all(#1)>=1 | 3 | 5 |
| update_user_lang_setting_db_err/project=gobadbd | [base cache 更新用户语言设置失败] | all(#1)>=1 | 3 | 5 |
| update_user_name_db_err/project=gobadbd | [base cache 更新 用户名失败] | all(#3)>=10 | 3 | 5 |
| update_user_private_db_err/project=gobadbd | [base cache 更新用户隐私设置失败] | all(#1)>=1 | 3 | 5 |
| update_user_profile_db_err/project=gobadbd | [base cache 更新用户profile失败] | all(#1)>=1 | 3 | 5 |
| update_user_property_db_err/project=gobadbd | [base cache 更新用户资料版本时间戳失败] | all(#1)>=1 | 3 | 5 |
| update_user_timezone_db_err/project=gobadbd | [base cache 更新用户时区失败] | all(#1)>=1 | 3 | 5 |

