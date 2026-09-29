package com.example.lostpetfinder.service;

import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.repository.FoundAnimalReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoundAnimalReportService {

    private final FoundAnimalReportRepository foundAnimalReportRepository;

    public FoundAnimalReportService(
            FoundAnimalReportRepository foundAnimalReportRepository) {

        this.foundAnimalReportRepository =
                foundAnimalReportRepository;
    }


    // CREATE
    public FoundAnimalReport createReport(
            FoundAnimalReport report) {

        if (report.getStatus() == null ||
                report.getStatus().isBlank()) {

            report.setStatus("ACTIVE");
        }

        return foundAnimalReportRepository.save(report);
    }


    // READ ALL
    public List<FoundAnimalReport> getAllReports() {

        return foundAnimalReportRepository.findAll();
    }


    // SEARCH BY LOCALITY
    public List<FoundAnimalReport> searchByLocality(
            String locality) {

        return foundAnimalReportRepository
                .findByLocalityIgnoreCase(locality);
    }


    // READ BY ID
    public Optional<FoundAnimalReport> getReportById(
            Long id) {

        return foundAnimalReportRepository.findById(id);
    }


    // UPDATE
    public FoundAnimalReport updateReport(
            Long id,
            FoundAnimalReport reportDetails) {

        FoundAnimalReport report =
                foundAnimalReportRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Found animal report not found"));

        report.setSpecies(reportDetails.getSpecies());
        report.setBreed(reportDetails.getBreed());
        report.setColour(reportDetails.getColour());
        report.setLocality(reportDetails.getLocality());
        report.setStatus(reportDetails.getStatus());
        report.setUser(reportDetails.getUser());

        return foundAnimalReportRepository.save(report);
    }


    // RESOLVE REPORT
    public void resolveReport(Long id) {

        FoundAnimalReport report =
                foundAnimalReportRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Found animal report not found"));

        report.setStatus("RESOLVED");

        foundAnimalReportRepository.save(report);
    }


    // DELETE
    public void deleteReport(Long id) {

        foundAnimalReportRepository.deleteById(id);
    }
}