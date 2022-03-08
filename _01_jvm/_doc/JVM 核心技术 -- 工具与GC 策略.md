

[toc]



# 工具

## 内置命令行工具

基础：JDK bin 目录下有的。

![image-20220308190635664](JVM 核心技术 -- 工具与GC 策略.assets/image-20220308190635664.png)



## 常见配合使用

### jps/jinfo =》 查看java 进程

这里存在的坑：

①：linxu jps 显示不全（需要sudo）

② ：不同JDK 版本，JPS 也显示不全，或操作不全

- 解决方案：使用一个JDK 运行，避免上面的②的问题



### jstat =》 查看JVM 内部gc 相关信息

> \>jstat -options
>
> -class 类加载(Class loader)信息统计
>
> -compiler JIT 即时编译器相关的统计信息
>
> <font color = "red">-gc</font> GC 相关的堆内存信息。 用法: jstat -gc -h 10 -t 864 1s 20 <font color="#FFA500">这句话是什么意思？TODO 待补充</font>
>
> 	- 例子：jstac -gc 19459 1000 1000 (pid， 打1k次，每次1000ms)
> 	- 这里呈现的是**字节数**
>
> <font color = "red">-gcutil</font> GC 相关区域的使用率（utilization）统计
>
> 	- 使用 jstac -gcutil -t 19459 1000 1000   （-t 会呈现时间戳）
> 	- 与上面的区别，这个现实的缩减信息，呈现**百分比**
>
> -gccapacity 各个内存池分代空间的容量
>
> -gccause 看上次 GC，本次 GC（如果正在 GC 中）的原因， 其他输出和 -gcutil 选项一致
>
> -gcnew 年轻代的统计信息。（New = Young = Eden + S0 + S1）
>
> -gcnewcapacity 年轻代空间大小统计
>
> -gcold 老年代和元数据区的行为统计
>
> -gcoldcapacity old 空间大小统计
>
> -gcmetacapacity meta 区大小统计
>
> 
>
> -printcompilation 打印 JVM 编译统计信息

![image-20220308191106458](JVM 核心技术 -- 工具与GC 策略.assets/image-20220308191106458.png)

#### jstat -gcutil -t pid 1000 1000

![image-20220308191940584](JVM 核心技术 -- 工具与GC 策略.assets/image-20220308191940584.png)



#### jstat -gc -t pid 1000 1000

![Week1-02(1)](JVM 核心技术 -- 工具与GC 策略.assets/Week1-02(1).jpg)





### jmap =》 产看heap 类或占用空间统计 

**作用：堆当前堆内存打一个快照**

选项：

>**-heap** 打印**堆内存（/内存池）**的配置和使用信息。
>
>**-histo** 看哪些类**占用的空间最多**, 直方图。
>
>-dump:format=b,file=xxxx.hprof Dump 堆内存。 => 这是二进制文件



![微信图片_20220308193040](JVM 核心技术 -- 工具与GC 策略.assets/微信图片_20220308193040.jpg)



### jstack =》 查看线程信息

>-F 强制执行 thread dump，可在 Java 进程卡死（hung 住）时使用，此选项可能需要系统权限。
>-m 混合模式（mixed mode)，将 Java 帧和native 帧一起输出，此选项可能需要系统权限。
>
>​    -> native 桢：Java 程序
>
>-l 长列表模式，将线程相关的 locks 信息一起输出，比如持有的锁，等待的锁。
>
>常用：jstack -l pid



jstack => 等价于 kill -3 pid (在linux 或者mac 上使用) 在线程执行下打印（正在运行的内容）



### jcmd =》 执行JVM 相关分析命令(整合命令)

包括：GC，线程、VM

>jcmd pid VM.version
>jcmd pid VM.flags
>jcmd pid VM.command_line
>jcmd pid VM.system_properties
>jcmd pid Thread.print
>jcmd pid GC.class_histogram
>jcmd pid GC.heap_info



---------

jrunscript/jss









## 图形化工具

 





# GC 策略

