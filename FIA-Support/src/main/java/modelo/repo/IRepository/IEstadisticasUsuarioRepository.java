/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo.IRepository;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Méndez
 */

public interface IEstadisticasUsuarioRepository {
  // Conteo por estado para un usuario final (solicitante)
  List<Object[]> conteoPorEstadoUsuario(String usuarioId);

  // Conteo por día (abiertos/cerrados) para el usuario en rango
  List<Object[]> conteoPorDiaUsuario(String usuarioId, LocalDate desde, LocalDate hasta);

  // Promedio de resolución (minutos) para ese usuario en rango
  Double promedioResolucionMinUsuario(String usuarioId, LocalDate desde, LocalDate hasta);
}

