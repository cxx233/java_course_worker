

[toc]



# 0. 作业

>**Week01 作业题目**
>
>**1.（选做）**自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。
>
>**2.（必做）**自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 Hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件在我的教室下载。
>
>**3.（必做）**画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
>
>**4.（选做）**检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。
>
>**注意：**如果没有线上系统，可以自己 run 一个 web/java 项目。
>
>**5.（选做）**本机使用 G1 GC 启动一个程序，仿照课上案例分析一下 JVM 情况。
>
>以上作业，要求 2 道必做题目提交到自己 GitHub 上面，然后把自己的作业链接填写到下方的表单里面：
>[ https://jinshuju.net/f/KCVEZT](https://jinshuju.net/f/KCVEZT)
>
>示例代码参考：
>[ https://github.com/JavaCourse00/JavaCourseCodes](https://github.com/JavaCourse00/JavaCourseCodes)
>学号查询方式：PC 端登录 time.geekbang.org, 点击右上角头像进入我的教室，左侧头像下方 G 开头的为学号
>
>



# 1. JVM 基础知识

## 编程语言的类别信息







## java 性质

>Java 是一种面向对象、静态类型、编译执行，
>有 VM/GC 和运行时、跨平台的高级语言。



## 字节码、类加载器、虚拟机关系

![image-20220307104911305](JVM 核心技术--基础知识.assets/image-20220307104911305.png)



# 2. 字节码技术



## 主要说明

Constant pool： 常量池

LocalVariableTable: 局部变量表



[第 6 章。Java 虚拟机指令集 (oracle.com)](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.getfield)

可具体观看文件里面的内容 [JVM-1-4-Java字节码技术.pdf](./JVM-1-4-Java字节码技术.pdf)





## 字节码相关概念

Java bytecode 由单字节（byte）的指令组成，理论上最多支持 256 个操作码（opcode）。
实际上 Java 只使用了200左右的操作码， 还有一些操作码则保留给调试操作。

>个人理解：
>
>每个指令都是一个单字节，并且有对应的值所表示，由于用的是16进制所表示，所以二进制所呈现就是两个字节效果。（注意：具体操作指令所需要的信息应该要查相关文档以及总结）
>
>如下图：
>
>![image-20220307105304837](JVM 核心技术--基础知识.assets/image-20220307105304837.png)



## 生成字节码文件

要呈现 localVeriable table 的数据需要编译的时候 加参数-g

```
javac -g xxx.java
```



```
javap -c -verbose demo.jvm.HelloByteCode
```



## 分析字节码文件

### 数值处理 & 本地变量表的分析



### 指令说明

#### load & store

![image-20220307105602900](JVM 核心技术--基础知识.assets/image-20220307105602900.png)





## 字节码运行时结构

前提：JVM 是一台基于栈的计算机器。

**每个线程都有一个独属于自己的<a id = "线程栈">线程栈(JVM Stack)</a> ，存储栈帧（Frame）。**

**每一次方法调用、JVM 都会自动创建一个栈帧。**

>栈帧由**操作数栈**、 **局部变量数组**以及一个 **Class 引用**组成。
>
>**Class 引用**指向当前方法在运行时常量池中对应的 Class。
>
>![image-20220307111232971](JVM 核心技术--基础知识.assets/image-20220307111232971.png)





## 方法调用的指令

<font color = "blue">Invokestatic</font>：顾名思义，这个指令用于调用某个类的静态方法，这是方法调用指令中最快的一个。
<font color = "blue">Invokespecial </font>：用来调用构造函数，但也可以用于调用同一个类中的 private 方法, 以及
见的超类方法。
<font color = "blue">invokevirtual </font>：如果是具体类型的目标对象，invokevirtual 用于调用公共、受保护和package 级的私有方法。
<font color = "blue">invokeinterface</font> ：当通过接口引用来调用方法时，将会编译为 invokeinterface 指令。
<font color = "blue">invokedynamic </font>： JDK7 新增加的指令，是实现“动态类型语言”（Dynamically Typed Language）支持而进行的升级改进，同时也是 JDK8 以后支持 lambda 表达式的实现基。





# 3. JVM 类加载器

## 786

### 类的生命周期-7

![image-20220307112438262](JVM 核心技术--基础知识.assets/image-20220307112438262.png)

1. 加载（Loading）：找 Class 文件
2. 验证（Verification）：验证格式、依赖
3. 准备（Preparation）：静态字段、方法表
4. 解析（Resolution）：符号解析为引用
5. 初始化（Initialization）：构造器、静态变
量赋值、静态代码块
6. 使用（Using）
7. 卸载（Unloading）





### 类的加载时机 - 8

1. 当虚拟机启动时，初始化用户指定的主类，就是启动执行的 main 方法所在的类；
2. 当遇到用以新建目标类实例的 new 指令时，初始化 new 指令的目标类，就是 new 一个类的时候要初始化；
3. 当遇到调用静态方法的指令时，初始化该静态方法所在的类；
4. 当遇到访问静态字段的指令时，初始化该静态字段所在的类；
5. 子类的初始化会触发父类的初始化；
6. 如果一个接口定义了 default 方法，那么直接实现或者间接实现该接口的类的初始化，会触发该接口的初始化；
7. 使用反射 API 对某个类进行反射调用时，初始化这个类，其实跟前面一样，反射调用要么是已经有实例了，要么是静态方法，都需要初始化；
8. 当初次调用 MethodHandle 实例时，初始化该 MethodHandle 指向的方法所在的。



### 不会初始化（可能会加载） - 6

1. 通过子类引用父类的静态字段，只会触发父类的初始化，而不会触发子类的初始化。
2. 定义对象数组，不会触发该类的初始化。
3. 常量在编译期间会存入调用类的常量池中，本质上并没有直接引用定义常量的类，不会触发定义常量所在的类。
4. 通过类名获取 Class 对象，不会触发类的初始化，Hello.class 不会让 Hello 类初始化。
5. 通过 Class.forName 加载指定类时，如果指定参数 initialize 为 false 时，也不会触发类初始化，其实这个参数是告诉虚拟机，是否要对类进行初始化。
(Class.forName”jvm.Hello”)默认会加载 Hello 类。
6. 通过 ClassLoader 默认的 loadClass 方法，也不会触发初始化动作（加载了，但是不初始化）。



## 类加载器类型

### 加载过程

先加载AppClassLoader，找不到会由ExtClassLoader 执行，再找不到：BootstrapClassLoader 进行加载。 =》 **双亲委托** （如下图）

![image-20220307122308549](JVM 核心技术--基础知识.assets/image-20220307122308549.png)



### 实现类

这里没有启动类加载器BottstrapClassLoader ，是因为该是由JVM 底层所实现的。

![image-20220307122443909](JVM 核心技术--基础知识.assets/image-20220307122443909.png)



### 加载器特点

TODO 待补充



### 代码例子：显示当前ClassLoader 加载了哪些Jar



### 如何实现自定义ClassLoader ，可以观看到的是哪个类。





### 添加引用类的几种方式

**问题：**什么是引用类？具体泛指什么？有没有例子。



1、放到 JDK 的 lib/ext 下，或者 -Djava.ext.dirs
2、 java-cp/classpath 或者 class 文件放到当前路径
3、自定义 ClassLoader 加载
4、拿到当前执行类的 ClassLoader，反射调用 addUrl 方法添加 Jar 或路径（JDK9 无效） 	=》 **问题：**这个怎么实现？有什么具体例子？ 看回视频





# 4. JVM 内存模型



## JVM 内存结构

### 上层抽象版

![image-20220307123214878](JVM 核心技术--基础知识.assets/image-20220307123214878.png)

**每个线程都只能访问自己的<a href = "线程栈"> 线程栈</a>**

- 每个线程都**不能访问** 其他线程的**局部变量**
  - 所有原生类型的局部变量都存储在线程栈内，因此其他线程是不可见的。
  - 处理方案：线程将原生变量值的副本传给另一个线程，但不能共享原生局部变量本身。

**堆内存**：含义以及特定说明

- 包含了Java 代码中创建的所有对象，不管是哪个线程创建的。其中也涵盖了包装类型（Byte 等）
  - 不管是创建一个对象并将其赋值给局部变量，还是赋值给另一个对象的成员变量，创建的对象都会被保存到堆内存中。
  - 对象的成员变量 与独享本身一起存储在堆上，不管成员变量的类型是原生数值还是对象引用。
- 类的静态变量也是放置此
- 亦称“共享堆”，堆中的所有对象，可以被所有线程访问，只要他们能拿到对象的引用地址。
- 







### 具体详细版

![image-20220307124055248](JVM 核心技术--基础知识.assets/image-20220307124055248.png)

![image-20220307133813589](JVM 核心技术--基础知识.assets/image-20220307133813589.png)





### JVM 内存整体结构



![image-20220307185026371](JVM 核心技术--基础知识.assets/image-20220307185026371.png)

- 解读：
  - 每启动一个线程，JVM 在栈空间分配对应的 <a href = "线程栈">线程栈</a>，比如1MB 的空间(-Xss1m)
    - 线程栈 = Java 方法栈
    - 使用JNI 方法 =》 分配一个单独的本地方法栈。（**问题：什么是本地方法栈**）
    - 每执行一个方法，创建对应的 栈帧（Frame） =》 会导致一个问题，调用链太长了Xss 分配的1m 不够用。
      - 解决方案：进行再配置大小，并且调整



### JVM <font color = "blue">线程栈</font>内存结构

![image-20220307190146204](JVM 核心技术--基础知识.assets/image-20220307190146204.png)

- 解读：
  - 栈帧的大小再一个方法编写完成后基本上就能确定（字节码已经确定了）
  - ![image-20220307190849080](JVM 核心技术--基础知识.assets/image-20220307190849080.png)





### JVM 堆内存结构

![image-20220307191134812](JVM 核心技术--基础知识.assets/image-20220307191134812.png)

- 解读
  - 堆内存
    - 堆：**受到GC 算法控制对象，堆内存上创建、回收、销毁。**
      - **年轻代**
        - Eden-Space：新生代
        - Survivor space：存活区
          - S0 和 S1，任何时刻，这两个总有一个是空的。
        - **新生代：S1：S2 = 8:1:1**
      - **老年代**
    - 非堆：不归GC 管理
      - Meatspace 元数据区
      - CCS：Compressed Class space： 存放class 信息，和MeataSpace 有交叉
      - Code Cache 存放JIT 编译器编译后的本地机器代码。



# 5. JVM 启动参数

在不同环境实现高效地运行，调节启动参数，做调教效果。



## 要求类型标准

- **\- 开头为标准参数=》** 所有JVM 都要实现，并且向后兼容

- \-D 设置系统属性

- \-X 非标准参数，基本传给JVM 的，默认JVM 实现这些参数的功能，但并不保证所有JVM 实现都满足， 且不保证向后兼容。
  - 使用 java -X 命令查看当前JVM 支持的非标准参数
- \-XX 非稳定参数， 转码用于控制JVM 行为，跟具体JVM 实现有关，可能会在下一个版本取消。
  - -XX： +-Flags 形式，+- 对布尔值进行开关。
  - \-XX： key=value 形式，置顶某个选项的指。
- 例如
  - ![image-20220307194622263](JVM 核心技术--基础知识.assets/image-20220307194622263.png)

## 启动参数分类

### 1. 系统属性参数

\-D 开头的

![image-20220307195208819](JVM 核心技术--基础知识.assets/image-20220307195208819.png)





### 2. 运行模式参数

1. -server：设置 JVM 使用 server 模式，特点是启动速度比较慢，但运行时性能和内存理效率很高，适用于生产环境。在具有 64 位能力的 JDK 环境下将默认启用该模式，而忽略 -client 参数。
2. -client ：JDK1.7 之前在32位的 x86 机器上的默认值是 -client 选项。设置 JVM 使用client 模式，特点是启动速度比较快，但运行时性能和内存管理效率不高，通常用于客户端应用程序或者 PC 应用开发和调试。此外，我们知道 JVM 加载字节码后，可以解释执行，也可以编译成本地代码再执行，所以可以配置 JVM 对字节码的处理模式。
3. -Xint：在解释模式（interpreted mode）下运行，-Xint 标记会强制 JVM 解释执行所有的字节码，这当然会降低运行速度，通常低10倍或更多。
  1. 在解释模式（interpreted mode）下运行 =》 再一定条件下编译成 本地执行的机械代码。 【<font color = "#ccd000">TODO</font> 什么意思】
4. -Xcomp：-Xcomp 参数与-Xint 正好相反，JVM 在第一次使用时会把所有的字节码编译成本地代码，从而带来最大程度的优化。【注意预热】
5. -Xmixed：-Xmixed 是混合模式，将解释模式和编译模式进行混合使用，有 JVM 自己决定，这是 JVM 的默认模式，也是推荐模式。 我们使用 java -version 可以看到 mixed mode 等信息。



<font color = "#ccd000">TODO</font> ：待补充相关实操图片



**解释模式：JVM 直接执行字节码组成的指令。**

-Xint 和 -Xcomp 是对立关系







### 3. 堆内存设置参数

<font color = "#fe1400">注意</font> ：涉及到内存的配置，需要根据实际情况大致估计。如下例子

>压力不大情况下，OOM， 4G 机器 =》 留可以使用：3.8G 内存
>
>1. （非堆+堆外） + JVM 需要：300M ~ 500M
>2. 给堆内的内存就3.2G ~ 3.3G =》-Xmx < 3.2G
>
>

<font color = "#fe1400">经验</font>

>①：尽量不要在线上做这种Java 应用程序的混合部署 
>
>​	（要求：同一个服务器，docker 运行，资源隔离，进程之间 不抢内存/资源）
>
>②：Xmx 最大值是操作系统内存的60% ~ 80%（不进行顶个配置）
>
>



<font color = "#fe1400">问题</font>

>1. 如果什么都不配置会如何？
>
>   1. 会默认值
>
>2. Xmx 是否与 Xms 设置相等？
>
>   1. 应该要设置相等，不相等的情况下，会堆内存扩容可能会导致性能抖动。
>
>      
>
>3. Xmx 设置为机器内存的什么比例合适？
>
>   1. 60~80%
>
>4. 作业：画一下 Xmx、Xms、Xmn、Meta、
>  DirectMemory、Xss 这些内存参数的关系
>
>  1. ![image-20220308161731052](JVM 核心技术--基础知识.assets/image-20220308161731052.png)



主要参数

><font color = "blue">-Xmx</font>, 指定最大堆内存。 如 -Xmx4g。这只是限制了 Heap 部分的最大值为4g。这个**内存不包括栈内存，也不包括堆外使用的内存**。
>
><font color = "blue">-Xms</font>, 指定堆内存空间的初始大小。 如 -Xms4g。 而且指定的内存大小，并不是操作系统实际分配的初始值，而是GC先规划好，用到才分配。 专用服务器上需要保持 –Xms 和 –Xmx 一致，否则应用刚启动可能就有好几个 FullGC。<font color = "red">当两者配置不一致时，堆内存扩容可能会导致性能抖动。</font>
>
>​	- 性能抖动的体现：未达到Xmx 大小是，前提进行多几次FullGC，导致STW 等相关效应。（这个STW 具体跟GC 算法有关，这里知识距离）
>
><font color = "blue">-Xmn</font>, 等价于 -XX:NewSize，使用 G1 垃圾收集器 不应该 设置该选项，在其他的某些业务场景下可以设置。官方建议设置为 -Xmx 的 1/2 ~ ¼。
>
>​	=》 处理年轻代 大小 
>
><font color = "blue">-XX</font>：MaxPermSize=size, 这是 JDK1.7 之前使用的。Java8 默认允许的Meta空间无限大，此参数无效。
>
><font color = "blue">-XX：MaxMetaspaceSize=size</font>, Java8 默认不限制 Meta 空间，**一般不允许设置该选项。**
>
><font color = "blue">-XX：MaxDirectMemorySize=size</font>，系统可以使用的最大堆外内存，这个参数跟 -Dsun.nio.MaxDirectMemorySize 效果相同。
>
>-<font color = "blue">Xss</font>, 设置每个<a href = "线程栈">线程栈</a>的字节数，影响栈的深度。 例如 -Xss1m 指定线程栈为1MB，与-XX:ThreadStackSize=1m 等价。[看H3： JVM <font color = "blue">线程栈</font>内存结构 的关系]
>
>



### 4. GC 设置参数

<font color = "#ccd000">TODO</font> ：问题：各个JVM 版本默认GC 是什么？

>
>
>



参数配置如下

><font color = "blue">-XX：+UseG1GC</font>：使用 G1 垃圾回收器
><font color = "blue">-XX：+UseConcMarkSweepGC</font>：使用 CMS 垃圾回收器
><font color = "blue">-XX：+UseSerialGC</font>：使用**串行**垃圾回收器
><font color = "blue">-XX：+UseParallelGC</font>：使用**并行**垃圾回收器
>// Java 11+
><font color = "blue">-XX：+UnlockExperimentalVMOptions -XX:+UseZGC </font>
>// Java 12+
><font color = "blue">-XX：+UnlockExperimentalVMOptions -XX:+UseShenandoahGC</font>





### 5. 分析诊断参数

><font color = "blue">-XX：+-HeapDumpOnOutOfMemoryError</font> 选项，当 OutOfMemoryError 产生，即内存溢出（堆内存或持久代) 时，自动 Dump 堆内存。
>	示例用法： java -XX:+HeapDumpOnOutOfMemoryError -Xmx256m ConsumeHeap
>
>
>
><font color = "blue">-XX：HeapDumpPath </font>选项，**与 HeapDumpOnOutOfMemoryError 搭配使用**，指定内存溢出时 Dump 文件的目录。
>如果没有指定则默认为启动 Java 程序的工作目录。
>	示例用法： java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/ ConsumeHeap  自动 Dump 的 hprof 文件会存储到 /usr/local/ 目录下。
>
>
>
><font color = "blue">-XX：OnError </font>选项，发生致命错误时（fatal error）执行的脚本。
>例如, 写一个脚本来记录出错时间, 执行一些命令，或者 curl 一下某个在线报警的 url。
>	示例用法：java -XX:OnError="gdb - %p" MyApp 可以发现有一个 %p 的格式化字符串，表示进程 PID。
>
>
>
><font color = "blue">-XX：OnOutOfMemoryError </font>选项，抛出 OutOfMemoryError 错误时执行的脚本。
><font color = "blue">-XX：ErrorFile=filename </font>选项，致命错误的日志文件名,绝对路径或者相对路径。
>-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1506，远程调试。 => 可配合idea 测试
>
>



### 6. JavaAgent 参数

可以通过无侵入方式来做很多事情，比如注入 AOP 代码，执行统计等等，权限非常大。

语法：

>-agentlib:libname[=options] 启用 native 方式的 agent，参考 LD_LIBRARY_PATH 路径。
>-agentpath:pathname[=options] 启用 native 方式的 agent。
>-javaagent:jarpath[=options] 启用外部的 agent 库，比如 pinpoint.jar 等等。
>-Xnoagent 则是禁用所有 agent。

以下示例开启 CPU 使用时间抽样分析：

>JAVA_OPTS="-agentlib:hprof=cpu=samples,file=cpu.samples.log"
