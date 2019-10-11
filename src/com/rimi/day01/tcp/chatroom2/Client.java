package com.rimi.day01.tcp.chatroom2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * @author shangzf
 * @date 2019/10/10 14:37
 */
public class Client {

    public void start(String host, int port) {
        Socket client = null;
        try {
            // 创建客户端
            client = new Socket(host, port);
            // 创建一个线程,用于接收服务端发送的消息
            ClientThread thread = new ClientThread(client);
            thread.start();
            // 向服务器发送数据
            String msg = "";
            do{
                // 接收控制台录入数据
                Scanner scanner = new Scanner(System.in);
                // 读取一行
                msg = scanner.nextLine();
                // 发送到服务器
                PrintWriter writer = new PrintWriter(client.getOutputStream(),true);
                writer.println(msg);
            } while(!"quit".equals(msg));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ClientThread extends Thread {
        private Socket client;
        private BufferedReader reader;

        ClientThread(Socket client) {
            try {
                this.client = client;
                this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            // 获取消息,并打印到控制台
            String msg = "";
            do {
                // 接收消息
                msg = read();
                System.out.println(msg);
            } while (msg != null && !"退出成功".equals(msg));
        }

        private String read() {
            String msg = "";
            try {
                msg = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return msg;
        }
    }
}
