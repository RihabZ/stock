package com.rihab.interventions.service;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rihab.interventions.dto.PieceRequestDTO;
import com.rihab.interventions.dto.TicketDTO;
import com.rihab.interventions.dto.UserDTO;
import com.rihab.interventions.entities.Demandeur;
import com.rihab.interventions.entities.Intervention;
import com.rihab.interventions.entities.PieceRechangeRequest;
import com.rihab.interventions.entities.Technicien;
import com.rihab.interventions.entities.Ticket;
import com.rihab.interventions.repos.PieceRequestRepository;
import com.rihab.interventions.repos.TicketRepository;

@Service
public class PieceRequestServiceImpl implements PieceRequestService {
	
	@Autowired
	PieceRequestRepository pieceRequestRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	TechnicienService technicienService;
	
	
	
	public PieceRechangeRequest toPieceRequest(PieceRequestDTO request) {
		// Récupérer le ticket de la base de données avec la date de création
	    Ticket ticket = ticketRepository.findById(request.getTicket().getInterCode())
	                                     .orElseThrow(() -> new EntityNotFoundException("Ticket non trouvé"));
	    // Utilisation de interCode dans le reste du code
	    return PieceRechangeRequest.builder()

	            .ticket(ticket)
	            .codeDemande(request.getCodeDemande())
	            .statutDemande(request.getStatutDemande())
	    		.quantiteDemande(request.getQuantiteDemande())
	    		.etat(request.getEtat())
	    		.autre(request.getAutre())
	    		.art(request.getArt())
	    		 .autreQute(request.getAutreQute())
	    	  
	           
	            // Map other fields if needed
	            .build();
	}

	public PieceRequestDTO toPieceRequestDTO(PieceRechangeRequest request) {
		PieceRequestDTO.PieceRequestDTOBuilder builder = PieceRequestDTO.builder()
	          
				.codeDemande(request.getCodeDemande())
	            .statutDemande(request.getStatutDemande())
	    		.quantiteDemande(request.getQuantiteDemande())
	    		.etat(request.getEtat())
	    		.autre(request.getAutre())
	    		.art(request.getArt())
	    		 .autreQute(request.getAutreQute())
	            .ticket(request.getTicket());
	           
	            
	    return builder.build();
	}
	
	
	@Override
	public PieceRequestDTO savePieceRequest(PieceRequestDTO pieceRequest) {
	    // Get the authenticated user
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	        // Handle unauthenticated users or return an error
	        return null;
	    }

	    // Vérifie si l'utilisateur est un technicien
	    if (!(authentication.getPrincipal() instanceof UserDetails)) {
	        // L'utilisateur n'est pas authentifié comme UserDetails
	        return null;
	    }

	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    String username = userDetails.getUsername();

	    // Récupère le technicien par son nom d'utilisateur
	    Technicien technicien = technicienService.getTechnicienByUsername(username);

	    if (technicien == null) {
	        // Handle the case where the technicien doesn't exist
	        return null;
	    }

	    // Convertir PieceRequestDTO en PieceRechangeRequest
	    PieceRechangeRequest pieceRechangeRequest = toPieceRequest(pieceRequest);
	    pieceRechangeRequest.setTicket(pieceRechangeRequest.getTicket());
	    
	    // Enregistrer la PieceRechangeRequest dans la base de données (ou faire toute autre opération nécessaire)
	    PieceRechangeRequest savedPieceRequest = pieceRequestRepository.save(pieceRechangeRequest);
	    
	    // Convertir la PieceRechangeRequest sauvegardée en PieceRequestDTO
	    return toPieceRequestDTO(savedPieceRequest);
	}

	
	
	
	@Override
	public void deletePieceRequest(PieceRechangeRequest inter) {
		pieceRequestRepository.delete(inter);
	}


	@Override
	public void deletePieceRequestByCodeDemande(long code) {
		pieceRequestRepository.deleteById(code);
	}


	@Override
	public PieceRequestDTO getPieceRequest(long code) {
		return toPieceRequestDTO(pieceRequestRepository.findById(code).get());
	}


	@Override
	public List<PieceRequestDTO> getAllPiecesRequests1() {
	return pieceRequestRepository.findAll().stream()
			.map(this::toPieceRequestDTO)
			.collect(Collectors.toList());
	}

	@Override
	public List<PieceRechangeRequest> getAllPiecesRequests() {
		
		 return pieceRequestRepository.findAll();
	}

	



	
	
	
	
	
	/*
	@Override
	public PieceRequestDTO updatePieceRequest(PieceRequestDTO inter) {
		// TODO Auto-generated method stub
		return null;
	}

*/

}
