package com.example.practice.RecyclerViewPractice;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.practice.R;

public class RVActivity extends AppCompatActivity {

    private Button mLinearBtn,mBtnHor,mBtnGrid,mBtnPu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_v);
        mLinearBtn = findViewById(R.id.btn_linear);
        mBtnHor = findViewById(R.id.btn_hor);
        mBtnGrid = findViewById(R.id.btn_grid);
        mBtnPu = findViewById(R.id.btn_pu);;
        mLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RVActivity.this, LinearActivity.class);
                startActivity(intent);
            }
        });
        mBtnHor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RVActivity.this, HorRVActivity.class);
                startActivity(intent);
            }
        });
        mBtnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RVActivity.this, GridRVActivity.class);
                startActivity(intent);
            }
        });
        mBtnPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RVActivity.this, PuActivity.class);
                startActivity(intent);
            }
        });
    }
}
