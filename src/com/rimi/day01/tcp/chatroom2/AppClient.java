package com.rimi.day01.tcp.chatroom2;

/**
 * @author Wang Xiaoping
 * @date 2019/10/10 15:33
 */
public class AppClient {
    public static void main(String[] args) {
        new Client().start("10.1.0.17",9999);

    }
}
