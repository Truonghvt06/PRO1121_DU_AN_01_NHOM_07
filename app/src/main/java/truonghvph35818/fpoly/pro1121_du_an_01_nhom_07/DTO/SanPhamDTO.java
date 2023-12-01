package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

import java.util.HashMap;
import java.util.List;

public class SanPhamDTO {
    private String maSp;
    private String anh;
    private String tenSP;
    private Long gia;
    private Long soLuong;
    private String maHang;
    private Long time;
    private String moTa;
    private List<String> kichCo;
    private  String maTheLoai;

    public SanPhamDTO() {
    }

    public SanPhamDTO(String maSp, String anh, String tenSP, Long gia, Long soLuong, String maHang, Long time, String moTa, List<String> kichCo, String maTheLoai) {
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maHang = maHang;
        this.time = time;
        this.moTa = moTa;
        this.kichCo = kichCo;
        this.maTheLoai = maTheLoai;
    }

    //Xử lý dữ liệu với Firebase
    public HashMap<String, Object> hashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("maSp", maSp);
        map.put("anh", anh);
        map.put("tenSP", tenSP);
        map.put("gia", gia);
        map.put("soLuong", soLuong);
        map.put("maHang", maHang);
        map.put("time", time);
        map.put("moTa", moTa);
        map.put("kichCo", kichCo);
        map.put("maTheLoai", maTheLoai);

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

    public List<String> getKichCo() {
        return kichCo;
    }

    public void setKichCo(List<String> kichCo) {
        this.kichCo = kichCo;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }
}
