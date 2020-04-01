package com.code.lab.changhr.concurrency.java8.phaser;
/*
* Phaser
* 从名称可以看出，它对线程协同的重点是任务阶段。phaser 中维护了所处阶段的数值。
*
* Phaser 相比 CyclicBarrier 更为灵活，这体现在它对参与者的动态增减。
* 并且参与者可以选择到达屏障点后是否阻塞。
*
* 两个概念：
* 1. phase。phaser 对阶段进行管理，而 phase 就是阶段，可以是阶段 1、阶段 2、阶段 3……
*    当所有的参与者到达某个阶段屏障点时，phaser 会进入下一阶段。
*
* 2. party。参与者，Phaser r 中会记录参与者的数量，可以通过 register 方法来添加，
*    或者通过 arriveAndDeregister 来注销。
*
* 使用：
* 1. new Phaser(): 创建新的 Phaser，并且参与者为 0。
* 2. phaser.register(): 增加参与者。
* 3. phaser.arriveAndDeregister(): 参与者到达，并且注销掉参与者。这个方法不会阻塞。
* 4. phaser.arriveAndAwaitAdvance(): 阻塞等待阶段达成。
*
* */