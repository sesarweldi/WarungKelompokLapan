package com.warung88;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.warung88.Adapter.CartListAdapter;
import com.warung88.Database.SQLiteHelper;
import com.warung88.Models.Cart;
import com.warung88.Models.Request;

import java.util.ArrayList;

public class CartListACtivity extends AppCompatActivity {
    ListView listView;
    public TextView totalPricetv;
    ArrayList<Cart> list = new ArrayList<Cart>();
    CartListAdapter adapter = null;


    SQLiteHelper helper;

    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list_activity);

        //deklarasi
        //===========================================================
        Button btnOrder = (Button) findViewById(R.id.btnPesan);
        listView = (ListView) findViewById(R.id.listView);
        totalPricetv = findViewById(R.id.total);
        int total=0;
        String totalPrice=null;

        list = new ArrayList<>();
        adapter = new CartListAdapter(this, R.layout.cart_item, list);
        listView.setAdapter(adapter);
        helper= new SQLiteHelper(this, "FoodDB.sqlite", null,1);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Cart");


        //dapetin semua data dari sql lite
        Cursor cursor = helper.getData("SELECT ID, NAME, QUANTITY, PRICE FROM CART");
        Cursor cursor1 = helper.getData("SELECT * FROM CART");

        list.clear();
        if(cursor.moveToFirst()){
            do{
                int id= cursor.getInt(0);
                String name = cursor.getString(1);
                String quantity=cursor.getString(2);
                String price=cursor.getString(3);
                Log.e("price: ", price);
                total=total+Integer.parseInt(price);
                Log.e("pricetotal: ", String.valueOf(total));
                list.add(new Cart(id, name,quantity,price));
            }
            while (cursor.moveToNext());
        }

        totalPricetv.setText(String.valueOf(total));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartListACtivity.this);
                final int pos = position;
                builder.setTitle("Dialog Hapus")
                        .setMessage("Apakah anda ingin menghapus item ini?")

                        //kalo penect yes
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.e("CART ID =", list.get(pos).toString());
                                Cart cart = list.get(pos);
                                helper.deleteData(cart.getId());
                                list.remove(pos);
                                adapter.notifyDataSetChanged();
                                listView.invalidateViews();
                            }
                        })


                        //kalo pencet no
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                builder.create();
                builder.show();
            }
        });



        //btn Order
        //----------------------------------------------------------------------------
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()>0){
                    showAlertDialog();
                }

                else
                    Toast.makeText(CartListACtivity.this, "Keranjang Belanja Kosong", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setFocusable(true);
        adapter.notifyDataSetChanged();
    }



    private void showAlertDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CartListACtivity.this);
        View mvView = getLayoutInflater().inflate(R.layout.dialog_order,null);

        final EditText mName = (EditText) mvView.findViewById(R.id.edt_nama_pemesan);
        final EditText mTlp = (EditText) mvView.findViewById(R.id.edt_tlp_pemesan);
        final EditText mAddress = (EditText) mvView.findViewById(R.id.edt_alamat_pemesan);
        final Button mfinalOrder = (Button) mvView.findViewById(R.id.finishOrder);


        mfinalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request(
                        mName.getText().toString().trim(),
                        mTlp.getText().toString().trim(),
                        mAddress.getText().toString().trim(),
                        totalPricetv.getText().toString().trim(),
                        list);

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Customer");
                reference.push().setValue(request)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CartListACtivity.this, "Pesanan berhasil di input dan sedang di proses",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(CartListACtivity.this,"Pesanan gagal di input!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
        });

       mBuilder.setView(mvView);
       AlertDialog dialog = mBuilder.create();
       dialog.show();
        }

    }





