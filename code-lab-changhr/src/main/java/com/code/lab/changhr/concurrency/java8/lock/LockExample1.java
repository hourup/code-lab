package com.code.lab.changhr.concurrency.java8.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * @author changhr2013
 * @date 2020/4/6
 */
public class LockExample1 {

    class Point {
        private double x, y;

        private final StampedLock stampedLock = new StampedLock();

        void move(double deltaX, double deltaY) {
            long stamp = stampedLock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                stampedLock.unlock(stamp);
            }
        }

        // 乐观锁读锁
        double distanceFromOrigin() {
            // 获得一个乐观读锁
            long stamp = stampedLock.tryOptimisticRead();
            // 将两个字段读入本地局部变量
            double currentX = x, currentY = y;
            // 检查发出乐观读锁后同时是否有其他写锁发生
            if (!stampedLock.validate(stamp)) {
                // 如果没有，我们再次获得一个读悲观锁
                stamp = stampedLock.readLock();
                try {
                    // 将两个字段读入本地局部变量
                    currentX = x;
                    currentY = y;
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        // 悲观锁读锁案例
        void moveIfAtOrigin(double newX, double newY) {
            long stamp = stampedLock.readLock();

            try {
                while (x == 0.0 && y == 0.0) {
                    // 将读锁转为写锁
                    long writeStamp = stampedLock.tryConvertToWriteLock(stamp);
                    if (writeStamp != 0L) {
                        stamp = writeStamp;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        stampedLock.unlockRead(stamp);
                    }
                }
            } finally {
                stampedLock.unlock(stamp);
            }
        }
    }
}
