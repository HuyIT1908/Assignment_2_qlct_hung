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

import com.example.assignment_2.Chi.FragmentLoaiChi;
import com.example.assignment_2.Context.ArrayLoaiChi;
import com.example.assignment_2.Context.ArrayLoaiThu;
import com.example.assignment_2.Context.LoaiChi;
import com.example.assignment_2.Context.LoaiThu;
import com.example.assignment_2.Database.LoaiChiContract;
import com.example.assignment_2.R;

import java.util.ArrayList;

public class AdapterRecyclerViewLoaiChi extends RecyclerView.Adapter<AdapterRecyclerViewLoaiChi.ViewHolder> {
    //    ArrayList<LoaiChi> list;
    Context context;
    setOnItemClickListener mListener;
    setOnItemClickListener mDeleteListener;
    private Cursor mCursor;


    public AdapterRecyclerViewLoaiChi(Cursor cursor, Context context) {
        mCursor = cursor;
        this.context = context;
    }

    public interface setOnItemClickListener {
        void onClick(int position);
        void onIconDelete(int position,AdapterRecyclerViewLoaiChi.ViewHolder viewHolder );
        void onIconUpdate(int position,AdapterRecyclerViewLoaiChi.ViewHolder viewHolder , String name);
    }

    public void setOnIconDeleteClickListener(setOnItemClickListener listener) {
        mDeleteListener = listener;
    }

    public void setOnItemClickListener(setOnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_item_loai_chi, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(LoaiChiContract.loaiChiEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(LoaiChiContract.loaiChiEntry._ID));
        holder.txtName.setText(name);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgDelete, imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.tv_name_row_item_loai_chi);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_delete_loai_chi);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit_loai_chi);
            int position = getAdapterPosition();
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDeleteListener.onIconDelete(position, AdapterRecyclerViewLoaiChi.ViewHolder.this);

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = mCursor.getString(mCursor.getColumnIndex(LoaiChiContract.loaiChiEntry.COLUMN_NAME));
                    mListener.onIconUpdate(position,AdapterRecyclerViewLoaiChi.ViewHolder.this,name);
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
