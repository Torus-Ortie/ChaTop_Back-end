package com.example.openclassrooms.chatop_back_end.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.openclassrooms.chatop_back_end.dto.RentalDTO;
import com.example.openclassrooms.chatop_back_end.dto.UserDTO;
import com.example.openclassrooms.chatop_back_end.services.RentalService;
import com.example.openclassrooms.chatop_back_end.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Map<String, List<RentalDTO>>> getRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<RentalDTO> getRental(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    @PostMapping("")
    public ResponseEntity<RentalDTO> postRental(
        @RequestParam("name") String name, 
		@RequestParam("surface") Integer surface,
		@RequestParam("price") Integer price,
		@RequestParam("picture") MultipartFile picture,
		@RequestParam("description") String description,
		Authentication authentication) {
        // Récupérer l'utilisateur actuellement authentifié
    	UserDTO currentUser = userService.getCurrentUser(authentication);

		// Créer un nouveau RentalDTO
		RentalDTO rentalDTO = new RentalDTO();
		rentalDTO.setName(name);
		rentalDTO.setSurface(surface);
		rentalDTO.setPrice(price);
		rentalDTO.setDescription(description);
		rentalDTO.setOwner_id(currentUser.getId());

		// Stocker l'image et obtenir son URL
		String pictureUrl = rentalService.storeFile(picture);
		rentalDTO.setPicture(pictureUrl);

		// Ajouter le nouveau Rental à la base de données
		rentalService.addRental(rentalDTO);

		// Retourner le nouveau Rental
		return ResponseEntity.ok(rentalDTO);
    }

    @PutMapping("/{id}")  // tester @ModelAttribute RentalDTO ...
	public ResponseEntity<RentalDTO> putRentalById(
		@PathVariable("id") final Integer id,
		@RequestParam("name") String name,
		@RequestParam("surface") Integer surface,
		@RequestParam("price") Integer price,
		@RequestParam("description") String description)
		{
		
		RentalDTO rentalDTO = rentalService.getRental(Long.valueOf(id.longValue()));
	
		rentalDTO.setName(name);
		rentalDTO.setSurface(surface);
		rentalDTO.setPrice(price);
		rentalDTO.setDescription(description);
		rentalDTO.setUpdated_at(LocalDateTime.now());
	
		return ResponseEntity.ok(rentalService.updateRental(rentalDTO, id));
	}
    
}
