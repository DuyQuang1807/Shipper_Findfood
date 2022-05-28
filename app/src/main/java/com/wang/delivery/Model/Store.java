package com.wang.delivery.Model;

public class Store {
    private String cv;
    private String diaChi;
    private String email;
    private String moTa;
    private String image;
    private String pass;
    private String phone;
    private String tenCuaHang;
    private String trangThai;
    private String tokenstore;

    public Store() {
    }

    public Store(String cv, String diaChi, String email, String moTa, String image, String pass, String phone, String tenCuaHang, String trangThai, String tokenstore) {
        this.cv = cv;
        this.diaChi = diaChi;
        this.email = email;
        this.moTa = moTa;
        this.image = image;
        this.pass = pass;
        this.phone = phone;
        this.tenCuaHang = tenCuaHang;
        this.trangThai = trangThai;
        this.tokenstore = tokenstore;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
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
