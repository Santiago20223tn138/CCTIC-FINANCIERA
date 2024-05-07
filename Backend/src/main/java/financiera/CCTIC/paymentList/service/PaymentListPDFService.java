package financiera.CCTIC.paymentList.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import financiera.CCTIC.credito.model.ConsultaListaPorCobrar;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentListPDFService {

    @Autowired
    CreditoRepository creditoRepository;

    public byte[] generateListPayments(int id) throws IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.LETTER.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.setMargins(30f, 30f, 30f, 30f);
            BaseColor mainColor = new BaseColor(249,134,44);

            java.util.List<Object[]> multo = creditoRepository.consultaListaPorCobrar(id);
            List<ConsultaListaPorCobrar> listaDeConsultaListaPorCobrar = new ArrayList<>();

            for (Object[] arrayDatos : multo) {
                ConsultaListaPorCobrar consultaListaPorCobrar = new ConsultaListaPorCobrar();
                consultaListaPorCobrar.setContrato_id((int) arrayDatos[0]);
                consultaListaPorCobrar.setCliente((String) arrayDatos[1]);
                consultaListaPorCobrar.setPendiente((BigDecimal) arrayDatos[2]);
                consultaListaPorCobrar.setTelefono_1((String) arrayDatos[3]);
                consultaListaPorCobrar.setDia_pago((String) arrayDatos[4]);
                consultaListaPorCobrar.setPago_semanal((int) arrayDatos[5]);
                consultaListaPorCobrar.setDomicilio((String) arrayDatos[6]);
                consultaListaPorCobrar.setContrato((String) arrayDatos[7]);
                consultaListaPorCobrar.setFolio((String) arrayDatos[8]);
                consultaListaPorCobrar.setTotal((BigInteger) arrayDatos[9]);
                consultaListaPorCobrar.setPagado((BigDecimal) arrayDatos[10]);
                consultaListaPorCobrar.setPagos_realizados((BigInteger) arrayDatos[11]);
                consultaListaPorCobrar.setEs_fecha_valida((int) arrayDatos[13]);
                listaDeConsultaListaPorCobrar.add(consultaListaPorCobrar);
            }

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f);

            document.open();

            PdfPTable table1 = new PdfPTable(1);
            table1.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            table1.setWidthPercentage(100f);

            // Celda derecha
            PdfPCell cellRight = new PdfPCell(image);
            cellRight.setBorder(PdfPCell.NO_BORDER);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table1.addCell(cellRight);

            table1.writeSelectedRows(
                    0,
                    -1,
                    document.leftMargin(),
                    document.top() + 10, // Puedes ajustar la posición vertical según tus necesidades
                    writer.getDirectContent()
            );

            Paragraph infoClient = new Paragraph("Lista de pagos por cobrar",
                    new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD));
            infoClient.setSpacingAfter(-20f);
            document.add(infoClient);

            Chunk linea = new Chunk(new LineSeparator(1f, 100f,mainColor, Element.ALIGN_CENTER, 0f ));
            document.add(linea);
            Paragraph p1 = new Paragraph("");
            p1.setSpacingAfter(10);
            document.add(p1);

            PdfPTable table = new PdfPTable(8);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100f);
            table.setWidths(new float[]{5f, 17f, 10f, 12f, 12f, 10f, 25f, 9f});
            addHeaders(table);

            for (ConsultaListaPorCobrar item : listaDeConsultaListaPorCobrar) {
                addData(table, item);
            }

            document.add(table);





            document.close();

            return baos.toByteArray();
        }catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void addHeaders(PdfPTable table) {
        table.addCell(createHeaderCell("Id"));
        table.addCell(createHeaderCell("Cliente"));
        table.addCell(createHeaderCell("Pendiente"));
        table.addCell(createHeaderCell("Teléfono"));
        table.addCell(createHeaderCell("Día Pago"));
        table.addCell(createHeaderCell("Semanal"));
        table.addCell(createHeaderCell("Domicilio"));
        table.addCell(createHeaderCell("Pagado"));
    }

    private PdfPCell createHeaderCell(String headerText) {
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        font.setStyle(Font.BOLD);
        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph(headerText, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(5);
        cell.addElement(paragraph);
        cell.setBackgroundColor(new BaseColor(249,134,44));
        return cell;
    }


    private void addData(PdfPTable table, ConsultaListaPorCobrar item) {
        // Celda para el ID
        PdfPCell idCell = new PdfPCell(new Paragraph(String.valueOf(item.getContrato_id())));
        idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(idCell);

        // Celda para el Cliente
        PdfPCell clienteCell = new PdfPCell(new Paragraph(item.getCliente(), new Font(Font.FontFamily.HELVETICA, 9f)));
        table.addCell(clienteCell);

        // Celda para el Pendiente
        PdfPCell pendienteCell = new PdfPCell(new Paragraph(item.getPendiente().toString()));
        pendienteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pendienteCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pendienteCell);

        // Celda para el Teléfono
        PdfPCell telefonoCell = new PdfPCell(new Paragraph(item.getTelefono_1()));
        table.addCell(telefonoCell);

        // Celda para el Día de Pago
        PdfPCell diaPagoCell = new PdfPCell(new Paragraph(item.getDia_pago()));
        diaPagoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        diaPagoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(diaPagoCell);

        // Celda para el Pago Semanal
        PdfPCell pagoSemanalCell = new PdfPCell(new Paragraph(String.valueOf(item.getPago_semanal())));
        pagoSemanalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pagoSemanalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(pagoSemanalCell);

        // Celda para el Domicilio
        PdfPCell domicilioCell = new PdfPCell(new Paragraph(item.getDomicilio(), new Font(Font.FontFamily.HELVETICA, 9f)));
        table.addCell(domicilioCell);

        // Celda vacía
        PdfPCell emptyCell = new PdfPCell();
        Paragraph pagado = new Paragraph(item.getisEs_fecha_valida() == 1 ? "PAGADO" : "");
        emptyCell.addElement(pagado);
        table.addCell(emptyCell);
    }

    public ResponseEntity<Message> paymentList(int id) throws IOException{
        byte[] pdfBytes = generateListPayments(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "paymentReceipt.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }
}
