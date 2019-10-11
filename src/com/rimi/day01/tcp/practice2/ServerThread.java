package com.rimi.day01.tcp.practice2;

import com.sun.org.apache.xpath.internal.functions.FuncSubstringBefore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务端线程类
 *
 * @author Wang Xiaoping
 * @date 2019/10/11 19:54
 */
public class ServerThread implements Runnable {
private static final Map<String, Socket> B_MAP=new HashMap<>();
private Socket socket;
private String ip;
private PrintWriter writer;
private BufferedReader reader;
private boolean flag=true;

ServerThread(Socket socket){
    try {
        this.socket=socket;
        this.ip=socket.getInetAddress().getHostAddress();
        this.writer=new PrintWriter(socket.getOutputStream(),true);
        this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

    } catch (IOException e) {
        e.printStackTrace();
    }
    writer.println("欢迎登录聊天室!!!");
    sendAll("上线了");
    B_MAP.put(ip,socket);
}

    private void sendAll(String msg) {
        for (Socket client : B_MAP.values()) {
            sendOne(socket,msg);
        }
    }

    private void sendOne(Socket socket, String msg) {
        try {
            new PrintWriter(socket.getOutputStream(),true).println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
       try {
           while (flag){
               receive();
           }
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           if(socket!=null){
               try {
                   socket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
    }

    private void receive() throws IOException {
     String msg=null;
     if((msg=reader.readLine())!=null){
         if("quit".equalsIgnoreCase(msg)){
             stop();
             writer.println("退出成功");
         }else{
             if(msg.startsWith("@")&&msg.contains(":")){
                 String chatIp = msg.substring(1, msg.indexOf(":"));
                 String content = msg.substring(msg.indexOf(":") + 1);
                 Socket socket = B_MAP.get(chatIp);
                 if(socket!=null){
                     sendOne(socket,"["+this.ip+"]悄悄滴对您说:"+content);
                 }else{
                     sendAll("["+this.ip+"]:"+msg);
                 }
             }else{
                 sendAll("["+this.ip+"]:"+msg);
             }
         }
     }
    }

    private void stop() {
        flag=false;
        B_MAP.remove(this.ip);
       sendAll("["+this.ip+"]:下线了");
    }
}
