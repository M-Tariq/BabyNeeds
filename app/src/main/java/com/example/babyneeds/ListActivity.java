package com.example.babyneeds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.babyneeds.adapter.RecycleViewAdapter;
import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    DatabaseHandler  databaseHandler;
    FloatingActionButton floatingActionButton;



    AlertDialog alertDialog; //
    AlertDialog.Builder alertDialogBuilder; // has method to show alert dialog


    private Button saveBtn;
    private EditText itemEdt, quantityEdt,  colorEdt, sizeEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView=findViewById(R.id.recyclerView_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHandler=new DatabaseHandler(this);


        List<Item> itemList=databaseHandler.getAllItems();
        Log.d("Size", "T: "+itemList.size());

        recycleViewAdapter=new RecycleViewAdapter(this, itemList);
        recycleViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recycleViewAdapter);

        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener((View.OnClickListener) this);

    }


    @Override
    public void onClick(View v) {
        createPopUp();
    }
    private void createPopUp() {
        alertDialogBuilder=new AlertDialog.Builder(this);
        final View view=getLayoutInflater().inflate(R.layout.pop_up, null);

        saveBtn=view.findViewById(R.id.save_button_id);

        itemEdt=view.findViewById(R.id.item_name_id);
        quantityEdt=view.findViewById(R.id.quantity_id);
        sizeEdt=view.findViewById(R.id.size_id);
        colorEdt=view.findViewById(R.id.color_id);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        itemEdt.getText().toString().trim().isEmpty()
                                || sizeEdt.getText().toString().trim().isEmpty()
                                || colorEdt.getText().toString().trim().isEmpty()
                                || quantityEdt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ListActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Item item = new Item();
                    item.setItemName(itemEdt.getText().toString().trim());
                    item.setItemSize(Integer.parseInt(sizeEdt.getText().toString().trim()));
                    item.setItemColor(colorEdt.getText().toString().trim());
                    item.setItemQuantity(Integer.parseInt(quantityEdt.getText().toString().trim()));

                    databaseHandler.addItem(item);


                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    //Snackbar.make(view, "Saved", 9000).show();
                                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    alertDialog.dismiss();
                                    startActivity(new Intent(ListActivity.this, ListActivity.class));
                                    finish();
                                }
                            }, 1200);
                }
            }

        });


        alertDialogBuilder.setView(view);
        alertDialog=alertDialogBuilder.create();// creating object of dialog
        alertDialog.show();
    }
}
