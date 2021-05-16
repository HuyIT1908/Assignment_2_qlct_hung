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

import com.example.assignment_2.Context.KhoanThu;
import com.example.assignment_2.Database.KhoanThuContract.*;
import com.example.assignment_2.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterRecyclerViewKhoanThu extends RecyclerView.Adapter<AdapterRecyclerViewKhoanThu.ViewHolder> {
    //    private ArrayList<KhoanThu> list;
    private Cursor mCursor;
    Context context;
    private setOnItemClickListener mListener;
    private setOnItemClickListener updateListener;
    private setOnItemClickListener deleteListener;

    public AdapterRecyclerViewKhoanThu(Cursor cursor, Context context) {
        mCursor = cursor;
        this.context = context;
    }

    public interface setOnItemClickListener {
        void onClick(int position, AdapterRecyclerViewKhoanThu.ViewHolder viewHolder,
                     String textLT, String textND, String textST);
        void onDelete(int position, AdapterRecyclerViewKhoanThu.ViewHolder viewHolder);
    }

    public void setOnItemClickListener(setOnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnUpdateListener(setOnItemClickListener listener) {
        updateListener = listener;
    }

    public void setOnDeleteListener(setOnItemClickListener listener) {
        deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_item_khoan_thu, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String tenLoaiThu = mCursor.getString(mCursor.getColumnIndex(khoanThuEntry.COLUMN_CATEGORIES));
        String noiDungLoaiThu = mCursor.getString(mCursor.getColumnIndex(khoanThuEntry.COLUMN_CONTENT));
        holder.tvTitleLoaiThu.setText(tenLoaiThu);
        holder.tvValueLoaiThu.setText(noiDungLoaiThu);
        String soTienThuDau = mCursor.getString(mCursor.getColumnIndex(khoanThuEntry.COLUMN_MONEY));
        DecimalFormat formater = new DecimalFormat("###,###,###,###,###");
        String soTienThuSau = formater.format(Double.parseDouble(soTienThuDau));
        holder.tvValueSoTienThu.setText(soTienThuSau + "vnđ");
        long id = mCursor.getLong(mCursor.getColumnIndex(khoanThuEntry._ID));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

//    public void removeItem(int position) {
//        list.remove(position);
//        notifyItemRemoved(position);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleLoaiThu, tvValueSoTienThu, tvValueLoaiThu;
        ImageView imgEdit, imgDelete;

        public ViewHolder(@NonNull View itemView, setOnItemClickListener mListener) {
            super(itemView);
            tvTitleLoaiThu = (TextView) itemView.findViewById(R.id.tv_loai_thu);
            tvValueLoaiThu = (TextView) itemView.findViewById(R.id.tv_value_loai_thu_2);
            tvValueSoTienThu = (TextView) itemView.findViewById(R.id.tv_value_tien_thu);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit_khoan_thu);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_delete_khoan_thu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textST_chua_XL1 = tvValueSoTienThu.getText().toString();
                    String textST_chua_XL2 = textST_chua_XL1.substring(0, textST_chua_XL1.indexOf("vn"));
                    String textST_chua_XL3[] = textST_chua_XL2.split("\\.");

                    String textST = String.join("",textST_chua_XL3);
                    updateListener.onClick(getAdapterPosition(), AdapterRecyclerViewKhoanThu.ViewHolder.this,
                            tvTitleLoaiThu.getText().toString(),tvValueLoaiThu.getText().toString(),textST);

                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.onDelete(getAdapterPosition(),AdapterRecyclerViewKhoanThu.ViewHolder.this);
                    Toast.makeText(itemView.getContext(), "Xóa " + tvTitleLoaiThu.getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public void swapCursor(Cursor newCursor){
        if (mCursor!=null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor!= null){
            notifyDataSetChanged();
        }
    }
}
