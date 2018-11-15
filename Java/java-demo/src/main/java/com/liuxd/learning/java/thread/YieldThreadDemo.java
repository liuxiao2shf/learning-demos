package com.liuxd.learning.java.thread;

/**
 * @author liuxiaodong
 * @date 2018/11/13
 * @description 在执行yield方法时，会让出CPU时间，让其他或者自己线程执行（谁先抢到谁执行）
 */
public class YieldThreadDemo {

    public static void main(String[] args) {
        YieldThread aThread = new YieldThread("A");
        YieldThread bThread = new YieldThread("B");
        aThread.start();
        bThread.start();
    }

    static class YieldThread extends Thread {

        private String name;

        public YieldThread(String name) {
            super(name);
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 50; i++) {
                System.out.println(getName() + " " + i);
                if (i == 30) {
                    yield();
                }
            }
        }
    }
}
