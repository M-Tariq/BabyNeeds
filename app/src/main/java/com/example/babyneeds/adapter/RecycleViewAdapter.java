package com.example.babyneeds.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babyneeds.R;
import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.example.babyneeds.util.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    public Context context;
    public List<Item> itemList=new ArrayList<>();
    Item item=new Item();

    public RecycleViewAdapter(Context context, List<Item> itemList) {
        this.itemList=itemList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content, parent, false);
        return new ViewHolder(view, context);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item=itemList.get(position);
        holder.itemName.setText("Item Name: "+item.getItemName());
        holder.itemQTY.setText("Item Qty: "+item.getItemQuantity());
        holder.itemColor.setText("Item Color: "+item.getItemColor());
        holder.itemSize.setText("Item Size: "+item.getItemSize());
        holder.itemDate.setText("Added Date: "+item.getDateItemAdded());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int id;
        public TextView itemName;
        public TextView itemQTY;
        public TextView itemColor;
        public TextView itemSize;
        public TextView itemDate;
        public Button edtButton;
        public Button delButton;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context=ctx;
            itemName=itemView.findViewById(R.id.item_name_tv);
            itemQTY=itemView.findViewById(R.id.item_qty_tv);
            itemColor=itemView.findViewById(R.id.item_color_tv);
            itemSize=itemView.findViewById(R.id.item_size_tv);
            itemDate=itemView.findViewById(R.id.item_date_tv);

            edtButton=itemView.findViewById(R.id.edt_button);
            delButton=itemView.findViewById(R.id.del_button);


            //edit
            edtButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos=getAdapterPosition();
                    Toast.makeText(context, "Edt clicked", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    final View view=LayoutInflater.from(context).inflate(R.layout.pop_up_edit,null,false);
                    builder.setView(view);
                    final AlertDialog alertDialog=builder.create();
                    alertDialog.show();

                    final EditText name, qty, size, color;
                    name=view.findViewById(R.id.item_name_update);
                    qty=view.findViewById(R.id.item_quantity_update);
                    size=view.findViewById(R.id.item_size_update);
                    color=view.findViewById(R.id.item_color_update);

                    item=itemList.get(pos);
                    name.setText(item.getItemName());
                    qty.setText(String.valueOf(item.getItemQuantity()));
                    size.setText(String.valueOf(item.getItemSize()));
                    color.setText(item.getItemColor());
                    Button updateButton=view.findViewById(R.id.update_button_update);
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String itemName=name.getText().toString().trim();
                            String itemColor=color.getText().toString().trim();
                            int itemSize=Integer.parseInt(size.getText().toString().trim());
                            int itemQty=Integer.parseInt(qty.getText().toString().trim());
                            item.setItemName(itemName);
                            item.setItemQuantity(itemQty);
                            item.setItemColor(itemColor);
                            item.setItemSize(itemSize);

                            DatabaseHandler databaseHandler=new DatabaseHandler(context);
                            databaseHandler.updateItem(item);
                            alertDialog.dismiss();
                            notifyItemChanged(pos);
                            Toast.makeText(v.getContext(), "Item Update Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            //delete
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setMessage("Are you Sure to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos=getAdapterPosition();
                                    Item item=new Item();
                                    item=itemList.get(pos);
                                    DatabaseHandler db=new DatabaseHandler(context);
                                    db.deleteItem(item.getId());
                                    itemList.remove(id);
                                    notifyItemRemoved(id);
                                    //notifyItemMoved(getAdapterPosition());
                                    Toast.makeText(context, "Item deleted Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });


        }
    }

}
