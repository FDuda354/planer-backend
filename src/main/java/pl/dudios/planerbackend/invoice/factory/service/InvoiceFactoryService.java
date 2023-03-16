package pl.dudios.planerbackend.invoice.factory.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.dudios.planerbackend.invoice.factory.model.FInvoice;
import pl.dudios.planerbackend.invoice.factory.model.FInvoiceItems;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceFactoryService {
    @Value("${app.uploadDir}")
    private String UPLOAD_DIR;

    public Resource createInvoice(FInvoice invoice, Long userId) throws IOException {
        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Add a new page to the document
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // Create a content stream for the page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Define font sizes for the title and the content
        int fontSizeTitle = 20;
        int fontSizeContent = 13;

        // Set the font for the title
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSizeTitle);


        // Set the font for the content
        contentStream.setFont(PDType1Font.HELVETICA, fontSizeContent);
        Resource res = new FileSystemResourceLoader().getResource(UPLOAD_DIR + "placeholder.jpg");
        PDImageXObject logo = JPEGFactory.createFromStream(document,
                new ByteArrayInputStream(res.getInputStream().readAllBytes()));

        contentStream.drawImage(logo, 40, 700, 150, 150);

        // write the dividers
        contentStream.moveTo(50, 700);
        contentStream.lineTo(550, 700);
        contentStream.stroke();

        // Write the invoice details to the document
        contentStream.beginText();
        contentStream.newLineAtOffset(40, 650);
        contentStream.showText("Owner:");
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText(invoice.getCustomerCompanyName());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText(invoice.getCustomerCompanyAddress() + " " + invoice.getCustomerZipCode());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("NIP: " + invoice.getCustomerCompanyNIP());
        contentStream.newLineAtOffset(+400, +60);
        contentStream.showText("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));


        contentStream.newLineAtOffset(-250, -100);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSizeTitle);
        contentStream.showText("INVOICE nr: " + invoice.getInvoiceId());
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(40, 500);

        contentStream.setFont(PDType1Font.HELVETICA, fontSizeContent);


        //Start writing the table like this:
        //Product name             Quantity   Price (Brutto)   VAT    Price (Netto)
        //Product 1                1          100              23%    77
        //Product 2                2          200              23%    154
        contentStream.showText("Product name");
        contentStream.newLineAtOffset(+100, 0);
        contentStream.showText("Quantity");
        contentStream.newLineAtOffset(+100, 0);
        contentStream.showText("Price (Brutto)");
        contentStream.newLineAtOffset(+100, 0);
        contentStream.showText("Price (Netto)");
        contentStream.newLineAtOffset(-300, -20);
        invoice.getItems().forEach(item -> {
            try {
                contentStream.showText(item.getProductName());
                contentStream.newLineAtOffset(+100, 0);
                contentStream.showText(item.getProductQuantity().toString());
                contentStream.newLineAtOffset(+100, 0);
                contentStream.showText(item.getProductPriceBrutto().toString());
                contentStream.newLineAtOffset(+100, 0);
                contentStream.showText("" + getTotalNetto(item.getProductPriceBrutto(), invoice.getVat()));
                contentStream.newLineAtOffset(-300, -20);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        BigDecimal brutto = getTotalBrutto(invoice);
        contentStream.newLineAtOffset(0, -50);
        contentStream.showText("Total price (Netto): " + getTotalNetto(brutto, invoice.getVat()));
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Total VAT: " + invoice.getVat() + "%");
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Total price (Brutto): " + brutto);

        contentStream.newLineAtOffset(0, -50);
        contentStream.showText( invoice.getCompanyName());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText(invoice.getCompanyAddress() + " " + invoice.getCompanyZipCode());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("NIP: " + invoice.getCompanyNIP());
        contentStream.newLineAtOffset(+350, +40);
        contentStream.showText("Payment method: " + invoice.getPaymentMethod());

        contentStream.endText();

        // Close the content stream
        contentStream.close();

        // Convert the PDF document to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
        byte[] pdfBytes = baos.toByteArray();


        // Return the PDF as a response entity with the appropriate headers
        return new InputStreamResource(new ByteArrayInputStream(pdfBytes));
    }

    private static BigDecimal getTotalBrutto(FInvoice invoice) {
        return invoice.getItems().stream()
                .map(FInvoiceItems::getProductPriceBrutto).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalNetto(BigDecimal priceBrutto, BigDecimal vat) {
        return priceBrutto.subtract(priceBrutto.multiply(vat.movePointLeft(2)));
    }
}
