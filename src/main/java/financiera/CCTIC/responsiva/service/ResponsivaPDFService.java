package financiera.CCTIC.responsiva.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.draw.LineSeparator;
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

@Service
@Transactional
public class ResponsivaPDFService {

    @Autowired
    CreditoRepository creditoRepository;

    public byte[] generateResponsiva(int idClient) throws IOException{
        Credito credito = creditoRepository.getByIdCliente(idClient);
        String clientName = credito.getCliente().getNombre() + " " +credito.getCliente().getPaterno()+" "+credito.getCliente().getMaterno();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            BaseColor mainColor = new BaseColor(249,134,44);
            document.setMargins(50f, 50f, 90f, 90f);
            document.open();

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f);

            PdfPTable table = new PdfPTable(2);
            PdfPTable tableFooter = new PdfPTable(2);
            table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            table.setWidthPercentage(100f);
            tableFooter.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            tableFooter.setWidthPercentage(100f);

            // Celda izquierda
            PdfPCell cellLeft = new PdfPCell(new Phrase("Carta responsiva de cliente.", new Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)));
            cellLeft.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cellLeft);

            // Celda derecha
            PdfPCell cellRight = new PdfPCell(image);
            cellRight.setBorder(PdfPCell.NO_BORDER);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellRight);

            table.writeSelectedRows(
                    0,
                    -1,
                    document.leftMargin(),
                    document.top() + 50, // Puedes ajustar la posición vertical según tus necesidades
                    writer.getDirectContent()
            );

            Paragraph dearClient = new Paragraph("Estimado cliente:",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            dearClient.setSpacingAfter(-10f);
            document.add(dearClient);

            Chunk linea = new Chunk(new LineSeparator(1f, 100f,mainColor, Element.ALIGN_CENTER, 0f ));
            document.add(linea);
            document.add(new Paragraph(""));

            Paragraph espacio = new Paragraph("");
            espacio.setSpacingAfter(20f);
            document.add(espacio);

            Paragraph bienvenida = new Paragraph();
            bienvenida.setAlignment(Element.ALIGN_JUSTIFIED);
            bienvenida.setSpacingAfter(10f);

            Chunk bienv1 = new Chunk(
                    "Por medio de la presente le damos la bienvenida al selecto grupo de clientes de ",
                    new Font(Font.FontFamily.HELVETICA, 11.f)
            );

            Chunk bienv2 = new Chunk(
                    "Finamor, ",
                    new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD)
            );

            Chunk bienv3 = new Chunk(
                    "de igual forma queremos dejar en claro algunos puntos que nos parecen importantes de resaltar, para que el cliente este enterado de  las RESPONSABILIDADES adquiridas en la relación.",
                    new Font(Font.FontFamily.HELVETICA, 11f)
            );

            bienvenida.add(bienv1);
            bienvenida.add(bienv2);
            bienvenida.add(bienv3);

            document.add(bienvenida);

            Paragraph responsabilidad = new Paragraph("Responsabilidades del cliente:",
                    new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD));
            responsabilidad.setSpacingAfter(5f);
            document.add(responsabilidad);

            List listaResponsabilidades = new List(true);

            listaResponsabilidades.setIndentationLeft(20);
            listaResponsabilidades.setIndentationRight(20);

            ListItem item1 = new ListItem("El cliente se compromete a pagar puntualmente los días acordados, por lo cual durante las primeras 16 semanas NO PODRÁ HABER RETRASO ALGUNO  O DE LO CONTRARIO EL CLIENTE ESTARÁ OBLIGADO A LA DEVOLUCION DEL MONTO TOTAL DE LA DEUDA.", new Font(Font.FontFamily.HELVETICA, 11f));
            item1.setAlignment(Element.ALIGN_JUSTIFIED);
            item1.setSpacingAfter(10f);
            ListItem item2 = new ListItem("Los pagos son SEMANALES Y COMPLETOS, SIN EXCEPCIÓN ALGUNA. En caso de existir algún inconveniente deberá ser notificado previamente al asesor.", new Font(Font.FontFamily.HELVETICA, 11f));
            item2.setAlignment(Element.ALIGN_JUSTIFIED);
            item2.setSpacingAfter(10f);
            ListItem item3 = new ListItem("El ser presta nombres ES UN DELITO FEDERAL, PENADO POR LA LEY.", new Font(Font.FontFamily.HELVETICA, 11f));
            item3.setAlignment(Element.ALIGN_JUSTIFIED);
            item3.setSpacingAfter(10f);
            ListItem item4 = new ListItem("Después de 4 semanas de no recibir el pago SU CUENTA SE INCREMENTARÁ CON LA APLICACIÓN DE INTERESES MORATORIOS QUE INCREMENTARAN  DIARIAMENTE.", new Font(Font.FontFamily.HELVETICA, 11f));
            item4.setAlignment(Element.ALIGN_JUSTIFIED);
            item4.setSpacingAfter(10f);
            ListItem item5 = new ListItem("En caso de cambio de domicilio DEBERÁ NOTIFICAR AL ASESOR Y PROPORCIONAR LA NUEVA DIRECCIÓN, ASÍ  COMO EL NUEVO COMPROBANTE DE DOMICILIO. ", new Font(Font.FontFamily.HELVETICA, 11f));
            item5.setAlignment(Element.ALIGN_JUSTIFIED);
            item5.setSpacingAfter(10f);
            ListItem item6 = new ListItem("El crédito no cuenta con ningún tipo de seguro, por lo que los pagos DEBERAN VERSE REFLEJADOS SEMANALMENTE SIN CONTRATIEMPOS", new Font(Font.FontFamily.HELVETICA, 11f));
            item6.setAlignment(Element.ALIGN_JUSTIFIED);
            item6.setSpacingAfter(10f);
            ListItem item7 = new ListItem("Los documentos firmados de manera digital cuentan con la misma validez que los documentos impresos y firmados manualmente y yo como cliente los acepto y me comprometo a someterme a lo que en ellos se indica reconociéndolos como documentos oficiales.", new Font(Font.FontFamily.HELVETICA, 11f));
            item7.setAlignment(Element.ALIGN_JUSTIFIED);
            item7.setSpacingAfter(10f);

            listaResponsabilidades.add(item1);
            listaResponsabilidades.add(item2);
            listaResponsabilidades.add(item3);
            listaResponsabilidades.add(item4);
            listaResponsabilidades.add(item5);
            listaResponsabilidades.add(item6);
            listaResponsabilidades.add(item7);

            document.add(listaResponsabilidades);

            Paragraph infoPresente = new Paragraph("Esperamos mantenga presente esta información para evitar multas o sanciones.",
                    new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD));
            infoPresente.setSpacingAfter(5f);
            document.add(infoPresente);

            Paragraph espacio2 = new Paragraph("");
            espacio2.setSpacingAfter(60f);
            document.add(espacio2);

            Paragraph nombreCliente = new Paragraph(
                    clientName.toUpperCase(),
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            nombreCliente.setAlignment(Element.ALIGN_CENTER);
            document.add(nombreCliente);

            Paragraph firmaElec = new Paragraph("Firma.", new Font(Font.FontFamily.HELVETICA, 11f));
            firmaElec.setAlignment(Element.ALIGN_CENTER);
            document.add(firmaElec);

            document.close();
            return baos.toByteArray();
        }catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Message> responsivaPDF(int id) throws IOException {

        byte[] pdfBytes = generateResponsiva(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "contract.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }
}
