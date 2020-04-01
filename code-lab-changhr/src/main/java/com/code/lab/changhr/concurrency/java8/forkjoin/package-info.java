package com.code.lab.changhr.concurrency.java8.forkjoin;
/*
* Fork/Join
*
* Excutor 的另外一种实现 ForkJoinPool。
*
* ForkJoinPool 的核心功能有两个：
* 第一个是 Fork，拆解你的任务。
* 第二个是 Join，合并任务的执行结果。
*
* 这个场景很常见，比如我们要处理一批数据，由于数据间没有依赖性，
* 那么我们可以把这一批数据拆解为更小的批次，多线程并行处理。
* 最后再合并处理的结果。
* Fork/Join 的核心思想就是分而治之。
*
* ForkJoinPool 用来把大任务切分为小任务，
* 如果切分完小任务还不够小（由你设置的阈值决定），那么就继续向下切分。
* 经过切分后，最后的任务是金字塔形状，计算完成后向上汇总。
*
* https://img03.sogoucdn.com/app/a/100520146/bcc11dadd3e6d1a8aa27a7d467642cbe
*
* ForkJoinPool 通过 submit 执行 ForkJoinTask 类型的任务。
* ForkJoinTask 是抽象类，有着不同的子类实现。
*
* 比较常用的是如下两种：
* 1、RecursiveAction，没有返回值；
* 2、RecurisiveTask，有返回值。
*
* 此外 submit 方法还可以执行 Callable 和 Runnable 的接口实现。
*
* ForkJoinPool 中的每个线程都维护自己的工作队列。这是一个双端队列，既可以先进先出，也可以先进后出。
* ForkJoinPool 通过任务窃取，使得任务的执行更为高效。
*
*/