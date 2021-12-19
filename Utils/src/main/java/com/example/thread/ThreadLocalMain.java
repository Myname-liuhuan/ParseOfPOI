package com.example.thread;

/**
 * 用来测试ThreadLocal类
 * @author huanliu
 */
public class ThreadLocalMain {
    //private static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
    private ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    /*static{
        stringThreadLocal.set("default");
    }*/

    public ThreadLocalMain(){
        stringThreadLocal.set("default");
    }

    private String getLocal(){
        return stringThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalMain localMain = new ThreadLocalMain();

        System.out.println(localMain.getLocal());

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(localMain.getLocal());
            }
        }).start();
    }
}
