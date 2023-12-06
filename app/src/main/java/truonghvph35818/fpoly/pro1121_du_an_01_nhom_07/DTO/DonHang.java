package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

import java.util.List;

public class DonHang {
    private String maDonHang;
    private String maKhachHang;
    private List<Don> listSP;
    private Long time;
    private int trangThai;
    private Long giaDon;
    private String ngayMua ;

    public DonHang(String maDon, String uid, List<Don> listDon, List<SanPhamDTO> list_sanPham, int trangThai, Object o, String ngayMua) {
    }

    public DonHang(String maDonHang, String maKhachHang, List<Don> listSP, Long time, int trangThai, Long giaDon, String ngayMua) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.listSP = listSP;
        this.time = time;
        this.trangThai = trangThai;
        this.giaDon = giaDon;
        this.ngayMua = ngayMua;
    }

    public DonHang() {
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public List<Don> getListSP() {
        return listSP;
    }

    public void setListSP(List<Don> listSP) {
        this.listSP = listSP;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Long getGiaDon() {
        return giaDon;
    }

    public void setGiaDon(Long giaDon) {
        this.giaDon = giaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }
}
