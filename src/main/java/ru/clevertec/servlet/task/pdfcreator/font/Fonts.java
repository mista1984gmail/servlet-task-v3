package ru.clevertec.servlet.task.pdfcreator.font;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import ru.clevertec.servlet.task.util.Constants;

import java.io.IOException;

public class Fonts {

	private static BaseFont baseFont = loadBaseFont(Constants.PATH_TO_TIMES_ROMAN_FONT);
	public final static Font FONT_TITLE = new Font(baseFont, 15, Font.NORMAL, BaseColor.BLACK);
	public final static Font FONT_LINE_SIZE_12 = new Font(baseFont, 12, Font.NORMAL, BaseColor.BLACK);
	public final static Font FONT_LINE_SIZE_8 = new Font(baseFont, 8, Font.NORMAL, BaseColor.BLACK);

	private static BaseFont loadBaseFont(String fontName) {
		BaseFont baseFont = null;
		try {
			baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		return baseFont;
	}

}
