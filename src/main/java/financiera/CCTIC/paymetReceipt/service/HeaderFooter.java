package financiera.CCTIC.paymetReceipt.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class HeaderFooter extends PdfPageEventHelper {
    private Image image;

    private Image imageCirculo;

    public HeaderFooter(Image image, Image imageCirculo) {
        this.image = image;
        this.imageCirculo = imageCirculo;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        BaseColor mainColor = new BaseColor(249, 134, 44);
        Chunk linea = new Chunk(new LineSeparator(1f, 100f, mainColor, Element.ALIGN_CENTER, 0f));

        PdfPTable table = new PdfPTable(1);
        PdfPTable tableFooter = new PdfPTable(1);
        table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        table.setWidthPercentage(100f);
        tableFooter.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        tableFooter.setWidthPercentage(100f);

        // Celda derecha
        PdfPCell cellRight = new PdfPCell(image);
        cellRight.setBorder(PdfPCell.NO_BORDER);
        cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellRight);

        // Footer

        // Celda derecha
        PdfPCell cellRightFooter = new PdfPCell();
        cellRightFooter.addElement(linea);
        cellRightFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellRightFooter.addElement(imageCirculo);
        cellRightFooter.addElement(linea);
        cellRightFooter.setBorder(PdfPCell.NO_BORDER);
        cellRightFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableFooter.addCell(cellRightFooter);

        // Posicionar la tabla en el encabezado
        table.writeSelectedRows(
                0,
                -1,
                document.leftMargin(),
                document.top() + 30, // Puedes ajustar la posición vertical según tus necesidades
                writer.getDirectContent()
        );

        tableFooter.writeSelectedRows(
                0,
                -1,
                document.leftMargin(),
                document.bottom() + 90, // Puedes ajustar la posición vertical según tus necesidades
                writer.getDirectContent()
        );
    }
}
