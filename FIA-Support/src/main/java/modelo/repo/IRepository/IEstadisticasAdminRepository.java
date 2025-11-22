package modelo.repo.IRepository;

import java.time.LocalDate;
import java.util.List;
import modelo.dominio.TicketMetricasDiarias;
import modelo.dto.ConteoPorCategoriaDTO;
import modelo.dto.ConteoPorEstadoDTO;
import modelo.dto.ConteoPorTecnicoDTO;

public interface IEstadisticasAdminRepository {

    List<ConteoPorEstadoDTO> conteoPorEstadoGlobal();

    List<ConteoPorTecnicoDTO> conteoPorTecnico();

    List<ConteoPorCategoriaDTO> conteoPorCategoria();

    List<TicketMetricasDiarias> metricasDiarias(LocalDate desde, LocalDate hasta);

    Double promedioResolucionMinGlobal(LocalDate desde, LocalDate hasta);
}
