package com.example.openclassrooms.chatop_back_end.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.models.Rental;
import com.example.openclassrooms.chatop_back_end.repositories.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public Optional<Rental> getRentalById(final Long id) {
        return rentalRepository.findById(id);
    }

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public void deleteRentalById(final Long id) {
        rentalRepository.deleteById(id);
    }

    public Rental saveRental(Rental rental) {
        Rental savedEmployee = rentalRepository.save(rental);
        return savedEmployee;
    }
}
