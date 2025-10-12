 package com.mycompany.soporte;
 
 
import java.util.ArrayList;
 import java.util.List;




public class ManejoTicket
 {
  public static final List<Ticket> listaTicket = new ArrayList<>();
 
  public static void añadirTicket(Ticket t) {
    listaTicket.add(t);
  }
 
  public static List<Ticket> getTickets() {
    return listaTicket;
  }
 }
