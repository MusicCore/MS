package com.example.muscishow.until;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class radom {
    //随机姓名
    public static String randomName() {
        Random random = new Random();
        String str = "";
        int hightPos, lowPos;
        for (int i = 0; i < 4; ++i) {
            hightPos = (176 + Math.abs(random.nextInt(39)));
            lowPos = (161 + Math.abs(random.nextInt(93)));
            byte[] b = new byte[2];
            b[0] = (Integer.valueOf(hightPos)).byteValue();
            b[1] = (Integer.valueOf(lowPos)).byteValue();
            try {
                str += new String(b, "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
