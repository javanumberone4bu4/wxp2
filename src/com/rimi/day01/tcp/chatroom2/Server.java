package com.rimi.day01.tcp.chatroom2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shangzf
 * @date 2019/10/10 14:37
 */
public class Server {

    /**
     * 启动服务端的方法
     */
    public void start(int port) {
        ServerSocket serverSocket = null;
        try {
            // 创建服务端
            serverSocket = new ServerSocket(port);
            // 循环获取客户端
            while(true){
                // 获取客户端
                Socket client = serverSocket.accept();
                // 创建一个服务端线程,用于处理每一个客户端的请求
                ServerThread thread = new ServerThread(client);
                // 开启线程
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    // 关闭服务端
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
