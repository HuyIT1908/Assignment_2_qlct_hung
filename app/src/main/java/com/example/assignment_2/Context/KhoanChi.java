package com.example.assignment_2.Context;


public class KhoanChi {
    private String soTienChi;
    private String loaiChi;
    private String noiDungChi;
    private Date_QLCT date;

    public KhoanChi(String soTienChi, String loaiChi, Date_QLCT date) {
        this.soTienChi = soTienChi;
        this.loaiChi = loaiChi;
        this.date = date;
    }

    public KhoanChi(String soTienChi, String loaiChi, String noiDungChi) {
        this.soTienChi = soTienChi;
        this.loaiChi = loaiChi;
        this.noiDungChi = noiDungChi;
    }

    public KhoanChi(String soTienChi, String loaiChi) {
        this.soTienChi = soTienChi;
        this.loaiChi = loaiChi;
    }

    public KhoanChi() {
    }

    public String getNoiDungChi() {
        return noiDungChi;
    }

    public void setNoiDungChi(String noiDungChi) {
        this.noiDungChi = noiDungChi;
    }

    public String getSoTienChi() {
        return soTienChi;
    }

    public void setSoTienChi(String soTienChi) {
        this.soTienChi = soTienChi;
    }

    public String getLoaiChi() {
        return loaiChi;
    }

    public void setLoaiChi(String loaiChi) {
        this.loaiChi = loaiChi;
    }

    public Date_QLCT getDate() {
        return date;
    }

    public void setDate(Date_QLCT date) {
        this.date = date;
    }
}
