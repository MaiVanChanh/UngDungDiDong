package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DBUser.User;

import java.util.ArrayList;

public class SignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dangky);

        final SQLSever sqlSever = new SQLSever(this);

        final EditText fullname = (EditText) findViewById(R.id.fullname);
        final EditText account = (EditText) findViewById(R.id.Account);
        final EditText gmail = (EditText) findViewById(R.id.gmail);
        final EditText password1 = (EditText) findViewById(R.id.pass);
        final EditText password2 = (EditText) findViewById(R.id.pass2);
        Button login = (Button) findViewById(R.id.login);
        Button signup = (Button) findViewById(R.id.SignUp);

        password1.setInputType(InputType.TYPE_CLASS_TEXT |//ẩn Text để làm mật khẩu
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password2.setInputType(InputType.TYPE_CLASS_TEXT |//ẩn Text để làm mật khẩu
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        final SQLSever sql_user = new SQLSever(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String acc= account.getText().toString();
                final String ten= fullname.getText().toString();
                final String Gmail = gmail.getText().toString();
                final String mk1= password1.getText().toString();
                final String mk2= password2.getText().toString();
                if(ten.equals("") || Gmail.equals("")|| mk1.equals("") || mk2.equals("") || acc.equals("")){
                    Toast.makeText(SignUpScreen.this, "Vui Lòng Điền Đủ Thông tin!!!", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder b=new AlertDialog.Builder(SignUpScreen.this);
                    b.setTitle("Sign Up");
                    b.setMessage("Bạn có muốn Đăng Ký?");
                    b.setIcon(R.drawable.icons8_adduser);
                    b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(mk1.equals(mk2)){
                                ArrayList<User> users= sqlSever.getArrayUser();
                                if(users != null){
                                    boolean ketqua = true;
                                    for(User x : users){
                                        if(x.getAccount().equals(acc)){
                                            ketqua = false;
                                        }
                                    }
                                    if(ketqua == true){
                                        User s = new User(acc, ten, Gmail, mk1, "Độc Giả", 1);
                                        sqlSever.AddUser(s);
                                        if(s!=null){
                                            Toast.makeText(SignUpScreen.this, "Đăng Ký Thành Công ^.^", Toast.LENGTH_SHORT).show();
                                            account.setText("");
                                            fullname.setText("");
                                            gmail.setText("");
                                            password1.setText("");
                                            password2.setText("");
                                        }else{
                                            Toast.makeText(SignUpScreen.this, "Đăng Ký Thất Bại ^.^", Toast.LENGTH_SHORT).show();
                                            account.setText("");
                                            fullname.setText("");
                                            gmail.setText("");
                                            password1.setText("");
                                            password2.setText("");
                                        }
                                    }else{
                                        Toast.makeText(SignUpScreen.this, "Account Đã Tồn tại!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(SignUpScreen.this, "Mật Khẩu Nhập Lại Không đúng!!!", Toast.LENGTH_SHORT).show();
                                password2.setText("");
                                password1.setText("");
                            }
                        }});
                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
                    b.create().show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLogin();
            }
        });
    }
    public void OpenLogin(){
        Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
        startActivity(intent);

    }
}


