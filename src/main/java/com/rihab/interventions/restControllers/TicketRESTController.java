package com.rihab.interventions.restControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihab.interventions.dto.TicketDTO;
import com.rihab.interventions.entities.Ticket;
import com.rihab.interventions.service.CalendarService;
import com.rihab.interventions.service.TicketService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TicketRESTController {

	@Autowired
	TicketService ticketService;
	@Autowired
	CalendarService calendarService;
/*

@RequestMapping(path="allTickets",method = RequestMethod.GET)
public List<Ticket> getAllTickets() {
	return ticketService.getAllTickets();
}
*/
	@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping(path = "allTickets", method = RequestMethod.GET)
public List<Ticket> getAllTickets() {
    return ticketService.getAllTickets1();
}


	
@RequestMapping(value="/getByCode/{interCode}",method = RequestMethod.GET)
public TicketDTO getTicketionById(@PathVariable("interCode") String interCode) {
	return ticketService.getTicket(interCode);
 }


//autorisation au admin seulement cette methode
@PreAuthorize("hasAuthority('CLIENT')")
@RequestMapping(path="/addTicket",method = RequestMethod.POST)

public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO) {
	return ticketService.saveTicket(ticketDTO);
}

@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping(path="/updateTicket",method = RequestMethod.PUT)
public TicketDTO updateTicket(@RequestBody TicketDTO ticketDTO) {
		return ticketService.updateTicket(ticketDTO);
}

@PreAuthorize("hasAuthority('TECHNICIEN')")
@PutMapping("/updateTicketStatus/{interCode}")
public TicketDTO updateTicketStatus(@PathVariable String interCode, @RequestParam String interStatut) {
    TicketDTO updatedTicket = ticketService.updateTicketStatus(interCode, interStatut);
    return(updatedTicket);
}

/*
@PreAuthorize("hasAuthority('TECHNICIEN')")
@RequestMapping(path="/updateTickett/{interCode}", method = RequestMethod.PUT)
public TicketDTO updateTicket(@PathVariable("interCode") String interCode, @RequestParam String interStatut) {
    return ticketService.updateTicketSelective(interCode, interStatut);
}
*/


@PreAuthorize("hasAuthority('CLIENT')")
@RequestMapping(value="/deleteTicket/{interCode}",method = RequestMethod.DELETE)

public void deleteTicket(@PathVariable("interCode") String interCode)
{
	ticketService.deleteTicketByCode(interCode);
}


@RequestMapping(value="/intersEqpt/{eqptCode}",method = RequestMethod.GET)
public List<Ticket> getTicketsByEquipementCodeEquipement(@PathVariable("eqptCode") String eqptCode) {
		return ticketService.findByEquipementEqptCode(eqptCode);
}


@RequestMapping(value="/searchByDesignation/{eqptDesignation}",method = RequestMethod.GET)
 public List<Ticket>getTicketByDesignation(@PathVariable("interDesignation") String interDesignation) {
    return ticketService.findByInterDesignation(interDesignation);
}


@RequestMapping(value="/searchByDesignationContains/{interDesignation}",method = RequestMethod.GET)
public List<Ticket> getTicketByInterDesignationContains(@PathVariable("interDesignation") String interDesignation) {
    return ticketService.findByInterDesignationContains(interDesignation);
}


@RequestMapping(value="/interNature/{code}",method = RequestMethod.GET)
public List<Ticket> getTicketsByInterventionNatureCode(@PathVariable("code") long code) {
		return ticketService.findByInterventionNatureCode(code);
}
@RequestMapping(value="/TicketTech", method = RequestMethod.GET)
public List<Ticket> getTicketsByLoggedInTechnicien() {
    return ticketService.findByLoggedInTechnicien();
}

@RequestMapping(value="/TicketDemandeur", method = RequestMethod.GET)
public List<Ticket> getTicketsByLoggedInDemandeur() {
    return ticketService.findByLoggedInDemandeur();
}



@GetMapping("/total")
public ResponseEntity<Long> getTotalTickets() {
    Long totalTickets = ticketService.countTotalTickets();
    return ResponseEntity.ok().body(totalTickets);
}

@GetMapping("/pending")
public ResponseEntity<Long> getPendingTickets() {
    Long pendingTickets = ticketService.countPendingTickets();
    return ResponseEntity.ok().body(pendingTickets);
}

@GetMapping("/todo")
public ResponseEntity<Long> getTodoTickets() {
    Long todoTickets = ticketService.countTodoTickets();
    return ResponseEntity.ok().body(todoTickets);
}


@GetMapping("/done")
public ResponseEntity<Long> countDoneTickets() {
    Long doneTickets = ticketService.countDoneTickets();
    return ResponseEntity.ok().body(doneTickets);
}

@GetMapping("/cancelled")
public ResponseEntity<Long> countCancelledTickets() {
    Long cancelledTickets = ticketService.countCancelledTickets();
    return ResponseEntity.ok().body(cancelledTickets);
}

@GetMapping("/blocked")
public ResponseEntity<Long> countBlockedTickets() {
    Long blockedTickets = ticketService.countBlockedTickets();
    return ResponseEntity.ok().body(blockedTickets);
}

//


@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/totalTicketsDemandeur")
public ResponseEntity<Long> getTotalTicketsDemandeur() {
    Long totalTickets = ticketService.totalTicketsDemandeurs();
    return ResponseEntity.ok().body(totalTickets);
}
@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/pendingTicketsDemandeur")
public ResponseEntity<Long> getPendingTicketsDemandeur() {
    Long pendingTickets = ticketService.attenteTicketsDemandeur();
    return ResponseEntity.ok().body(pendingTickets);
}
@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/todoTicketsDemandeur")
public ResponseEntity<Long> getTodoTicketsDemandeur() {
    Long todoTickets = ticketService.TodoTicketsDemandeur();
    return ResponseEntity.ok().body(todoTickets);
}

@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/doneTicketsDemandeur")
public ResponseEntity<Long> countDoneTicketsDemandeur() {
    Long doneTickets = ticketService.doneTicketsDemandeur();
    return ResponseEntity.ok().body(doneTickets);
}
@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/cancelledTicketsDemandeur")
public ResponseEntity<Long> countCancelledTicketsDemandeur() {
    Long cancelledTickets = ticketService.cancelledTicketsDemandeur();
    return ResponseEntity.ok().body(cancelledTickets);
}
@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/blockedTicketsDemandeur")
public ResponseEntity<Long> countBlockedTicketsDemandeur() {
    Long blockedTickets = ticketService.blockedTicketsDemandeur();
    return ResponseEntity.ok().body(blockedTickets);
}


@PreAuthorize("hasAuthority('MANAGER')")
@GetMapping("/ticketByClient")
public ResponseEntity<Map<String, Long>> countTicketsByClient() {
    Map<String, Long> ticketsByClient = ticketService.countTicketsByClient();
    if (ticketsByClient.isEmpty()) {
        // Gérer le cas où il n'y a pas de tickets associés aux clients
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ticketsByClient);
}







@PreAuthorize("hasAuthority('CLIENT')")
@GetMapping("/closedDemandeur")
public List<TicketDTO> getClosedTickets() {
    List<TicketDTO> closedTickets = ticketService.getClosedTickets();
    return (closedTickets);
}





@PreAuthorize("hasAuthority('TECHNICIEN')")
@GetMapping("/archiveTech")
public List<TicketDTO> getArchivedTickets() {
    List<TicketDTO> closedTickets = ticketService.getArchivedTickets();
    return (closedTickets);
}

@PreAuthorize("hasAuthority('MANAGER')")
@GetMapping("/allArchived")
public List<Ticket> getAllArchivedTickets() {
    return ticketService.getALLArchivedTickets();
}
/*
@PreAuthorize("hasAuthority('MANAGER')")
@GetMapping("/add-ticket")
public ResponseEntity<String> addTicketToCalendar() {
    try {
        // Appelez la méthode pour récupérer les tickets à afficher dans le calendrier
        // Cette méthode devrait retourner la liste des tickets à afficher dans le calendrier
        List<Ticket> tickets = ticketService.getTicketsForCalendar();

        // Ici, vous pouvez effectuer d'autres opérations si nécessaire
        // Par exemple, formater les données des tickets avant de les renvoyer

        return ResponseEntity.ok("Tickets récupérés pour affichage dans le calendrier avec succès");
    } catch (Exception e) {
        // Gérez l'exception et renvoyez une réponse appropriée
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des tickets pour affichage dans le calendrier");
    }
}
*/

@GetMapping("/realizable")
public ResponseEntity<List<TicketDTO>> getRealizableTickets() {
    List<TicketDTO> realizableTickets = ticketService.extractRealizableTicketInfo();
    return new ResponseEntity<>(realizableTickets, HttpStatus.OK);
}















}
