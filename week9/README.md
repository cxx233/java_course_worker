# 第7周作业


## 作业内容

1、（选做）实现简单的 Protocol Buffer/Thrift/gRPC（选任一个）远程调用 demo。
2、（选做）实现简单的 WebService-Axis2/CXF 远程调用 demo。
3、（必做）改造自定义 RPC 的程序，提交到 GitHub：
1）尝试将服务端写死查找接口实现类变成泛型和反射
2）尝试将客户端动态代理改成字节码生成，添加异常处理
3）尝试使用 Netty+HTTP 作为 client 端传输方式
4、（挑战☆☆）升级自定义 RPC 的程序：
1）尝试使用压测并分析优化 RPC 性能
2）尝试使用 Netty+TCP 作为两端传输方式
3）尝试自定义二进制序列化或者使用 kyro/fst 等
4）尝试压测改进后的 RPC 并分析优化，有问题欢迎群里讨论
5）尝试将 fastjson 改成 xstream
6）尝试使用字节码生成方式代替服务端反射
