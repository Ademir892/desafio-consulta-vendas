package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(nativeQuery = true, value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS total " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name")
    List<Object[]> searchSalesSummary(@Param("minDate") LocalDate minDate,
                                            @Param("maxDate") LocalDate maxDate);


    @Query(nativeQuery = true, value = "SELECT tb_sales.id AS id, tb_sales.date AS date, tb_sales.amount AS amount, " +
            "tb_seller.name AS sellerName " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "ORDER BY tb_sales.date",
            countQuery = "SELECT COUNT(*) FROM tb_sales " +
                    "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
                    "WHERE tb_sales.date BETWEEN :minDate AND :maxDate")
    Page<Object[]> searchSalesReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT tb_seller.id AS id, tb_sales.date, tb_sales.amount, tb_seller.name " +
            "FROM tb_sales " +
            "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
            "WHERE tb_sales.date BETWEEN CAST(:minDate AS DATE) AND CAST(:maxDate AS DATE) " +
            "AND LOWER(tb_seller.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "ORDER BY tb_sales.date DESC",
            countQuery = "SELECT COUNT(*) FROM tb_sales " +
                    "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id " +
                    "WHERE tb_sales.date BETWEEN CAST(:minDate AS DATE) AND CAST(:maxDate AS DATE) " +
                    "AND LOWER(tb_seller.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Object[]> searchSalesReportWithName(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name, Pageable pageable);
}
