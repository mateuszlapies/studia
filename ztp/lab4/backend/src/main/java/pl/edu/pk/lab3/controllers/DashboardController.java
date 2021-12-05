package pl.edu.pk.lab3.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.pk.lab3.beans.Book;
import pl.edu.pk.lab3.beans.Status;
import pl.edu.pk.lab3.responses.DashboardResponse;
import pl.edu.pk.lab3.responses.ExceptionResponse;
import pl.edu.pk.lab3.responses.Response;
import pl.edu.pk.lab3.services.DashboardService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard/{id}")
    public Response getBook(@PathVariable int id) {
        try {
            return new DashboardResponse(dashboardService.getBook(id), Status.GET);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ExceptionResponse(404, ex);
        }
    }

    @GetMapping("/dashboard")
    public DashboardResponse getBooks() {
        return new DashboardResponse(dashboardService.getBooks());
    }

    @PostMapping("/dashboard")
    public DashboardResponse addBook(@RequestBody Book book) {
        dashboardService.addBook(book);
        return new DashboardResponse(book, Status.POST);
    }

    @DeleteMapping("/dashboard/{id}")
    public Response removeBook(@PathVariable int id) {
        try {
            return new DashboardResponse(dashboardService.removeBook(id), Status.DELETE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ExceptionResponse(404, ex);
        }

    }

    @GetMapping("/verify")
    public Response verifyAdmin() {
        return new Response(200, "Is an admin");
    }
}
