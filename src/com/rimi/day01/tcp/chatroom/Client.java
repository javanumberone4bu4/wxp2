package com.rimi.day01.tcp.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author Wang Xiaoping
 * @date 2019/10/10 14:11
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //获取客户端套接字
        Socket socket=new Socket("localhost",9999);
        //接收服务端发送数据
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //读取数据
        reader.readLine();
        //发送给服务端
        PrintWriter writer=new PrintWriter(socket.getOutputStream());
        //从控制台录入数据
        String s=new Scanner(System.in).nextLine();
        //输出给服务器
        writer.println(s);

    }
}
