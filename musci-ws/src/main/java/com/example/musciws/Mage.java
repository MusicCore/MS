package com.example.musciws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
public class Mage {
    private static ObjectMapper gson = new ObjectMapper();
    /**
     * 那个聊天室
     */
    private String table;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 所发送的消息
     */
    private String message;

}
