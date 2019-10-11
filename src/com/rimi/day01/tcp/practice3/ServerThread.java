package com.rimi.day01.tcp.practice3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Xiaoping
 * @date 2019/10/11 20:49
 */
public class ServerThread implements Runnable {
    private static final Map<String, Socket> SOCKET_CLIENT=new HashMap<>();
    private Socket socket;
    private String ip;
    private BufferedReader in;
    private PrintWriter out;
    private boolean flag=true;

    ServerThread(Socket socket){
        try {
            this.socket=socket;
            this.ip=socket.getInetAddress().getHostAddress();
            this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out=new PrintWriter(socket.getOutputStream(),true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("欢迎光临天上人间");
        sendAll("上线了");
        SOCKET_CLIENT.put(ip,socket);
    }

    private void sendAll(String msg) {
        for (Socket client : SOCKET_CLIENT.values()) {
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
            while(flag){
                receive();
            }
        }catch (Exception e){
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

    private void receive() {
        String str=null;
        try {
            if((str=in.readLine())!=null){
                if("quit".equalsIgnoreCase(str)){
                        stop();
                        out.println("退出成功");
                }else{
                    if(str.startsWith("@")&&str.contains(":")){
                        String chatIp = str.substring(1, str.indexOf(":"));
                        String content = str.substring(str.indexOf(":") + 1);
                        Socket socket = SOCKET_CLIENT.get(chatIp);
                        if(socket!=null){
                            sendOne(socket,"["+this.ip+"]悄悄地对您说:"+content);
                        }else {
                            sendAll("["+this.ip+"]:"+str);
                        }
                    }else{
                        sendAll("["+this.ip+"]:"+str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        flag=false;
        sendAll("下线了");
        SOCKET_CLIENT.remove(this.ip);
    }
}
