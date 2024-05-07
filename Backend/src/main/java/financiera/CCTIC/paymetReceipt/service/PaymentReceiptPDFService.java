package financiera.CCTIC.paymetReceipt.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import financiera.CCTIC.client.model.Cliente;
import financiera.CCTIC.credito.model.ConsultaListaPorCobrar;
import financiera.CCTIC.credito.repository.CreditoRepository;
import financiera.CCTIC.credito.service.CreditoService;
import financiera.CCTIC.historico_pagos.model.Historico_pagos;
import financiera.CCTIC.historico_pagos.service.historialPagosRepository;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentReceiptPDFService {
    @Autowired
    historialPagosRepository historialPagosRepository;
    public byte[] generatePaymentReceipt(Long id) throws IOException{
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            NumberFormat formato = NumberFormat.getCurrencyInstance();
            Optional<Historico_pagos> payment = historialPagosRepository.findById(id);
            Cliente client = payment.get().getCredito().getCliente();
            List<Historico_pagos> payments = historialPagosRepository.findAllByCreditoId(payment.get().getCredito().getId());
            int sumaMontos = 0;
            for (Historico_pagos historicoPagos : payments) {
                sumaMontos += historicoPagos.getMonto();
            }
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            BaseColor mainColor = new BaseColor(249, 134, 44);
            BaseColor bgTable = new BaseColor(242, 242, 242);

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f);

            String userPath = "images/user-icon.png";
            byte[] userBytes = new ClassPathResource(userPath).getInputStream().readAllBytes();
            Image iconImage = Image.getInstance(userBytes);
            iconImage.scaleToFit(10f, 10f);

            // Cargar y escalar la imagen icon-balance.png
            String balanceImagePath = "images/icon-balance.png";
            byte[] balanceBytes = new ClassPathResource(balanceImagePath).getInputStream().readAllBytes();
            Image balanceIcon = Image.getInstance(balanceBytes);
            balanceIcon.scaleToFit(10f, 10f);

// Cargar y escalar la imagen icon-credit.png
            String creditImagePath = "images/icon-credit.png";
            byte[] creditBytes = new ClassPathResource(creditImagePath).getInputStream().readAllBytes();
            Image creditIcon = Image.getInstance(creditBytes);
            creditIcon.scaleToFit(10f, 10f);

// Cargar y escalar la imagen icon-link.png
            String linkImagePath = "images/icon-link.png";
            byte[] linkBytes = new ClassPathResource(linkImagePath).getInputStream().readAllBytes();
            Image linkIcon = Image.getInstance(linkBytes);
            linkIcon.scaleToFit(10f, 10f);

// Cargar y escalar la imagen icon-money.png
            String moneyImagePath = "images/icon-money.png";
            byte[] moneyBytes = new ClassPathResource(moneyImagePath).getInputStream().readAllBytes();
            Image moneyIcon = Image.getInstance(moneyBytes);
            moneyIcon.scaleToFit(10f, 10f);

            String footerPath = "images/img-footer.png";
            byte[] footerBytes = new ClassPathResource(footerPath).getInputStream().readAllBytes();
            Image footerIcon = Image.getInstance(footerBytes);
            footerIcon.scaleToFit(30f, 30f);

            footerIcon.setAlignment(Element.ALIGN_CENTER);
            footerIcon.setSpacingAfter(-12);

            HeaderFooter headerFooter = new HeaderFooter(image, footerIcon);
            writer.setPageEvent(headerFooter);

            document.setMargins(50f, 50f, 50f, 50f);

            document.open();

            Paragraph title = new Paragraph("RECIBO DE PAGO", new Font(Font.FontFamily.HELVETICA, 17f, Font.BOLD));
            title.setSpacingAfter(-10f);
            document.add(title);
            Chunk linea = new Chunk(new LineSeparator(1f, 100f, mainColor, Element.ALIGN_CENTER, 0f));
            document.add(linea);
            document.add(new Paragraph(""));

            Paragraph paragraph1 = new Paragraph("Comprobante de la operación realizada el día de hoy, y que corresponde al pago semanal del crédito", new Font(Font.FontFamily.HELVETICA, 10f));
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setSpacingBefore(20);
            document.add(paragraph1);
            Paragraph paragraph2 = new Paragraph("Relacionado con la siguiente Información.", new Font(Font.FontFamily.HELVETICA, 10f));
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph2);

            PdfPTable tableClient = new PdfPTable(3);
            tableClient.setWidthPercentage(100);
            tableClient.setWidths(new float[]{5f, 15f, 80f});


            Paragraph pcliente = new Paragraph("CLIENTE", new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD));
            PdfPCell cellTitleClient = new PdfPCell(pcliente);
            //
            cellTitleClient.setBorder(PdfPCell.NO_BORDER);
            cellTitleClient.setBorderWidthRight(1f);
            cellTitleClient.setRightIndent(15);
            cellTitleClient.setBorderColorRight(mainColor);
            cellTitleClient.setBackgroundColor(bgTable);
            cellTitleClient.setFixedHeight(80);
            cellTitleClient.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTitleClient.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Celda para el icono en la esquina inferior izquierda
            PdfPCell iconCell = new PdfPCell();
            iconCell.setBorder(PdfPCell.NO_BORDER);
            iconCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            iconCell.setVerticalAlignment(Element.ALIGN_TOP);
            iconCell.setBackgroundColor(bgTable);
            iconCell.setPadding(5);

            iconCell.addElement(iconImage);

            tableClient.addCell(iconCell);
            tableClient.addCell(cellTitleClient);

            PdfPTable insideClient = new PdfPTable(1);
            PdfPCell cellInside1 = new PdfPCell();
            Paragraph paragraphName = new Paragraph();
            Chunk chunkNameLabel = new Chunk("  Titular de la Cuenta:   ", new Font(Font.FontFamily.HELVETICA, 11));
            Chunk chunkName = new Chunk(" "+client.getNombre()+" "+client.getPaterno()+" "+client.getMaterno(), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphName.add(chunkNameLabel);
            paragraphName.add(chunkName);
            cellInside1.setFixedHeight(40);
            cellInside1.addElement(paragraphName);
            cellInside1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellInside1.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda interna
            cellInside1.setBorderWidthBottom(1f);
            cellInside1.setBorderColorBottom(mainColor);
            PdfPCell cellInside2 = new PdfPCell();
            Paragraph paragraphNoClient = new Paragraph();
            Chunk chunkNoClientLabel = new Chunk("  No. Cliente:   ", new Font(Font.FontFamily.HELVETICA, 11));
            Chunk chunkNoClient = new Chunk(" "+client.getNumero_cliente(), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphNoClient.add(chunkNoClientLabel);
            paragraphNoClient.add(chunkNoClient);
            cellInside2.setFixedHeight(40);
            cellInside2.addElement(paragraphNoClient);
            cellInside2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellInside2.setBorder(PdfPCell.NO_BORDER);
            insideClient.addCell(cellInside1);
            insideClient.addCell(cellInside2);

            PdfPCell cellClientInfo = new PdfPCell(insideClient);
            cellClientInfo.setBorder(PdfPCell.NO_BORDER);
            cellClientInfo.setBackgroundColor(bgTable);
            tableClient.addCell(cellClientInfo);


            tableClient.setSpacingBefore(30);
            document.add(tableClient);

            // Crear tabla para crédito
            PdfPTable tableCredit = new PdfPTable(3);
            tableCredit.setWidthPercentage(100);
            tableCredit.setWidths(new float[]{5f, 15f, 80f});

// Celda para el icono en la esquina inferior izquierda
            PdfPCell iconCellCredit = new PdfPCell();
            iconCellCredit.setBorder(PdfPCell.NO_BORDER);
            iconCellCredit.setHorizontalAlignment(Element.ALIGN_LEFT);
            iconCellCredit.setVerticalAlignment(Element.ALIGN_TOP);
            iconCellCredit.setBackgroundColor(bgTable);
            iconCellCredit.setPadding(5);
            iconCellCredit.addElement(creditIcon);

// Celda para el título "CRÉDITO"
            Paragraph pCredit = new Paragraph("CRÉDITO", new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD));
            PdfPCell cellTitleCredit = new PdfPCell(pCredit);
            cellTitleCredit.setBorder(PdfPCell.NO_BORDER);
            cellTitleCredit.setBorderWidthRight(1f);
            cellTitleCredit.setRightIndent(15);
            cellTitleCredit.setBorderColorRight(mainColor);
            cellTitleCredit.setBackgroundColor(bgTable);
            cellTitleCredit.setFixedHeight(80);
            cellTitleCredit.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTitleCredit.setVerticalAlignment(Element.ALIGN_MIDDLE);

// Agregar las celdas a la tabla de crédito
            tableCredit.addCell(iconCellCredit);
            tableCredit.addCell(cellTitleCredit);

// Información interna de crédito para tabla de crédito
            PdfPTable insideCredit = new PdfPTable(1);
            insideCredit.setWidthPercentage(100);
            PdfPCell cellInside1Credit = new PdfPCell();
            Paragraph paragraphContractNumber = new Paragraph();
            Chunk chunkContractNumberLabel = new Chunk("  Número de Contrato:   ", new Font(Font.FontFamily.HELVETICA, 11));
            Chunk chunkContractNumber = new Chunk(" "+payment.get().getCredito().getContrato(), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphContractNumber.add(chunkContractNumberLabel);
            paragraphContractNumber.add(chunkContractNumber);
            cellInside1Credit.setFixedHeight(40);
            cellInside1Credit.addElement(paragraphContractNumber);
            cellInside1Credit.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellInside1Credit.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda interna
            cellInside1Credit.setBorderWidthBottom(1f);
            cellInside1Credit.setBorderColorBottom(mainColor);
            PdfPCell cellInside2Credit = new PdfPCell();
            Paragraph paragraphFolioNumber = new Paragraph();
            Chunk chunkFolioNumberLabel = new Chunk("  Número de Folio:   ", new Font(Font.FontFamily.HELVETICA, 11));
            Chunk chunkFolioNumber = new Chunk(" "+ payment.get().getCredito().getFolio(), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphFolioNumber.add(chunkFolioNumberLabel);
            paragraphFolioNumber.add(chunkFolioNumber);
            cellInside2Credit.setFixedHeight(40);
            cellInside2Credit.addElement(paragraphFolioNumber);
            cellInside2Credit.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellInside2Credit.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda interna
            insideCredit.addCell(cellInside1Credit);
            insideCredit.addCell(cellInside2Credit);

            PdfPCell cellCreditInfo = new PdfPCell(insideCredit);
            cellCreditInfo.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda externa
            cellCreditInfo.setBackgroundColor(bgTable);
            tableCredit.addCell(cellCreditInfo);

// Espaciado antes de la tabla de crédito
            tableCredit.setSpacingBefore(30);

// Agregar tabla de crédito al documento
            document.add(tableCredit);

// Crear tabla para balance
            PdfPTable tableBalance = new PdfPTable(3);
            tableBalance.setWidthPercentage(100);
            tableBalance.setWidths(new float[]{5f, 15f, 80f});

// Celda para el icono en la esquina inferior izquierda
            PdfPCell iconCellBalance = new PdfPCell();
            iconCellBalance.setBorder(PdfPCell.NO_BORDER);
            iconCellBalance.setHorizontalAlignment(Element.ALIGN_LEFT);
            iconCellBalance.setVerticalAlignment(Element.ALIGN_TOP);
            iconCellBalance.setBackgroundColor(bgTable);
            iconCellBalance.setPadding(5);
            iconCellBalance.addElement(balanceIcon);

// Celda para el título "BALANCE"
            Paragraph pBalance = new Paragraph("BALANCE", new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD));
            PdfPCell cellTitleBalance = new PdfPCell(pBalance);
            cellTitleBalance.setBorder(PdfPCell.NO_BORDER);
            cellTitleBalance.setBorderWidthRight(1f);
            cellTitleBalance.setRightIndent(15);
            cellTitleBalance.setBorderColorRight(mainColor);
            cellTitleBalance.setBackgroundColor(bgTable);
            cellTitleBalance.setFixedHeight(80);
            cellTitleBalance.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTitleBalance.setVerticalAlignment(Element.ALIGN_MIDDLE);

// Agregar las celdas a la tabla de balance
            tableBalance.addCell(iconCellBalance);
            tableBalance.addCell(cellTitleBalance);

// Información interna de balance para tabla de balance
            PdfPTable insideBalance = new PdfPTable(1);
            insideBalance.setWidthPercentage(100);
            PdfPCell cellInside1Balance = new PdfPCell();
            Paragraph paragraphInitialPayment = new Paragraph();
            Chunk chunkInitialPaymentLabel = new Chunk("  Pago Semananal:   ", new Font(Font.FontFamily.HELVETICA, 11));
            Chunk chunkInitialPayment = new Chunk(formato.format(payment.get().getMonto()), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphInitialPayment.add(chunkInitialPaymentLabel);
            paragraphInitialPayment.add(chunkInitialPayment);
            cellInside1Balance.setFixedHeight(40);
            cellInside1Balance.addElement(paragraphInitialPayment);
            cellInside1Balance.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellInside1Balance.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda interna
            cellInside1Balance.setBorderWidthBottom(1f);
            cellInside1Balance.setBorderColorBottom(mainColor);
            PdfPCell cellInside2Balance = new PdfPCell();
            Paragraph paragraphWeeklyPayment = new Paragraph();
            Chunk chunkWeeklyPaymentLabel = new Chunk("  Saldo Pendiente:   ", new Font(Font.FontFamily.HELVETICA, 11));
            Chunk chunkWeeklyPayment = new Chunk(formato.format(payment.get().getCredito().getMonto() - sumaMontos), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphWeeklyPayment.add(chunkWeeklyPaymentLabel);
            paragraphWeeklyPayment.add(chunkWeeklyPayment);
            cellInside2Balance.setFixedHeight(40);
            cellInside2Balance.addElement(paragraphWeeklyPayment);
            cellInside2Balance.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellInside2Balance.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda interna
            insideBalance.addCell(cellInside1Balance);
            insideBalance.addCell(cellInside2Balance);

            PdfPCell cellBalanceInfo = new PdfPCell(insideBalance);
            cellBalanceInfo.setBorder(PdfPCell.NO_BORDER); // No mostrar bordes en la celda externa
            cellBalanceInfo.setBackgroundColor(bgTable);
            tableBalance.addCell(cellBalanceInfo);

// Espaciado antes de la tabla de balance
            tableBalance.setSpacingBefore(30);

// Agregar tabla de balance al documento
            document.add(tableBalance);

            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(100);
            table2.setWidths(new float[]{50f, 50f});

            PdfPTable tableInside2 = new PdfPTable(1);
            tableInside2.setWidthPercentage(95);

            PdfPCell cellImporte = new PdfPCell();
            cellImporte.setBackgroundColor(bgTable);
            cellImporte.setBorder(Rectangle.NO_BORDER);
            Paragraph paragraphImporte = new Paragraph("IMPORTE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphImporte.setAlignment(Element.ALIGN_CENTER);
            cellImporte.addElement(paragraphImporte);
            cellImporte.addElement(linea);
            Paragraph paragraphCantidad = new Paragraph("Cantidad Pagada.", new Font(Font.FontFamily.HELVETICA, 10));
            paragraphCantidad.setSpacingBefore(5);
            paragraphCantidad.setIndentationLeft(10);
            cellImporte.addElement(paragraphCantidad);
            Paragraph paragraphAmount = new Paragraph(formato.format(payment.get().getMonto()), new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD));
            paragraphAmount.setSpacingBefore(20);
            paragraphAmount.setAlignment(Element.ALIGN_CENTER);
            paragraphAmount.setSpacingAfter(20);
            cellImporte.addElement(paragraphAmount);
            cellImporte.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellImporte.setRightIndent(10);

            PdfPTable tableIcon = new PdfPTable(2);
            tableIcon.setWidthPercentage(100);
            tableIcon.setWidths(new float[]{20f, 80f});
            tableIcon.setSpacingBefore(10);

            PdfPCell cellIconMoney = new PdfPCell();
            cellIconMoney.addElement(moneyIcon);
            cellIconMoney.setBorder(Rectangle.NO_BORDER);
            tableIcon.addCell(cellIconMoney);
            Paragraph paragraphDate = new Paragraph(payment.get().getFecha(), new Font(Font.FontFamily.HELVETICA, 8));
            paragraphDate.setAlignment(Element.ALIGN_RIGHT);
            paragraphDate.setIndentationRight(5);
            PdfPCell cellDate = new PdfPCell();
            cellDate.setBorder(Rectangle.NO_BORDER);
            cellDate.addElement(paragraphDate);
            tableIcon.addCell(cellDate);
            cellImporte.addElement(tableIcon);

            PdfPTable tableInside3 = new PdfPTable(1);
            tableInside3.setWidthPercentage(95);

            PdfPCell cellOperacion = new PdfPCell();
            cellOperacion.setBackgroundColor(bgTable);
            cellOperacion.setBorder(Rectangle.NO_BORDER);
            Paragraph paragraphOperacion = new Paragraph("OPERACIÓN", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
            paragraphOperacion.setAlignment(Element.ALIGN_CENTER);
            cellOperacion.addElement(paragraphOperacion);
            cellOperacion.addElement(linea);
            Paragraph paragraphFolio = new Paragraph("Folio.", new Font(Font.FontFamily.HELVETICA, 10));
            paragraphFolio.setSpacingBefore(5);
            paragraphFolio.setIndentationLeft(10);
            cellOperacion.addElement(paragraphFolio);
            Paragraph paragraphFolio2 = new Paragraph(payment.get().getFolio(), new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD));
            paragraphFolio2.setSpacingBefore(20);
            paragraphFolio2.setAlignment(Element.ALIGN_CENTER);
            paragraphFolio2.setSpacingAfter(20);
            cellOperacion.addElement(paragraphFolio2);
            cellOperacion.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellOperacion.setRightIndent(10);

            PdfPTable tableIconOp = new PdfPTable(2);
            tableIconOp.setWidthPercentage(100);
            tableIconOp.setWidths(new float[]{20f, 80f});
            tableIconOp.setSpacingBefore(10);

            PdfPCell cellLink = new PdfPCell();
            cellLink.addElement(linkIcon);
            cellLink.setBorder(Rectangle.NO_BORDER);
            tableIconOp.addCell(cellLink);
            Paragraph paragraphUbi = new Paragraph("Temixco, Morelos.", new Font(Font.FontFamily.HELVETICA, 8));
            paragraphUbi.setAlignment(Element.ALIGN_RIGHT);
            paragraphUbi.setIndentationRight(5);
            PdfPCell cellUbi = new PdfPCell();
            cellUbi.setBorder(Rectangle.NO_BORDER);
            cellUbi.addElement(paragraphUbi);
            tableIconOp.addCell(cellUbi);
            cellOperacion.addElement(tableIconOp);

            tableInside2.addCell(cellImporte);
            tableInside2.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cellInside = new PdfPCell();
            cellInside.setBorder(Rectangle.NO_BORDER);
            cellInside.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellInside.addElement(tableInside2);
            table2.addCell(cellInside);

            tableInside3.addCell(cellOperacion);
            tableInside3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell cellInsideOp = new PdfPCell();
            cellInsideOp.setBorder(Rectangle.NO_BORDER);
            cellInsideOp.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellInsideOp.addElement(tableInside3);
            table2.addCell(cellInsideOp);

            table2.setSpacingBefore(40);
            table2.setSpacingAfter(10);
            document.add(table2);



            document.close();

            return baos.toByteArray();
        }catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<Message> paymentReceiptPDF(Long id) throws IOException{

        byte[] pdfBytes = generatePaymentReceipt(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "paymentReceipt.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }


}
