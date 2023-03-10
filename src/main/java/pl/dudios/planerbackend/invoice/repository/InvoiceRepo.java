package pl.dudios.planerbackend.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudios.planerbackend.invoice.model.Invoice;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByUserId(Long userId);

    Optional<Invoice> findBySlug(String invoiceSlug);
}
