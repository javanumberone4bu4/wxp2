package com.rimi.day01.tcp.practice2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author Wang Xiaoping
 * @date 2019/10/11 20:21
 */
public class Client {
    public void start(String host,int port){
        try {
            Socket socket=new Socket(host,port);
            new Thread(new ClientThread(socket)).start();
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in);
            boolean flag=true;
            while(flag){
                String line = scanner.nextLine();
                out.println(line);
                if("quit".equalsIgnoreCase(line)){
                    flag=false;
                }
            }
            System.out.println("你已经退出");
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
