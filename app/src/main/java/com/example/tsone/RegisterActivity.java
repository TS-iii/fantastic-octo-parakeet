package com.example.tsone;

import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

import java.util.UUID;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class RegisterActivity extends AppCompatActivity {

    TextView textview;  // 기기번호
    EditText editText;  // 이름
    EditText editText2;  // 참고사항
    ArrayList<PersonInfo> result;
    String mac;


    IntentIntegrator integrator;

    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

            if(result!=null){
                if(result.getContents()==null){}
                else{

                    mac=result.getContents();
                    textview.setText(mac);

                }
            } else{
                super.onActivityResult(requestCode, resultCode, data);


            }


        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textview = findViewById(R.id.textView);




        result=new ArrayList<PersonInfo>();
        result=getIntent().getParcelableArrayListExtra("key");

        integrator=new IntentIntegrator(this);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                integrator.setPrompt("바코드를 사각형 안에 비춰주세요");
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);


                integrator.initiateScan();



            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp=textview.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("name", editText.getText().toString());
                intent.putExtra("contents", editText2.getText().toString());
                intent.putExtra("mac",mac);


                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


}