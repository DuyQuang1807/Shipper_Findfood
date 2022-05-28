package com.wang.delivery.Model;

public class User {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String image;
    private String diachi;
    private String ngaySinh;
    private String gioiTinh;
    String token;
    private String trangThai;

    public User(){

    }

    public  User(String name, String phone, String image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public User(String email, String password, String name, String phone, String image, String diachi, String ngaySinh, String gioiTinh, String token, String trangThai) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.diachi = diachi;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.token = token;
        this.trangThai = trangThai;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
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
}
