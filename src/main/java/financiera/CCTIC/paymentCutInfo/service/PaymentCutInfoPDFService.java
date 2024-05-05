package financiera.CCTIC.paymentCutInfo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import financiera.CCTIC.client.model.Cliente;
import financiera.CCTIC.cortepagos.model.Cortepagos;
import financiera.CCTIC.cortepagos_detalles.model.Cortepagos_detalles;
import financiera.CCTIC.cortepagos_detalles.repository.CortePagosRepository;
import financiera.CCTIC.cortepagos_detalles.repository.Cortepagos_detallesRepository;
import financiera.CCTIC.credito.model.ConsultaListaPorCobrar;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentCutInfoPDFService {

    @Autowired
    Cortepagos_detallesRepository cortepagos_detallesRepository;

    @Autowired
    CortePagosRepository cortePagosRepository;

    NumberFormat formato = NumberFormat.getCurrencyInstance();

    public byte[] generatePaymentCutInfo(int corteId) throws IOException{
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f) ;

            Optional<Cortepagos> cortepagos = cortePagosRepository.findById((long) corteId);
            Cortepagos corte = cortepagos.get();
            List<Cortepagos_detalles> detallesCorto = cortepagos_detallesRepository.findCortepagos_detallesByidcortepago(corte);

            // Listas para almacenar los objetos donde "pagado" es igual a 1 y 0, respectivamente
            List<Cortepagos_detalles> pagadoLista = new ArrayList<>();
            List<Cortepagos_detalles> noPagadoLista = new ArrayList<>();

            // Iterar sobre la lista de Cortepagos_detalles
            for (Cortepagos_detalles detalle : detallesCorto) {
                if (detalle.getPagado() == 1) {
                    pagadoLista.add(detalle);
                } else if (detalle.getPagado() == 0) {
                    noPagadoLista.add(detalle);
                }
            }

            Document document = new Document(PageSize.LETTER.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.setMargins(30f, 30f, 30f, 30f);
            BaseColor mainColor = new BaseColor(249,134,44);

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

            Paragraph infoClient = new Paragraph("Información del corte",
                    new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD));
            infoClient.setSpacingAfter(-20f);
            document.add(infoClient);

            Chunk linea = new Chunk(new LineSeparator(1f, 100f,mainColor, Element.ALIGN_CENTER, 0f ));
            document.add(linea);
            Paragraph p1 = new Paragraph("");
            p1.setSpacingAfter(10);
            document.add(p1);

            PdfPTable table = new PdfPTable(1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100f);

            addInfoTable(table, "Id", corte.getId()+"");
            addInfoTable(table, "Fecha", corte.getFecha()+"");
            addInfoTable(table, "Monto", formato.format(corte.getMonto()));
            document.add(table);

            Paragraph paragraphTitlePaid = new Paragraph();
            paragraphTitlePaid.add("Pagos realizados");
            paragraphTitlePaid.setFont(new Font(Font.FontFamily.HELVETICA, 25f, Font.BOLD));
            paragraphTitlePaid.setAlignment(Element.ALIGN_CENTER);
            paragraphTitlePaid.setSpacingBefore(10f);
            document.add(paragraphTitlePaid);

            if (!pagadoLista.isEmpty()){
                PdfPTable tablePaid = new PdfPTable(5);
                tablePaid.setSpacingBefore(10);
                tablePaid.setWidthPercentage(100f);
                tablePaid.setWidths(new float[]{5f,20f, 13f, 47f, 15});
                addHeaders(tablePaid, true);
                for (Cortepagos_detalles item: pagadoLista){
                    addData(tablePaid, item);
                }
                document.add(tablePaid);
            }else {
                document.add(msjEmptyList("No se realizaron pagos"));
            }




            Paragraph paragraphTitleNotPaid = new Paragraph();
            paragraphTitleNotPaid.add("Pagos faltantes");
            paragraphTitleNotPaid.setFont(new Font(Font.FontFamily.HELVETICA, 25f, Font.BOLD));
            paragraphTitleNotPaid.setAlignment(Element.ALIGN_CENTER);
            paragraphTitleNotPaid.setSpacingBefore(10f);
            document.add(paragraphTitleNotPaid);

            if (!noPagadoLista.isEmpty()){
                PdfPTable tableNotPaid = new PdfPTable(5);
                tableNotPaid.setSpacingBefore(10);
                tableNotPaid.setWidthPercentage(100f);
                tableNotPaid.setWidths(new float[]{5f,20f, 13f, 47f, 15});
                addHeaders(tableNotPaid, false);
                for (Cortepagos_detalles item: noPagadoLista){
                    addData(tableNotPaid, item);
                }
                document.add(tableNotPaid);
            }else {
                document.add(msjEmptyList("Todos los pagos se realizaron"));
            }



            document.close();
            return baos.toByteArray();
        }catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void addInfoTable(PdfPTable table, String header, String info){
        Paragraph paragraph = new Paragraph();
        Chunk chunkHeader = new Chunk(header + ": ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
        Chunk chunkinfo = new Chunk(info, new Font(Font.FontFamily.HELVETICA, 11f));
        paragraph.add(chunkHeader);
        paragraph.add(chunkinfo);
        table.addCell(paragraph);
    }

    private Paragraph msjEmptyList(String msj){
        Paragraph paragraph = new Paragraph(msj);
        paragraph.setFont(new Font(Font.FontFamily.HELVETICA, 11f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    private void addHeaders(PdfPTable table, Boolean paid) {
        table.addCell(createHeaderCell("Id"));
        table.addCell(createHeaderCell("Crédito"));
        table.addCell(createHeaderCell(paid ? "Pagado" : "Semanal"));
        table.addCell(createHeaderCell("Cliente"));
        table.addCell(createHeaderCell("Teléfono"));
    }

    private PdfPCell createHeaderCell(String headerText) {
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        font.setStyle(Font.BOLD);
        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph(headerText, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBackgroundColor(new BaseColor(249,134,44));
        return cell;
    }

    private void addData(PdfPTable table, Cortepagos_detalles item) {


        Cliente cliente = item.getIdcredito().getCliente();
        String nombre = cliente.getNombre() +" "+ cliente.getPaterno() +" "+ cliente.getMaterno();
        // Celda para el ID
        PdfPCell idCell = new PdfPCell(new Paragraph(String.valueOf(item.getIdcredito().getId())));
        idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(idCell);

        // Celda para el Credito
        PdfPCell creditoCell = new PdfPCell(new Paragraph(item.getIdcredito().getContrato()));
        creditoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        creditoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(creditoCell);

        // Celda para el Monto
        if (item.getId_hisotrico() != null){
            PdfPCell montoCell = new PdfPCell(new Paragraph(formato.format(item.getId_hisotrico().getMonto())));
            montoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            montoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(montoCell);
        }else {
            PdfPCell montoCell = new PdfPCell(new Paragraph(formato.format(item.getIdcredito().getPago_semanal())));
            montoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            montoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(montoCell);
        }

        // Celda para el Cliente
        PdfPCell clienteCell = new PdfPCell(new Paragraph(nombre/*, new Font(Font.FontFamily.HELVETICA, 9f)*/));
        table.addCell(clienteCell);

        // Celda para el Teléfono
        PdfPCell telefonoCell = new PdfPCell(new Paragraph(item.getIdcredito().getCliente().getTelefono_1()));
        telefonoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        telefonoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(telefonoCell);

    }

    public ResponseEntity<Message> paymentCutInfo(int corteId) throws IOException{
        byte[] pdfBytes = generatePaymentCutInfo(corteId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "paymentCutInfo.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }
}
