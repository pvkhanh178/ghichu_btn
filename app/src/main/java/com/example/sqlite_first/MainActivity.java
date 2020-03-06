package com.example.sqlite_first;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.sqlite_first.R.id.ic_add;
import static com.example.sqlite_first.R.id.ic_delete;

public class MainActivity extends AppCompatActivity {
    LayoutInflater inflate;
    Database database;
    MenuItem itemThem;
    MenuItem itemXoa;
    ListView lvCV;
    ArrayList<CongViec> arrCV;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCV = (ListView) findViewById(R.id.lvDanhSach);
        arrCV = new ArrayList<>();
        adapter = new CongViecAdapter(MainActivity.this, R.layout.dong_ghichu, arrCV);
        lvCV.setAdapter(adapter);
        //tao database ghiChu
        database = new Database(this, "baitap", null, 1);
        //tao bang cv
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(ID INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(200), NoiDung VARCHAR(200))");
        //INSERT DATA
        //database.QueryData("INSERT INTO CongViec VALUES(null, 'Viet ung dung','324')");
        getData();
    }
    void activityMain(){
        itemThem.setVisible(true);
        itemXoa.setVisible(false);
        setContentView(R.layout.activity_main);
        lvCV = (ListView) findViewById(R.id.lvDanhSach);
        adapter = new CongViecAdapter(MainActivity.this, R.layout.dong_ghichu, arrCV);
        lvCV.setAdapter(adapter);
    }
    private void Them_GhiChu(){
        setContentView(R.layout.them_ghichu);
        itemThem.setVisible(false);
        final EditText edtTieuDe = findViewById(R.id.etTieuDe_Them);
        final EditText edtNoiDung = findViewById(R.id.etNoiDung_Them);


        Button btnThem = findViewById(R.id.buttonThem);
        Button btnHuy = findViewById(R.id.buttonHuy_Them);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieuDe = edtTieuDe.getText().toString().trim();
                String noiDung = edtNoiDung.getText().toString().trim();
                if(tieuDe.equals("")||noiDung.equals("")){
                    if(tieuDe.equals("")){
                        Toast.makeText(MainActivity.this,"Bạn chưa nhập tiêu đề!", Toast.LENGTH_LONG).show();
                        edtTieuDe.requestFocus();
                    }else if(noiDung.equals("")) {
                        Toast.makeText(MainActivity.this, "Bạn chưa nhập nội dung!", Toast.LENGTH_LONG).show();
                        edtNoiDung.requestFocus();
                    }
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '"+tieuDe+"', '"+noiDung+"')");
                    Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    //this.dismiss();
                    getData();
                    activityMain();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMain();
            }
        });
    }
    public void Sua_GhiChu(final int id, final String tieuDe, String noiDung){
        setContentView(R.layout.sua_ghichu);
        itemThem.setVisible(false);
        itemXoa.setVisible(true);
        itemXoa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Xoa_GhiChu(tieuDe, id);
                activityMain();
                return true;
            }
        });
        final EditText edtTieuDe = (EditText) this.findViewById(R.id.etTieuDe_Sua);
        final EditText edtNoiDung = (EditText) this.findViewById(R.id.etNoiDung_Sua);
        Button btnSua = (Button) this.findViewById(R.id.buttonSua);
        final Button btnHuy = (Button) this.findViewById(R.id.buttonHuy_Sua);
        edtTieuDe.setText(tieuDe);
        edtNoiDung.setText(noiDung);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_TieuDe = edtTieuDe.getText().toString().trim();
                String new_NoiDung = edtNoiDung.getText().toString().trim();
                if(new_TieuDe.isEmpty()||new_NoiDung.isEmpty()){
                    if(new_TieuDe.equals("")){
                        Toast.makeText(MainActivity.this,"Bạn chưa nhập tiêu đề!", Toast.LENGTH_LONG).show();
                        edtTieuDe.requestFocus();
                    }else if(new_NoiDung.equals("")) {
                        Toast.makeText(MainActivity.this, "Bạn chưa nhập nội dung!", Toast.LENGTH_LONG).show();
                        edtNoiDung.requestFocus();
                    }
                }else{
                    database.QueryData("UPDATE CongViec SET TieuDe = '"+new_TieuDe+"', NoiDung = '"+new_NoiDung+"' Where ID = '"+id+"'");
                    Toast.makeText(MainActivity.this, "Sửa thành công!", Toast.LENGTH_LONG).show();
                    getData();
                    activityMain();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityMain();
            }
        });
    }
    public  void Xoa_GhiChu(final String tieuDe, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        String temp = "Bạn muốn xóa ghi chú "+tieuDe+" không?";
        final TextView messenge = dialog.findViewById(R.id.messenge);
        Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        Button btnXoa = (Button) dialog.findViewById(R.id.btn_xoa);
        messenge.setText(temp);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.QueryData("DELETE From CongViec Where ID = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã Xóa " + tieuDe, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getData();
                activityMain();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                activityMain();
            }
        });
        dialog.show();
//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.dialog, null);
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        TextView info = findViewById(R.id.dialog_info);
//        Button btn_Huy;
//        Button btn_Xoa;
//        btn_Huy = findViewById(R.id.btn_huy);
//        btn_Xoa = findViewById(R.id.btn_xoa);
//        btn_Xoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database.QueryData("DELETE From CongViec Where ID = '"+id+"'");
//                Toast.makeText(MainActivity.this, "Đã Xóa " + tencv, Toast.LENGTH_SHORT).show();
//                getData();
//                activityMain();
//            }
//        });
//        btn_Huy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityMain();
//            }
//        });
//        alert.setView(alertLayout);
//        AlertDialog dialog = alert.create();
//        dialog.show();




//        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this, R.layout.dialog);
        // Setting Icon to Dialog

//        dialogXoa.setMessage("Bạn có muốn xóa \""+tencv+"\" không?");
//        dialogXoa.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                database.QueryData("DELETE From CongViec Where ID = '"+id+"'");
//                Toast.makeText(MainActivity.this, "Đã Xóa " + tencv, Toast.LENGTH_SHORT).show();
//                getData();
//            }
//        });
//        dialogXoa.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        dialogXoa.show();
    }
    private void getData(){
        //select data
        Cursor dataCV = database.GetData("select * from CongViec");
        arrCV.clear();
        while (dataCV.moveToNext()){

            int id = dataCV.getInt(0);
            String tieuDe = dataCV.getString(1);
            String noiDung = dataCV.getString(2);
            arrCV.add(new CongViec(id, tieuDe, noiDung));
//            Toast.makeText(this,ten,Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        itemThem = menu.findItem(ic_add);
        itemXoa = menu.findItem(ic_delete);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == ic_add){
            Them_GhiChu();
        }
        return super.onOptionsItemSelected(item);
    }

}
