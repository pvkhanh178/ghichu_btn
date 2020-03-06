package com.example.sqlite_first;

public class CongViec {
    private int ID;
    private String tieuDe;
    private String noiDung;
    public CongViec() {
    }

    public CongViec(int ID, String tieuDe, String noiDung) {
        this.ID = ID;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
