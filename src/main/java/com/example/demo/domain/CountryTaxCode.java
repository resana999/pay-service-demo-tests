package com.example.demo.domain;

import java.math.BigDecimal;

public enum CountryTaxCode {
    DE("DE([0-9]{9})", 19.00),
    IT("IT([0-9]{11})", 22.00),
    GR("GR([0-9]{9})", 20.00),
    FR("FR([a-zA-Z]{2})([0-9]{9})", 24.00);

    public final String pattern;

    public final BigDecimal taxPercent;

    CountryTaxCode(String pattern, double taxPercent) {
        this.pattern = pattern;
        this.taxPercent = BigDecimal.valueOf(taxPercent);
    }
}
