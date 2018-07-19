| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| cpu.idle [cpu占用率太高] | all(#3)<=5 | 3 | 0 |  |
| df.bytes.free.percent | [磁盘空间不足] | all(#3)<=15 | 3 | 0 |  |
| disk.io.util | [磁盘IO太高] | all(#8)>=85 | 2 | 5 |  |
| kernel.maxfiles |[文件限制太小] | all(#3)<=65536 | 3 | 5 |  |
| mem.memfree.percent | [内存不足] | all(#5)<=7 | 3 | 0 |  |
| net.if.in.bytes |[网卡入流量大于80M] | all(#3)>=80000000 | 3 | 5 |  |
| net.if.out.bytes | [网卡出流量大于80M] | all(#3)>=80000000 | 3 | 5 |  |
| net.port.listen/port=29765 | [monitor_agent挂了] | all(#2)==0 | 3 | 0 |  |
| proc.num/name=crond | [crond挂了] | all(#2)==0 | 3 | 0 |  |
| proc.num/name=sshd | [ssh挂了] | all(#2)==0 | 3 | 0 |  |

