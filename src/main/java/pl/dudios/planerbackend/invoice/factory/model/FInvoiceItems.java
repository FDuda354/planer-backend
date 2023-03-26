package pl.dudios.planerbackend.invoice.factory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FInvoiceItems {

    private String productName;
    private Integer productQuantity;
    private BigDecimal productPriceNetto;
    private BigDecimal productPriceBrutto;
}
