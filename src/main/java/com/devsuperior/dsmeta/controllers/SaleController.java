package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SalesBySellerReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Page<SalesBySellerReportDTO>> getReport(
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate,
            @RequestParam(value = "name", defaultValue = "") String name,
            Pageable pageable) {

        Page<SalesBySellerReportDTO> saleSellerReportDTOS = service.salesSellerReport(minDate, maxDate, name, pageable);
        return ResponseEntity.ok(saleSellerReportDTOS);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<Page<SalesSummaryReportDTO>> getSummary(
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate,
            Pageable pageable) {

        Page<SalesSummaryReportDTO> salesSummaryReportDTOS = service.salesSummaryReport(minDate, maxDate, pageable);
        return ResponseEntity.ok(salesSummaryReportDTOS);
    }
}
