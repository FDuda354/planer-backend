package pl.dudios.planerbackend.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @Column(name = "zipcode")
    private String zipCode;
    private String companyAddress;
    @Column(name = "company_nip")
    private String companyNIP;
    private LocalDate date;
    private LocalDateTime placeDate;
    private String description;
    private String title;
    private BigDecimal priceNetto;
    private BigDecimal vat;
    private BigDecimal priceBrutto;
    private String slug;
    private String image;
    @Column(name = "userid")
    private Long userId;

}
