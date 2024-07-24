package com.devsuperior.dsmeta.services;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
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

	public List<SaleSummaryMinDTO> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
		if (maxDate == null) {
			maxDate = LocalDate.now();
		}
		if (minDate == null) {
			minDate = maxDate.minusYears(2L);
		}
		List<Object[]> results = repository.searchSalesSummary(minDate, maxDate);
		List<SaleSummaryMinDTO> dtoList = new ArrayList<>();
		for (Object[] result : results) {
			String sellerName = (String) result[0];
			Double amount = ((Number) result[1]).doubleValue();
			dtoList.add(new SaleSummaryMinDTO(sellerName, amount));
		}
		return dtoList;
	}


	public Page<SaleDTO> getSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
		if (maxDate == null) {
			maxDate = LocalDate.now();
		}
		if (minDate == null) {
			minDate = maxDate.minusYears(2L);
		}

		Page<Object[]> results;
		if (name == null || name.isEmpty()) {
			results = repository.searchSalesReport(minDate, maxDate, pageable);
		} else {
			results = repository.searchSalesReportWithName(minDate, maxDate, name, pageable);
		}

		return results.map(result -> {
			Long id = ((BigInteger) result[0]).longValue();
			LocalDate date = ((java.sql.Date) result[1]).toLocalDate();
			Double amount = ((Number) result[2]).doubleValue();
			String sellerName = (String) result[3];
			return new SaleDTO(id, date, amount, sellerName);
		});
	}

}
