package com.code.lab.changhr.concurrency.mooc.section2;

import java.util.LinkedList;

/**
 * @author changhr
 * @create 2020-03-27 17:14
 */
public class WaitNotifyClient {

    public static void main(String[] args) {
        LinkedList<Task> tasks = new LinkedList<>();

        Student ming = new Student("小明", tasks);
        ming.start();

        Student wang = new Student("小王", tasks);
        wang.start();

        Teacher li = new Teacher("李老师", tasks);
        li.start();

        Teacher zhang = new Teacher("张老师", tasks);
        zhang.start();
    }

}
