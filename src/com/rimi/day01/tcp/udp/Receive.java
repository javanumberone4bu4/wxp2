package com.rimi.day01.tcp.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 接收
 *  在java中使用udp协议进行网络通信,需要使用
 *  java.net.DatagramPacket 数据报包
 *  java.net.DatagramSocket 数据报套接字(使用接收和发送数据报包)
 *      3.创建一个数据报包
 *      4.读取数据,把数据读取到报包中
 *      5.释放资源
 * @author Wang Xiaoping
 * @date 2019/10/11 14:16
 */
public class Receive {
    public static void main(String[] args) throws IOException {
        //* 步骤:1.创建数据报套接字,给定该接收端的端口号
        DatagramSocket datagramSocket=new DatagramSocket(10010);
      //2.创建一个缓冲区,用于存放接收的数据
        byte[] bytes=new byte[1024];
        //3.创建一个数据报包
        DatagramPacket packet=new DatagramPacket(bytes,bytes.length);
        // 4.读取数据,把数据读取到报包中
        datagramSocket.receive(packet);
        //打印接收到的数据
        System.out.println("发送的数据为:"+new String(bytes,0,packet.getLength()));
        //释放资源
        datagramSocket.close();
    }
}
