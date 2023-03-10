package pl.dudios.planerbackend.invoice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.dudios.planerbackend.common.UploadResponse;
import pl.dudios.planerbackend.invoice.model.Invoice;
import pl.dudios.planerbackend.invoice.model.dto.InvoiceDto;
import pl.dudios.planerbackend.invoice.service.InvoiceService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Validated

@RestController
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoicesByUserId(@AuthenticationPrincipal Long userId) {
        return invoiceService.getAllInvoicesByUserId(userId);
    }

    @GetMapping("/invoice/{invoiceSlug}")
    public Invoice getInvoicesById(@PathVariable String invoiceSlug) {
        return invoiceService.getInvoiceBySlug(invoiceSlug);
    }

    @PostMapping("/invoice")
    public Invoice addInvoice(@RequestBody @Valid InvoiceDto invoice, @AuthenticationPrincipal Long userId) {
        return invoiceService.addInvoice(invoice, userId);
    }

    @PutMapping("/invoice/{invoiceSlug}")
    public Invoice updateInvoice(@RequestBody @Valid InvoiceDto invoice, @PathVariable String invoiceSlug) {
        return invoiceService.updateInvoice(invoice, invoiceSlug);
    }

    @DeleteMapping("/invoice/{invoiceId}")
    public void deleteInvoiceById(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoiceById(invoiceId);
    }

    @PostMapping("/invoice/upload-image")
    public UploadResponse uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String savedFileName = invoiceService.uploadInvoiceImage(file.getOriginalFilename(), inputStream);
            return new UploadResponse(savedFileName);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving file " + e.getMessage());
        }

    }

    @GetMapping("/data/invoiceImage/{fileName}")
    public ResponseEntity<Resource> serveFiles(@PathVariable String fileName) {
        Resource file = invoiceService.serveFiles(fileName);
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(fileName)))
                    .body(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download/invoiceImage/{fileName}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable String fileName) {

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-" + UUID.randomUUID() + "." + extension;
        Resource file = invoiceService.serveFiles(fileName);
        if (extension.equals("pdf"))
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, newFileName)
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(file);
        else if (extension.equals("jpeg") || extension.equals("png"))
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, newFileName)
                    .contentType(MediaType.parseMediaType("image/" + extension))
                    .body(file);

        return null;
    }
}
