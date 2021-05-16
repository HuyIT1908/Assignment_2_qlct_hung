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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_2.Adapter.AdapterRecyclerViewKhoanChi;
import com.example.assignment_2.Adapter.AdapterRecyclerViewKhoanThu;
import com.example.assignment_2.Context.ArrayLoaiChi;
import com.example.assignment_2.Context.KhoanChi;
import com.example.assignment_2.Context.KhoanThu;
import com.example.assignment_2.Context.LoaiChi;
import com.example.assignment_2.Database.DataKhoanChi;
import com.example.assignment_2.Database.KhoanChiContract;
import com.example.assignment_2.Database.LoaiChiContract;
import com.example.assignment_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentKhoanChi extends Fragment {
    private ArrayList<LoaiChi> listLoaiChi;
    private FloatingActionButton btnAdd;
    private AdapterRecyclerViewKhoanChi adapterRecyclerViewKhoanChi;
    private View mRootView;
    private String listSpnLoaiChi[];
    private SQLiteDatabase mDatabase;
    public FragmentLoaiChi fragLChi;


    public FragmentKhoanChi(FragmentLoaiChi fragmentLoaiChi){
        fragLChi = fragmentLoaiChi;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_khoan_chi, container, false);

        DataKhoanChi dbHelper = new DataKhoanChi(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_khoan_chi);
        btnAdd = (FloatingActionButton) mRootView.findViewById(R.id.btn_action_float_add_khoan_chi);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listLoaiChi = new ArrayList<>();
//        listKhoanChi = new ArrayList<>();
//        listKhoanChi.add(new KhoanChi("3000000", "Ăn","ăn tháng 10"));
//        listKhoanChi.add(new KhoanChi("300000", "Đi chơi","Chơi tháng 10"));
//        listKhoanChi.add(new KhoanChi("30000000", "Mua quần áo","mua quần áo tháng 10"));
//        listKhoanChi.add(new KhoanChi("300000", "Tiền điện","tiền điện tháng 10"));
//        listKhoanChi.add(new KhoanChi("100000", "Tiền nước","tiền nước tháng 10"));
        adapterRecyclerViewKhoanChi = new AdapterRecyclerViewKhoanChi(getAllItem(), getActivity());
        recyclerView.setAdapter(adapterRecyclerViewKhoanChi);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = getLayoutInflater().inflate(R.layout.dialog_khoan_chi_add, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Button btnCalcel = (Button) layout.findViewById(R.id.btn_huy_khoan_chi_add);
                Button btnOk = (Button) layout.findViewById(R.id.btn_luu_khoan_chi_add);
                EditText edLoaiChi = (EditText) layout.findViewById(R.id.ed_nhap_title_khoan_chi_add);
                Spinner spnLoaiChi = (Spinner) layout.findViewById(R.id.spiner_so_tien_thu_trong_khoan_chi_add);
                EditText edSoTienChi = (EditText) layout.findViewById(R.id.ed_nhap_so_tien_khoan_chi_add);
                getListLoaiChi(fragLChi.getAllItem());
                ArrayAdapter adapterSpn = new ArrayAdapter<String>(getContext(),
                        R.layout.support_simple_spinner_dropdown_item, createListSpn());
                spnLoaiChi.setAdapter(adapterSpn);
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                final String[] luu_loai_chi = new String[1];
                spnLoaiChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        luu_loai_chi[0] = adapterSpn.getItem(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                clearDialog(edLoaiChi, edSoTienChi, spnLoaiChi);
                btnCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KhoanChi kc = new KhoanChi(edSoTienChi.getText().toString(), luu_loai_chi[0],
                                edLoaiChi.getText().toString());
                        createKhoanChi(kc);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        adapterRecyclerViewKhoanChi.setOnUpdateListener(new AdapterRecyclerViewKhoanChi.setOnItemClickListener() {
            @Override
            public void onClick(int position) {
            }

            @Override
            public void onUpdateClick(int position, AdapterRecyclerViewKhoanChi.ViewHolder viewHolder,
                                      String textLC, String textND, String textST) {
                View layout = getLayoutInflater().inflate(R.layout.dialog_khoan_chi_update, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Button btnCalcel = (Button) layout.findViewById(R.id.btn_huy_khoan_chi_update);
                Button btnOk = (Button) layout.findViewById(R.id.btn_luu_khoan_chi_update);
                Spinner spnLoaiChi = (Spinner) layout.findViewById(R.id.spiner_so_tien_thu_trong_khoan_chi_update);
                EditText edNoiDungChi = (EditText) layout.findViewById(R.id.ed_nhap_title_khoan_chi_update);
                EditText edSoTienChi = (EditText) layout.findViewById(R.id.ed_nhap_so_tien_khoan_chi_update);
                getListLoaiChi(fragLChi.getAllItem());
                ArrayAdapter adapterSpn = new ArrayAdapter<String>(getContext(),
                        R.layout.support_simple_spinner_dropdown_item, createListSpn());
                spnLoaiChi.setAdapter(adapterSpn);
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                edNoiDungChi.setText(textND);
                edSoTienChi.setText(textST);
                spnLoaiChi.setSelection(getIndext(spnLoaiChi, textLC));
                btnCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
                final String[] stringSpnLoaiThuUpdate = new String[1];
                spnLoaiChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        stringSpnLoaiThuUpdate[0] = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long id = (long) viewHolder.itemView.getTag();
                        KhoanChi kc = new KhoanChi(edSoTienChi.getText().toString(), stringSpnLoaiThuUpdate[0],
                                edNoiDungChi.getText().toString());
                        upDateItem(position, kc, id);
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onDeleteClick(int position, AdapterRecyclerViewKhoanChi.ViewHolder viewHolder) {

            }
        });
        adapterRecyclerViewKhoanChi.setOnDeleteListener(new AdapterRecyclerViewKhoanChi.setOnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onUpdateClick(int position, AdapterRecyclerViewKhoanChi.ViewHolder viewHolder,
                                      String textLC, String textND, String textST) {

            }
            
            @Override
            public void onDeleteClick(int position, AdapterRecyclerViewKhoanChi.ViewHolder viewHolder) {
//                listKhoanChi.remove(position);
                long id = (long) viewHolder.itemView.getTag();
                mDatabase.delete(KhoanChiContract.khoanChiEntry.TABLE_NAME,
                        KhoanChiContract.khoanChiEntry._ID+" =? ",new String[]{String.valueOf(id)} );
                adapterRecyclerViewKhoanChi.swapArr(getAllItem());
            }
        });
        return mRootView;
    }

    private void upDateItem(int position, KhoanChi kc,long id) {
//        listKhoanChi.set(position,kc);
        ContentValues values = new ContentValues();
        values.put(KhoanChiContract.khoanChiEntry.COLUMN_CATEGORIES,kc.getLoaiChi());
        values.put(KhoanChiContract.khoanChiEntry.COLUMN_CONTENT,kc.getNoiDungChi());
        values.put(KhoanChiContract.khoanChiEntry.COLUMN_MONEY,kc.getSoTienChi());

        mDatabase.update(KhoanChiContract.khoanChiEntry.TABLE_NAME, values,
                KhoanChiContract.khoanChiEntry._ID+" =?", new String[]{String.valueOf(id)});
        adapterRecyclerViewKhoanChi.swapArr(getAllItem());

    }

    private int getIndext(Spinner spnLoaiChi, String loaiChi) {
        for (int i = 0; i < spnLoaiChi.getCount(); i++) {
            if (spnLoaiChi.getItemAtPosition(i).toString().equalsIgnoreCase(loaiChi)) {
                return i;
            }
        }
        return 0;
    }

    public void createKhoanChi(KhoanChi khoanChi) {
//        listKhoanChi.add(khoanChi);
        ContentValues cv = new ContentValues();
        cv.put(KhoanChiContract.khoanChiEntry.COLUMN_CATEGORIES, khoanChi.getLoaiChi());
        cv.put(KhoanChiContract.khoanChiEntry.COLUMN_CONTENT,khoanChi.getNoiDungChi());
        cv.put(KhoanChiContract.khoanChiEntry.COLUMN_MONEY,khoanChi.getSoTienChi());
        mDatabase.insert(KhoanChiContract.khoanChiEntry.TABLE_NAME,null,cv);
        adapterRecyclerViewKhoanChi.swapArr(getAllItem());
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
    }

    public void clearDialog(EditText edNoiDung, EditText edSoTienThu, Spinner spnLoaiThu) {
        edNoiDung.setText("");
        edSoTienThu.setText("");
        spnLoaiThu.setSelection(0);
    }

    public String[] createListSpn() {
        listSpnLoaiChi = new String[listLoaiChi.size()];
        int i = 0;
        for (LoaiChi a : listLoaiChi) {
            listSpnLoaiChi[i] = a.getTen_thu_nhap();
            i++;
        }
        return listSpnLoaiChi;
    }

    public void getListLoaiChi(Cursor cursor){
        listLoaiChi.clear();
        if (cursor.moveToFirst()) {
            do {
                LoaiChi lc = new LoaiChi(cursor.getString(cursor.getColumnIndex(LoaiChiContract.loaiChiEntry.COLUMN_NAME)));
                listLoaiChi.add(lc);
            } while (cursor.moveToNext());
        }

    }

    public Cursor getAllItem() {

        Cursor cursor = mDatabase.query(
                KhoanChiContract.khoanChiEntry.TABLE_NAME ,
                null,
                null,
                null,
                null,
                null,
                KhoanChiContract.khoanChiEntry.COLUMN_TIMESTAMP + " DESC "
        );

        return cursor;
    }
}
