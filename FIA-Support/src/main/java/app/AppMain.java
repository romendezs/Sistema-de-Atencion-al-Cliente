/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import Vista.Admin.GestionTicketsUI;
import controlador.TicketController;
import controlador.AssignmentController;
import controlador.WorkflowController;
import controlador.ReportingController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author Méndez
 */
import javax.swing.SwingUtilities;
import modelo.servicios.AssignmentService;
import modelo.servicios.ReportingService;
import modelo.servicios.TicketService;
import modelo.servicios.WorkflowService;

public class AppMain {

    public static void main(String[] args) {
        // 1️⃣ Initialize JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fiasupportPU");

        // 2️⃣ Create your controllers (inject dependencies as needed)
        TicketController ticketController = new TicketController((TicketService) emf);
        AssignmentController assignmentController = new AssignmentController((AssignmentService) emf);
        WorkflowController workflowController = new WorkflowController((WorkflowService) emf);
        ReportingController reportingController = new ReportingController((ReportingService) emf);

        // 3️⃣ Launch the UI
        SwingUtilities.invokeLater(() -> {
            GestionTicketsUI ui = new GestionTicketsUI(
                    ticketController,
                    assignmentController,
                    workflowController,
                    reportingController
            );
            ui.setVisible(true);
        });
    }
}
