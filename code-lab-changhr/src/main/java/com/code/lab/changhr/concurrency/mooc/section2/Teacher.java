package com.code.lab.changhr.concurrency.mooc.section2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author changhr
 * @create 2020-03-27 14:51
 */
public class Teacher extends Thread {

    private String name;

    private List<String> punishWords = Arrays.asList("internationalization", "hedgehog", "penicillin", "oasis", "nirvana", "miserable");

    private final LinkedList<Task> tasks;

    private int MAX = 10;

    public Teacher(String name, LinkedList<Task> tasks) {
        // 调用 Thread 构造方法，设置线程名
        super(name);
        this.name = name;
        this.tasks = tasks;
    }

    public void arrangePunishment() throws InterruptedException {

        String threadName = Thread.currentThread().getName();

        while (true) {
            synchronized (tasks) {
                if (tasks.size() < MAX) {
                    Task task = new Task(new Random().nextInt(3) + 1, getPunishedWord());
                    tasks.add(task);
                    System.out.println(threadName + " 留了作业，抄写 " + task.getWordToCopy() + " " + task.getLeftCopyCount() + " 遍");
                    tasks.notifyAll();
                } else {
                    System.out.println(threadName + " 开始等待");
                    tasks.wait();
                    System.out.println("teacher 线程 " + threadName + "线程 - " + name);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            arrangePunishment();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPunishedWord() {
        return punishWords.get(new Random().nextInt(punishWords.size()));
    }
}
