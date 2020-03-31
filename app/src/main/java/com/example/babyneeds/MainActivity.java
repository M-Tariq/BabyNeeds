package com.example.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AlertDialog alertDialog; //
    AlertDialog.Builder alertDialogBuilder; // has method to show alert dialog
    DatabaseHandler databaseHandler;
    private Button saveBtn;
    private EditText itemEdt, quantityEdt,  colorEdt, sizeEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler=new DatabaseHandler(this);
        byPassActivity();


//        Log.d("Main", "onCreate: "+itemList.get(0).getItemName()+"//"+itemList.get(0).getDateItemAdded());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp();
            }

        });
    }



    private void createPopUp() {
        alertDialogBuilder=new AlertDialog.Builder(MainActivity.this);
        final View view=getLayoutInflater().inflate(R.layout.pop_up, null);

        saveBtn=view.findViewById(R.id.save_button_id);
        itemEdt=view.findViewById(R.id.item_name_id);
        quantityEdt=view.findViewById(R.id.quantity_id);
        sizeEdt=view.findViewById(R.id.size_id);
        colorEdt=view.findViewById(R.id.color_id);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, qty, color, size;
                name=itemEdt.getText().toString().trim();
                size=sizeEdt.getText().toString().trim();
                color=colorEdt.getText().toString().trim();
                qty=quantityEdt.getText().toString().trim();
                Item item=new Item();
                item.setItemName(name);
                item.setItemSize(Integer.parseInt(size));
                item.setItemColor(color);
                item.setItemQuantity(Integer.parseInt(qty));
                if (name!=null || size!=null || color!=null || qty!=null) {
                    databaseHandler.addItem(item);
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(view, "Item Added", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    alertDialog.dismiss();
                                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                                }
                            }, 1200);
                } else {
                    Snackbar.make(view, "Fields can not be empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Toast.makeText(MainActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }
            }

        });
        alertDialogBuilder.setView(view);
        alertDialog=alertDialogBuilder.create();// creating object of dialog
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void byPassActivity(){
        if(databaseHandler.getAllItems().size()>0){
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
