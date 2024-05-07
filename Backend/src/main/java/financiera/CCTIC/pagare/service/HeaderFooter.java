package financiera.CCTIC.pagare.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
    private Image image;

    public HeaderFooter(Image image) {
        this.image = image;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        table.setWidthPercentage(100f);
        image.setSpacingBefore(40f);


        // Celda derecha
        PdfPCell cellRight = new PdfPCell(image);
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
