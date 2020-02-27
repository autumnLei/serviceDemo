package com.example.administrator.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    public static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand:: message:"+intent.getStringExtra("message")
                +",flags:"+flags+",startId:"+startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: "+intent.getStringExtra("message"));

        return new MyBinder();
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        //return super.onUnbind(intent);

        //返回true再次绑定服务会跳过onBind执行onRebind方法
        return true;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();

    }

    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    public String doSomeThing(String param){
        Log.d(TAG, "doSomeThing: "+param);
        return "从MyService返回的数据";
    }
}
