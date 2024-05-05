package financiera.CCTIC.paymentRecord.service;

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
        table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        table.setWidthPercentage(100f);
        imageCirculo.setSpacingBefore(15f);
        image.setSpacingBefore(15f);

        // Celda izquierda
        PdfPCell cellLeft = new PdfPCell(image);
        cellLeft.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cellLeft);

        // Celda derecha
        PdfPCell cellRight = new PdfPCell(imageCirculo);
        cellRight.setBorder(PdfPCell.NO_BORDER);
        cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellRight);

        // Calcular la posición vertical del encabezado con un margen superior
        float topMargin = document.top() + 50; // Puedes ajustar el margen superior según tus necesidades

        // Posicionar la tabla en el encabezado con margen superior
        table.writeSelectedRows(
                0,
                -1,
                document.leftMargin(),
                topMargin,
                writer.getDirectContent()
        );
    }
}
