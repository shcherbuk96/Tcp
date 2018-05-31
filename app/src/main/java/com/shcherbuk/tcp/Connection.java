package com.shcherbuk.tcp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class Connection {
    String TAG="Main";

    private Socket socket;

    public void openConnection(Context context){
        closeConnection();
        try {
            socket=new Socket(Constants.SERVER_IP,Constants.SERVER_PORT);
            Log.e(TAG,"соединение установлено");
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        if(socket!=null && !socket.isClosed()){
            try {
                socket.close();
                Log.e(TAG,"соединение закрыто");
            } catch (IOException e) {
                e.printStackTrace();
            }   finally {
                socket=null;
            }
        }
        socket=null;
    }

    public void sendData(int data,Context context){
        if(socket==null ||socket.isClosed()){
            Log.e(TAG,"Сокет не создан или закрыт");
        }else{
            try {
                socket.getOutputStream().write(data);
                socket.getOutputStream().flush();
            } catch (IOException e) {
                Log.e(TAG,"Не возможно отправить данные" + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}
