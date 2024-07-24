package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleSummaryMinDTO {

    private String sellerName;
    private Double amount;

    public SaleSummaryMinDTO(){
    }

    public SaleSummaryMinDTO(String sellerName, Double amount) {
        this.sellerName = sellerName;
        this.amount = amount;
    }

    public SaleSummaryMinDTO(Sale entity) {
        sellerName = entity.getSeller().getName();
        amount = entity.getAmount();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
