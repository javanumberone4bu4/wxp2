package com.rimi.day01.tcp.practice3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author Wang Xiaoping
 * @date 2019/10/11 20:42
 */
public class Server {
    public static void main(String[] args) {
        new Server().start(9999);
    }
    public void start(int port){
        ServerSocket socket=null;
        try {
             socket=new ServerSocket(port);
             while (true){
                 Socket client = socket.accept();
                 new Thread(new ServerThread(client)).start();
             }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
