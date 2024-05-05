package financiera.CCTIC.contact.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
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
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@Transactional
public class ContractPDFService {

    @Autowired
    CreditoRepository creditoRepository;

    public byte[] generateContract(int idClient) throws IOException {
        Credito credito = creditoRepository.getByIdCliente(idClient);
        Cliente cliente = credito.getCliente();
        NumberFormat formato = NumberFormat.getCurrencyInstance();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Date fecha = formatoEntrada.parse(credito.getFecha());
            String fechaFormateada = formatoSalida.format(fecha);

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            String logoPath = "images/logo_financiera.png";
            byte[] logoBytes = new ClassPathResource(logoPath).getInputStream().readAllBytes();
            Image image = Image.getInstance(logoBytes);
            image.scaleToFit(40f, 40f);

            String circulosPath = "images/circulos.png";
            byte[] circulosBytes = new ClassPathResource(circulosPath).getInputStream().readAllBytes();
            Image imageCirculo = Image.getInstance(circulosBytes);
            imageCirculo.scaleToFit(60f, 60f);


            HeaderFooter headerFooter = new HeaderFooter(image, imageCirculo);
            writer.setPageEvent(headerFooter);

            BaseColor mainColor = new BaseColor(249,134,44);

            document.setMargins(50f, 50f, 90f, 90f);
            document.open();

            Paragraph date = new Paragraph(fechaFormateada, new Font(Font.FontFamily.HELVETICA, 9.7f));
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            Paragraph infoClient = new Paragraph("Información del cliente",
                    new Font(Font.FontFamily.HELVETICA, 14.2f, Font.BOLD));
            infoClient.setSpacingAfter(-10f);
            document.add(infoClient);

            Chunk linea = new Chunk(new LineSeparator(1f, 100f,mainColor, Element.ALIGN_CENTER, 0f ));
            document.add(linea);
            document.add(new Paragraph(""));

            addSection(document, "Nombre", cliente.getNombre() + " "+cliente.getPaterno()+" "+cliente.getMaterno());
            addSection(document, "Domicilio", cliente.getDomicilio());
            addSection(document, "Teléfono", cliente.getTelefono_1());

            Paragraph espacio = new Paragraph("");
            espacio.setSpacingAfter(30f);
            document.add(espacio);

            Paragraph infoConvenio = new Paragraph("Información general del convenio", new Font(Font.FontFamily.HELVETICA, 14.2f, Font.BOLD));
            infoConvenio.setSpacingAfter(-10f);
            document.add(infoConvenio);

            document.add(linea);

            Paragraph space = new Paragraph("");
            space.setSpacingAfter(20f);
            document.add(space);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100f);
            tabla.setWidths(new float[]{24f, 23f, 23f, 30f});

            tableHead(tabla, "No.Contrato", credito.getContrato(), mainColor);
            tableHead(tabla, "No. Folio.", credito.getFolio(), mainColor);
            tableHead(tabla, "No. Cliente.", cliente.getNumero_cliente(), mainColor);
            tableHead(tabla, "No.Sucursal.", "17", mainColor);

            PdfPCell cell5 = new PdfPCell();
            cell5.setBorderColor(mainColor);
            cell5.setPadding(8f);
            Phrase montoPhrase = new Phrase();
            montoPhrase.add(new Chunk("Monto entregado al cliente \n", new  Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)));
            montoPhrase.add(new Chunk("\n"));
            montoPhrase.add(new Chunk(formato.format(credito.getMonto())+" M.N.", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)));
            cell5.addElement(montoPhrase);
            tabla.addCell(cell5);

            PdfPCell cell6 = new PdfPCell();
            cell6.setPadding(8f);
            cell6.setBorderColor(mainColor);
            Phrase plazoSem = new Phrase();
            plazoSem.add(new Chunk("Plazo del préstamo en semanas.\n",
                    new Font (Font.FontFamily.HELVETICA, 12f, Font.BOLD)));
            plazoSem.add(new Chunk("\n"));
            Paragraph weeklyTerm = new Paragraph(credito.getSemanas()+"", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            weeklyTerm.setAlignment(Element.ALIGN_CENTER);
            Paragraph cellParagraph = new Paragraph();
            cellParagraph.add(weeklyTerm);
            cellParagraph.setAlignment(Element.ALIGN_CENTER);
            cell6.addElement(plazoSem);
            cell6.addElement(cellParagraph);
            tabla.addCell(cell6);

            Paragraph spaceTable = new Paragraph("");
            spaceTable.setSpacingAfter(5f);

            PdfPCell cell7 = new PdfPCell();
            cell7.setPadding(8f);
            cell7.setBorderColor(mainColor);
            Paragraph montoSemanal = new Paragraph();

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD);
            montoSemanal.add(new Chunk("Monto semanal.\n", boldFont));
            montoSemanal.add(new Chunk("\n"));

            montoSemanal.add(new Chunk("Pago inicial mínimo:\n", boldFont));
            montoSemanal.add(new Chunk(formato.format(credito.getPago_inicial())+" M.N.\n", boldFont));
            montoSemanal.add(new Chunk("\n"));

            montoSemanal.add(new Chunk("Pago semanal:\n", boldFont));
            montoSemanal.add(new Chunk(formato.format(credito.getPago_semanal())+" M.N.\n", boldFont));

            Paragraph spacingBefore = new Paragraph("\n");
            spacingBefore.setSpacingBefore(5f);
            montoSemanal.add(spacingBefore);

            cell7.addElement(montoSemanal);
            tabla.addCell(cell7);

            PdfPCell cell8 = new PdfPCell();
            cell8.setPadding(8F);
            cell8.setBorderColor(mainColor);
            Phrase montoPagar = new Phrase();
            montoPagar.add(new Chunk("Monto por Pagar. (Incluyendo cargos por servicio)\n",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)));
            montoPagar.add(new Chunk("\n"));
            montoPagar.add(new Chunk(formato.format(credito.getMonto())+" M.N.", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)));
            cell8.addElement(montoPagar);
            tabla.addCell(cell8);

            PdfPCell cell9 = new PdfPCell();
            cell9.setPadding(8f);
            cell9.setBorderColor(mainColor);
            Phrase diaPago = new Phrase();
            diaPago.add(
                    new Chunk(
                            "Día acordado para realizar el pago.\n",
                            new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
                    )
            );
            diaPago.add(new Chunk("\n"));
            diaPago.add(new Chunk(new LineSeparator(2f, 70f, BaseColor.BLACK, Element.ALIGN_CENTER, 0f)));
            cell9.addElement(diaPago);
            tabla.addCell(cell9);

            PdfPCell cell10 = new PdfPCell();
            cell10.setPadding(8f);
            cell10.setBorderColor(mainColor);
            Phrase frecPago = new Phrase();
            frecPago.add(
                    new Chunk(
                            "Frecuencia de pago.\n",
                            new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
                    )
            );
            frecPago.add(new Chunk("\n"));
            Paragraph chunkSemanal =
                    new Paragraph("Semanal", new Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL));
            chunkSemanal.setAlignment(Element.ALIGN_CENTER);
            Paragraph cellParagraphSemanal = new Paragraph();
            cellParagraphSemanal.add(chunkSemanal);
            cellParagraphSemanal.setAlignment(Element.ALIGN_CENTER);
            cell10.addElement(frecPago);
            cell10.addElement(cellParagraphSemanal);
            tabla.addCell(cell10);

            PdfPCell cell11 = new PdfPCell();
            cell11.setPadding(8f);
            cell11.setBorderColor(mainColor);
            Phrase pagoInicial = new Phrase();
            pagoInicial.add(
                    new Chunk(
                            "Pago Inicial Entregado.\n",
                            new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
                    )
            );
            pagoInicial.add(new Chunk("\n"));
            pagoInicial.add(new Chunk("$ "));
            pagoInicial.add(
                    new Chunk(
                            new LineSeparator(
                                    2f,
                                    70f,
                                    BaseColor.BLACK,
                                    Element.ALIGN_CENTER,
                                    0f
                            )
                    )
            );
            cell11.addElement(pagoInicial);
            tabla.addCell(cell11);

            PdfPCell cell12 = new PdfPCell();
            cell12.setPadding(8f);
            cell12.setBorderColor(mainColor);

            Paragraph montoPendiente = new Paragraph(
                    "Monto pendiente por Pagar. (Después del pago Inicial)",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            montoPendiente.setSpacingAfter(10f);

            montoPendiente.add(new Chunk("\n"));
            montoPendiente.add(new Chunk("\n", new Font(Font.FontFamily.HELVETICA, 2f)));
            montoPendiente.add(new Chunk("$ "));
            montoPendiente.add(
                    new Chunk(
                           new LineSeparator(
                                    2f,
                                    70f,
                                    BaseColor.BLACK,
                                    Element.ALIGN_CENTER,
                                    0f
                            )
                    )
            );

            cell12.addElement(montoPendiente);
            tabla.addCell(cell12);

            tabla.setSpacingAfter(10f);
            document.add(tabla);

            Paragraph descripcion = new Paragraph();
            descripcion.setAlignment(Element.ALIGN_JUSTIFIED);
            descripcion.setSpacingAfter(10f);

            Chunk desc1 = new Chunk(
                    "CONVENIO DE PAGO POR PRESTAMO QUE SE CELEBRA POR FINANCIERA MORELIA S.A. de  C.V., EN ADELANTE “FINAMOR”, Y LA PERSONA CUYO NOMBRE APARECE EN EL APARTADO DE",
                    new Font(Font.FontFamily.HELVETICA, 11.2f)
            );

            Chunk desc2 = new Chunk(
                    " Información de cliente,",
                    new Font(Font.FontFamily.HELVETICA, 11.2f, Font.BOLD)
            );

            Chunk desc3 = new Chunk(
                    "EN ADELANTE EL “CLIENTE”, A QUIENES EN CONJUNTO SE LES  DENOMINARÁ LAS “PARTES”.",
                    new Font(Font.FontFamily.HELVETICA, 11.2f)
            );

            descripcion.add(desc1);
            descripcion.add(desc2);
            descripcion.add(desc3);

            document.add(descripcion);

            Paragraph primera = new Paragraph();
            primera.setSpacingAfter(10f);

            Chunk prim1 = new Chunk("PRIMERA.-", new Font(Font.FontFamily.HELVETICA, 11.2f, Font.BOLD));
            Chunk prim2 = new Chunk(
                    "Como motivo de este contrato Finamor brindará al Cliente en calidad de préstamo, la cantidad identificada en la columna ",
                    new Font(Font.FontFamily.HELVETICA, 11.2f)
            );
            Chunk prim3 = new Chunk(
                    "“Monto entregado al cliente.” ",
                    new Font(Font.FontFamily.HELVETICA, 11.2f, Font.BOLD)
            );
            Chunk prim4 = new Chunk("Detallado en la sección ", new Font(Font.FontFamily.HELVETICA, 11.2f));
            Chunk prim5 = new Chunk(
                    "“Información general del convenio”, ",
                    new Font(Font.FontFamily.HELVETICA, 11.2f, Font.BOLD)
            );
            Chunk prim6 = new Chunk(
                    "obligándose el Cliente a pagar a Finamor el monto identificado como ",
                    new Font(Font.FontFamily.HELVETICA, 11.2f)
            );
            Chunk prim7 = new Chunk(
                    "“Monto Total por Pagar.”",
                    new Font(Font.FontFamily.HELVETICA, 11.2f, Font.BOLD)
            );
            Chunk prim8 = new Chunk(
                    ", de acuerdo al número de pagos semanales y modalidades establecidas en dicha sección.",
                    new Font(Font.FontFamily.HELVETICA, 11.2f)
            );

            primera.add(prim1);
            primera.add(prim2);
            primera.add(prim3);
            primera.add(prim4);
            primera.add(prim5);
            primera.add(prim6);
            primera.add(prim7);
            primera.add(prim8);

            document.add(primera);

            Paragraph segunda = new Paragraph();
            segunda.setSpacingAfter(10f);
            segunda.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk seg1 = new Chunk("SEGUNDA.-", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk seg2 = new Chunk(
                    " El Cliente manifiesta su aceptación para obligarse a los términos y condiciones del presente Contrato, el uso de firmas electrónicas, digitales, numéricas, alfanuméricas, mediante metodologías biométricas o de cualquier otra forma y que dichos medios alternativos de firma  o consentimiento y los documentos en donde sean aplicados dichos consentimientos, serán  considerados válidos e irrefutables para todos los efectos legales, de conformidad con los  artículos que de manera enunciativa más no limitativa se indican: 1803 y 1834 Bis del Código  Civil Federal, 80, 89, 1061 Bis y los demás relativos y aplicables del Código de Comercio, así  como la NOM-151-SCFI-2016 vigente, en vez de manifestar su voluntad a través de una firma  autógrafa. De tal forma, las Partes por este acto, reconocen y aceptan que la firma del Contrato  será mediante firma electrónica, (en adelante “firma”) reconociendo que esta pertenece de  manera exclusiva a la Parte que la genera, por lo que reconocen su fuerza, equivalencia e  integridad produciendo todas las consecuencias legales que tiene su firma autógrafa, y que en  caso de disputa acerca de la misma, se obligan a reconocer su validez, integridad, eficacia y no  repudio para todos los efectos legales a que haya lugar",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            segunda.add(seg1);
            segunda.add(seg2);

            document.add(segunda);

            Paragraph tercera = new Paragraph();
            tercera.setSpacingAfter(10f);
            tercera.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk ter1 = new Chunk("TERCERA.-", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk ter2 = new Chunk(
                    "Finamor declara haber entregado con anterioridad a este acto en concepto de  préstamo al cliente quien reconoce haberla recibido a su plena conformidad, la cantidad descrita  en la sección ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk ter3 = new Chunk(
                    "Monto entregado al cliente ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk ter4 = new Chunk(
                    "como concepto de préstamo personal, lo cual se  llevará a cabo en el domicilio del Cliente por conducto de cualquier representante, empleado o  agente autorizado por Finamor (en adelante “representante Finamor”) y/o por cualquier otro  medio autorizado por Finamor.",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            tercera.add(ter1);
            tercera.add(ter2);
            tercera.add(ter3);
            tercera.add(ter4);

            document.add(tercera);

            Paragraph cuarta = new Paragraph();
            cuarta.setSpacingAfter(10f);
            cuarta.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk cuarta1 = new Chunk("CUARTA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk cuarta2 = new Chunk(
                    "El Cliente podrá disponer del préstamo en efectivo, mediante cheque nominativo  emitido por una institución bancaria autorizada, órdenes de pago y/o por cualquier otro medio  financiero o electrónico autorizado.",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            cuarta.add(cuarta1);
            cuarta.add(cuarta2);

            document.add(cuarta);

            Paragraph quinta = new Paragraph();
            quinta.setSpacingAfter(10f);
            quinta.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk quinta1 = new Chunk("QUINTA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk quinta2 = new Chunk(
                    "Por las partes se acuerda que los pagos se realizarán semanalmente en el día  acordado en la sección ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk quinta3 = new Chunk(
                    "Día acordado para realizar el pago ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk quinta4 = new Chunk("descrito en la tabla ", new Font(Font.FontFamily.HELVETICA, 12f));
            Chunk quinta5 = new Chunk(
                    "Información  general del convenio, ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk quinta6 =
                    new Chunk("y se utilizarán para cubrir el ", new Font(Font.FontFamily.HELVETICA, 12f));
            Chunk quinta7 = new Chunk(
                    "Monto entregado al cliente, ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk quinta8 = new Chunk(
                    "descrito en  la misma tabla y que el capital prestado ha de devolverse en un plazo máximo de 33 semanas,  a contar desde la firma del presente contrato, acordando que los montos semanales a cubrirse  deberán ser los descritos en la sección ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk quinta9 = new Chunk(
                    "Información general del convenio ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk quinta10 = new Chunk(
                    "considerando los  montos semanales, días acordados y plazo del préstamo ahí descrito, no obstante, el cliente podrá cubrir de forma anticipada el capital pendiente en cualquier momento para completar el ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk quinta11 =
                    new Chunk(" Monto Total por Pagar.", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));

            quinta.add(quinta1);
            quinta.add(quinta2);
            quinta.add(quinta3);
            quinta.add(quinta4);
            quinta.add(quinta5);
            quinta.add(quinta6);
            quinta.add(quinta7);
            quinta.add(quinta8);
            quinta.add(quinta9);
            quinta.add(quinta10);
            quinta.add(quinta11);

            document.add(quinta);

            Paragraph sexta = new Paragraph();
            sexta.setSpacingAfter(10f);
            sexta.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk sexta1 = new Chunk("SEXTA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk sexta2 = new Chunk(
                    "Cada pago realizado por el Cliente, Finamor a través de su representante deberá  entregar un recibo que ampare la operación. Una vez recibido el pago, el representante de  Finamor lo registrará en el sistema, posterior a ello el Cliente y representante Finamor firmarán de aceptación sobre la tarjeta de pagos, además de uno de los medios digitales con los que se  generará su folio único de pago que deberá ser anotado en la tarjeta de pagos proporcionada  al cliente, de igual forma en el transcurso de esa semana deberá llegar su recibo de pago digital  al cliente al número de teléfono o correo que se descritos en la sección ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk sexta3 =
                    new Chunk("Información del Cliente.", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));

            sexta.add(sexta1);
            sexta.add(sexta2);
            sexta.add(sexta3);
            document.add(sexta);

            Paragraph septima = new Paragraph();
            septima.setSpacingAfter(10f);
            septima.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk septima1 = new Chunk("SEPTIMA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk septima2 = new Chunk(
                    "En todo caso de incumplimiento por parte del cliente de cualquiera de las  obligaciones de pago a su cargo derivado de este contrato, imputables al cliente o al representante de Finamor en el que no se haya recibido el pago, bajo ninguna circunstancia se  entenderá que Finamor renuncia a dicho pago. Por tal motivo, la cantidad que se haya dejado  de recibir deberá ser entregada a Finamor junto con el siguiente pago semanal. Por lo anterior,  se entenderá que el Cliente incurre en mora cuando no realice el pago puntual de cualquiera de  los pagos semanales establecidos en el presente Contrato. En caso de incumplimiento del pago  total y puntual de cada pago semanal, las partes acuerdan que el ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk septima3 =
                    new Chunk("Monto Total por Pagar", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk septima4 = new Chunk(
                    " será  considerado inmediatamente como devengado y pagadero a solicitud de Finamor. En caso de  cualquier cambio en la información para localizar al cliente, el cliente deberá notificarlo por  escrito a Finamor, y en caso de no hacerlo, se obliga a reembolsar a Finamor, todos los gastos  en que haya incurrido en localización, incluyendo gastos judiciales y/o extrajudiciales en que  incurra Finamor. Finamor estará facultado para reportar al Cliente ante el Buró de Crédito o ante  cualquier sociedad de información crediticia autorizada, por cualquier falta de pago. ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            septima.add(septima1);
            septima.add(septima2);
            septima.add(septima3);
            septima.add(septima4);

            document.add(septima);

            Paragraph octava = new Paragraph();
            octava.setSpacingAfter(10f);
            octava.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk octava1 = new Chunk("OCTAVA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk octava2 = new Chunk(
                    "Toda la información personal que el cliente proporcione a Finamor con motivo de la  celebración de este acuerdo, quedará resguardada por Finamor y podrá ser utilizada  únicamente para el llevar a cabo las operaciones que Finamor considere necesarias para el  cumplimiento de este convenio.",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            octava.add(octava1);
            octava.add(octava2);
            document.add(octava);

            Paragraph novena = new Paragraph();
            novena.setSpacingAfter(10f);
            novena.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk novena1 = new Chunk("NOVENA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk novena2 = new Chunk(
                    "Toda modificación del presente contrato deberá, para ser válida, constar por escrito,  debidamente firmado por ambas partes.",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            novena.add(novena1);
            novena.add(novena2);
            document.add(novena);

            Paragraph decima = new Paragraph();
            decima.setSpacingAfter(10f);
            decima.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk decima1 = new Chunk("DECIMA.- ", new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD));
            Chunk decima2 = new Chunk(
                    "Las partes intervinientes, con expresa renuncia al fuero que pudiera corresponderles,  acuerdan para la solución de todo litigio, discrepancia, cuestión o reclamación resultantes de la  ejecución o interpretación del presente contrato y relacionados con él, directa o indirectamente,  someterse a los juzgados y tribunales del lugar en el que tiene domicilio la sucursal de Finamor.",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            decima.add(decima1);
            decima.add(decima2);
            document.add(decima);

            Paragraph textoAceptacionCliente = new Paragraph();
            textoAceptacionCliente.setSpacingAfter(10f);
            textoAceptacionCliente.setAlignment(Element.ALIGN_JUSTIFIED);

            Chunk txtAc1 = new Chunk(
                    "Al firmar el presente documento el cliente acepta y certifica haber recibido el monto indicado en  la sección ",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );
            Chunk txtAc2 = new Chunk(
                    "información general del contrato ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk txtAc3 = new Chunk("en la columna ", new Font(Font.FontFamily.HELVETICA, 12f));
            Chunk txtAc4 = new Chunk(
                    "monto entregado al cliente, ",
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            Chunk txtAc5 = new Chunk(
                    "además de entender a detalle las cláusulas del mismo, que fueron explicadas por el  representante Finamor previas a la firma del contrato.",
                    new Font(Font.FontFamily.HELVETICA, 12f)
            );

            textoAceptacionCliente.add(txtAc1);
            textoAceptacionCliente.add(txtAc2);
            textoAceptacionCliente.add(txtAc3);
            textoAceptacionCliente.add(txtAc4);
            textoAceptacionCliente.add(txtAc5);

            document.add(textoAceptacionCliente);

            // Agregar línea de firma
            Chunk lineaFirma = new Chunk(
                    new LineSeparator(1f, 30f, BaseColor.BLACK, Element.ALIGN_CENTER, 0f)
            );
            document.add(lineaFirma);
            document.add(new Paragraph(""));

            // Agregar texto "Firma electrónica"
            Paragraph firmaElec = new Paragraph("Firma electrónica.", new Font(Font.FontFamily.HELVETICA, 11.2f));
            firmaElec.setAlignment(Element.ALIGN_CENTER);
            document.add(firmaElec);

            String name = cliente.getNombre() + " " + cliente.getPaterno() + " " + cliente.getMaterno();

            Paragraph nombreCliente = new Paragraph(
                    name.toUpperCase(),
                    new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            );
            nombreCliente.setAlignment(Element.ALIGN_CENTER);
            document.add(nombreCliente);

            document.close();
            writer.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addSection(Document document, String label, String text) throws DocumentException {
        document.add(new Phrase(label + ": ", new Font(Font.FontFamily.HELVETICA, 11.2f, Font.BOLD)));
        document.add(new Chunk(text, new Font(Font.FontFamily.HELVETICA, 9.7f)));
        document.add(new Paragraph(""));
    }

    private static  void tableHead(PdfPTable tabla, String title, String argument, BaseColor mainColor){
        Phrase headP = new Phrase();
        headP.add(new Chunk(title + "\n", new Font(Font.FontFamily.HELVETICA, 9.7f, Font.BOLD)));
        headP.add(new Chunk(argument+ "\n", new Font(Font.FontFamily.HELVETICA, 9.7f, Font.BOLD)));
        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(mainColor);
        cell.addElement(headP);
        cell.setPadding(8f);
        tabla.addCell(cell);

    }



    public ResponseEntity<Message> contractPDF(int id) throws IOException {

        byte[] pdfBytes = generateContract(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "contract.pdf");

        return new ResponseEntity<>(new Message("Pdf Generado", false, pdfBytes), HttpStatus.OK);
    }


}

