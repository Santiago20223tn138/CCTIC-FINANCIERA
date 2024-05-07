package financiera.CCTIC.paymentRecord.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import financiera.CCTIC.client.model.Cliente;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.credito.repository.CreditoRepository;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@Transactional
public class PaymentRecordPDFService {

    @Autowired
    CreditoRepository creditoRepository;
    public byte[] generatePaymentRecord(int idClient) throws IOException{
        Credito credito = creditoRepository.getByIdCliente(idClient);
        Cliente cliente = credito.getCliente();
        LocalDate initialDate = LocalDate.parse(credito.getFecha());
        LocalDate finalDate = initialDate.plusWeeks(credito.getSemanas());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.LETTER.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f);

            String circulosPath = "images/circulos.png";
            byte[] circulosBytes = new ClassPathResource(circulosPath).getInputStream().readAllBytes();
            Image imageCirculo = Image.getInstance(circulosBytes);
            imageCirculo.scaleToFit(60f, 60f);

            String halfTable1 = "images/halfTable1.png";
            byte[] halfTable1Bytes = new ClassPathResource(halfTable1).getInputStream().readAllBytes();
            Image image1 = Image.getInstance(halfTable1Bytes);
            image1.setAlignment(Element.ALIGN_MIDDLE);
            image1.scaleToFit(327f, 327f);

            String halfTable2 = "images/halfTable2.png";
            byte[] halfTable2Bytes = new ClassPathResource(halfTable2).getInputStream().readAllBytes();
            Image image2 = Image.getInstance(halfTable2Bytes);
            image2.scaleToFit(550f, 510f);
            image2.setAlignment(Element.ALIGN_CENTER);

            HeaderFooter headerFooter = new HeaderFooter(image, imageCirculo);
            writer.setPageEvent(headerFooter);

            document.setMargins(30f, 30f, 50f, 30f);
            document.open();

            // Crear una tabla para dividir la página en dos partes
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            // Segunda mitad de la página con el título centrado
            PdfPCell secondHalf = new PdfPCell();

            //secondHalf.setBorder(PdfPTable.NO_BORDER);
            Paragraph paragraph1 = new Paragraph("Préstamo personal.",
                    new Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD));
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setSpacingAfter(10f);
            secondHalf.addElement(paragraph1);
            Paragraph paragraph = new Paragraph("Registro de pagos semanales de crédito", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(10f);
            secondHalf.addElement(paragraph);
            secondHalf.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            secondHalf.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            secondHalf.setBorder(PdfPCell.NO_BORDER);


//            secondHalf.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//            secondHalf.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

            Paragraph paragraphName = new Paragraph();
            Chunk phraseName = new Chunk("Cliente: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkName = new Chunk(cliente.getNombre()+" "+cliente.getPaterno()+" "+cliente.getMaterno(), new Font(Font.FontFamily.HELVETICA, 11f));
            paragraphName.add(phraseName);
            paragraphName.add(chunkName);
            paragraphName.setSpacingBefore(15f);
            secondHalf.addElement(paragraphName);

            Paragraph paragraphContract = new Paragraph();
            Chunk phraseContract = new Chunk("Contrato: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkContract = new Chunk(credito.getContrato(), new Font(Font.FontFamily.HELVETICA, 11f));
            paragraphContract.add(phraseContract);
            paragraphContract.add(chunkContract);
            paragraphContract.setSpacingBefore(15f);
            secondHalf.addElement(paragraphContract);

            PdfPTable tableInside = new PdfPTable(2);
            tableInside.setWidthPercentage(100);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(PdfPCell.NO_BORDER);
            PdfPCell cell2 = new PdfPCell();
            cell2.setBorder(PdfPCell.NO_BORDER);

            Paragraph paragraphStartDate = new Paragraph();
            Chunk phraseStartDate = new Chunk("Fecha Inicio: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkStartDate = new Chunk(credito.getFecha(), new Font(Font.FontFamily.HELVETICA, 11f));
            paragraphStartDate.add(phraseStartDate);
            paragraphStartDate.add(chunkStartDate);

            Paragraph paragraphFinalDate = new Paragraph();
            Chunk phraseFinalDate = new Chunk("Fecha Fin: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkFinalDate = new Chunk(finalDate.toString(), new Font(Font.FontFamily.HELVETICA, 11f));
            paragraphFinalDate.add(phraseFinalDate);
            paragraphFinalDate.add(chunkFinalDate);


            cell1.addElement(paragraphStartDate);
            cell2.addElement(paragraphFinalDate);

            PdfPTable tableInsideImg = new PdfPTable(1);
            tableInsideImg.setWidthPercentage(100);
            PdfPCell cellImg1 = new PdfPCell();
            cellImg1.setBorder(PdfPCell.NO_BORDER);
            cellImg1.addElement(image1);

            tableInside.addCell(cell1);
            tableInside.addCell(cell2);
            tableInside.setSpacingAfter(15f);
            tableInside.setSpacingBefore(15f);
            secondHalf.addElement(tableInside);

            tableInsideImg.addCell(cellImg1);

            secondHalf.addElement(tableInsideImg);


            table.addCell(secondHalf);

            // Primera mitad de la página
            PdfPCell firstHalf = new PdfPCell();
            firstHalf.setBorder(PdfPCell.NO_BORDER);
            firstHalf.addElement(image2);
            table.addCell(firstHalf);



            // Agregar la tabla al documento
            document.add(table);
            // Dibujar el rectángulo punteado
            PdfContentByte canvas = writer.getDirectContent();
            canvas.saveState();
            canvas.setRGBColorStroke(249,134,44);
            canvas.setLineDash(3, 3); // Establecer el patrón punteado
            canvas.rectangle(30, 455, 350, 25); // Definir las coordenadas y dimensiones del rectángulo
            canvas.stroke();
            canvas.restoreState();

            PdfContentByte canvasContract = writer.getDirectContent();
            canvasContract.saveState();
            canvasContract.setRGBColorStroke(249,134,44);
            canvasContract.setLineDash(3, 3);
            canvasContract.rectangle(30, 422, 200, 25);
            canvasContract.stroke();
            canvasContract.restoreState();

            PdfContentByte canvasDateStart = writer.getDirectContent();
            canvasDateStart.saveState();
            canvasDateStart.setRGBColorStroke(249,134,44);
            canvasDateStart.setLineDash(3, 3);
            canvasDateStart.rectangle(30, 389, 170, 25);
            canvasDateStart.stroke();
            canvasDateStart.restoreState();

            PdfContentByte canvasDateFinal = writer.getDirectContent();
            canvasDateFinal.saveState();
            canvasDateFinal.setRGBColorStroke(249,134,44);
            canvasDateFinal.setLineDash(3, 3);
            canvasDateFinal.rectangle(211, 389, 170, 25);
            canvasDateFinal.stroke();
            canvasDateFinal.restoreState();

            document.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Message> paymentRecordPDF(int id) throws IOException {

        byte[] pdfBytes = generatePaymentRecord(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "registropago.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }
}
