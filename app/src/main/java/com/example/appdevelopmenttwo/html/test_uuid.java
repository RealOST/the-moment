package com.example.appdevelopmenttwo.html;

import java.util.UUID;

public class test_uuid {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        String id = uuid.toString().replace("-","");
        System.out.println(id);
    }
}
