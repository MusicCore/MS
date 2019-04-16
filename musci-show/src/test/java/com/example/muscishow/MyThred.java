package com.example.muscishow;

public class MyThred extends Thread {
    public void run(){
        for (int i = 1000;i>0;i--){
            System.out.println("MyThred:"+i);
        }
    }
}
