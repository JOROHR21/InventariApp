package Models;

public class Habi {

    private String idhabi;
    private String productoh;
    private String cantidadh;
    private String fecharegistro;

    public String getIdhabi() {
        return idhabi;
    }

    public void setIdhabi(String idhabi) {
        this.idhabi = idhabi;
    }

    public String getProductoh() {
        return productoh;
    }

    public void setProductoh(String productoh) {
        this.productoh = productoh;
    }

    public String getCantidadh() {
        return cantidadh;
    }

    public void setCantidadh(String cantidadh) {
        this.cantidadh = cantidadh;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    @Override
    public String toString() {
        return productoh;
    }
}
