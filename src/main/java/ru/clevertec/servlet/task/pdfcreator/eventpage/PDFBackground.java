package ru.clevertec.servlet.task.pdfcreator.eventpage;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.servlet.task.util.Constants;

import java.io.IOException;

public class PDFBackground extends PdfPageEventHelper {

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		Image background = null;
		try {
			background = Image.getInstance(Constants.PATH_TO_BACKGROUND);
		} catch (BadElementException | IOException e) {
			throw new RuntimeException(e);
		}
		float width = document.getPageSize()
							  .getWidth();
		float height = document.getPageSize()
							   .getHeight();
		try {
			writer.getDirectContentUnder()
				  .addImage(background, width, 0, 0, height, 0, 0);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

}
