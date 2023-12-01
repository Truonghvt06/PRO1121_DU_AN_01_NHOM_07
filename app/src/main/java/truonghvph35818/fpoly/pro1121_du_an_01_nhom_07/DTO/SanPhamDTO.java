package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

import java.util.HashMap;
import java.util.List;

public class SanPhamDTO {
    private String maSp;
    boolean giaydep;
    private String anh;
    private String tenSP;
    private Long gia;

    private String maHang;
    private Long time;
    private String moTa;
    private long kichCo;
    private  String thuonghieu;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String maSp, boolean giaydep, String anh, String tenSP, Long gia, String maHang, Long time, String moTa, long kichCo, String thuonghieu) {
        this.maSp = maSp;
        this.giaydep = giaydep;
        this.anh = anh;
        this.tenSP = tenSP;
        this.gia = gia;
        this.maHang = maHang;
        this.time = time;
        this.moTa = moTa;
        this.kichCo = kichCo;
        this.thuonghieu = thuonghieu;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public boolean isGiaydep() {
        return giaydep;
    }

    public void setGiaydep(boolean giaydep) {
        this.giaydep = giaydep;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Long getGia() {
        return gia;
    }

    public void setGia(Long gia) {
        this.gia = gia;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public long getKichCo() {
        return kichCo;
    }

    public void setKichCo(long kichCo) {
        this.kichCo = kichCo;
    }

    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }
}
