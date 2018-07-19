| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| error.db.conn_fail | [数据库连接失败] | all(#3)>3 | 2 | 0 |
| error.redis.conn_fail/os=ios  | all(#3)>=40 | 1 | 3 |
| fail__/v1/moment/lastest/os=ios | [信息流失败] | all(#3)>=20 | 2 | 0 | 07:00-24:00 |
| logger.geo.geodb/os=ios | [地理库请求] | all(#3)<=3 | 2 | 0 | 07:00-24:00 |
| logger.notify.leanplum_ok/os=android | [leanplum通知少] | all(#4)<2 | 3 | 0 |
| logger.transcription_fail | [语音转文字失败] | all(#3)>=3 | 3 | 3 |
| logger.translate | [翻译降低] | all(#3)<=20 | 3 | 3 |
| logger.user.lastchat/os=ios | [最联系人少] | all(#4)<=0 | 3 | 3 |
| lp.proxy.succ | [lp代理少] | all(#3)<50 | 2 | 3 | 09:00-24:00 |
| ok__/api/register/htregister2 | [注册2] | all(#3)<1 | 3 | 0 |
| ok__/v1/moment/lastest/os=android | [信息流少] | all(#3)<=100 | 3 | 0 |
| ok__/v1/moment/lastest/os=ios | [信息流] | all(#3)<=300 | 3 | 0 |
| p2p.message.fail | [OR发P2P消息失败] | all(#3)>5 | 2 | 0 |
| SW/LOG/momentLogger/chmom | [信息流使用logger] | all(#3)<100 | 2 | 0 |
| SW/LOG/momentLogger/get | [信息流加载logger] | all(#3)<500 | 2 | 0 |
| SW/LOG/pointsLogger/host=webapi | [翻译点数处理少] | all(#3)<=250 | 3 | 3 |
| SW/LOG/search | [搜索请求] | all(#3)<200 | 2 | 3 |
| SW/MMH/found_face/host=webapi | [自拍检测] | all(#8)==0 | 2 | 0 | 07:00-24:00 |
| SW/MOMENT/mntHandler/host=webapi | [信息流处理] | all(#5)<5 | 2 | 0 |
| trans.text.google_text/os=ios | [代理翻译太多] | all(#3)>=30 | 3 | 0 |
| wns.callback.fail | [维纳斯回调失败] | all(#5)>=10 | 3 | 0 |
| wns.callback.succ | [维纳斯回调下降] | all(#3)<5000 | 2 | 0 |
