package com.example.assignment_2.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_2.Database.LoaiThuContract.*;
import com.example.assignment_2.R;

import java.util.ArrayList;

public class AdapterRecyclerViewLoaiThu extends RecyclerView.Adapter<AdapterRecyclerViewLoaiThu.ViewHolder> {
//    ArrayList<LoaiThu> list;
    Context context;
    setOnItemClickListener mListener;
    setOnItemClickListener deleteListener;
    setOnItemClickListener updateListener;
    Cursor mCursor;

    public AdapterRecyclerViewLoaiThu(Cursor cursor, Context context) {
        mCursor = cursor;
        this.context = context;
    }

    public interface setOnItemClickListener{
        void onClick(int position, AdapterRecyclerViewLoaiThu.ViewHolder viewHolder, String name);
    }

    public void setOnItemClickListener(setOnItemClickListener listener){
        mListener=listener;
    }

    public void setOnDeleteListener(setOnItemClickListener listener) {
        deleteListener = listener;
    }

    public void setOnUpdateListener(setOnItemClickListener listener){
        updateListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_item_loai_thu,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(loaiThuEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(loaiThuEntry._ID));
        holder.itemView.setTag(id);
        holder.txtName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

//    public void RemoveItem(int position){
//        list.remove(position);
//        notifyItemRemoved(position);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView imgDelete,imgEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName= (TextView) itemView.findViewById(R.id.tv_name_row_item);
            imgDelete= (ImageView) itemView.findViewById(R.id.img_delete_loai_thu);
            imgEdit= (ImageView) itemView.findViewById(R.id.img_edit_loai_thu);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    deleteListener.onClick(position, AdapterRecyclerViewLoaiThu.ViewHolder.this, "");
                    Toast.makeText(itemView.getContext(), "Xóa "+txtName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = txtName.getText().toString();
                    int position = getAdapterPosition();
                    updateListener.onClick(position, AdapterRecyclerViewLoaiThu.ViewHolder.this, name);
                }
            });
        }
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }
}
