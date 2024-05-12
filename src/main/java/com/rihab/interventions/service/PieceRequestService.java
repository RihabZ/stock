package com.rihab.interventions.service;

import java.util.List;

import com.rihab.interventions.dto.PieceRequestDTO;
import com.rihab.interventions.entities.PieceRechangeRequest;

public interface PieceRequestService {

	PieceRequestDTO savePieceRequest(PieceRequestDTO inter) ;
	//PieceRequestDTO updatePieceRequest(PieceRequestDTO inter);
		//void deletePieceRequest(PieceRequestDTO inter);
		void deletePieceRequestByCodeDemande(long code);
		 PieceRequestDTO getPieceRequest(long code);
		
		 List<PieceRequestDTO> getAllPiecesRequests1();
			
		void deletePieceRequest(PieceRechangeRequest inter);
		List<PieceRechangeRequest> getAllPiecesRequests();

}
