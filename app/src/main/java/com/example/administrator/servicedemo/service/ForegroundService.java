package com.example.administrator.servicedemo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.example.administrator.servicedemo.R;

public class ForegroundService extends Service {
    public ForegroundService() {
    }

    @Override
    public void onCreate() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("ForegroundService");
        builder.setContentText("ForegroundService正在运行");
        if(Build.VERSION.SDK_INT >= 26) {
            createChannel();
            builder.setChannelId("channel_01");
        }
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        startForeground(1, notification);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api=26)
    public void createChannel(){
        /**
         * 创建通知渠道1
         */
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //渠道id
        String id = "channel_01";
        //用户可以看到的通知渠道的名字
        String name = "前台通知";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        //配置通知渠道的属性
        channel.setDescription("前台通知的专用渠道");
        //设置通知出现时的闪灯（如果android设备支持的话）
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        //在notificationmanager中创建该通知渠道
        manager.createNotificationChannel(channel);
    }
}
