package com.rimi.day01.tcp.practice2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 客户端线程类
 *
 * @author Wang Xiaoping
 * @date 2019/10/11 20:29
 */
public class ClientThread implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private boolean flag;
    ClientThread(Socket socket){
        this.socket=socket;
        try {
            this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

    private void receive() throws IOException {
        String msg=null;
        if((msg=reader.readLine())!=null){
            if("退出成功".equals(msg)){
                flag=false;
            }else{
                System.out.println(msg);
            }
        }

    }
}
