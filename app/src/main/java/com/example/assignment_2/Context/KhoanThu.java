package com.example.assignment_2.Context;


import androidx.recyclerview.widget.RecyclerView;

public class KhoanThu {
    private String soTienThu;
    private String loaiThu;
    private String noiDungThu;
    private Date_QLCT date;

    public KhoanThu(String soTienThu, String loaiThu, Date_QLCT date) {
        this.soTienThu = soTienThu;
        this.loaiThu = loaiThu;
        this.date = date;
    }

    public KhoanThu(String soTienThu, String loaiThu, String noiDungThu) {
        this.soTienThu = soTienThu;
        this.loaiThu = loaiThu;
        this.noiDungThu = noiDungThu;
    }

    public KhoanThu(String soTienThu, String loaiThu) {
        this.soTienThu = soTienThu;
        this.loaiThu = loaiThu;
    }

    public KhoanThu() {
    }

    public String getNoiDungThu() {
        return noiDungThu;
    }

    public void setNoiDungThu(String noiDungThu) {
        this.noiDungThu = noiDungThu;
    }

    public Date_QLCT getDate() {
        return date;
    }

    public void setDate(Date_QLCT date) {
        this.date = date;
    }

    public String getSoTienThu() {
        return soTienThu;
    }

    public void setSoTienThu(String soTienThu) {
        this.soTienThu = soTienThu;
    }

    public String getLoaiThu() {
        return loaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        this.loaiThu = loaiThu;
    }
}
