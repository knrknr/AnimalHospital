package com.nrsoft.animalhospital;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.VH> {
    Context context;
    ArrayList<Item> items;

    public AnimalAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new VH(itemView);
    }

    boolean isFavor(Item item){
        SQLiteDatabase db= context.openOrCreateDatabase("animalhospital", Context.MODE_PRIVATE, null);
        Cursor cursor= db.rawQuery("SELECT * FROM favor WHERE name=? AND mgtNo=?", new String[]{item.name, item.mgtNo});
        int rowNum= cursor.getCount();
        if(rowNum>0) return true;
        else return false;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item= items.get(position);
        holder.tvName.setText(item.name);
        holder.tvAddr1.setText(item.addrgi);
        holder.tvAddr2.setText(item.addrdo);
        holder.tvTel.setText(item.tel);
        holder.tvCondition.setText(item.condition);

        boolean favor= isFavor(item);

        holder.tbFav.setChecked(favor);

        holder.tbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SQLiteDatabase db= context.openOrCreateDatabase("animalhospital", Context.MODE_PRIVATE, null);
                if (b) {
                    Toast.makeText(context, "찜이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    db.execSQL("INSERT INTO favor(name, mgtNo) VALUES(?,?)", new String[]{item.name, item.mgtNo});

                }else {
                    Toast.makeText(context, "찜이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    db.execSQL("DELETE FROM favor WHERE name=? AND mgtNo=?", new String[]{item.name, item.mgtNo});
                }
            }
        });

        holder.itemView.setOnClickListener(v->{
            Intent intent= new Intent(context, ItemActivity.class);
            intent.putExtra("name",item.name);
            intent.putExtra("addr",item.addrdo);
            intent.putExtra("tel",item.tel);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class//////////////////////////////////////////////////////
    class VH extends RecyclerView.ViewHolder{

        //holder가 가지고 있는 것들
        TextView tvName;
        TextView tvAddr1;
        TextView tvAddr2;
        TextView tvTel;
        TextView tvCondition;
        ToggleButton tbFav;


        public VH(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tv_name);
            tvAddr1= itemView.findViewById(R.id.tv_addr1);
            tvAddr2= itemView.findViewById(R.id.tv_addr2);
            tvTel= itemView.findViewById(R.id.tv_tel);
            tvCondition= itemView.findViewById(R.id.tv_condition);
            tbFav=itemView.findViewById(R.id.tb_favor);

        }
    }//////////////////////////////////////////////////////////////////
}



