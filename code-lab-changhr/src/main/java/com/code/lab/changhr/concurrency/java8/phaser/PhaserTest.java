package com.code.lab.changhr.concurrency.java8.phaser;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 阶段间串行，阶段内并行。
 *
 * 我们设想如下场景：期末考试到了，软件学院三个班共有 60 个学生一起参加考试，
 * 全部交卷后，有 3 个老师做判卷的工作，再由 3 位辅导员公布成绩。
 *
 * 这个过程中分为三个阶段：
 *
 * 学生考试，参与者 50
 * 老师判卷，参与者 3
 * 辅导员公布成绩，参与者 3
 *
 * 这个过程使用一个 CyclicBarrier 是无法实现的，因为 CyclicBarrier 的参与者数量无法变化。
 * 为了日志的简洁，下面的代码只模拟 10 个学生考试：
 *
 * @author changhr
 * @create 2020-04-01 11:55
 */
public class PhaserTest {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Phaser phaser = new Phaser();

        // 主线程注册
        // 因为主线程要使用 phaser 来控制流程，他也是参与者之一
        phaser.register();

        System.out.println("开始考试...");

        // 10 个学生线程分别启动开始考试，然后交卷，交卷后通知 phaser 已到达并且注销
        IntStream.rangeClosed(1, 10).forEach(
                number -> {
                    // 注册参与者
                    phaser.register();
                    Thread student = new Thread(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("学生 " + number + " 交卷");
                        phaser.arriveAndDeregister();
                    });
                    student.start();
                }
        );

        // 学生并行考试时，主线程会先执行到此代码，但由于本 phase 还没有达成，所以阻塞在此
        phaser.arriveAndAwaitAdvance();
        System.out.println("考试结束");

        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");

        System.out.println("开始判卷...");
        // 所有学生达成后，开始新的 phase，下面启动三个老师线程，开始判卷
        IntStream.rangeClosed(1, 3).forEach(
                number -> {
                    phaser.register();
                    Thread teacher = new Thread(
                            () -> {
                                try {
                                    TimeUnit.SECONDS.sleep(3);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("老师 " + number + " 判卷完成");
                                phaser.arriveAndDeregister();
                            }
                    );
                    teacher.start();
                }
        );

        // 老师判卷时，逐线程会先执行到此行代码，但由于本 phase 还没有达成，所以阻塞在此
        phaser.arriveAndAwaitAdvance();
        System.out.println("判卷结束");

        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");

        System.out.println("开始公布成绩...");
        // 所有老师都达成后，开始新的 phase，下面启动三个辅导员线程，公布成绩
        IntStream.rangeClosed(1, 3).forEach(
                number -> {
                    phaser.register();

                    Thread counsellor = new Thread(
                            () -> {
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("辅导员 " + number + " 公布成绩完成");
                                phaser.arriveAndDeregister();
                            }
                    );

                    counsellor.start();
                }
        );

        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
        System.out.println("公布成绩结束");
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
        stopWatch.stop();
    }

}
