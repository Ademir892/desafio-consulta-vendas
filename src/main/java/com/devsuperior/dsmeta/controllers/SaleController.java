package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;
import java.time.LocalDate;
import java.util.List;

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
	public ResponseEntity<Page<SaleDTO>> getSalesReport(
			@RequestParam(value = "minDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
			@RequestParam(value = "maxDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate,
			@RequestParam(value = "name", required = false) String name,
			Pageable pageable) {
		Page<SaleDTO> salesReport = service.getSalesReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(salesReport);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryMinDTO>> getsSalesSummary(
		@RequestParam(value = "minDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
		@RequestParam(value = "maxDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate
		){
		List<SaleSummaryMinDTO> salesSummary = service.getSalesSummary(minDate, maxDate);
		return ResponseEntity.ok(salesSummary);

	}
}
