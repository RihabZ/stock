package com.rihab.interventions.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rihab.interventions.entities.PieceRechange;
import com.rihab.interventions.entities.PieceRechangeId;
import com.rihab.interventions.service.PieceRechangeService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PieceRechangeRestController {
	
	@Autowired
	PieceRechangeService pieceRechangeService;
	
	
	@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping(path="allPieceRechange",method = RequestMethod.GET)
public List<PieceRechange> getAllPiecesRechange() {
	return pieceRechangeService.getAllPiecesRechange();
}


@RequestMapping(path="/getbyId/{id}",method = RequestMethod.GET)
public PieceRechange getPieceRechangeById(@PathVariable("id") PieceRechangeId id) {
	return pieceRechangeService.getPieceRechange(id);
 }
@PreAuthorize("hasAuthority('MANAGER')")
//autorisation au admin seulement cette methode
@RequestMapping(path="/addPieceRechange",method = RequestMethod.POST)
public PieceRechange createPieceRechange(@RequestBody PieceRechange pieceRechange) {
	
	return pieceRechangeService.savePieceRechange(pieceRechange);
}


@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping(path="/updatePieceRechange", method = RequestMethod.PUT)
public ResponseEntity<?> updatePieceRechange(@RequestBody PieceRechange updatedPiece) {
    try {
        PieceRechange updated = pieceRechangeService.updatePieceRechange(updatedPiece);
        return ResponseEntity.ok().body(updated);
    } catch (RuntimeException e) {
        // Gérez l'exception si la pièce de rechange n'est pas trouvée
        return ResponseEntity.notFound().build();
    }
}


@RequestMapping(value="/delPieceRechange/{id}",method = RequestMethod.DELETE)

public void deletePieceRechange(@PathVariable("id") PieceRechangeId id)
{
	pieceRechangeService.deletePieceRechangeById(id);
}


}


