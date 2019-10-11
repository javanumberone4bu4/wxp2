package com.rimi.day01.tcp.practice3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Wang Xiaoping
 * @date 2019/10/11 21:18
 */
public class ClientThread implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private boolean flag;


    ClientThread(Socket socket){
        try {
            this.socket=socket;
            this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.flag=true;
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
        String msg=null;
        try {
            if((msg=in.readLine())!=null){
                if("退出成功".equals(msg)){
                    flag=false;
                }else{
                    System.out.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
