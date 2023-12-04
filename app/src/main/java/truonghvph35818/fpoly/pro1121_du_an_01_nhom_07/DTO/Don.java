package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO;

public class Don {
    private String maSP;
    private Long soLuong;

    public Don() {
    }

    public Don(String maSP, Long soLuong) {
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
}
