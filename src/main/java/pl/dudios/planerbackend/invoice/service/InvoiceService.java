package pl.dudios.planerbackend.invoice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.dudios.planerbackend.common.ExistingFileRenameUtils;
import pl.dudios.planerbackend.common.SlugifyUtils;
import pl.dudios.planerbackend.invoice.model.Invoice;
import pl.dudios.planerbackend.invoice.model.dto.InvoiceDto;
import pl.dudios.planerbackend.invoice.repository.InvoiceRepo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;
    @Value("${app.uploadDir}")
    private String UPLOAD_DIR;


    public List<Invoice> getAllInvoicesByUserId(Long userId) {
        return invoiceRepo.findAllByUserId(userId);
    }

    public Invoice getInvoiceBySlug(String invoiceSlug) {
        return invoiceRepo.findBySlug(invoiceSlug).orElseThrow();
    }

    public Invoice addInvoice(InvoiceDto invoice, Long userId) {
        return invoiceRepo.save(Invoice.builder()
                .companyName(invoice.getCompanyName())
                .companyAddress(invoice.getCompanyAddress())
                .zipCode(invoice.getZipCode())
                .description(invoice.getDescription())
                .companyNIP(invoice.getCompanyNIP())
                .image(invoice.getImage())
                .date(invoice.getDate())
                .priceBrutto(invoice.getPriceBrutto())
                .priceNetto(invoice.getPriceBrutto().subtract(invoice.getPriceBrutto().multiply(invoice.getVat().movePointLeft(2))))
                .vat(invoice.getVat())
                .title(invoice.getTitle())
                .placeDate(LocalDateTime.now())
                .slug(SlugifyUtils.slugifySlug(invoice.getSlug()))
                .userId(userId)
                .build());
    }

    public Invoice updateInvoice(InvoiceDto invoice, String invoiceSlug) {
        Invoice invoiceToUpdate = invoiceRepo.findBySlug(invoiceSlug).orElseThrow();
        invoiceToUpdate.setCompanyName(invoice.getCompanyName());
        invoiceToUpdate.setCompanyAddress(invoice.getCompanyAddress());
        invoiceToUpdate.setZipCode(invoice.getZipCode());
        invoiceToUpdate.setDescription(invoice.getDescription());
        invoiceToUpdate.setCompanyNIP(invoice.getCompanyNIP());
        invoiceToUpdate.setImage(invoice.getImage());
        invoiceToUpdate.setDate(invoice.getDate());
        invoiceToUpdate.setPriceBrutto(invoice.getPriceBrutto());
        invoiceToUpdate.setVat(invoice.getVat());
        invoiceToUpdate.setTitle(invoice.getTitle());
        invoiceToUpdate.setPlaceDate(LocalDateTime.now());
        invoiceToUpdate.setSlug(SlugifyUtils.slugifySlug(invoice.getSlug()));
        invoiceToUpdate.setPriceNetto(invoice.getPriceBrutto().subtract(invoice.getPriceBrutto().multiply(invoice.getVat().movePointLeft(2))));
        return invoiceRepo.save(invoiceToUpdate);
    }

    public void deleteInvoiceById(Long invoiceId) {
        invoiceRepo.deleteById(invoiceId);
    }

    public String uploadInvoiceImage(String fileName, InputStream inputStream) {
        String newFileName = SlugifyUtils.slugifyFileName(fileName);
        newFileName = ExistingFileRenameUtils.renameFileIfExists(Path.of(UPLOAD_DIR), newFileName);
        Path filePath = Paths.get(UPLOAD_DIR).resolve(newFileName);
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Cant save file", e);
        }
        return newFileName;
    }

    public Resource serveFiles(String fileName) {
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        return resourceLoader.getResource(UPLOAD_DIR + fileName);
    }
//TODO: wtf is this??
//    public Resource serveFile(String fileName) {
//        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
//        Resource file = resourceLoader.getResource(UPLOAD_DIR + fileName);
//        System.out.println("elooooo3");
//        String contentType = "";
//        try {
//            contentType = Files.probeContentType(Path.of(file.getURI()));
//            System.out.println("elooooo4");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (contentType != null && (contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("application/pdf"))) {
//            return file;
//        } else {
//            throw new RuntimeException("Invalid file type");
//        }
//    }

//    public String uploadInvoiceImage(String fileName, InputStream inputStream) {
//        String newFileName = SlugifyUtils.slugifyFileName(fileName);
//        newFileName = ExistingFileRenameUtils.renameFileIfExists(Path.of("/opt/app"+UPLOAD_DIR), newFileName);
//        Path filePath = Paths.get("/opt/app"+UPLOAD_DIR).resolve(newFileName);
//        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
//            inputStream.transferTo(outputStream);
//        } catch (IOException e) {
//            throw new RuntimeException("Cant save file", e);
//        }
//        return newFileName;
//    }
//
//    public Resource serveFiles(String fileName) {
//        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
//        return resourceLoader.getResource(UPLOAD_DIR + fileName);
//    }
}
