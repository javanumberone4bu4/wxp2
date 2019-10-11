package com.rimi.day01.tcp.practice3;



import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Wang Xiaoping
 * @date 2019/10/11 21:09
 */
public class Client {
    public static void main(String[] args) {
        new Client().start("10.1.0.17",9999);
    }
    public void start(String host,int  port){
        try {
            Socket socket=new Socket(host,port);
            new Thread(new ClientThread(socket)).start();
            PrintWriter writer=new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner=new Scanner(System.in);
            boolean flag=true;
            while(flag){
                String line = scanner.nextLine();
                writer.println(line);
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
