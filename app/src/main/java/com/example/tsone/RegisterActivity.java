package com.example.tsone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    TextView textview;  // 기기번호
    EditText editText;  // 이름
    EditText editText2;  // 참고사항
    ArrayList<PersonInfo> result;
    String mac;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;


    BluetoothDevice mBluetoothDevice;


    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textview = findViewById(R.id.textView);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();



        result=new ArrayList<PersonInfo>();
        result=getIntent().getParcelableArrayListExtra("key");


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listPairedDevices();


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


// 블루투스 어댑터에서 페어링된 기기 목록을 받아오고 목록창을 띄워준다.
    void listPairedDevices() {

        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                           mListPairedDevices = new ArrayList<String>();

                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                }

                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                        Toast.makeText(getApplicationContext(), "mac 주소 확인:" + mac , Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
        }


    }

// 디바이스 이름을 받아서 페어링된 디바이스 목록중 이 이름과 동일한게 있으면
// uuid를 매개변수로 블루투스 소켓을 하나 만들고 연결을 한다
// 그리고 내가 정의한 쓰레드 클래스에 소켓을 매개변수로 줘서 인스턴스를 만들고 실행시킨다
    void connectSelectedDevice(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                mac= mBluetoothDevice.getAddress();

                Log.d("tesung",mac);

                break;
            }
        }




    }

// 생성자 매개변수에서 블루투스 소켓을 받아오고
// 인풋 스트림과 아웃풋 스트림을 이 소켓 메소드로 만든다
//  메인 로직은 run 에서 실행되는데 무한 대기하면서 이 소켓의 인풋스트림으로 오는 데이터 있나 모니터링하고
// 있으면 데이터 받아서 처리한다.



}