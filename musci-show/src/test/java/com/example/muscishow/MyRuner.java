package com.example.muscishow;

public class MyRuner implements Runnable{
    @Override
    public void run() {
        //线程中执行代码
        //而run方法只是thread的一个普通方法调用，还是在主线程里执行
        //调用start方法方可启动线程
        for(int i=0;i<1000;i++){
            System.out.println("MyRuner:"+i);
        }
    }
}
