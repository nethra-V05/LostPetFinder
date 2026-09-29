package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.entity.LostPetReport;
import com.example.lostpetfinder.service.LostPetReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lost-pets")
public class LostPetReportController {

    private final LostPetReportService lostPetReportService;

    public LostPetReportController(
            LostPetReportService lostPetReportService) {

        this.lostPetReportService = lostPetReportService;
    }

    // CREATE LOST PET REPORT
    @PostMapping
    public ResponseEntity<LostPetReport> createReport(
            @Valid @RequestBody LostPetReport report) {

        return ResponseEntity.ok(
                lostPetReportService.createReport(report)
        );
    }

    // GET ALL LOST PET REPORTS
    @GetMapping
    public ResponseEntity<List<LostPetReport>> getAllReports() {

        return ResponseEntity.ok(
                lostPetReportService.getAllReports()
        );
    }

    // SEARCH BY LOCALITY
    @GetMapping("/search")
    public ResponseEntity<List<LostPetReport>> searchByLocality(
            @RequestParam String locality) {

        return ResponseEntity.ok(
                lostPetReportService.searchByLocality(locality)
        );
    }

    // GET LOST PET REPORT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<LostPetReport> getReportById(
            @PathVariable Long id) {

        return lostPetReportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // FIND POSSIBLE MATCHES
    @GetMapping("/{id}/matches")
    public ResponseEntity<List<FoundAnimalReport>> findMatches(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                lostPetReportService.findMatches(id)
        );
    }

    // UPDATE LOST PET REPORT
    @PutMapping("/{id}")
    public ResponseEntity<LostPetReport> updateReport(
            @PathVariable Long id,
            @Valid @RequestBody LostPetReport report) {

        return ResponseEntity.ok(
                lostPetReportService.updateReport(id, report)
        );
    }

    // RESOLVE LOST PET REPORT
    @PutMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveReport(
            @PathVariable Long id) {

        lostPetReportService.resolveReport(id);

        return ResponseEntity.ok().build();
    }

    // DELETE LOST PET REPORT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long id) {

        lostPetReportService.deleteReport(id);

        return ResponseEntity.noContent().build();
    }
}
