package com.example.musicapi.common.until;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class Utils {
    public  String getTimeById(int id) throws Exception{
        String args1="python "+"F:\\smm\\Music\\musci-manger\\src\\main\\java\\com\\example\\muscimanger\\py\\b.py ";
        Process process=Runtime.getRuntime().exec(args1);
        BufferedReader in=new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
        String line;
        String result="";
        while ((line=in.readLine())!=null){
            result+=line;
        }
        in.close();
        process.waitFor();
        System.out.println(result);
        if (result.charAt(0)=='-'){
            return "";
        }
        return result;
    }
    public  void getList()throws Exception{
        for (int i=1314;i<1317;i++){
            getTimeById(i);
        }
    }
}
