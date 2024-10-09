package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SalesBySellerReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    /*
        SELECT * FROM tb_sales s
        INNER JOIN tb_seller v ON s.seller_id = v.id
        WHERE UPPER(v.name) LIKE UPPER('%ODINSON%')
        AND s.date BETWEEN '2022-05-01' AND '2022-05-31';
     */
    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesBySellerReportDTO(s.id, s.date, s.amount, s.seller.name) " +
            "FROM Sale s " +
            "INNER JOIN s.seller v " +
            "WHERE UPPER(v.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) " +
            "AND s.date BETWEEN :startDate AND :endDate")
    Page<SalesBySellerReportDTO> salesSellerReport(LocalDate startDate, LocalDate endDate, String sellerName, Pageable pageable);

    /*
        SELECT v.name, SUM(s.amount)
        FROM tb_sales s
        INNER JOIN tb_seller v ON s.seller_id = v.id
        WHERE s.date BETWEEN '2022-01-01' AND '2022-06-30'
        GROUP BY v.name;
     */
    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesSummaryReportDTO(s.seller.name, SUM(s.amount)) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY s.seller.name")
    Page<SalesSummaryReportDTO> salesSummaryReport(LocalDate startDate, LocalDate endDate, Pageable pageable);

}
