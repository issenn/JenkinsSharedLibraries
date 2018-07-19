| metric/tags | note | condition | max | P | run |
| - | - | - | - | - | - | - |
| response_fail/project=openlanguage | [开言连续返回3次失败] | all(#3)>1 | 3 | 0 |  |
| response_success/project=openlanguage | [开言英语无人访问] | all(#3)<1 | 3 | 0 |  |

