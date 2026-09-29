package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.entity.FoundAnimalReport;
import com.example.lostpetfinder.service.FoundAnimalReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/found-animals")
public class FoundAnimalReportController {

    private final FoundAnimalReportService foundAnimalReportService;

    public FoundAnimalReportController(
            FoundAnimalReportService foundAnimalReportService) {

        this.foundAnimalReportService =
                foundAnimalReportService;
    }

    // CREATE FOUND ANIMAL REPORT
    @PostMapping
    public ResponseEntity<FoundAnimalReport> createReport(
            @Valid @RequestBody FoundAnimalReport report) {

        return ResponseEntity.ok(
                foundAnimalReportService.createReport(report)
        );
    }

    // GET ALL FOUND ANIMAL REPORTS
    @GetMapping
    public ResponseEntity<List<FoundAnimalReport>> getAllReports() {

        return ResponseEntity.ok(
                foundAnimalReportService.getAllReports()
        );
    }

    // SEARCH BY LOCALITY
    @GetMapping("/search")
    public ResponseEntity<List<FoundAnimalReport>> searchByLocality(
            @RequestParam String locality) {

        return ResponseEntity.ok(
                foundAnimalReportService.searchByLocality(locality)
        );
    }

    // GET FOUND ANIMAL REPORT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<FoundAnimalReport> getReportById(
            @PathVariable Long id) {

        return foundAnimalReportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE FOUND ANIMAL REPORT
    @PutMapping("/{id}")
    public ResponseEntity<FoundAnimalReport> updateReport(
            @PathVariable Long id,
            @Valid @RequestBody FoundAnimalReport report) {

        return ResponseEntity.ok(
                foundAnimalReportService.updateReport(id, report)
        );
    }

    // RESOLVE FOUND ANIMAL REPORT
    @PutMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveReport(
            @PathVariable Long id) {

        foundAnimalReportService.resolveReport(id);

        return ResponseEntity.ok().build();
    }

    // DELETE FOUND ANIMAL REPORT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long id) {

        foundAnimalReportService.deleteReport(id);

        return ResponseEntity.noContent().build();
    }
}
