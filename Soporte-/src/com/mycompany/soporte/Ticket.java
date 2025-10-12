package com.mycompany.soporte;

 import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


 
 public class Ticket
 {
  private String asunto;
 private String descripcion;
  private String estado;
 private String fechaCreacion;
  
   public Ticket(String asunto, String descripcion) {
     this.asunto = asunto;
    this.descripcion = descripcion;
   this.estado = "Pendiente";
    this.fechaCreacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
   }

   public String getAsunto() { return this.asunto; }
   public String getDescripcion() { return this.descripcion; }
  public String getEstado() { return this.estado; } 
  public String getFechaCreacion() {return this.fechaCreacion;}
  public void setEstado() {this.estado = this.estado;}
}


