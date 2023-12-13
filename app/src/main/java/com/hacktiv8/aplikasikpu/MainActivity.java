package com.hacktiv8.aplikasikpu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBHelper helper;
    LayoutInflater inflater;
    View dialogView;
    TextView Tv_NomorNik, Tv_Nama, Tv_NomorHp, Tv_JK, Tv_Tanggal, Tv_Alamat;
    ImageView imageView;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_simpan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        helper = new DBHelper(this);
        listView = (ListView)findViewById(R.id.list_data);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListView(){
        Cursor cursor = helper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = helper.oneData(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Pilih Opsi");

        //Tambah List
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(MainActivity.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.view_data, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        Tv_NomorNik = (TextView) dialogView.findViewById(R.id.tv_NomorNik);
                        Tv_Nama = (TextView) dialogView.findViewById(R.id.tv_Nama);
                        Tv_NomorHp = (TextView) dialogView.findViewById(R.id.tv_NomorHp);
                        Tv_Tanggal = (TextView) dialogView.findViewById(R.id.tv_Tanggal);
                        Tv_JK = (TextView) dialogView.findViewById(R.id.tv_JK);
                        Tv_Alamat = (TextView) dialogView.findViewById(R.id.tv_Alamat);
                        imageView = (ImageView) dialogView.findViewById(R.id.tv_imageView);

                        Tv_NomorNik.setText("Nomor NIK: " + cur.getString(cur.getColumnIndex(DBHelper.row_nomorNik)));
                        Tv_Nama.setText("Nama: " + cur.getString(cur.getColumnIndex(DBHelper.row_nama)));
                        Tv_NomorHp.setText("Nomor HP: " + cur.getString(cur.getColumnIndex(DBHelper.row_nomorhp)));
                        Tv_Tanggal.setText("Tanggal: " + cur.getString(cur.getColumnIndex(DBHelper.row_tanggal)));
                        Tv_JK.setText("Jenis Kelamin: " + cur.getString(cur.getColumnIndex(DBHelper.row_jk)));
                        Tv_Alamat.setText("Alamat: " + cur.getString(cur.getColumnIndex(DBHelper.row_alamat)));

                        // Misalkan cur adalah objek Cursor yang berisi data entri yang ingin ditampilkan
                        String fotoPath = cur.getString(cur.getColumnIndex(DBHelper.row_foto));

                        // Periksa apakah path foto tidak null atau kosong
//                        if (!TextUtils.isEmpty(fotoPath)) {
//                            // Konversi path ke URI
//                            Uri fotoUri = Uri.parse(fotoPath);
//
//                            // Periksa apakah URI yang dihasilkan benar
//                            if (fotoUri != null) {
//                                // Set URI ke ImageView
//                                imageView.setImageURI(fotoUri);
//                            } else {
//                                Log.e("ImageView Error", "URI is null");
//                            }
//                        } else {
//                            // Handle kasus ketika path foto kosong
//                            Log.e("ImageView Error", "Path foto kosong");
//                        }

                        viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        viewData.show();
                }
                switch (which) {
                    case  1:
                        Intent iddata = new Intent(MainActivity.this, EditActivity.class);
                        iddata.putExtra(DBHelper.row_id, id);
                        startActivity(iddata);
                }
                switch (which) {
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Data Ini akan dihapus");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteData(id);
                                Toast.makeText(MainActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                setListView();
                            }
                        });
                        builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }
}