package com.rihab.interventions.service;

import java.util.List;

import com.rihab.interventions.entities.PieceRechange;
import com.rihab.interventions.entities.PieceRechangeId;

public interface PieceRechangeService {
		

	PieceRechange savePieceRechange(PieceRechange departement);
	PieceRechange updatePieceRechange(PieceRechange Departement);
void deletePieceRechange(PieceRechange departement);
 void deletePieceRechangeById(PieceRechangeId id);
 PieceRechange getPieceRechange(PieceRechangeId id);
List<PieceRechange> getAllPiecesRechange();


}
