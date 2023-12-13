package com.hacktiv8.aplikasikpu;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_data, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListNama = (TextView)v.findViewById(R.id.listNama);
        holder.ListFoto = (ImageView)v.findViewById(R.id.listFoto);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_id)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_nama)));

        // Ambil path foto dari Cursor
        String fotoPath = cursor.getString(cursor.getColumnIndex(DBHelper.row_foto));

        // Tampilkan foto jika path foto tidak kosong
        if (!TextUtils.isEmpty(fotoPath)) {
            Uri fotoUri = Uri.parse(fotoPath);
            holder.ListFoto.setImageURI(fotoUri);
        } else {
            // Atur gambar default jika path kosong
            holder.ListFoto.setImageResource(R.drawable.baseline_image_24);
        }
    }

    class MyHolder{
        TextView ListID;
        TextView ListNama;
        ImageView ListFoto;
    }
}
