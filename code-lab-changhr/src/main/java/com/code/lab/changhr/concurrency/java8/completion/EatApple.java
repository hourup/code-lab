package com.code.lab.changhr.concurrency.java8.completion;

import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author changhr
 * @create 2020-04-01 11:32
 */
public class EatApple {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(8);
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(pool);

        Stream.of("苹果", "梨", "葡萄", "桃")
                .forEach(fruit -> service.submit(() ->{
                    if (fruit.equals("苹果")){
                        TimeUnit.SECONDS.sleep(6);
                    }else if (fruit.equals("梨")) {
                        TimeUnit.SECONDS.sleep(1);
                    }else if (fruit.equals("葡萄")) {
                        TimeUnit.SECONDS.sleep(10);
                    }else if (fruit.equals("桃")) {
                        TimeUnit.SECONDS.sleep(3);
                    }
                    return "洗干净的" + fruit;
                }));

        String result;
        while ((result = service.take().get()) != null) {
            System.out.println("吃掉" + result);
        }
    }

}
