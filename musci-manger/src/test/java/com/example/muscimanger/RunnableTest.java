package com.example.muscimanger;

/**
 * @Author: yuanci
 * @Date: 2019/4/11
 * @Version: 1.0
 * @Description:
 */
public class RunnableTest {
    public static void main(String args[]){
        Runnable r1 = new MyRunable();
        Runnable r2 = new MyRunable();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
    }
}
class MyRunable implements Runnable{
    int i = 0;
    @Override
    public void run() {
        for ( i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+ " " +i);
            try {
//                Thread.sleep(1000);
                if(i==3) Thread.yield();
            }catch (Exception e){

            }
        }
    }
}