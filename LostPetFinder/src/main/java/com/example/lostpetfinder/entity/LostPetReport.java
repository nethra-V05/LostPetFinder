package com.example.lostpetfinder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "lost_pet_reports")
public class LostPetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Species is required")
    private String species;

    @NotBlank(message = "Breed is required")
    private String breed;

    @NotBlank(message = "Colour is required")
    private String colour;

    @NotBlank(message = "Locality is required")
    private String locality;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public LostPetReport() {
    }

    public LostPetReport(
            String species,
            String breed,
            String colour,
            String locality,
            String status,
            User user) {

        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.locality = locality;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}