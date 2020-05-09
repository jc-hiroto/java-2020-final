package com.example.practice;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.practice.util.ToastUtil;

public class DialogActivity extends AppCompatActivity {

    private Button mBtnDialog1,mBtnDialog2,mBtnDialog3,mBtnDialog4,mBtnDialog5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mBtnDialog1 = findViewById(R.id.btn_dialog1);
        mBtnDialog2 = findViewById(R.id.btn_dialog2);
        mBtnDialog3 = findViewById(R.id.btn_dialog3);
        mBtnDialog4 = findViewById(R.id.btn_dialog4);
        mBtnDialog5 = findViewById(R.id.btn_dialog5);
        Onclick onclick = new Onclick();
        mBtnDialog1.setOnClickListener(onclick);
        mBtnDialog2.setOnClickListener(onclick);
        mBtnDialog3.setOnClickListener(onclick);
        mBtnDialog4.setOnClickListener(onclick);
        mBtnDialog5.setOnClickListener(onclick);

    }

    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_dialog1:
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DialogActivity.this);
                    builder1.setTitle("請回答").setMessage("你覺得跳船原理如何")
                            .setIcon(R.drawable.face)
                            .setPositiveButton("超屌", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this, "你這騙子");
                        }
                    }).setNeutralButton("還可以", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this, "別騙人");
                        }
                    }).setNegativeButton("超爛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this, "誠實才是美德");
                        }
                    }).show();

                    break;
                case R.id.btn_dialog2:
                    final String[] array2 = new String[]{"船組","電組"};
                    AlertDialog.Builder builder2 = new AlertDialog.Builder((DialogActivity.this));
                    builder2.setTitle("選擇類組").setItems(array2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this,array2[which]+"超爛");
                        }
                    }).show();
                    break;
                case R.id.btn_dialog3:
                    final String[] array3 = new String[]{"船組","電組"};
                    AlertDialog.Builder builder3 = new AlertDialog.Builder((DialogActivity.this));
                    builder3.setTitle("選擇類組")
                            .setSingleChoiceItems(array3, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtil.showMsg(DialogActivity.this,array3[which]+"最爛");
                            dialog.dismiss();
                        }
                    }).setCancelable(false).show();
                    break;
                case R.id.btn_dialog4:
                    final String[] array4 = new String[]{"烤肉","拉麵","火鍋","熱炒"};
                    boolean[] isSelected = new boolean[]{false,false,false,true};
                    AlertDialog.Builder builder4 = new AlertDialog.Builder((DialogActivity.this));
                    builder4.setTitle("選擇晚餐").setMultiChoiceItems(array4, isSelected, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            ToastUtil.showMsg(DialogActivity.this, array4[which]+"讚啦");
                        }
                    }).setPositiveButton("好呀", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("我不要", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                    break;
                case R.id.btn_dialog5:
                    AlertDialog.Builder builder5 = new AlertDialog.Builder(DialogActivity.this);
                    View view = LayoutInflater.from(DialogActivity.this).inflate(R.layout.layout_dialog,null);
                    EditText etUsername = view.findViewById(R.id.et_username);
                    EditText etPassword = view.findViewById(R.id.et_password);
                    Button btnLogin = view.findViewById(R.id.btn_login);
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    builder5.setTitle("請先登入").setView(view).show();
                    break;
            }
        }
    }
}
