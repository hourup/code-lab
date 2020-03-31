package com.code.lab.changhr.concurrency.java8.cfuture.onjava;

import com.code.lab.changhr.concurrency.utils.Delay;

/**
 * @author changhr2013
 * @date 2020/3/31
 */
public class MaterialStore {

    private MaterialStore() {
    }

    public static class Batter {
        public Batter(Eggs eggs, Milk milk, Sugar sugar, Flour flour) {
            if (eggs.isPrep() && milk.isPrep() && sugar.isPrep() && flour.isPrep()) {
                Integer delay = Delay.delay(1000);
                System.out.println("搅拌材料耗时：" + delay + "ms");
                this.prep = true;
            } else {
                throw new RuntimeException("材料没准备好");
            }
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class Eggs {
        public Eggs() {
            Integer delay = Delay.delay(1000);
            System.out.println("准备 Eggs 耗时：" + delay + "ms");
            this.prep = true;
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class Milk {
        public Milk() {
            Integer delay = Delay.delay(1000);
            System.out.println("准备 Milk 耗时：" + delay + "ms");
            this.prep = true;
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class Sugar {
        public Sugar() {
            Integer delay = Delay.delay(1000);
            System.out.println("准备 Sugar 耗时：" + delay + "ms");
            this.prep = true;
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class Flour {

        public Flour() {
            Integer delay = Delay.delay(1000);
            System.out.println("准备 Flour 耗时：" + delay + "ms");
            this.prep = true;
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class Baked {

        public Baked(Batter batter) {
            if (batter.isPrep()) {
                Integer delay = Delay.delay(1000);
                System.out.println("烘烤耗时：" + delay + "ms");
                this.prep = true;
            } else {
                throw new RuntimeException("Batter 没准备好");
            }
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class Frosting {

        public Frosting() {
            Integer delay = Delay.delay(1000);
            System.out.println("准备奶油耗时：" + delay + "ms");
            this.prep = true;
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }

    public static class FrostCake {

        public FrostCake(Frosting frosting, Baked baked) {
            if (frosting.isPrep() && baked.isPrep()) {
                Integer delay = Delay.delay(1000);
                System.out.println("制作蛋糕耗时：" + delay + "ms");
                this.prep = true;
            } else {
                throw new RuntimeException("Frosting 或 Baked 没准备好！");
            }
        }

        private boolean prep;

        public boolean isPrep() {
            return prep;
        }
    }
}
