package com.wang.delivery.Model;

public class Food {
    private String idfood;
    private String tenSanPham;
    private double giaTien;
    private int soLuong;
    //    private long giaTien;
//    private long soLuong;
    private String anh;
    private String diaChi;
    private String mota;
    private String status;
    private String matheloai;
    private String idCuaHang;
    private String idDanhMuc;
    private String idSanPham;
    private int khuyenMai;
    private String trangThai;
    private String tokenstore;



    public Food() {
    }

    public Food(String idfood, String tenSanPham, double giaTien, int soLuong, String anh, String diaChi, String mota, String status, String matheloai, String idCuaHang, String idDanhMuc, String idSanPham, int khuyenMai, String trangThai, String tokenstore) {
        this.idfood = idfood;
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.anh = anh;
        this.diaChi = diaChi;
        this.mota = mota;
        this.status = status;
        this.matheloai = matheloai;
        this.idCuaHang = idCuaHang;
        this.idDanhMuc = idDanhMuc;
        this.idSanPham = idSanPham;
        this.khuyenMai = khuyenMai;
        this.trangThai = trangThai;
        this.tokenstore = tokenstore;
    }

//    public Food(String idfood, String tenSanPham, long giaTien, long soLuong, String anh, String diaChi, String mota, String status, String matheloai, String idCuaHang, String idDanhMuc, String idSanPham, String khuyenMai, String trangThai, String tokenstore) {
//        this.idfood = idfood;
//        this.tenSanPham = tenSanPham;
//        this.giaTien = giaTien;
//        this.soLuong = soLuong;
//        this.anh = anh;
//        this.diaChi = diaChi;
//        this.mota = mota;
//        this.status = status;
//        this.matheloai = matheloai;
//        this.idCuaHang = idCuaHang;
//        this.idDanhMuc = idDanhMuc;
//        this.idSanPham = idSanPham;
//        this.khuyenMai = khuyenMai;
//        this.trangThai = trangThai;
//        this.tokenstore = tokenstore;
//    }

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String idfood) {
        this.idfood = idfood;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getIdCuaHang() {
        return idCuaHang;
    }

    public void setIdCuaHang(String idCuaHang) {
        this.idCuaHang = idCuaHang;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTokenstore() {
        return tokenstore;
    }

    public void setTokenstore(String tokenstore) {
        this.tokenstore = tokenstore;
    }
}
