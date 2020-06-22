package com.example.tsone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {


    // 수정
    EditText editText;
    EditText editText2;
    TextView textView;

    // 조회
    TextView textView2;
    TextView textView3;
    TextView textView4;

    LinearLayout linearLayout;
    LinearLayout linearLayout1;


    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    String mac;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 수정
        editText=findViewById(R.id.profileEditTextName);
        editText2=findViewById(R.id.profileEditTextContents);
        textView=findViewById(R.id.profileTextViewDevice1);

        // 조회
        textView2=findViewById(R.id.profileTextViewName);
        textView3=findViewById(R.id.profileTextViewDevice);
        textView4=findViewById(R.id.prfileTextViewContents);

        linearLayout=findViewById(R.id.showView);
        linearLayout1=findViewById(R.id.updateView);

        Intent intent=getIntent();
       builder= new AlertDialog.Builder(this);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        textView2.setText(intent.getStringExtra("name"));
        textView4.setText(intent.getStringExtra("contents"));
        mac=intent.getStringExtra("mac");
        editText.setText(intent.getStringExtra("name"));
        editText2.setText(intent.getStringExtra("contents"));
     //   Toast.makeText(getApplicationContext(), mac, Toast.LENGTH_LONG).show();

        Button button=findViewById(R.id.updateButton);
        Button button1=findViewById(R.id.deleteButton);
        Button button2=findViewById(R.id.profileUpdateButton);

        final Button button4=findViewById(R.id.button4);  // 연결하기
        final Button button5=findViewById(R.id.button5);  // 연결 끊기

        mBluetoothHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == BT_MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                    mTvReceiveData.setText(readMessage);

                    Toast.makeText(getApplicationContext(), readMessage, Toast.LENGTH_LONG).show();

                    if(readMessage.charAt(0)=='1'){  // 배회
//                        vibrator.vibrate(1000);
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                     Ringtone ringtone=RingtoneManager.getRingtone(getApplicationContext(),notification);
                   //     AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
                     //   ringtone.setAudioAttributes(audioAttributes);
                        ringtone.play();


                       builder.setIcon(R.drawable.ic_launcher_background);
                        builder.setMessage("배회 입니다.");
                        builder.setTitle("알림");

                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "배회확인"  , Toast.LENGTH_LONG).show();
//                                ringtone.stop();
                            }
                        });

//
                        AlertDialog alertDialog;
                        alertDialog=builder.create();
                        alertDialog.show();


                    }
                    else if(readMessage.charAt(0)=='2'){  //낙상
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone ringtone=RingtoneManager.getRingtone(getApplicationContext(),notification);
                        //     AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
                        //   ringtone.setAudioAttributes(audioAttributes);
                        ringtone.play();


                        builder.setIcon(R.drawable.ic_launcher_background);
                        builder.setMessage("낙상 입니다.");
                        builder.setTitle("알림");

                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "낙상확인"  , Toast.LENGTH_LONG).show();
//                                ringtone.stop();
                            }
                        });

//
                        AlertDialog alertDialog;
                        alertDialog=builder.create();
                        alertDialog.show();


                    }

                    else if(readMessage.charAt(0)=='3'){

                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone ringtone=RingtoneManager.getRingtone(getApplicationContext(),notification);
                        //     AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build();
                        //   ringtone.setAudioAttributes(audioAttributes);
                        ringtone.play();


                        builder.setIcon(R.drawable.ic_launcher_background);
                        builder.setMessage("배변/배뇨 입니다.");
                        builder.setTitle("알림");

                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "배변/배뇨확인"  , Toast.LENGTH_LONG).show();
//                                ringtone.stop();
                            }
                        });

//
                        AlertDialog alertDialog;
                        alertDialog=builder.create();
                        alertDialog.show();

                    }


                }
            }
        };

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(mBluetoothAdapter.isEnabled()){
                 connectSelectedDevice(mac);
                }

                button4.setVisibility(View.INVISIBLE);
                button5.setVisibility(View.VISIBLE);

            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mThreadConnectedBluetooth.cancel();

                button5.setVisibility(View.INVISIBLE);
                button4.setVisibility(View.VISIBLE);

            }
        });


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);

            }
        });

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intent=new Intent();
                intent.putExtra("flag","0"); //delete
                intent.putExtra("mac",mac);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String name=editText.getText().toString();
                String contents=editText2.getText().toString();

                Intent intent=new Intent();
                intent.putExtra("name",name);
                intent.putExtra("contents",contents);
                intent.putExtra("mac",mac);
                intent.putExtra("flag","1");
                setResult(RESULT_OK,intent);
                finish();

            }
        });

    }
    void connectSelectedDevice(String selectedDeviceName) {

        mPairedDevices=mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getAddress())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }
    private class ConnectedBluetoothThread extends Thread{

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        public ConnectedBluetoothThread(BluetoothSocket socket){
            mmSocket=socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try{
                tmpIn=socket.getInputStream();
                tmpOut=socket.getOutputStream();
            }  catch(IOException e){

                Log.d("TSBluetooth","블루투스 연결중 오류 발생22");
            }

            mmInStream=tmpIn;
            mmOutStream=tmpOut;

        }

        public void run(){

            byte[] buffer=new byte[1024];
            int bytes;

            while(true){

                try{
                    bytes=mmInStream.available();
                    if(bytes!=0){
                        SystemClock.sleep(100);
                        bytes=mmInStream.available();
                        bytes=mmInStream.read(buffer,0,bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ,bytes,-1,buffer).sendToTarget();
                    }
                } catch(IOException e){ break; }
            }


        }

        public void write(String str){
            byte[] bytes=str.getBytes();
            try{
                mmOutStream.write(bytes);
            } catch (Exception e){
                Log.d("TSBluetooth","데이터 전송중 오류 발생22");

            }
        }

        public void cancel(){
            try{
                mmSocket.close();
            } catch(IOException e){
                Log.d("TSBluetooth","소켓 해제중 오류 발생22");

            }
        }


    }
}