package pl.dudios.planerbackend.invoice.factory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FInvoice {

    private String customerCompanyName;
    private String customerZipCode;
    private String customerCompanyAddress;
    private String customerCompanyNIP;
    private Long invoiceId;
    private BigDecimal totalPpriceNetto;
    private BigDecimal vat;
    private BigDecimal totalPriceBrutto;
    private String companyName;
    private String companyZipCode;
    private String companyAddress;
    private String companyNIP;
    private String companyBankNumber;
    private String paymentMethod;
    private List<FInvoiceItems> items;


}
