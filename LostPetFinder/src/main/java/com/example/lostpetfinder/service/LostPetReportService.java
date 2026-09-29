package com.example.lostpetfinder.service;

import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.repository.FoundAnimalReportRepository;
import com.example.lostpetfinder.repository.LostPetReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostPetReportService {

    private final LostPetReportRepository lostPetReportRepository;

    private final FoundAnimalReportRepository foundAnimalReportRepository;

    public LostPetReportService(
            LostPetReportRepository lostPetReportRepository,
            FoundAnimalReportRepository foundAnimalReportRepository) {

        this.lostPetReportRepository = lostPetReportRepository;
        this.foundAnimalReportRepository = foundAnimalReportRepository;
    }


    // CREATE
    public LostPetReport createReport(LostPetReport report) {

        if (report.getStatus() == null ||
                report.getStatus().isBlank()) {

            report.setStatus("ACTIVE");
        }

        return lostPetReportRepository.save(report);
    }


    // READ ALL
    public List<LostPetReport> getAllReports() {

        return lostPetReportRepository.findAll();
    }


    // SEARCH BY LOCALITY
    public List<LostPetReport> searchByLocality(String locality) {

        return lostPetReportRepository
                .findByLocalityIgnoreCase(locality);
    }


    // READ BY ID
    public Optional<LostPetReport> getReportById(Long id) {

        return lostPetReportRepository.findById(id);
    }


    // UPDATE
    public LostPetReport updateReport(
            Long id,
            LostPetReport reportDetails) {

        LostPetReport report =
                lostPetReportRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lost pet report not found"));

        report.setSpecies(reportDetails.getSpecies());
        report.setBreed(reportDetails.getBreed());
        report.setColour(reportDetails.getColour());
        report.setLocality(reportDetails.getLocality());
        report.setStatus(reportDetails.getStatus());
        report.setUser(reportDetails.getUser());

        return lostPetReportRepository.save(report);
    }


    // RESOLVE REPORT
    public void resolveReport(Long id) {

        LostPetReport report =
                lostPetReportRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lost pet report not found"));

        report.setStatus("RESOLVED");

        lostPetReportRepository.save(report);
    }


    // FIND POSSIBLE MATCHES
    public List<FoundAnimalReport> findMatches(Long lostPetId) {

        LostPetReport lostPet =
                lostPetReportRepository.findById(lostPetId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Lost pet report not found"));


        // Resolved lost reports should not
        // have active match suggestions
        if ("RESOLVED".equalsIgnoreCase(
                lostPet.getStatus())) {

            return List.of();
        }


        return foundAnimalReportRepository
                .findBySpeciesIgnoreCaseAndLocalityIgnoreCaseAndStatusIgnoreCase(
                        lostPet.getSpecies(),
                        lostPet.getLocality(),
                        "ACTIVE"
                );
    }


    // DELETE
    public void deleteReport(Long id) {

        lostPetReportRepository.deleteById(id);
    }
}