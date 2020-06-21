package extras;

import Admin.Admin;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pdf{
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,  Font.BOLD);
        public void utilJTableToPdf(File pdfNewFile, String title, TableView table){
            try {
                // We create the document and set the file name.
                // Creamos el documento e indicamos el nombre del fichero.
                Document document = new Document();
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                document.open();
                document.addTitle(title);
                document.addSubject(title);
                document.addKeywords(title);
                document.addAuthor("PhoneSystem");
                document.addCreator("PhoneSystem");
                Anchor anchor = new Anchor(title, categoryFont);
                anchor.setName(title);

                Chapter catPart = new Chapter(new Paragraph(anchor), 1);

                PdfPTable tablep = new PdfPTable(table.getColumns().size());

                PdfPCell columnHeader;
                for (int column = 0; column < table.getColumns().size(); column++) {
                    Phrase phrase=new Phrase(((TableColumn)table.getColumns().get(column)).getText());
                    phrase.getFont().setSize(7);
                    columnHeader = new PdfPCell(phrase);
                    columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);

                    tablep.addCell(columnHeader);

                }
                tablep.setHeaderRows(1);
                if(tablep.getNumberOfColumns()==17){
                    float[] medidaCeldas = {2.40f,5.70f,8.70f,4.70f,5.50f,5.0f,5.0f,6.0f,6.0f,6.0f,6.0f,6.0f,6.0f,6.0f,6.0f,7.0f,7.0f};
                    tablep.setWidths(medidaCeldas);}
                System.out.println(tablep.getNumberOfColumns());
                for (int row = 0; row < table.getItems().size(); row++) {

                    for (int column = 0; column < table.getColumns().size(); column++) {
                        ObservableList<String> e= FXCollections.observableArrayList();
                        e.addAll((ObservableList<String>)table.getItems().get(row));
                        Paragraph columna1 = new Paragraph(e.get(column));
                        columna1.getFont().setSize(6);
                        tablep.addCell(columna1);
                    }
                }
                catPart.add(tablep);

                document.add(catPart);

                document.close();
                //error.setText("Inventario exportado");
            } catch (DocumentException documentException) {

                //error.setText("Error al exportar inventario ");
            }

        }
}
