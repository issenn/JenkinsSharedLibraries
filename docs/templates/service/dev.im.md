| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| googleplay_gettoken_refresh_failed_count/project=IM | [GooglePlay获取Token失败] | all(#1)>=2 | 3 | 0 |
| googleplay_verify_failed_count/project=IM | [GooglePlay校验失败] | all(#5)>=1 | 3 | 0 |
| googleplay_verify_post_failed_count/project=IM | [GooglePlay post失败] | all(#1)>=2 | 3 | 0 |
| imserver_appstore_drop_count/project=IM | [AppStore 队列丢包了] | all(#1)>=1 | 3 | 0 |
| imserver_appstore_revew_failed/project=IM | [AppStore 续费校验失败] | all(#3)>=1 | 3 | 0 |
| imserver_appstore_verify_failed/project=IM | [AppStore 校验失败] | all(#3)>=1 | 3 | 0 |
| imserver_busi_slow_count/project=IM | [IM慢处理量增加] | all(#3)>=1000 | 3 | 0 |
| imserver_busi_slow_response_count/project=IM | [IM队列堵塞量太多] | all(#3)>=100 | 3 | 0 |
| imserver_friend_queue/project=IM | [friend 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_lbs_queue/project=IM | [lbs 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_lbs_s2sdataquery_err/project=IM | [发送请求到LBS失败] | all(#3)>=3 | 3 | 0 |
| imserver_login_queue/project=IM | [login 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_login_req_count/project=IM | [IM登录请求量下降] | all(#30)<0 | 3 | 5 |
| imserver_mc_get_faield/project=IM | [Memcache Get 失败] | all(#3)>=1000 | 3 | 0 |
| imserver_message_queue/project=IM | [message 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_muc_queue/project=IM | [muc 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_muc_send_failed_count/project=IM | [发送请求到MUC失败] | all(#3)>=300 | 3 | 0 |
| imserver_mysql_exec_failed/project=IM | [mysql执行失败] | all(#3)>=10 | 3 | 0 |
| imserver_off_s2ssendrowpacket_err/project=IM | [获取离线失败] | all(#3)>=5 | 3 | 0 |
| imserver_other_queue/project=IM | [other 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_pay_queue/project=IM | [购买队列堵住] | all(#3)>=1500 | 3 | 0 |
| imserver_push_failed_count/project=IM | [发送请求到PUSH失败] | all(#3)>=3 | 3 | 0 |
| imserver_recv_packet_count/project=IM | [IM收包量下降] | all(#1)<0 | 3 | 0 |
| imserver_recv_packet_count/project=IM | [IM收包量突变] | diff(#3)>=4000 | 3 | 0 |
| imserver_relogin_count/project=IM | [IM重连请求量下降] | all(#10)<0 | 3 | 0 |
| imserver_user_queue/project=IM | [user 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_voip_queue/project=IM | [voip 队列堵住了] | all(#3)>=1500 | 3 | 0 |
| imserver_voip_send_failed_count/project=IM | [发送请求到VOIP失败] | all(#3)>=3 | 3 | 0 |
| s2s_invalid_packet_count/project=IM | [S2SService 收到非法报文] | all(#1)>=10 | 3 | 0 |

