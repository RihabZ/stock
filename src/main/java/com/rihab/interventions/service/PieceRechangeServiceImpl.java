package com.rihab.interventions.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rihab.interventions.entities.PieceRechange;
import com.rihab.interventions.entities.PieceRechangeId;
import com.rihab.interventions.repos.PieceRechangeRepository;

@Service
public class PieceRechangeServiceImpl  implements PieceRechangeService {
	
	@Autowired
	PieceRechangeRepository pieceRechangeRepository;
	


	@Override
	public PieceRechange savePieceRechange(PieceRechange departement) {
	    // Obtenez les valeurs des clés primaires de l'article et de l'équipement depuis departement
	    long articleId = departement.getArticle().getCodeArticle();
	    String equipementId = departement.getEquipement().getEqptCode();
	    
	    // Créez un nouvel objet PieceRechangeId avec les clés primaires obtenues
	    PieceRechangeId pieceRechangeId = new PieceRechangeId(equipementId, articleId);
	    
	    // Créez un nouvel objet PieceRechange avec l'objet PieceRechangeId
	    PieceRechange pieceRechange = new PieceRechange();
	    pieceRechange.setId(pieceRechangeId);
	    pieceRechange.setArticle(departement.getArticle());
	    pieceRechange.setEquipement(departement.getEquipement());
	    pieceRechange.setEqprValeurFrequence(departement.getEqprValeurFrequence());
	    pieceRechange.setEqprUnitFrequence(departement.getEqprUnitFrequence());
	    pieceRechange.setEqprQte(departement.getEqprQte());
	    
	    // Enregistrez l'objet PieceRechange dans la base de données
	    return pieceRechangeRepository.save(pieceRechange);
	}

	@Override
	public PieceRechange updatePieceRechange(PieceRechange updatedPiece) {
	    // Récupérez l'identifiant de la pièce de rechange mise à jour
	    PieceRechangeId updatedPieceId = updatedPiece.getId();
	    
	    // Vérifiez si la pièce de rechange existe déjà dans la base de données
	   Optional<PieceRechange> existingPieceOptional = pieceRechangeRepository.findById(updatedPieceId);
	    
	    if(existingPieceOptional.isPresent()) {
	        // Si la pièce de rechange existe, mettez à jour ses champs avec les nouvelles valeurs
	        PieceRechange existingPiece = existingPieceOptional.get();
	        existingPiece.setEqprValeurFrequence(updatedPiece.getEqprValeurFrequence());
	        existingPiece.setEqprUnitFrequence(updatedPiece.getEqprUnitFrequence());
	        existingPiece.setEqprQte(updatedPiece.getEqprQte());
	        
	        // Enregistrez les modifications dans la base de données
	        return pieceRechangeRepository.save(existingPiece);
	    } else {
	        // Si la pièce de rechange n'existe pas, vous pouvez choisir de lever une exception ou de créer une nouvelle entrée
	        // Ici, nous levons une exception pour indiquer que la pièce de rechange n'a pas été trouvée
	        throw new RuntimeException("Pièce de rechange non trouvée avec l'identifiant : " + updatedPieceId);
	    }
	}

@Override
public void deletePieceRechange(PieceRechange departement) {
	pieceRechangeRepository.delete(departement);
}


@Override
public void deletePieceRechangeById(PieceRechangeId id) {
	pieceRechangeRepository.deletePieceRechangeById(id);
}


@Override
public PieceRechange getPieceRechange(PieceRechangeId id) {
return pieceRechangeRepository.findById(id).get();
}


@Override
public List<PieceRechange> getAllPiecesRechange() {
return pieceRechangeRepository.findAll();
}





}
