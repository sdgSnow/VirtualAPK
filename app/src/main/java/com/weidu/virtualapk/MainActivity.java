package com.weidu.virtualapk;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_plugin = findViewById(R.id.bt_plugin);
        Button load_plugin = findViewById(R.id.load_plugin);
        bt_plugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Given "com.didi.virtualapk.demo" is the package name of plugin APK,
                // and there is an activity called `MainActivity`.
                Intent intent = new Intent();
                intent.setClassName("com.weidu.virtualapk", "com.weidu.plugindemo.PluginActivity");
                startActivity(intent);
            }
        });
        load_plugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlugin(view);
            }
        });


        String pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/Test.apk");
        File plugin = new File(pluginPath);
        try {
            PluginManager.getInstance(this).loadPlugin(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlugin(View view) {
        PluginManager pluginManager = PluginManager.getInstance(this);
        //此处是当查看插件apk是否存在,如果存在就去加载(比如修改线上的bug,把插件apk下载到sdcard的根目录下取名为plugin-release.apk)
        File apk = new File(Environment.getExternalStorageDirectory(), "plugin-release.apk");
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
                Toast.makeText(this, "插件加载成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "插件加载异常！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
