package Models;

public class Cocina {
    private String idcocina;
    private String producto;
    private String cantidad;
    private String fecharegistro;

    public String getIdcocina() {
        return idcocina;
    }

    public void setIdcocina(String idcocina) {
        this.idcocina = idcocina;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    @Override
    public String toString() {
        return producto;
    }
}
