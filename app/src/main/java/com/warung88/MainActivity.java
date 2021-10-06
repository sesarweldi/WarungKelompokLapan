package com.warung88;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.warung88.Adapter.FoodAdapter;
import com.warung88.Models.Food;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static  final int REQUEST_CODE = 1;

    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Food> foods = new ArrayList<>();
        foods.add(new Food("Ayam Nasi Uduk","Makanan", R.drawable.nasiuduk, 15000));
        foods.add(new Food("Ayam Goreng", "Makanan", R.drawable.ayam, 12000));
        foods.add(new Food("Ikan Bakar", "Makanan", R.drawable.ikanbakar, 10000));
        foods.add(new Food("Es Teh Manis", "Minuman", R.drawable.esteh, 5000));
        foods.add(new Food("Es jeruk", "Minuman", R.drawable.esjeruk, 7000));
        foods.add(new Food("Es Campur", "Dessert", R.drawable.escampur, 10000));


        adapter = new FoodAdapter(this, foods);
        ListView orderListView =(ListView) findViewById(R.id.order_list_view);
        orderListView.setAdapter(adapter);
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(MainActivity.this, FoodDetailActivity.class);

                Food currentFood = foods.get(position);
                Log.e("FOOD NAME", currentFood.getFoodName());
                i.putExtra("name",currentFood.getFoodName());
                i.putExtra("type",currentFood.getType());
                i.putExtra("image",currentFood.getmImageResouce());
                i.putExtra("price",currentFood.getFoodPrice());
                startActivity(i);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_cart:
                Intent i = new Intent(MainActivity.this, CartListACtivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
