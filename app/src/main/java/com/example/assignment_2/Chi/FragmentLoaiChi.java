package com.example.assignment_2.Chi;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_2.Adapter.AdapterRecyclerViewLoaiChi;
import com.example.assignment_2.Adapter.AdapterRecyclerViewLoaiThu;
import com.example.assignment_2.Context.ArrayLoaiChi;
import com.example.assignment_2.Context.LoaiChi;
import com.example.assignment_2.Context.LoaiThu;
import com.example.assignment_2.Database.DataLoaiChi;
import com.example.assignment_2.Database.LoaiChiContract;
import com.example.assignment_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentLoaiChi extends Fragment {
    private View mRootView;
    private FloatingActionButton btnAdd;
    public ArrayList<LoaiChi> listLoaiChi;
    AdapterRecyclerViewLoaiChi adapterRecyclerViewLoaiChi;
    private SQLiteDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_loai_chi, container, false);

        DataLoaiChi dbHelper = new DataLoaiChi(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_loai_chi);
        btnAdd = (FloatingActionButton) mRootView.findViewById(R.id.btn_action_float_add_loai_chi);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapterRecyclerViewLoaiChi = new AdapterRecyclerViewLoaiChi(getAllItem() , getActivity());
        recyclerView.setAdapter(adapterRecyclerViewLoaiChi);
//        taoListMau();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflaterBtnAdd = getLayoutInflater();
                View layoutBtnAdd = inflaterBtnAdd.inflate(R.layout.dialog_loai_chi_add, null);
                AlertDialog.Builder builderBtnAdd = new AlertDialog.Builder(getContext());
                Button btnCalcel = (Button) layoutBtnAdd.findViewById(R.id.btn_huy_loai_chi_add);
                Button btnOk = (Button) layoutBtnAdd.findViewById(R.id.btn_luu_loai_chi_add);
                EditText edLoaiChiAdd = (EditText) layoutBtnAdd.findViewById(R.id.ed_nhap_title_add_loai_chi);
                builderBtnAdd.setView(layoutBtnAdd);
                AlertDialog alertDialogBtnAdd = builderBtnAdd.create();
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add(edLoaiChiAdd);
                        alertDialogBtnAdd.dismiss();
                    }
                });
                btnCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogBtnAdd.dismiss();
                    }
                });
                alertDialogBtnAdd.show();
            }
        });
        adapterRecyclerViewLoaiChi.setOnItemClickListener(new AdapterRecyclerViewLoaiChi.setOnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onIconDelete(int position, AdapterRecyclerViewLoaiChi.ViewHolder viewHolder) {

            }

            @Override
            public void onIconUpdate(int position, AdapterRecyclerViewLoaiChi.ViewHolder viewHolder, String name) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_loai_chi_update, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                TextView tv_ten_loai_thu = (TextView) layout.findViewById(R.id.tv_sua_ten_loai_chi_update);
                Button btnCalcel = (Button) layout.findViewById(R.id.btn_huy_loai_chi_update);
                Button btnOk = (Button) layout.findViewById(R.id.btn_luu_loai_chi_update);
                EditText edLoaiChiUpdate = (EditText) layout.findViewById(R.id.ed_nhap_title_update_loai_chi);
                tv_ten_loai_thu.setText("Sửa " + name + " thành");
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                btnCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        LoaiChi lc = new LoaiChi(edLoaiChiUpdate.getText().toString());
                        long id =(long) viewHolder.itemView.getTag();
                        updateItem(edLoaiChiUpdate.getText().toString(), id);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        adapterRecyclerViewLoaiChi.setOnIconDeleteClickListener(new AdapterRecyclerViewLoaiChi.setOnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onIconDelete(int position, AdapterRecyclerViewLoaiChi.ViewHolder viewHolder) {
                long id =(long) viewHolder.itemView.getTag();
                mDatabase.delete(LoaiChiContract.loaiChiEntry.TABLE_NAME,
                        LoaiChiContract.loaiChiEntry._ID + "=?",new String[]{String.valueOf(id)});
                adapterRecyclerViewLoaiChi.swapCursor(getAllItem());
            }

            @Override
            public void onIconUpdate(int position, AdapterRecyclerViewLoaiChi.ViewHolder viewHolder, String name) {

            }
        });

        return mRootView;
    }

    private void updateItem(String loaiChi, long id) {
        ContentValues values = new ContentValues();
        values.put(LoaiChiContract.loaiChiEntry.COLUMN_NAME,loaiChi);
        mDatabase.update(LoaiChiContract.loaiChiEntry.TABLE_NAME,values,LoaiChiContract.loaiChiEntry._ID+" =?",
                new String[]{String.valueOf(id)});
        adapterRecyclerViewLoaiChi.swapCursor(getAllItem());
    }

    public void add(EditText edLoaiChiAdd) {
        String loaiChi = edLoaiChiAdd.getText().toString();
        if (loaiChi.trim().length()==0){
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(LoaiChiContract.loaiChiEntry.COLUMN_NAME, loaiChi);
        mDatabase.insert(LoaiChiContract.loaiChiEntry.TABLE_NAME,null,cv);
        adapterRecyclerViewLoaiChi.swapCursor(getAllItem());
    }

    public void taoListMau(){
        listLoaiChi = new ArrayList<>();
        listLoaiChi.add(new LoaiChi("Tiền điện"));
        listLoaiChi.add(new LoaiChi("Tiền nước"));
        listLoaiChi.add(new LoaiChi("Tiền internet"));
        listLoaiChi.add(new LoaiChi("Tiền mua quần áo"));
        listLoaiChi.add(new LoaiChi("Tiền ăn"));
        ContentValues cv = new ContentValues();
        for (LoaiChi lchi : listLoaiChi) {
            cv.put(LoaiChiContract.loaiChiEntry.COLUMN_NAME, lchi.getTen_thu_nhap());
            mDatabase.insert(LoaiChiContract.loaiChiEntry.TABLE_NAME,null,cv);
        }
        adapterRecyclerViewLoaiChi.swapCursor(getAllItem());
    }

    public Cursor getAllItem() {
        return mDatabase.query(
                LoaiChiContract.loaiChiEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                LoaiChiContract.loaiChiEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}
