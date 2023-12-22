package ru.clevertec.servlet.task.pdfcreator.eventpage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.servlet.task.util.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PDFPagination extends PdfPageEventHelper {

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		PdfPTable footer = new PdfPTable(2);
		try {
			footer.setWidths(new int[]{10, 20});
			footer.setWidthPercentage(50);

			footer.setTotalWidth(527);
			footer.setLockedWidth(true);
			footer.getDefaultCell()
				  .setFixedHeight(30);
			footer.getDefaultCell()
				  .setBorder(Rectangle.TOP);
			footer.getDefaultCell()
				  .setBorderColor(BaseColor.BLUE);
			footer.getDefaultCell()
				  .setHorizontalAlignment(Element.ALIGN_LEFT);
			footer.addCell(new Phrase(String.format("Created %s", getDateTimeForPagination()),
					new Font(Font.FontFamily.HELVETICA, 8)));

			PdfPCell totalPageCount = new PdfPCell();
			totalPageCount.setBorder(Rectangle.TOP);
			totalPageCount.setBorderColor(BaseColor.BLUE);
			footer.getDefaultCell()
				  .setHorizontalAlignment(Element.ALIGN_RIGHT);
			footer.addCell(new Phrase(String.format("Page %d", writer.getPageNumber() - 1),
					new Font(Font.FontFamily.HELVETICA, 8)));

			PdfContentByte canvas = writer.getDirectContent();
			canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
			footer.writeSelectedRows(0, -1, 34, 20, canvas);
			canvas.endMarkedContentSequence();
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	private static String getDateTimeForPagination() {
		return LocalDateTime.now()
							.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN_FOR_PAGINATION));
	}

}
