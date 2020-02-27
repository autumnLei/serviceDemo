package com.example.administrator.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.servicedemo.service.ForegroundService;
import com.example.administrator.servicedemo.service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private MyService myService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            myService = ((MyService.MyBinder)iBinder).getService();
        }

        /**
         * 注意 该方法只有后台服务被强制终止时间才会调用 自己接触绑定不会调用
         * @param componentName
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            myService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.close_service).setOnClickListener(this);
        findViewById(R.id.start_service).setOnClickListener(this);
        findViewById(R.id.bind_service).setOnClickListener(this);
        findViewById(R.id.unbind_service).setOnClickListener(this);
        findViewById(R.id.operate_service).setOnClickListener(this);
        findViewById(R.id.start_foreground_service).setOnClickListener(this);
        findViewById(R.id.stop_foreground_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_service:
                Intent intent_close = new Intent(MainActivity.this, MyService.class);
                stopService(intent_close);
                break;
            case R.id.start_service:
                Intent intent_start = new Intent(MainActivity.this, MyService.class);
                intent_start.putExtra("message", "启动后台服务");
                startService(intent_start);
                break;
            case R.id.bind_service:
                Intent intent_bind = new Intent(MainActivity.this, MyService.class);
                intent_bind.putExtra("message", "绑定后台服务");
                bindService(intent_bind, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(serviceConnection);
                //不设置为null 解除绑定服务终止也会保存一份实例 可以照常调用Service中的方法
                myService = null;
                break;
            case R.id.operate_service:
                if (myService == null){
                    return;
                } else {
                    String returnValue = myService.doSomeThing("test");
                    Log.d(TAG, "onClick: "+returnValue);
                }
                break;
            case R.id.start_foreground_service:
                Intent intent_start_foreground = new Intent(MainActivity.this, ForegroundService.class);
                startService(intent_start_foreground);
                break;
            case R.id.stop_foreground_service:
                Intent intent_stop_foreground = new Intent(MainActivity.this, ForegroundService.class);
                stopService(intent_stop_foreground);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //避免服务在退出客户端后还存活
        unbindService(serviceConnection);
        Intent intent_close = new Intent(MainActivity.this, MyService.class);
        stopService(intent_close);
        super.onDestroy();
    }
}
