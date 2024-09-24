package com.example.openclassrooms.chatop_back_end.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.dto.RentalDTO;
import com.example.openclassrooms.chatop_back_end.models.Rental;
import com.example.openclassrooms.chatop_back_end.repositories.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Map<String, List<RentalDTO>> getRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalDTOs = rentals.stream().map(rental -> modelMapper.map(rental, RentalDTO.class)).toList();
        return Collections.singletonMap("rentals", rentalDTOs);
    }

    public RentalDTO getRental(final Long id) {
        return modelMapper.map(rentalRepository.findById(id), RentalDTO.class);
    }
}
