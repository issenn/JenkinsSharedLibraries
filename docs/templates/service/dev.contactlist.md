| metric/tags | note | condition | max | P | run | 
| - | - | - | - | - | - | - |
| update_contact_list_req/project=contactlist | [最近联系人接受请求量到达最大值] | all(#2)>=8000 | 3 | 5 |  |
| update_contact_list_req/project=contactlist | [最近联系人接受请求量降0] | all(#1)<=30 | 3 | 5 |  |
| update_contact_list_req/project=contactlist | [最近联系人接受请求量突变3000] | diff(#1)>=3000 | 3 | 5 |  |
| update_contact_list_slow/project=contactlist | [最近联系人慢处理量超过阈值] | all(#3)>=50 | 3 | 5 |  |

