package com.rimi.day01.tcp.chatroom2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 服务端线程
 *
 * @author shangzf
 * @date 2019/10/10 14:44
 */
public class ServerThread extends Thread {
    /**
     * 存储聊天是中的客户端
     */
    private static final Map<String, Socket> CLIENT_SOCKETS = new HashMap<>();

    private Socket client;
    private String ip;
    private BufferedReader in;
    private PrintWriter writer;

    ServerThread(Socket client) {
        try {
            this.client = client;
            // 获取客户端的IP
            this.ip = client.getInetAddress().getHostAddress();
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.writer = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通知其他人上线了
        send("上线了");
        // 把客户端存到列表中
        CLIENT_SOCKETS.put(ip, client);
    }

    @Override
    public void run() {
        // 读取客户端发送的消息,并把消息转发到所有的客户端中
        String msg = "";
        do {
            // 接收消息
            msg = read();
            // 发消息
            if(msg.contains("@")&&msg.contains(":")){
                String s = msg.split("@")[1].split(":")[0];
                String s1 = msg.split("@")[1].split(":")[1];
                Socket socket = find(msg, s);
                if(socket!=null){
                    sendSingle(s1,s);
                }else{
                    send(msg);
                }

            }else {
                send(msg);
            }
        } while (msg != null && !"quit".equals(msg));
        close();
    }

    /**
     * 读取客户端发送的消息
     *
     * @return
     */
    public String read() {
        String msg = "";
        try {
            msg = in.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
           close();
        }
        return msg;
    }
   public void close(){
        if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(client!=null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }
    public void send(String msg) {
        if (msg != null && !"".equals(msg)) {
            // 发送给所有人
            Iterator<Socket> iterator = CLIENT_SOCKETS.values().iterator();
            while (iterator.hasNext()) {
                Socket socket = iterator.next();
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    // 如果 客户端发送的消息是"quit",则告知其他人它以退出
                    if ("quit".equals(msg)) {
                        if (socket == this.client) {
                            writer.println("退出成功");
                            // 在在线列表中移除
                            CLIENT_SOCKETS.remove(this.ip);
                        } else {
                            writer.println("[" + this.ip + "]: 下线了");
                        }
                    } else {
                        writer.println("[" + this.ip + "]: " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendSingle(String msg,String ip1){
        if (msg != null && !"".equals(msg)) {
            // 发送给所有人
            Iterator<Socket> iterator = CLIENT_SOCKETS.values().iterator();
            while (iterator.hasNext()) {
                Socket socket = iterator.next();
                try {
                   if(socket.getInetAddress().getHostAddress().equals(ip1)) {
                       PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                       writer.println("[" + this.ip + "]私发: " + msg);
                       break;
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Socket find(String msg,String ip1){
        if (msg != null && !"".equals(msg)) {
            Iterator<Socket> iterator = CLIENT_SOCKETS.values().iterator();
            while(iterator.hasNext()){
                Socket socket = iterator.next();
                if(socket.getInetAddress().getHostAddress().equals(ip1)) {
                    return socket;
                }
            }
        }
        return null;
    }
}
