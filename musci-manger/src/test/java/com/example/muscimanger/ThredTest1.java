package com.example.muscimanger;

/**
 * @Author: yuanci
 * @Date: 2019/4/11
 * @Version: 1.0
 * @Description:
 */
public class ThredTest1 {

    public static void main(String args[]){
        Thread t1 = new MyThred();
        Thread t2 = new MyThred();
        t1.start();
        t2.start();
    }
}
class MyThred extends Thread{
    private int j = 0;

    @Override
    public void run() {
        for (j = 0; j < 5; j++) {
            System.out.println(Thread.currentThread().getName()+ " " +j);
        }
    }
}
