package com.code.lab.changhr.concurrency.java8.forkjoin;

import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * 假如让你计算 1-10000 的和，我们可以把任务拆解为 100 个，每个任务计算 100 个数字之和。
 *
 * @author changhr
 * @create 2020-04-01 16:05
 */
public class Task extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 100;

    private int from;

    private int to;

    public Task(int from, int to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {

        if (THRESHOLD > (to - from)) {
            return IntStream.range(from, to + 1)
                    .reduce(Integer::sum)
                    .getAsInt();
        } else {
            int forkNumber = (from + to) / 2;
            Task left = new Task(from, forkNumber);
            Task right = new Task(forkNumber + 1, to);

            left.fork();
            right.fork();
            return left.join() + right.join();
        }
    }
}
