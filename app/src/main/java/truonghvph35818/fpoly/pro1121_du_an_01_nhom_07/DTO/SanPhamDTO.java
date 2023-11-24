package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

import java.util.HashMap;
import java.util.List;

public class SanPhamDTO {
    private String maSp;
    private String anh;
    private String tenSP;
    private Long gia;
    private Long soLuong;
    private String maTheLoai;
    private String maHienThi;
    private List<String> kichCo;
    private String moTa;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String maSp, String anh, String tenSP, Long gia, Long soLuong, String maTheLoai, String maHienThi, List<String> kichCo, String moTa) {
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maTheLoai = maTheLoai;
        this.maHienThi = maHienThi;
        this.kichCo = kichCo;
        this.moTa = moTa;
    }

    //Xử lý dữ liệu với Firebase
    public HashMap<String, Object> hashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("maSp", maSp);
        map.put("anh", anh);
        map.put("tenSP", tenSP);
        map.put("gia", gia);
        map.put("soLuong", soLuong);
        map.put("maTheLoai", maTheLoai);
        map.put("maHienThi", maHienThi);
        map.put("kichCo", kichCo);
        map.put("moTa", moTa);

        return  map;
    }





    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
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

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getMaHienThi() {
        return maHienThi;
    }

    public void setMaHienThi(String maHienThi) {
        this.maHienThi = maHienThi;
    }

    public List<String> getKichCo() {
        return kichCo;
    }

    public void setKichCo(List<String> kichCo) {
        this.kichCo = kichCo;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
