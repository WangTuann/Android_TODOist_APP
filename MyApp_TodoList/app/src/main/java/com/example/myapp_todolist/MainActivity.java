package com.example.myapp_todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;

    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa listview
        lvCongViec=(ListView)findViewById(R.id.listviewCongViec);
        arrayCongViec=new ArrayList<>();
        adapter=new CongViecAdapter(this,R.layout.dong_cong_viec,arrayCongViec);
        lvCongViec.setAdapter(adapter);
        //tao database ghichu
        database=new Database(this,"ghichu.sqlite",null,1);
        //taobang cong viec
        database.QuerryData("CREATE TABLE IF NOT EXISTS CongViec(ID INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");
        //them data
        //insert data
        //database.QuerryData("INSERT INTO CongViec VALUES(NULL, 'lam bai Android')");
        GetDataCongViec();
    }

    private void GetDataCongViec(){
        //select data
        Cursor dataCongViec=database.GetData("Select * from CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            //lay id
            String ten=dataCongViec.getString(1);
            //lay ten
            int id=dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }
    public void DialogSuaCV(String ten,int id){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suacongviec);

        EditText editText=(EditText)dialog.findViewById(R.id.textSua);
        Button btnSua=(Button)dialog.findViewById(R.id.btnSua);
        Button btnHuy=(Button)dialog.findViewById(R.id.btnHUY);

        editText.setText(ten);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi=editText.getText().toString();
                database.QuerryData("UPDATE CongViec set TenCV='"+tenMoi+"'where ID='"+id+"'");
                Toast.makeText(MainActivity.this,"Da cap nhat",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });


    }
    public void DialogXoaCV(String tenCV,int id){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Ban co muon xoa cong viec  "+tenCV+" khong?");
        dialogXoa.setPositiveButton("co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QuerryData("Delete from CongViec where ID='"+id+"'");
                Toast.makeText(MainActivity.this,"da xoa"+tenCV,Toast.LENGTH_SHORT).toString();
                GetDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private  void DialogThem(){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themcongviec);

        EditText edtTen=(EditText) dialog.findViewById(R.id.texthem);
        Button btnThem=(Button) dialog.findViewById(R.id.btnThem);
        Button btnHuy=(Button)dialog.findViewById(R.id.btnHUY);
        //HUY
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //THEM
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCV=edtTen.getText().toString();
                if (tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap ten cong viec", Toast.LENGTH_SHORT).show();
                }else {
                    database.QuerryData("INSERT INTO CongViec VALUES(NULL, '"+tenCV+"')");
                    Toast.makeText(MainActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
        dialog.show();
    }
}