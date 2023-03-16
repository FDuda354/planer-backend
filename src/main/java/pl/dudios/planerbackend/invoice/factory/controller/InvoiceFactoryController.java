package pl.dudios.planerbackend.invoice.factory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.planerbackend.invoice.factory.model.FInvoice;
import pl.dudios.planerbackend.invoice.factory.service.InvoiceFactoryService;
import pl.dudios.planerbackend.invoice.model.Invoice;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class InvoiceFactoryController {


    private final InvoiceFactoryService invoiceFactoryService;

    @PostMapping("/invoice/create")
    public ResponseEntity<Resource> createInvoice(@RequestBody FInvoice invoice, @AuthenticationPrincipal Long userId) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "invo"+ UUID.randomUUID()+ ".pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(invoiceFactoryService.createInvoice(invoice, userId));

    }
//    @PostMapping("/invoice/create")
//    public ResponseEntity<Resource> createInvoice(@RequestBody Invoice invoice) throws IOException {
//
//        // Create a new PDF document
//        PDDocument document = new PDDocument();
//
//        // Add a new page to the document
//        PDPage page = new PDPage(PDRectangle.A4);
//        document.addPage(page);
//
//        // Create a content stream for the page
//        PDPageContentStream contentStream = new PDPageContentStream(document, page);
//
//        // Define font sizes for the title and the content
//        int fontSizeTitle = 20;
//        int fontSizeContent = 12;
//
//        // Set the font for the title
//        contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSizeTitle);
//
//        // Write the title to the document
//        contentStream.beginText();
//        contentStream.newLineAtOffset(50, 750);
//        contentStream.showText("INVOICE");
//        contentStream.endText();
//
//        // Set the font for the content
//        contentStream.setFont(PDType1Font.HELVETICA, fontSizeContent);
//
//        // Write the invoice details to the document
//        contentStream.beginText();
//        contentStream.newLineAtOffset(50, 700);
//        contentStream.showText("Company Name: " + invoice.getCompanyName());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Zip Code: " + invoice.getZipCode());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Company Address: " + invoice.getCompanyAddress());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Company NIP: " + invoice.getCompanyNIP());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Place and Date: " + invoice.getDate().toString());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Description: " + invoice.getDescription());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Title: " + invoice.getTitle());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Price (Netto): " + invoice.getPriceNetto());
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("VAT: " + invoice.getVat() + "%");
//        contentStream.newLineAtOffset(0, -20);
//        contentStream.showText("Price (Brutto): " + invoice.getPriceBrutto());
//        contentStream.endText();
//
//        // Add the company logo to the document
//        Resource res = new FileSystemResourceLoader().getResource(UPLOAD_DIR + "placeholder.jpg");
//        PDImageXObject logo = JPEGFactory.createFromStream(document,
//                //add placeholder.pdf from data.invoiceImages
//                new ByteArrayInputStream(res.getInputStream().readAllBytes()));
//
//        contentStream.drawImage(logo, 50, 450, 150, 150);
//
//        // Close the content stream
//        contentStream.close();
//
//        // Convert the PDF document to a byte array
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        document.save(baos);
//        document.close();
//        byte[] pdfBytes = baos.toByteArray();
//
//        // Create an input stream resource from the byte array
//        Resource resource = new InputStreamResource(new ByteArrayInputStream(pdfBytes));
//        // Create headers for the response
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "invoisdace.pdf");
//
//        // Return the PDF as a response entity with the appropriate headers
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
//    }
}
