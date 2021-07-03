package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myapplication.DBUser.User;

import java.util.ArrayList;

import static android.content.Intent.EXTRA_USER;

public class LoginScreen extends AppCompatActivity {
    SQLSever dd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* ull madng hinh */

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.dangnhap);

        final EditText password = (EditText) findViewById(R.id.pass);
        final EditText username = (EditText) findViewById(R.id.username);
        final Button login = (Button) findViewById(R.id.login);
        final Button signup = (Button) findViewById(R.id.SignUp);
        final Button quenmk = (Button) findViewById(R.id.quenmk);

        final SQLSever sqlSever = new SQLSever(this);
        // ArrayList<User> list = new ArrayList<>();
        //----------Tài Khoản Gốc-------------------
        // User s = new User("chanh", "MaiChanh", "maivanchanh321@gmail.com", "123", "CEO", 3);
        //sqlSever.AddUser(s);
        //---------------------------------------------------------------------------
        password.setInputType(InputType.TYPE_CLASS_TEXT |//ẩn Text để làm mật khẩu
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignUp();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                if(name.equals("") || pass.equals("")){
                    Toast.makeText( LoginScreen.this, "Vui Lòng Điền Đủ Thông tin!!!", Toast.LENGTH_SHORT).show();
                }else{
                    User s = sqlSever.getUser(name);
                    if(s != null){
                        if(s.getPassword().equals(pass)){
                            Toast.makeText( LoginScreen.this, "Đăng nhập thành công ^.^", Toast.LENGTH_SHORT).show();
                            password.setText("");
                            username.setText("");
                            Login(s);
                        }else {
                            password.setText("");
                            username.setText("");
                            Toast.makeText( LoginScreen.this, "Tài khoản hoặc mật khẩu không chính xác!!!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        password.setText("");
                        username.setText("");
                        Toast.makeText( LoginScreen.this, "Tài khoản Không Tồn tại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenForgotPassword();
            }
        });
    }

    public void OpenSignUp(){
        Intent intent = new Intent( LoginScreen.this, SignUpScreen.class);
        startActivity(intent);
    }

    public void Login(User s){
        Intent intent = new Intent( LoginScreen.this, MainActivity.class);
           intent.putExtra(EXTRA_USER, s.getAccount());
         startActivity(intent);
         finish();
    }

    public void OpenForgotPassword(){
        Intent intent = new Intent( LoginScreen.this, ForgotPassword.class);
        startActivity(intent);
    }


}
