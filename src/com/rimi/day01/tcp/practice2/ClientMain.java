package com.rimi.day01.tcp.practice2;

/**
 * @author Wang Xiaoping
 * @date 2019/10/11 20:39
 */
public class ClientMain {
    public static void main(String[] args) {
        new Client().start("10.1.0.17",9999);
    }
}
