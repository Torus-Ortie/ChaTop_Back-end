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

        // Set Owner Id to a new list of RentalDTO
        List<RentalDTO> rentalDTOs = rentals.stream().map(rental -> {
            RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
            rentalDTO.setOwner_id(rental.getOwner().getId());
            return rentalDTO;
        }).collect(Collectors.toList());
        return Collections.singletonMap("rentals", rentalDTOs);
    }

    public RentalDTO getRental(final Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Rental not found with id : " + id));
        RentalDTO rentalDTO = modelMapper.map(rentalRepository.findById(id), RentalDTO.class);
        rentalDTO.setOwner_id(rental.getOwner().getId());
        return rentalDTO;
    }

    public String storeFile(MultipartFile picture) {
        try {
            // Save the image file
            String fileName = UUID.randomUUID().toString() + "." + getFileExtension(StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename())));
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
            // Convert RentalDTO to Rental
            Rental rental = new Rental();
            rental.setName(rentalDTO.getName());
            rental.setSurface(rentalDTO.getSurface());
            rental.setPrice(rentalDTO.getPrice());
            rental.setPicture(rentalDTO.getPicture());
            rental.setDescription(rentalDTO.getDescription());
            rental.setCreated_at(rentalDTO.getCreated_at());
            rental.setUpdated_at(rentalDTO.getUpdated_at());

            // Retrieve the user who is the owner of the Rental
            User owner = userRepository.findById(rentalDTO.getOwner_id()).orElseThrow(() -> new NoSuchElementException("Onwer not found with id : " + (rentalDTO.getOwner_id())));

            // EntityManager to persist or update the owner in the same Hibernate session as persisting the tenancy.
            entityManager.persist(owner);
            
            rental.setOwner(owner);
        
            // Save the new Rental in the database
            rentalRepository.save(rental);
        } catch (Exception e) {
            throw new RuntimeException("Error during rental creation: " + e.getMessage());
        } 
    }

    public RentalDTO updateRental(RentalDTO rentalDTO, Integer id) {
        Rental rental = rentalRepository.findById(Long.valueOf(id.longValue())).orElseThrow(() -> new NoSuchElementException("Rental not found with id : " + id));

        // Convert Rental to RentalDTO
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        rental.setUpdated_at(rentalDTO.getUpdated_at());
        rentalRepository.save(rental);

        return rentalDTO;
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}
