package com.rihab.interventions.restControllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rihab.interventions.dto.UserDTO;
import com.rihab.interventions.entities.AuthenticationResponse;
import com.rihab.interventions.entities.Magasinier;
import com.rihab.interventions.entities.Role;
import com.rihab.interventions.entities.User;
import com.rihab.interventions.register.LoginRequest;
import com.rihab.interventions.repos.DemandeurRepository;
import com.rihab.interventions.repos.MagasinierRepository;
import com.rihab.interventions.repos.ManagerRepository;
import com.rihab.interventions.repos.TechnicienRepository;
import com.rihab.interventions.repos.UserRepository;
import com.rihab.interventions.service.AuthenticationService;
import com.rihab.interventions.service.JwtService;
import com.rihab.interventions.service.MagasinierService;
import com.rihab.interventions.util.EmailSender;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authService;
    private final MagasinierService magasinierService;

   

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDTO request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

 /*
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        return ResponseEntity.ok(authService.authenticate(login, password));
    }
*/
  
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
             @RequestBody UserDTO request
    ) {
    	 return ResponseEntity.ok(authService.authenticate(request));
    }
   
    @GetMapping("/allUsers")
    public List<UserDTO> getAllUsers() {
        return authService.getAllUsers();
    }
    

 // supprimer un user
    @RequestMapping(value="/delUser/{user_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") Long userId) {
        authService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDTO) {
        UserDTO updatedUser = authService.updateProfile(userDTO);
        return ResponseEntity.ok(updatedUser);
    }
    
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUserProfile() {
        UserDTO currentUserProfile = authService.getCurrentUserDetails();
        return ResponseEntity.ok(currentUserProfile);
    }
/*
    @GetMapping("/allMag") // Définissez votre URL spécifique pour cette méthode
    public List<UserDTO> getAllMagasiniers() {
        List<Magasinier> magasiniers = magasinierService.getAllMagasiniers();
        List<User> magasiniersWithRole = magasiniers.stream()
                                                .filter(m -> m.getRole() == Role.MAGASINIER)
                                                .collect(Collectors.toList());
        return magasiniersWithRole.stream()
                .map(magasinier -> convertToDTO(magasinier))
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User magasinier) {
        return UserDTO.builder()
                .id(magasinier.getId())
                .nom(magasinier.getNom())
                .prenom(magasinier.getPrenom())
                .email(magasinier.getEmail())
                .tel(magasinier.getTel())
                .age(magasinier.getAge())
                .role(magasinier.getRole())
                .sexe(magasinier.getSexe())
                .dateEmbauche(magasinier.getDateEmbauche())
                .build();
    }
}
    
  */  @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/totalTechniciens")
    public ResponseEntity<Long> getTotalTechniciens() {
        long totalTechniciens = authService.getTotalTechniciens();
        return ResponseEntity.ok(totalTechniciens);
    }
  @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/totaMagasiniers")
    public ResponseEntity<Long> getTotalMagasiniers() {
        long totalMagasiniers = authService.getTotalMagasiniers();
        return ResponseEntity.ok(totalMagasiniers);
    }
  @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/totalDemandeurs")
    public ResponseEntity<Long> getTotalDemandeurs() {
        long totalDemandeurs = authService.getTotalDemandeurs();
        return ResponseEntity.ok(totalDemandeurs);
    }

    
    
}