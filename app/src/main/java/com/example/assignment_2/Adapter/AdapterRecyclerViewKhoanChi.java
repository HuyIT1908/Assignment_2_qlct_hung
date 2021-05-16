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

import com.example.assignment_2.Context.KhoanChi;
import com.example.assignment_2.Database.KhoanChiContract;
import com.example.assignment_2.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterRecyclerViewKhoanChi extends RecyclerView.Adapter<AdapterRecyclerViewKhoanChi.ViewHolder> {
    private ArrayList<KhoanChi> list;
    Context context;
    private setOnItemClickListener mListener;
    private setOnItemClickListener mListenerUpdate;
    private setOnItemClickListener mListenerDelete;
    private Cursor mCursor;

    public AdapterRecyclerViewKhoanChi(Cursor cursor, Context context) {
        mCursor = cursor;
        this.context = context;
    }

    public interface setOnItemClickListener {
        void onClick(int position);

        void onUpdateClick(int position, AdapterRecyclerViewKhoanChi.ViewHolder viewHolder,
                           String textLC, String textND, String textST);

        void onDeleteClick(int position, AdapterRecyclerViewKhoanChi.ViewHolder viewHolder);

    }

    public void setOnItemClickListener(setOnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnUpdateListener(setOnItemClickListener listener) {
        mListenerUpdate = listener;
    }

    public void setOnDeleteListener(setOnItemClickListener listener) {
        mListenerDelete = listener;
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
        String loaiChi = mCursor.getString(mCursor.getColumnIndex(KhoanChiContract.khoanChiEntry.COLUMN_CATEGORIES));
        String noiDungChi = mCursor.getString(mCursor.getColumnIndex(KhoanChiContract.khoanChiEntry.COLUMN_CONTENT));
        long id = mCursor.getLong(mCursor.getColumnIndex(KhoanChiContract.khoanChiEntry._ID));

        holder.tvTitleLoaiThu.setText(loaiChi);
        holder.tvValueLoaiThu.setText(noiDungChi);
        String soTienChiDau = mCursor.getString(mCursor.getColumnIndex(KhoanChiContract.khoanChiEntry.COLUMN_MONEY));
        DecimalFormat formater = new DecimalFormat("###,###,###,###,###");
        String soTienChiSau = formater.format(Double.parseDouble(soTienChiDau));
        holder.tvValueSoTienThu.setText(soTienChiSau + "vnđ");
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

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

                    mListenerUpdate.onUpdateClick(getAdapterPosition(),
                            AdapterRecyclerViewKhoanChi.ViewHolder.this, tvTitleLoaiThu.getText().toString(),
                            tvValueLoaiThu.getText().toString(), textST);

                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenerDelete.onDeleteClick(getAdapterPosition(), AdapterRecyclerViewKhoanChi.ViewHolder.this);
                    Toast.makeText(itemView.getContext(), "Xóa " + tvTitleLoaiThu.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void swapArr(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

}
