package pl.dudios.planerbackend.invoice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InvoiceDto {
    @NotBlank
    @Length(min = 3, max = 200)
    private String companyName;
    @NotBlank
    private String zipCode;
    @NotBlank
    @Length(min = 3, max = 200)
    private String companyAddress;
    @NotBlank
    private String companyNIP;
    @NotNull
    private LocalDate date;
    @NotBlank
    @Length(min = 3, max = 1000)
    private String description;
    @NotBlank
    @Length(min = 3, max = 200)
    private String title;
    @NotNull
    @Min(0)
    private BigDecimal vat;
    @NotNull
    @Min(0)
    private BigDecimal priceBrutto;
    @NotBlank
    @Length(min = 3, max = 200)
    private String slug;
    private String image;

}
