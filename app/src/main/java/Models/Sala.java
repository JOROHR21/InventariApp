package Models;

public class Sala {
    private String idsala;
    private String productos;
    private String cantidads;
    private String fecharegistro;

    public String getIdsala() {
        return idsala;
    }

    public void setIdsala(String idsala) {
        this.idsala = idsala;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getCantidads() {
        return cantidads;
    }

    public void setCantidads(String cantidads) {
        this.cantidads = cantidads;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    @Override
    public String toString() {
        return productos;
    }


}
