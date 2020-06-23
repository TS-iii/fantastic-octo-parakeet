package com.example.tsone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_MENU=101;
    public static final int REQUEST_PROFILE_MENU=102;
    ArrayList<PersonInfo> result;
    PersonAdapter adapter;
    RecyclerView recyclerView;
    PersonDatabase database;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_MENU){
            Toast.makeText(getApplicationContext(),"onActivityResult 결과 메서드 호출됨",Toast.LENGTH_LONG).show();


            if(resultCode==RESULT_OK){
                String name=data.getStringExtra("name");
                String contents=data.getStringExtra("contents");
                String mac=data.getStringExtra("mac");

                Toast.makeText(getApplicationContext(),"name:"+name+",+contents:"+contents + ", mac:"+mac,Toast.LENGTH_LONG).show();


                adapter.addItem(new PersonInfo(name,contents,mac));
                adapter.notifyDataSetChanged();

                database.insertRecord(name,contents,mac);
            }



        }

        if(requestCode==REQUEST_PROFILE_MENU){

            if(resultCode==RESULT_OK){

                String flag=data.getStringExtra("flag");

                if(flag.equals("0")){

                    // 데이터베이스에서 삭제후 화면 갱신
                    Toast.makeText(getApplicationContext(),"삭제",Toast.LENGTH_LONG).show();
                    database.deleteRecord(data.getStringExtra("mac"));
                    ArrayList<PersonInfo> result=database.selectAll();
                    adapter.setItems(result);

                    recyclerView.setAdapter(adapter);


                }
                if(flag.equals("1")){

                    Toast.makeText(getApplicationContext(),"수정",Toast.LENGTH_LONG).show();
                    database.updateRecord(data.getStringExtra("name"),data.getStringExtra("contents"),data.getStringExtra("mac"));
                    ArrayList<PersonInfo> result=database.selectAll();
                    adapter.setItems(result);

                    recyclerView.setAdapter(adapter);
                    // 데이터베이스에서 데이터 수정 후 화면 갱신
                }


            }




        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new PersonAdapter();

        if(database!=null){
            database.close();
            database=null;
        }

        database=PersonDatabase.getInstance(this);
        boolean isOpen=database.open();
        if(isOpen){
            Log.d(TAG,"Book database is open");

        } else { Log.d(TAG,"Book db is not open"); }

        AutoPermissions.Companion.loadAllPermissions(this,101);

        result=database.selectAll();
        adapter.setItems(result);

        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {
                PersonInfo item= adapter.getItem(position);
                Toast.makeText(getApplicationContext(),"아이템 선택됨:"+item.getName(),Toast.LENGTH_LONG).show();

                Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("contents",item.getContents());
                intent.putExtra("mac",item.getMac());

                startActivityForResult(intent,REQUEST_PROFILE_MENU);

            }
        });


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                ArrayList<PersonInfo> result=database.selectAll();
                intent.putParcelableArrayListExtra("key",result);

                startActivityForResult(intent,REQUEST_CODE_MENU);

            }
        });
    }




}