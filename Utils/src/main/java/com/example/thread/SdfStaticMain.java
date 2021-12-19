package com.example.thread;

import com.example.utils.DateFormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 测试静态sdf工具类的调用效率
 * 开启100个线程，每个线程调用方法10万次
 * 结果：静态线程安全的方式更好，创建的方式执行时间波动较大，但是还是没前者快，且后者内存消耗更多，建议使用前者。
 * @author huanliu
 */
public class SdfStaticMain {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        MyThread myThread = new MyThread();
        MyThread2 myThread2 = new MyThread2();
        for (int i = 0; i < 100; i++){
            Thread t = new Thread(myThread,"Thread" + i);
            //Thread t = new Thread(myThread2,"Thread" + i);
            t.start();
        }
        //等待所有子线程执行完后才继续向下执行
        MyThread.count.await();
        System.out.println((System.currentTimeMillis()-start) + "ms");
    }

    /**
     * 使用静态，线程安全的方式格式化数据
     */
    static class MyThread implements Runnable {
        static CountDownLatch count = new CountDownLatch(100);

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++){
                DateFormatUtil.format("yyyy-MM-dd HH:mm:ss", new Date());
            }
            count.countDown();
        }
    }

    /**
     * 使用创建simpledateformat的方式格式化数据
     */
    static class MyThread2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.format(new Date());
            }
            MyThread.count.countDown();
        }
    }


}
