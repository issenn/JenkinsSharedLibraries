| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| CPUP/project=QIMServer | [QIM cpu过高 请注意重启] | all(#3)>=260 | 3 | 0 |
| RSS/project=QIMServer | [QIM内存超过限制请重启] | all(#3)>=2800 | 3 | 0 |

