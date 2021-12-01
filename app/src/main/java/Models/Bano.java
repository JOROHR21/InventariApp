package Models;

public class Bano {
    private String idbano;
    private String productob;
    private String cantidadb;
    private String fecharegistro;

    public String getIdbano() {
        return idbano;
    }

    public void setIdbano(String idbano) {
        this.idbano = idbano;
    }

    public String getProductob() {
        return productob;
    }

    public void setProductob(String productob) {
        this.productob = productob;
    }

    public String getCantidadb() {
        return cantidadb;
    }

    public void setCantidadb(String cantidadb) {
        this.cantidadb = cantidadb;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    @Override
    public String toString() {
        return productob;
    }
}
