package com.rimi.day01.tcp.chatroom2;

/**
 * @author Wang Xiaoping
 * @date 2019/10/10 15:32
 */
public class AppServer {
    public static void main(String[] args) {
         new Server().start(9999);
    }
}
