package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SalesBySellerReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SalesBySellerReportDTO> salesSellerReport(
            String startDate,
            String endDate,
            String sellerName,
            Pageable pageable) {

        LocalDate today = LocalDate.now();
        LocalDate start;
        LocalDate end;

        if (endDate.isEmpty()) {
            end = today;
        } else {
            try {
                end = LocalDate.parse(endDate);
            } catch (Exception e) {
                end = today;
            }
        }

        if (startDate.isEmpty()) {
            start = end.minusYears(1);
        } else {
            try {
                start = LocalDate.parse(startDate);
            } catch (Exception e) {
                start = end.minusYears(1);
            }
        }

        return repository.salesSellerReport(start, end, sellerName, pageable);
    }

    public Page<SalesSummaryReportDTO> salesSummaryReport(
            String startDate,
            String endDate,
            Pageable pageable) {

        LocalDate today = LocalDate.now();
        LocalDate start;
        LocalDate end;

        if (endDate.isEmpty()) {
            end = today;
        } else {
            try {
                end = LocalDate.parse(endDate);
            } catch (Exception e) {
                end = today;
            }
        }

        if (startDate.isEmpty()) {
            start = end.minusYears(1);
        } else {
            try {
                start = LocalDate.parse(startDate);
            } catch (Exception e) {
                start = end.minusYears(1);
            }
        }

        return repository.salesSummaryReport(start, end, pageable);
    }
}
