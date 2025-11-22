package modelo.dto;

public class ConteoPorEstadoDTO {
    private String estado;
    private Long total;

    public ConteoPorEstadoDTO(String estado, Long total) {
        this.estado = estado;
        this.total = total;
    }

    public String getEstado() { return estado; }
    public Long getTotal() { return total; }
}
