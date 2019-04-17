package com.example.muscishow;

import java.util.ArrayList;
import java.util.Iterator;

public class Serach implements Runnable {
    ArrayList<Phone> phones;
    String name;
    Serach( ArrayList<Phone> phones,String name){
        this.phones=phones;
        this.name=name;
    }
    @Override
    public void run() {
        Iterator iterator = phones.iterator();
        while(iterator.hasNext()){
            Phone phone = (Phone) iterator.next();
            if (phone.getName().equals(name)){
                System.out.println("id:"+phone.getId()+" name:"+phone.getName()+" num:"+phone.getNum());
                return;
            }
        }
    }
}
