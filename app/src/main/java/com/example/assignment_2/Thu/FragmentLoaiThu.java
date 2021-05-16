package com.example.assignment_2.Thu;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_2.Adapter.AdapterRecyclerViewLoaiThu;
import com.example.assignment_2.Context.LoaiThu;
import com.example.assignment_2.Database.DataLoaiThu;
import com.example.assignment_2.Database.KhoanChiContract;
import com.example.assignment_2.Database.LoaiChiContract;
import com.example.assignment_2.Database.LoaiThuContract.*;
import com.example.assignment_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentLoaiThu extends Fragment {
    private View mRootView;
    private FloatingActionButton btnAdd;
    private ArrayList<LoaiThu> listLoaiThu;
    AdapterRecyclerViewLoaiThu adapterRecyclerViewLoaiThu;
    private SQLiteDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_loai_thu, container, false);

        DataLoaiThu dbHelper = new DataLoaiThu(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_loai_thu);
        btnAdd = (FloatingActionButton) mRootView.findViewById(R.id.btn_action_float_add_loai_thu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listLoaiThu = new ArrayList<>();
//        listLoaiThu.add(new LoaiThu("Lương"));
//        listLoaiThu.add(new LoaiThu("Chạy Grab"));
//        listLoaiThu.add(new LoaiThu("Tiền Youtube"));
//        listLoaiThu.add(new LoaiThu("Viết blog"));
//        listLoaiThu.add(new LoaiThu("Lãi ngân hàng"));
        adapterRecyclerViewLoaiThu = new AdapterRecyclerViewLoaiThu(getAllItem(), getActivity());
        recyclerView.setAdapter(adapterRecyclerViewLoaiThu);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflaterBtnAdd = getLayoutInflater();
                View layoutBtnAdd = inflaterBtnAdd.inflate(R.layout.dialog_loai_thu_add,null);
                AlertDialog.Builder builderBtnAdd = new AlertDialog.Builder(getContext());
                Button btnCalcel = (Button) layoutBtnAdd.findViewById(R.id.btn_huy_loai_thu_add);
                Button btnOk = (Button) layoutBtnAdd.findViewById(R.id.btn_luu_loai_thu_add);
                EditText edLoaiThuAdd = (EditText) layoutBtnAdd.findViewById(R.id.ed_nhap_title_add);
                builderBtnAdd.setView(layoutBtnAdd);
                AlertDialog alertDialogBtnAdd = builderBtnAdd.create();
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoaiThu lc = new LoaiThu(edLoaiThuAdd.getText().toString());
                        add(lc);
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
        adapterRecyclerViewLoaiThu.setOnUpdateListener(new AdapterRecyclerViewLoaiThu.setOnItemClickListener() {

            @Override
            public void onClick(int position, AdapterRecyclerViewLoaiThu.ViewHolder viewHolder, String name) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_loai_thu_update, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                TextView tv_ten_loai_thu = (TextView) layout.findViewById(R.id.tv_sua_ten_loai_thu_update);
                EditText edLoaiThu = (EditText) layout.findViewById(R.id.ed_nhap_title_update);
                Button btnCalcel = (Button) layout.findViewById(R.id.btn_huy_loai_thu_update);
                Button btnOk = (Button) layout.findViewById(R.id.btn_luu_loai_thu_update);
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
                        LoaiThu lt = new LoaiThu(edLoaiThu.getText().toString());
                        long id  = (long) viewHolder.itemView.getTag();
                        updateItem(lt,position, id);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        adapterRecyclerViewLoaiThu.setOnDeleteListener(new AdapterRecyclerViewLoaiThu.setOnItemClickListener() {
            @Override
            public void onClick(int position, AdapterRecyclerViewLoaiThu.ViewHolder viewHolder, String name) {
                long id =(long) viewHolder.itemView.getTag();
                mDatabase.delete(loaiThuEntry.TABLE_NAME,loaiThuEntry._ID+" =?",
                        new String[]{String.valueOf(id)});
                adapterRecyclerViewLoaiThu.swapCursor(getAllItem());
            }
        });
        return mRootView;
    }

    private void updateItem(LoaiThu lt, int position, long id) {
        if (lt.getTen_thu_nhap().trim().length() == 0 ){
            return;
        }
        ContentValues values = new ContentValues();
        values.put(loaiThuEntry.COLUMN_NAME,lt.getTen_thu_nhap());
        mDatabase.update(loaiThuEntry.TABLE_NAME,values,loaiThuEntry._ID+" =?",
                new String[]{String.valueOf(id)});
        adapterRecyclerViewLoaiThu.swapCursor(getAllItem());
    }

    public void add(LoaiThu lt){
        if (lt.getTen_thu_nhap().trim().length() == 0 ){
            return;
        }
        ContentValues values = new ContentValues();
        values.put(loaiThuEntry.COLUMN_NAME,lt.getTen_thu_nhap());
        mDatabase.insert(loaiThuEntry.TABLE_NAME,null,values);
        adapterRecyclerViewLoaiThu.swapCursor(getAllItem());
    }
    public Cursor getAllItem() {
        return mDatabase.query(
                loaiThuEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                loaiThuEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

}
