package com.code.lab.changhr.concurrency.java8.cfuture;
/*
* 本包演示 CompletableFuture 类的使用
*
* CompletableFuture 实现了 Future 接口。
*
* Stream 和 CompletableFuture 的设计都遵循了类似的模式：
* 它们都使用了 Lambda 表达式以及流水线的思想。
* CompletableFuture 和 Future 的关系就跟 Stream 和 Collection 的关系一样。
*
* 调整线程池的大小
*
* 调整线程池的大小《java 并发编程实战》一书中，Brian Goetz 和合著者们为线程池大小的优化提供了不少中肯的建议。
* 这非常重要，如果线程池中线程的数量过多，
* 最终它们会竞争稀缺的处理器和内存资源，浪费大量的时间在上下文切换上。
* 反之，如果线程的数目过少，正如你的应用所面临的情况,处理器的一些核可能就无法充分利用。
*
* Brian Goetz 建议，线程池大小与处理器的利用率之比可以使用下面的公式进行估算：
*
* N(threads) = N(CPU) * U(CPU) * (1 + W/C)
*
* 其中:
* N(threads) 是处理器的核的数目,可以通过 Runtime.getRuntime().availableProcessors() 得到
* U(CPU) 是期望的 CPU 利用率(该值应该介于 0 和 1 之间)
* W/C 是等待时间与计算时间的比率
*
*/