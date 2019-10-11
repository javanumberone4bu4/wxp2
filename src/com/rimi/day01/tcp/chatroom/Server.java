package com.rimi.day01.tcp.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 服务端
 *
 * @author Wang Xiaoping
 * @date 2019/10/10 14:04
 */
public class Server {
    public static void main(String[] args) throws Exception {
        //创建serverSocket
        ServerSocket serverSocket=new ServerSocket(9999);
        //获取一个客户端
        Socket client = serverSocket.accept();
        //给客户端发送数据
        PrintWriter printWriter=new PrintWriter(client.getOutputStream(),true);
        printWriter.println("连接成功");
        //接收客户端发送的数据
        BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
        String line = reader.readLine();
        System.out.println(line);

        Scanner scanner=new Scanner(System.in);
        String line1 = scanner.nextLine();
        //发送数据
        printWriter.println(line1);

    }
}
