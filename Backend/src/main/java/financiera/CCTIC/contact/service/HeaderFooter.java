package financiera.CCTIC.contact.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
    private Image image;
    private Image imageCirculo;

    public HeaderFooter(Image image, Image imageCirculo) {
        this.image = image;
        this.imageCirculo = imageCirculo;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(2);
        PdfPTable tableFooter = new PdfPTable(2);
        table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        table.setWidthPercentage(100f);
        tableFooter.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        tableFooter.setWidthPercentage(100f);

        // Celda izquierda
        PdfPCell cellLeft = new PdfPCell(new Phrase("Préstamo Personal. ", new Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)));
        cellLeft.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cellLeft);

        // Celda derecha
        PdfPCell cellRight = new PdfPCell(image);
        cellRight.setBorder(PdfPCell.NO_BORDER);
        cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellRight);

        // Footer
        // Celda izquierda
        PdfPCell cellLeftFooter = new PdfPCell(imageCirculo);
        cellLeftFooter.setBorder(PdfPCell.NO_BORDER);
        cellLeftFooter.setHorizontalAlignment(Element.ALIGN_LEFT);
        tableFooter.addCell(cellLeftFooter);

        // Celda derecha
        PdfPCell cellRightFooter = new PdfPCell(new Phrase("Pagina " + writer.getPageNumber() + " de 3"));
        cellRightFooter.setBorder(PdfPCell.NO_BORDER);
        cellRightFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tableFooter.addCell(cellRightFooter);

        // Posicionar la tabla en el encabezado
        table.writeSelectedRows(
                0,
                -1,
                document.leftMargin(),
                document.top() + 50, // Puedes ajustar la posición vertical según tus necesidades
                writer.getDirectContent()
        );

        tableFooter.writeSelectedRows(
                0,
                -1,
                document.leftMargin(),
                document.bottom() - 30, // Puedes ajustar la posición vertical según tus necesidades
                writer.getDirectContent()
        );
    }
}
