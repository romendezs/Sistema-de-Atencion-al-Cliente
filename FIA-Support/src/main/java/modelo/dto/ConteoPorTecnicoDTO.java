package modelo.dto;

public class ConteoPorTecnicoDTO {
    private String tecnico;
    private Long total;

    public ConteoPorTecnicoDTO(String tecnico, Long total) {
        this.tecnico = tecnico;
        this.total = total;
    }

    public String getTecnico() { return tecnico; }
    public Long getTotal() { return total; }
}
