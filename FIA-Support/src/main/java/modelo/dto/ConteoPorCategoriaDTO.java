package modelo.dto;

public class ConteoPorCategoriaDTO {
    private String categoria;
    private Long total;

    public ConteoPorCategoriaDTO(String categoria, Long total) {
        this.categoria = categoria;
        this.total = total;
    }

    public String getCategoria() { return categoria; }
    public Long getTotal() { return total; }
}
