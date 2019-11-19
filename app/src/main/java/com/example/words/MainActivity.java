package com.example.words;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(findViewById(R.id.fragment));
        NavigationUI.setupActionBarWithNavController(this, navController);

        String[] english = {
                "Hello",
                "World",
                "Android",
                "Google",
                "Studio",
                "Project",
                "Database",
                "Recycler",
                "View",
                "String",
                "Value",
                "Integer"
        };

        String[] chinese = {
                "你好",
                "世界",
                "安卓系统",
                "谷歌公司",
                "工作室",
                "项目",
                "数据库",
                "回收站",
                "视图",
                "字符串",
                "价值",
                "整数类型"
        };
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navController.navigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.fragment).getWindowToken(), 0);
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }


}
