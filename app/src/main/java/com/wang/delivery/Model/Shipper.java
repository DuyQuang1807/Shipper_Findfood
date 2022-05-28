package com.wang.delivery.Model;

public class Shipper {
    private String email;
    private String password;
    private String hoTen;
    private String phone;
    private String anh;
    private String diaChi;
    private String ngaysinh;
    private String gioitinh;
    String token;
    private String trangThai;
    private String cv;

    public Shipper() {
    }

    public Shipper(String email, String password, String hoTen, String phone, String anh, String diaChi, String ngaysinh, String gioitinh, String token, String trangThai, String cv) {
        this.email = email;
        this.password = password;
        this.hoTen = hoTen;
        this.phone = phone;
        this.anh = anh;
        this.diaChi = diaChi;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.token = token;
        this.trangThai = trangThai;
        this.cv = cv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
