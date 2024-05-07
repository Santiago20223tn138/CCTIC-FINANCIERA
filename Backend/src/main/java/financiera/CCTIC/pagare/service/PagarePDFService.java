package financiera.CCTIC.pagare.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import financiera.CCTIC.client.model.Cliente;
import financiera.CCTIC.credito.model.Credito;
import financiera.CCTIC.credito.repository.CreditoRepository;
import financiera.CCTIC.utils.Message;
import financiera.CCTIC.utils.NumberToWordsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@Transactional
public class PagarePDFService {

    @Autowired
    CreditoRepository creditoRepository;

    public byte[] generatePagare(int idClient) throws IOException{

        Credito credito = creditoRepository.getByIdCliente(idClient);
        Cliente cliente = credito.getCliente();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat formato = NumberFormat.getNumberInstance();
        String numeroLetras = NumberToWordsConverter.convertir(credito.getMonto());

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Date fecha = formatoEntrada.parse(credito.getFecha());
            String day = new SimpleDateFormat("dd", new Locale("es", "ES")).format(fecha);
            String month = new SimpleDateFormat("MMMM", new Locale("es", "ES")).format(fecha);
            String year = new SimpleDateFormat("yy", new Locale("es", "ES")).format(fecha);

            Document document = new Document(new Rectangle(612, 396));
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f);

            BaseColor colorGrey1 = new BaseColor(121, 121, 121);
            document.setMargins(30f, 30f, 30f, 10f);
            document.open();

            HeaderFooter headerFooter = new HeaderFooter(image);
            writer.setPageEvent(headerFooter);

            PdfContentByte canvas = writer.getDirectContent();
            canvas.saveState();
            canvas.setRGBColorFill(128, 128, 128);
            canvas.roundRectangle(25, 337, 80, 35,5);
            canvas.fill();
            canvas.restoreState();

            PdfContentByte canvasNo = writer.getDirectContent();
            canvasNo.saveState();
            canvasNo.setRGBColorFill(236, 236, 236);
            canvasNo.roundRectangle(190, 338, 83, 28,5);
            canvasNo.fill();
            canvasNo.restoreState();

            PdfContentByte canvasFolio = writer.getDirectContent();
            canvasFolio.saveState();
            canvasFolio.setRGBColorFill(236, 236, 236);
            canvasFolio.roundRectangle(345, 338, 100, 28,5);
            canvasFolio.fill();
            canvasFolio.restoreState();

            PdfContentByte canvasAmount = writer.getDirectContent();
            canvasAmount.saveState();
            canvasAmount.setRGBColorFill(236, 236, 236);
            canvasAmount.roundRectangle(280, 273, 70, 18,5);
            canvasAmount.fill();
            canvasAmount.restoreState();

            PdfContentByte canvasAmountLetter = writer.getDirectContent();
            canvasAmountLetter.saveState();
            canvasAmountLetter.setRGBColorFill(236, 236, 236);
            canvasAmountLetter.roundRectangle(360, 273, 120, 18,5);
            canvasAmountLetter.fill();
            canvasAmountLetter.restoreState();

            PdfContentByte canvasDay = writer.getDirectContent();
            canvasDay.saveState();
            canvasDay.setRGBColorFill(236, 236, 236);
            canvasDay.roundRectangle(420, 230, 20, 18,5);
            canvasDay.fill();
            canvasDay.restoreState();

            PdfContentByte canvasMonth = writer.getDirectContent();
            canvasMonth.saveState();
            canvasMonth.setRGBColorFill(236, 236, 236);
            canvasMonth.roundRectangle(455, 230, 60, 18,5);
            canvasMonth.fill();
            canvasMonth.restoreState();

            PdfContentByte canvasYear = writer.getDirectContent();
            canvasYear.saveState();
            canvasYear.setRGBColorFill(236, 236, 236);
            canvasYear.roundRectangle(37, 210, 80, 18,5);
            canvasYear.fill();
            canvasYear.restoreState();

            PdfContentByte canvasDay2 = writer.getDirectContent();
            canvasDay2.saveState();
            canvasDay2.setRGBColorFill(236, 236, 236);
            canvasDay2.roundRectangle(205, 140, 20, 18,5);
            canvasDay2.fill();
            canvasDay2.restoreState();

            PdfContentByte canvasMonth2 = writer.getDirectContent();
            canvasMonth2.saveState();
            canvasMonth2.setRGBColorFill(236, 236, 236);
            canvasMonth2.roundRectangle(243, 140, 70, 18,5);
            canvasMonth2.fill();
            canvasMonth2.restoreState();

            PdfContentByte canvasYear2 = writer.getDirectContent();
            canvasYear2.saveState();
            canvasYear2.setRGBColorFill(236, 236, 236);
            canvasYear2.roundRectangle(362, 140, 15, 18,5);
            canvasYear2.fill();
            canvasYear2.restoreState();

            PdfContentByte canvasCity = writer.getDirectContent();
            canvasCity.saveState();
            canvasCity.setRGBColorFill(236, 236, 236);
            canvasCity.roundRectangle(455, 140, 130, 18,5);
            canvasCity.fill();
            canvasCity.restoreState();

            PdfContentByte canvasInfo = writer.getDirectContent();
            canvasInfo.saveState();
            canvasInfo.setRGBColorFill(236, 236, 236);
            canvasInfo.roundRectangle(25, 25, 565, 100,5);
            canvasInfo.fill();
            canvasInfo.restoreState();


            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);


            PdfPCell cell1 = new PdfPCell();
            Paragraph title = new Paragraph();
            Chunk chunkTitle = new Chunk("Pagaré", new Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD, BaseColor.WHITE));
            title.add(chunkTitle);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(PdfPCell.NO_BORDER);

            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.addElement(title);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell();
            Paragraph no = new Paragraph();
            Chunk chunkNoLabel = new Chunk("No.   ", new Font(Font.FontFamily.HELVETICA, 10,  Font.BOLD, colorGrey1));
            Chunk chunkNo = new Chunk(credito.getContrato(), new Font(Font.FontFamily.HELVETICA, 11));
            no.add(chunkNoLabel);
            no.add(chunkNo);
            cell2.addElement(no);
            cell2.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell();
            Paragraph folio = new Paragraph();
            Chunk chunkFolioLabel = new Chunk("Foilio.    ", new Font(Font.FontFamily.HELVETICA, 10,  Font.BOLD, colorGrey1));
            Chunk chunkFolio = new Chunk(credito.getFolio(), new Font(Font.FontFamily.HELVETICA, 11));
            folio.add(chunkFolioLabel);
            folio.add(chunkFolio);
            cell3.addElement(folio);
            cell3.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell3);

            PdfPCell cellLogo = new PdfPCell();
            cellLogo.addElement(new Paragraph(""));
            cellLogo.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cellLogo);

            document.add(table);

            PdfPTable tablep1 = new PdfPTable(1);
            tablep1.setWidthPercentage(100);

            PdfPCell cellp1 = new PdfPCell();
            Paragraph paragraph1 = new Paragraph("", new Font(Font.FontFamily.HELVETICA, 10));
            paragraph1.add("Por este pagaré me obligo incondicionalmente a pagar a la orden de Financiera Morelia o (a cualquier otra persona que " +
                    "cuente con el presente pagaré en lo subsecuente denominado el \"Tenedor\") en cualquier domicilio que el tenedor señale " +
                    "al deudor para hacer el pago por la suma de $   "+ formato.format(credito.getMonto()) +".00         (   "+numeroLetras+" pesos               00/100 M.N.) en una sola " +
                    "exhibición.");
            paragraph1.setSpacingBefore(20f);
            paragraph1.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph1.setIndentationLeft(7);
            paragraph1.setIndentationRight(7);
            cellp1.addElement(paragraph1);
            cellp1.setBorder(PdfPCell.NO_BORDER);
            tablep1.addCell(cellp1);
            tablep1.setSpacingBefore(20f);
            document.add(tablep1);


            PdfPTable tablep2 = new PdfPTable(1);
            tablep2.setWidthPercentage(100);

            PdfPCell cellp2 = new PdfPCell();
            Paragraph paragraph2 = new Paragraph("", new Font(Font.FontFamily.HELVETICA, 10));
            paragraph2.add("El presente pagaré tendrá vencimiento a día fijo por lo que deberá ser cubierto el día         de                      del año                               ");
            paragraph2.setSpacingBefore(10f);
            paragraph2.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph2.setIndentationLeft(7);
            paragraph2.setIndentationRight(7);
            cellp2.addElement(paragraph2);
            cellp2.setBorder(PdfPCell.NO_BORDER);
            tablep2.addCell(cellp2);
            tablep2.setSpacingBefore(10f);
            tablep2.setSpacingAfter(20);
            document.add(tablep2);

            Paragraph paragraph3 = new Paragraph("", new Font(Font.FontFamily.HELVETICA, 10));
            paragraph3.add("El presente pagaré podrá ser transmitido por el Tenedor ya sea por endoso, sesión ordinaria o cualquier otra forma " +
                    "permitida por la ley para su bobro y no se requerirá protesto previo.");
            paragraph3.setSpacingBefore(10f);
            paragraph3.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph3.setIndentationLeft(7);
            paragraph3.setIndentationRight(7);
            document.add(paragraph3);

            PdfPTable tablep4 = new PdfPTable(1);
            tablep4.setWidthPercentage(100);
            PdfPCell cellp4 = new PdfPCell();

            Paragraph paragraph4 = new Paragraph("", new Font(Font.FontFamily.HELVETICA, 10));
            paragraph4.add("El presente pagaré se suscribe el día   "+day+ "   de   "+month+"           del año 20 "+year+"   en la ciudad de Temixco, Morelos");
            paragraph4.setSpacingBefore(10f);
            paragraph4.setIndentationLeft(7);
            paragraph4.setIndentationRight(7);
            cellp4.addElement(paragraph4);
            cellp4.setBorder(PdfPCell.NO_BORDER);
            tablep4.addCell(cellp4);
            tablep4.setSpacingBefore(10f);
            tablep4.setSpacingAfter(20);
            document.add(tablep4);

            PdfPTable tableInfo = new PdfPTable(1);
            tableInfo.setWidthPercentage(100);
            PdfPCell cellInfo = new PdfPCell();
            cellInfo.setBorder(PdfPCell.NO_BORDER);

            Paragraph paragraphInfo =  new Paragraph("Información del deudor", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            cellInfo.addElement(paragraphInfo);
            Paragraph paragraphName = new Paragraph();
            Chunk chunkNameLabel = new Chunk("Nombre: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkName = new Chunk(cliente.getNombre() +" "+cliente.getPaterno()+" "+cliente.getMaterno(), new Font(Font.FontFamily.HELVETICA, 9));
            paragraphName.add(chunkNameLabel);
            paragraphName.add(chunkName);
            cellInfo.addElement(paragraphName);

            Paragraph paragraphAddress = new Paragraph();
            Chunk chunkAddressLabel = new Chunk("Dirección: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkAddress = new Chunk(cliente.getDomicilio(), new Font(Font.FontFamily.HELVETICA, 9));
            paragraphAddress.add(chunkAddressLabel);
            paragraphAddress.add(chunkAddress);
            cellInfo.addElement(paragraphAddress);

            Paragraph paragraphSign = new Paragraph("Firma del deudor", new Font(Font.FontFamily.HELVETICA, 10));
            paragraphSign.setAlignment(Element.ALIGN_RIGHT);
            cellInfo.addElement(paragraphSign);

            Paragraph paragraphPhone = new Paragraph();
            Chunk chunkPhoneLabel = new Chunk("Teléfono: ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
            Chunk chunkPhone = new Chunk(cliente.getTelefono_1(), new Font(Font.FontFamily.HELVETICA, 9));
            paragraphPhone.add(chunkPhoneLabel);
            paragraphPhone.add(chunkPhone);
            cellInfo.addElement(paragraphPhone);

            tableInfo.addCell(cellInfo);
            document.add(tableInfo);

            document.close();
            return baos.toByteArray();
        }catch (DocumentException e){
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Message> pagarePDF(int id) throws IOException{
        byte[] pdfBytes = generatePagare(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "pagare.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }
}
