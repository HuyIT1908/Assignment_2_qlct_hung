package com.example.assignment_2.Thu;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.assignment_2.Adapter.AdapterRecyclerViewKhoanThu;
import com.example.assignment_2.Context.KhoanThu;
import com.example.assignment_2.Context.LoaiChi;
import com.example.assignment_2.Context.LoaiThu;
import com.example.assignment_2.Database.DataKhoanThu;
import com.example.assignment_2.Database.KhoanChiContract;
import com.example.assignment_2.Database.KhoanThuContract.*;
import com.example.assignment_2.Database.LoaiChiContract;
import com.example.assignment_2.Database.LoaiThuContract;
import com.example.assignment_2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentKhoanThu extends Fragment {
    private ArrayList<LoaiThu> listLoaiThu;
    private FloatingActionButton btnAdd;
    private AdapterRecyclerViewKhoanThu adapterRecyclerViewKhoanThu;
    private View mRootView;
    private String listSpnLoaiThu[];
    private SQLiteDatabase mDatabase;
    public FragmentLoaiThu fragLThu;

    public FragmentKhoanThu(FragmentLoaiThu fragmentLoaiThu) {
        fragLThu = fragmentLoaiThu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_khoan_thu, container, false);

        DataKhoanThu dbHelper = new DataKhoanThu(getContext());
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_khoan_thu);
        btnAdd = (FloatingActionButton) mRootView.findViewById(R.id.btn_action_float_add_khoan_thu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listLoaiThu = new ArrayList<>();
//        listKhoanThu = new ArrayList<>();
//        listKhoanThu.add(new KhoanThu("3000000", "Lương","luong tháng 10"));
//        listKhoanThu.add(new KhoanThu("300000", "Chạy Grab","grab tháng 10"));
//        listKhoanThu.add(new KhoanThu("30000000", "Tiền Youtube","youtube tháng 10"));
//        listKhoanThu.add(new KhoanThu("300000", "Viết blog","blog tháng 10"));
//        listKhoanThu.add(new KhoanThu("100000", "Lãi ngân hàng","lai NH tháng 10"));
        listSpnLoaiThu = new String[0];

        adapterRecyclerViewKhoanThu = new AdapterRecyclerViewKhoanThu(getAllItem(), getActivity());
        recyclerView.setAdapter(adapterRecyclerViewKhoanThu);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = getLayoutInflater().inflate(R.layout.dialog_khoan_thu_add, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Button btnCalcel = (Button) layout.findViewById(R.id.btn_huy_khoan_thu);
                Button btnOk = (Button) layout.findViewById(R.id.btn_luu_khoan_thu);
                EditText edLoaiThu = (EditText) layout.findViewById(R.id.ed_nhap_title_khoan_thu);
                Spinner spnLoaiThu = (Spinner) layout.findViewById(R.id.spiner_so_tien_thu_trong_khoan_thu);
                EditText edSoTienThu = (EditText) layout.findViewById(R.id.ed_nhap_so_tien_khoan_thu_add);
                getListLoaiThu(fragLThu.getAllItem());
                ArrayAdapter adapterSpn = new ArrayAdapter<String>(getContext(),
                        R.layout.support_simple_spinner_dropdown_item, createListSpn());
                spnLoaiThu.setAdapter(adapterSpn);
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                final String[] luu_loai_thu = new String[1];
                spnLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        luu_loai_thu[0] = adapterSpn.getItem(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                clearDialog(edLoaiThu, edSoTienThu, spnLoaiThu);
                btnCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KhoanThu kt = new KhoanThu(edSoTienThu.getText().toString(), luu_loai_thu[0], edLoaiThu.getText().toString());
                        createKhoanThu(kt);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        adapterRecyclerViewKhoanThu.setOnUpdateListener(new AdapterRecyclerViewKhoanThu.setOnItemClickListener() {
            @Override
            public void onClick(int position, AdapterRecyclerViewKhoanThu.ViewHolder viewHolder,
                                String textLT, String textND, String textST) {
                View layout = getLayoutInflater().inflate(R.layout.dialog_khoan_thu_update, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Button btnCalcel = (Button) layout.findViewById(R.id.btn_huy_khoan_thu_update);
                Button btnOk = (Button) layout.findViewById(R.id.btn_luu_khoan_thu_update);
                Spinner spnLoaiThu = (Spinner) layout.findViewById(R.id.spiner_so_tien_thu_trong_khoan_thu_update);
                EditText edNoiDungThu = (EditText) layout.findViewById(R.id.ed_nhap_title_khoan_thu_update);
                EditText edSoTienThu = (EditText) layout.findViewById(R.id.ed_nhap_so_tien_khoan_thu_update);
                getListLoaiThu(fragLThu.getAllItem());
//                Toast.makeText(getContext(), listLoaiThu.size(), Toast.LENGTH_SHORT).show();
                ArrayAdapter adapterSpn = new ArrayAdapter<String>(getContext(),
                        R.layout.support_simple_spinner_dropdown_item, createListSpn());
                spnLoaiThu.setAdapter(adapterSpn);
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                edNoiDungThu.setText(textND);
                edSoTienThu.setText(textST);
                spnLoaiThu.setSelection(getIndext(spnLoaiThu, textLT));
                btnCalcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
                final String[] StringSpnLoaiThuUpdate = new String[1];
                spnLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        StringSpnLoaiThuUpdate[0] = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KhoanThu kt = new KhoanThu(edSoTienThu.getText().toString(), StringSpnLoaiThuUpdate[0], edNoiDungThu.getText().toString());
                        long id = (long) viewHolder.itemView.getTag();
                        updateItem(position, kt, id);
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onDelete(int position, AdapterRecyclerViewKhoanThu.ViewHolder viewHolder) {

            }
        });
        adapterRecyclerViewKhoanThu.setOnDeleteListener(new AdapterRecyclerViewKhoanThu.setOnItemClickListener() {
            @Override
            public void onClick(int position, AdapterRecyclerViewKhoanThu.ViewHolder viewHolder, String textLT, String textND, String textST) {

            }

            @Override
            public void onDelete(int position, AdapterRecyclerViewKhoanThu.ViewHolder viewHolder) {
                long id = (long) viewHolder.itemView.getTag();
                mDatabase.delete(khoanThuEntry.TABLE_NAME, khoanThuEntry._ID + " =?", new String[]{String.valueOf(id)});
                adapterRecyclerViewKhoanThu.swapCursor(getAllItem());
            }
        });
        return mRootView;
    }

    private void updateItem(int position, KhoanThu kt, long id) {
        ContentValues values = new ContentValues();
        values.put(khoanThuEntry.COLUMN_CATEGORIES, kt.getLoaiThu());
        values.put(khoanThuEntry.COLUMN_CONTENT, kt.getNoiDungThu());
        values.put(khoanThuEntry.COLUMN_MONEY, kt.getSoTienThu());
        mDatabase.update(khoanThuEntry.TABLE_NAME, values, khoanThuEntry._ID + " =?", new String[]{String.valueOf(id)});
        adapterRecyclerViewKhoanThu.swapCursor(getAllItem());
    }

    private int getIndext(Spinner spnLoaiThu, String loaiThu) {
        for (int i = 0; i < spnLoaiThu.getCount(); i++) {
            if (spnLoaiThu.getItemAtPosition(i).toString().equalsIgnoreCase(loaiThu)) {
                return i;
            }
        }
        return 0;
    }

    public void createKhoanThu(KhoanThu khoanThu) {
        ContentValues values = new ContentValues();
        values.put(khoanThuEntry.COLUMN_CATEGORIES, khoanThu.getLoaiThu());
        values.put(khoanThuEntry.COLUMN_CONTENT, khoanThu.getNoiDungThu());
        values.put(khoanThuEntry.COLUMN_MONEY, khoanThu.getSoTienThu());
        mDatabase.insert(khoanThuEntry.TABLE_NAME, null, values);
        adapterRecyclerViewKhoanThu.swapCursor(getAllItem());
    }

    public void clearDialog(EditText edNoiDung, EditText edSoTienThu, Spinner spnLoaiThu) {
        edNoiDung.setText("");
        edSoTienThu.setText("");
        spnLoaiThu.setSelection(0);
    }

    public String[] createListSpn() {
        listSpnLoaiThu = new String[listLoaiThu.size()];
        int i = 0;
        for (LoaiThu a : listLoaiThu) {
            listSpnLoaiThu[i] = a.getTen_thu_nhap();
            i++;
        }
        return listSpnLoaiThu;
    }
    public void getListLoaiThu(Cursor cursor) {
        listLoaiThu.clear();
        if (cursor.moveToFirst()) {
            do {
                LoaiThu lc = new LoaiThu(cursor.getString(cursor.getColumnIndex(LoaiThuContract.loaiThuEntry.COLUMN_NAME)));
                listLoaiThu.add(lc);
            } while (cursor.moveToNext());
        }

    }

    public Cursor getAllItem() {
        return mDatabase.query(
                khoanThuEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                khoanThuEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}
