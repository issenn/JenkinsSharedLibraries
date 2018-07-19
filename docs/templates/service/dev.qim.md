| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| googleplay_gettoken_parse_error_count/project=QIM | [GooglePlay token失效] | all(#1)>=0 | 3 | 5 | 
| googleplay_gettoken_refresh_failed_count/project=QIM | [GooglePlay获取token失败] | all(#1)>=2 | 3 | 5 | 
| googleplay_verify_failed_count/project=QIM | [GooglePlay 校验失败] | all(#10)>=5 | 3 | 5 | 
| googleplay_verify_post_failed_3_count/project=QIM | [GooglePlay 3次连接不上] | all(#1)>=0 | 3 | 5 | 
| googleplay_verify_post_failed_count/project=QIM | [GooglePlay post失败] | all(#3)>=1 | 3 | 5 | 
| googleplay_verify_succ_count/project=QIM/project=QIM | [GooglePlay两小时无购买] | all(#120)<=0 | 3 | 5 | 06:00-24:00
| imserver_busi_slow_count/project=QIM | [QIM慢处理增加] | all(#3)>=1000 | 3 | 5 | 
| imserver_busi_slow_response_count/project=QIM | [QIM队列堵塞量太多] | all(#3)>=100 | 3 | 1 | 
| qimserver_appstore_drop_count/project=QIM | [AppStore队列丢包了] | all(#1)>=1 | 3 | 1 | 
| qimserver_appstore_revew_failed/project=QIM | [AppStore续费校验失败] | all(#3)>=1 | 3 | 5 | 
| qimserver_appstore_verify_failed/project=QIM | [AppStore校验失败] | all(#3)>=1 | 3 | 5 | 
| qimserver_check_sign_failed_count/project=QIM | [WNS Push通道签名校验失败] | all(#3)>=10 | 3 | 5 | 
| qimserver_friend_queue/project=QIM | [friend 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_google_check_verify_failed_cnt/project=QIM | [GooglePlay 购买验证格式错误] | all(#10)>=0 | 3 | 5 | 
| qimserver_lbs_queue/project=QIM | [lbs 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_lbs_s2sdataquery_err/project=QIM | [到LBS请定位信息失败] | all(#3)>=3 | 3 | 5 | 
| qimserver_login_queue/project=QIM | [login 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_login_req_count/project=QIM | [QIM登录请求量下降] | all(#5)<=3 | 3 | 5 | 
| qimserver_mc_get_faield/project=QIM | [Memcache Get 失败] | all(#3)>=1100 | 3 | 5 | 
| qimserver_message_queue/project=QIM | [message 队列堵住了] | all(#3)>=1500 | 3 | 1 | 
| qimserver_muc_queue/project=QIM | [muc 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_muc_send_failed_count/project=QIM | [发送请求到MUC失败] | all(#3)>=3 | 3 | 5 | 
| qimserver_mysql_exec_failed/project=QIM | [mysql 执行失败] | all(#2)>=10 | 3 | 5 | 
| qimserver_off_s2ssendrowpacket_err/project=QIM | [获取离线失败] | all(#3)>=15 | 3 | 5 | 
| qimserver_other_queue/project=QIM | [other 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_pay_queue/project=QIM | [购买队列堵住] | all(#1)>=1500 | 3 | 1 | 
| qimserver_push_failed_count/project=QIM | [发送请求到PUSH失败] | all(#3)>=3 | 3 | 5 | 
| qimserver_push_msg_failed_count/project=QIM | [push到WNS成功但是客户端没收到] | all(#3)>=300 | 3 | 5 | 
| qimserver_recv_packet_count/project=QIM | [QIM收包量下降] | all(#3)<=3000 | 3 | 5 | 
| qimserver_recv_packet_count/project=QIM | [QIM收包量突变] | diff(#3)>=10000 | 3 | 5 | 
| qimserver_relogin_count/project=QIM | [QIM重练请求量下降] | all(#3)<=100 | 3 | 5 | 
| qimserver_sfi_req_cnt/project=QIM/project=QIM | [获取会员信息请求量突降 请查看] | all(#1)<=300 | 3 | 1 | 
| qimserver_user_queue/project=QIM | [user 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_voip_queue/project=QIM | [voip 队列堵住了] | all(#1)>=1500 | 3 | 1 | 
| qimserver_voip_send_failed_count/project=QIM | [发送请求到VOIP失败] | all(#3)>=3 | 3 | 5 | 
| s2s_invalid_packet_count/project=QIM | [S2SService 收到非法报文] | all(#1)>=10 | 3 | 5 | 

