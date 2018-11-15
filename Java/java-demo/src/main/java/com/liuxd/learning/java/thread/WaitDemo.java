package com.liuxd.learning.java.thread;

/**
 * @author liuxiaodong
 * @date 2018/11/13
 * @description 建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC
 */
public class WaitDemo {

    private static final int COUNT = 10;

    public static void main(String[] args) {
        NameObject aLock = new NameObject("a");
        NameObject bLock = new NameObject("b");
        NameObject cLock = new NameObject("c");
        WaitThread aThread = new WaitThread("A", cLock, aLock);
        WaitThread bThread = new WaitThread("B", aLock, bLock);
        WaitThread cThread = new WaitThread("C", bLock, cLock);
        aThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cThread.start();
    }

    static class WaitThread extends Thread {
        private String name;
        private NameObject preLock;
        private NameObject selfLock;

        public WaitThread(String name, NameObject preLock, NameObject selfLock) {
            super(name);
            this.name = name;
            this.preLock = preLock;
            this.selfLock = selfLock;
        }

        @Override
        public void run() {
            int count = COUNT;
            while (count > 0) {
                System.out.println(getName() + " 需要锁:" + preLock.getName());
                synchronized (preLock) {
                    System.out.println(getName() + " 锁定:" + preLock.getName());
                    System.out.println(getName() + " 需要锁:" + selfLock.getName());
                    synchronized (selfLock) {
                        System.out.println(getName() + " 锁定:" + selfLock.getName());
                        System.out.println("=>" + getName());

                        count--;

                        selfLock.notify();
                        System.out.println(getName() + " 释放锁:" + selfLock.getName());
                    }

                    if(count > 0){
                        try {
                            preLock.wait();
                            System.out.println(getName() + " 获得锁:" + preLock.getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class NameObject {

        private String name;

        public NameObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
