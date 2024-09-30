package com.example.openclassrooms.chatop_back_end.services;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.example.openclassrooms.chatop_back_end.dto.RentalDTO;
import com.example.openclassrooms.chatop_back_end.models.Rental;
import com.example.openclassrooms.chatop_back_end.models.User;
import com.example.openclassrooms.chatop_back_end.repositories.RentalRepository;
import com.example.openclassrooms.chatop_back_end.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.Data;

@Data
@Service
public class RentalService {
    @Value("${image.path}")
    private String imagePath;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    public Map<String, List<RentalDTO>> getRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalDTOs = rentals.stream().map(rental -> {
            RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
            rentalDTO.setOwner_id(rental.getOwner().getId());
            return rentalDTO;
        }).collect(Collectors.toList());
        return Collections.singletonMap("rentals", rentalDTOs);
    }

    public RentalDTO getRental(final Long id) {
        Rental rental = rentalRepository.findById(id).get();
        RentalDTO rentalDTO = modelMapper.map(rentalRepository.findById(id), RentalDTO.class);
        rentalDTO.setOwner_id(rental.getOwner().getId());
        return rentalDTO;
    }

    public String storeFile(MultipartFile picture) {
        try {
            // Save the image file
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            Files.copy(picture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Generate an absolute URL for the image
            String imageUrl = imagePath + "/" + fileName;
            return imageUrl;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Transactional
    public void addRental(RentalDTO rentalDTO) {
        try {
            // Convertir le RentalDTO en Rental
            Rental rental = new Rental();
            rental.setName(rentalDTO.getName());
            rental.setSurface(rentalDTO.getSurface());
            rental.setPrice(rentalDTO.getPrice());
            rental.setPicture(rentalDTO.getPicture());
            rental.setDescription(rentalDTO.getDescription());
            rental.setCreated_at(rentalDTO.getCreated_at());
            rental.setUpdated_at(rentalDTO.getUpdated_at());

            // Récupérer l'utilisateur qui est le propriétaire du Rental
            User owner = userRepository.findById(rentalDTO.getOwner_id()).get();

            // Ici, nous utilisons EntityManager pour persister ou mettre à jour le propriétaire dans la même session Hibernate que la persistance de la location.
            entityManager.persist(owner);
            
            rental.setOwner(owner);
        
            // Enregistrer le nouveau Rental dans la base de données
            rentalRepository.save(rental);
        } catch (Exception e) {
            throw new RuntimeException("Error during rental creation: " + e.getMessage());
        } 
    }

    public RentalDTO updateRental(RentalDTO rentalDTO, Integer id) {
        Rental rental = rentalRepository.findById(Long.valueOf(id.longValue())).get();
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        rental.setUpdated_at(rentalDTO.getUpdated_at());
        rentalRepository.save(rental);

        return rentalDTO;
    }
}
