package id.ignitech.iak.Model;

/**
 * Created by Asus on 22/11/2017.
 */

public class ModelFasilitator {
    public String alamat;
    public String nama;
    public String quota;
    public String tanggal1;
    public String tanggal2;
    public String level;

    public ModelFasilitator() {}

    public ModelFasilitator(String nama, String tanggal1, String tanggal2, String alamat, String quota, String level) {
        this.alamat = alamat;
        this.nama = nama;
        this.quota = quota;
        this.tanggal1 = tanggal1;
        this.tanggal2 = tanggal2;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getTanggal1() {
        return tanggal1;
    }

    public void setTanggal1(String tanggal1) {
        this.tanggal1 = tanggal1;
    }

    public String getTanggal2() {
        return tanggal2;
    }

    public void setTanggal2(String tanggal2) {
        this.tanggal2 = tanggal2;
    }
}
