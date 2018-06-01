package com.shcherbuk.tcp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    String TAG="Main";

    private Socket socket;

    public void openConnection(final Context context){
        closeConnection();
        try {
            socket=new Socket(Constants.SERVER_IP,Constants.SERVER_PORT);
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "AAAA", Toast.LENGTH_SHORT).show();
                }
            };
            handler.post(runnable);
            Log.e(TAG,"Соединение установлено");
        } catch (final IOException e) {
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Соединение не установлено" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
            handler.post(runnable);
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

    public void sendData(int data, final Context context){
        if(socket==null ||socket.isClosed()){
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Сокет не создан или закрыт", Toast.LENGTH_SHORT).show();
                }
            };
            handler.post(runnable);
            Log.e(TAG,"Сокет не создан или закрыт");
        }else{
            try {
                socket.getOutputStream().write(data);
                socket.getOutputStream().flush();
                Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Данные отправлены", Toast.LENGTH_SHORT).show();
                    }
                };
                handler.post(runnable);
            } catch (final IOException e) {
                Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Не возможно отправить данные" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                };
                handler.post(runnable);
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
