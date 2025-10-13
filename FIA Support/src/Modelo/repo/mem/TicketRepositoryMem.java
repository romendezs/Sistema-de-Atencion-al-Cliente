/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.repo.mem;

import Modelo.repo.*;
import Modelo.dominio.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Méndez
 */
public class TicketRepositoryMem implements ITicketRepository {

    private final List<Ticket> tickets = new ArrayList<>();
    private final Map<Integer, Empleado> empleados = new LinkedHashMap<>();

    // Catálogos (nuevo): para construir UsuarioFinal según tu constructor
    private final Map<Integer, Facultad> facultades = new LinkedHashMap<>();
    private final Map<Integer, Carrera> carreras = new LinkedHashMap<>();

    private final AtomicInteger seq = new AtomicInteger(1);

    @Override
    public List<Ticket> findAll() {
        return Collections.unmodifiableList(tickets);
    }

    @Override
    public Ticket findById(int id) {
        for (Ticket t : tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Empleado> findAllEmpleados() {
        return new ArrayList<>(empleados.values());
    }

    @Override
    public Ticket create(UsuarioFinal solicitante, String titulo, String descripcion) {
        int id = seq.getAndIncrement();
        Ticket ticket = new Ticket(id, solicitante, titulo, descripcion);
        ticket.getHistorial().add(new Seguimiento(Estado.PENDIENTE, "Creado", LocalDate.now()));
        tickets.add(ticket);
        return ticket;
    }

    @Override
    public void update(int ticketId, String titulo, String descripcion) {
        Ticket t = findById(ticketId);
        if (t != null) {
            if (titulo != null) {
                t.setTitulo(titulo);
            }
            if (descripcion != null) {
                t.setDescripcion(descripcion);
            }
        }
    }

    @Override
    public void deleteById(int ticketId) {
        tickets.removeIf(t -> t.getId() == ticketId);
    }

    @Override
    public void cancel(int ticketId, String comentario) {
        Ticket t = findById(ticketId);
        if (t != null) {
            String nota = (comentario == null || comentario.trim().isEmpty())
                    ? "Ticket cancelado"
                    : comentario.trim();
            t.setEstadoActual(Estado.CERRADO);
            t.getHistorial().add(new Seguimiento(Estado.CERRADO, nota, LocalDate.now()));
        }
    }

    @Override
    public void assignToEmpleado(int ticketId, int empleadoId) {
        Ticket t = findById(ticketId);
        Empleado emp = empleados.get(empleadoId);
        if (t != null && emp != null) {
            t.setAsignado(emp);
            t.setEstadoActual(Estado.ASIGNADO);
            t.getHistorial().add(new Seguimiento(Estado.ASIGNADO, "Asignado a " + emp, LocalDate.now()));
        }
    }

    @Override
    public void addSeguimiento(int ticketId, Estado estado, String comentario) {
        Ticket t = findById(ticketId);
        if (t != null) {
            t.setEstadoActual(estado);
            t.getHistorial().add(new Seguimiento(estado, comentario, LocalDate.now()));
        }
    }

    @Override
    public void seed() {
        tickets.clear();
        empleados.clear();

        // ---------- Facultades ----------
        Facultad fia = new Facultad(1, "Ingeniería y Arquitectura");
        Facultad fch = new Facultad(2, "Ciencias y Humanidades");
        Facultad fec = new Facultad(3, "Ciencias Económicas");

        // ---------- Carreras (usa idFacultad, NO el objeto) ----------
        Carrera isi = new Carrera(1, "Ingeniería de Sistemas Informáticos", fia.getId());
        Carrera ind = new Carrera(2, "Ingeniería Industrial", fia.getId());
        Carrera arq = new Carrera(3, "Arquitectura", fia.getId());
        Carrera psi = new Carrera(4, "Psicología", fch.getId());
        Carrera ts = new Carrera(5, "Trabajo Social", fch.getId());
        Carrera cpa = new Carrera(6, "Contaduría Pública", fec.getId());
        Carrera adm = new Carrera(7, "Administración de Empresas", fec.getId());

        // ---------- Empleados ----------
        empleados.put(1, new Empleado(1, "Santiago", "Hernández"));
        empleados.put(2, new Empleado(2, "Juan", "Pérez"));
        empleados.put(3, new Empleado(3, "Carlos", "Rodríguez"));

        // ---------- Usuarios finales (tu constructor nuevo) ----------
        UsuarioFinal u1 = new UsuarioFinal("MS24030", "José", "Roberto", true, fia, isi);
        UsuarioFinal u2 = new UsuarioFinal("GC22090", "Alex", "Ezequiel", true, fia, ind);
        UsuarioFinal u3 = new UsuarioFinal("OL24002", "Christopher", "Enrique", false, null, null);

        // ---------- Tickets ----------
        Ticket t1 = new Ticket(1, u1, "Televisor B41",
                "El televisor del laboratorio no enciende.");
        t1.getHistorial().add(new Seguimiento(Estado.PENDIENTE, "Creado", java.time.LocalDate.now().minusDays(1)));

        Ticket t2 = new Ticket(2, u2, "Expediente en Línea",
                "Tengo inconvenientes con mi usuario, por favor asignarme a un programador.");
        t2.setAsignado(empleados.get(1));
        t2.setEstadoActual(Estado.EN_PROCESO);
        t2.getHistorial().add(new Seguimiento(Estado.PENDIENTE, "Creado", java.time.LocalDate.now().minusDays(4)));
        t2.getHistorial().add(new Seguimiento(Estado.ASIGNADO, "Asignado a Santiago Hernández", java.time.LocalDate.now().minusDays(3)));
        t2.getHistorial().add(new Seguimiento(Estado.EN_PROCESO, "Comunicación por correo", java.time.LocalDate.now().minusDays(2)));

        Ticket t3 = new Ticket(3, u3, "Cuentas de Google",
                "No puedo iniciar sesión en la cuenta institucional.");
        t3.getHistorial().add(new Seguimiento(Estado.PENDIENTE, "Creado", java.time.LocalDate.now().minusDays(2)));

        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);

        int nextId = tickets.stream().mapToInt(Ticket::getId).max().orElse(0) + 1;
        seq.set(nextId);
    }
}
    
