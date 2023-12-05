package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

public class GioHang {
    private String maGio;
    private String maKhachHang;
    private String maSanPham;
    private String kichCo;
    private Long soLuong;

    public GioHang() {
    }

    public GioHang(String maGio, String maKhachHang, String maSanPham, String kichCo, Long soLuong) {
        this.maGio = maGio;
        this.maKhachHang = maKhachHang;
        this.maSanPham = maSanPham;
        this.kichCo = kichCo;
        this.soLuong = soLuong;
    }

    public String getMaGio() {
        return maGio;
    }

    public void setMaGio(String maGio) {
        this.maGio = maGio;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
}
