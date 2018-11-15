package com.liuxd.learning.java.thread;

import java.util.Random;

/**
 * @author liuxiaodong
 * @date 2018/11/13
 * @description 如果子线程耗时较长，主线程执行完成早于子线程，但是需要等待子线程执行完成的时候，使用join
 */
public class ThreadJoinDemo {

    public static void main(String[] args) {
        executeWithOutJoin();
        executeWithJoin();
    }

    /**
     * 使用join让主线程等待子线程执行完成
     */
    private static void executeWithJoin() {
        System.out.println(Thread.currentThread().getName() + "主线程运行开始!");
        JoinThread aThread = new JoinThread("A");
        JoinThread bThread = new JoinThread("B");
        aThread.start();
        bThread.start();

        try {
            aThread.join();
            bThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "主线程运行结束!");
    }

    /**
     * 不使用join执行线程
     */
    private static void executeWithOutJoin() {
        System.out.println(Thread.currentThread().getName() + "主线程运行开始!");
        JoinThread aThread = new JoinThread("A");
        JoinThread bThread = new JoinThread("B");
        aThread.start();
        bThread.start();
        System.out.println(Thread.currentThread().getName() + "主线程运行结束!");
    }

    static class JoinThread extends Thread {

        private Random random = new Random();
        private String name;

        public JoinThread(String name) {
            super(name);
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
            for (int i = 0; i < 5; i++) {
                System.out.println("子线程" + name + "运行 : " + i);
                try {
                    sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
        }
    }
}
