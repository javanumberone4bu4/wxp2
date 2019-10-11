package com.rimi.day01.tcp.udp;

import java.io.IOException;
import java.net.*;

/**
 * 发送
 * 步骤:
 * 1.创建数据报套接字,指定该发送端的端口
 * 2.创建需要发送的数据
 * 3.创建数据报包,并指定发送的ip地址和端口号
 * 4.调用发送的方法
 * 5.释放资源
 * @author Wang Xiaoping
 * @date 2019/10/11 14:36
 */
public class Send {
    public static void main(String[] args) throws IOException {
        //1.创建数据报套接字,指定该发送端的端口
        DatagramSocket socket=new DatagramSocket(10086);
        //2.创建需要发送的数据
        String msg="Hello world!!!";
        //3.创建数据报包,并指定发送的ip地址和端口号
        DatagramPacket packet=new DatagramPacket(msg.getBytes(),msg.length(), InetAddress.getLocalHost(),10010);
        //4.调用发送的方法
        socket.send(packet);
        //5.释放资源
        socket.close();
    }
}
