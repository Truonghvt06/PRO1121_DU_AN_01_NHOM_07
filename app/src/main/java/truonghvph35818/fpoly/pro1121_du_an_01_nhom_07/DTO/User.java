package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

import java.util.List;

public class User {
    private  String anh;
    private String maUser;
    private String Email;
    private String hoTen;
    private String SDT;
    private String ngaySinh;
    private String gioiTinh;
    private int chucVu;
    private List<String> diachi;
    private String chonDiaCHi;
    private int trangThai;


    public User() {
    }

    public User(String anh, String maUser, String email, String hoTen, String SDT, String ngaySinh, String gioiTinh, int chucVu, List<String> diachi, String chonDiaCHi, int trangThai) {
        this.anh = anh;
        this.maUser = maUser;
        Email = email;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.diachi = diachi;
        this.chonDiaCHi = chonDiaCHi;
        this.trangThai = trangThai;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
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

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }

    public List<String> getDiachi() {
        return diachi;
    }

    public void setDiachi(List<String> diachi) {
        this.diachi = diachi;
    }

    public String getChonDiaCHi() {
        return chonDiaCHi;
    }

    public void setChonDiaCHi(String chonDiaCHi) {
        this.chonDiaCHi = chonDiaCHi;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}

