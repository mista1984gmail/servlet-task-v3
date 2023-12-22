package ru.clevertec.servlet.task.pdfcreator.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.servlet.task.entity.dto.ClientDto;
import ru.clevertec.servlet.task.pdfcreator.PDFWriter;
import ru.clevertec.servlet.task.pdfcreator.eventpage.PDFBackground;
import ru.clevertec.servlet.task.pdfcreator.eventpage.PDFPagination;
import ru.clevertec.servlet.task.pdfcreator.font.Fonts;
import ru.clevertec.servlet.task.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFWriterImpl implements PDFWriter {

	/**
	 * Сохраняет список Клиентов в pdf файл
	 * Задает фон страницы, а также
	 * создает на каждой странице дату создания документа и нумерацию страниц
	 *
	 * @param clients список Клиентов
	 * @param pathForSave путь куда сохранить pdf
	 * @param fileName имя файла
	 */
	@Override
	public void writeClientsToPDF(List<ClientDto> clients, String pathForSave, String fileName) {
		Document document = new Document(PageSize.A4, 5, 5, 150, 50);

		try {
			File directory = new File(pathForSave);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			String fileNameForSave = "";
			if (fileName == null || fileName.isEmpty()) {
				fileNameForSave = "all_clients_info_" + getDateTimeForFileName();
			} else {
				fileNameForSave = fileName;
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream((
					pathForSave + "/" + fileNameForSave + ".pdf")));
			writer.setPageEvent(new PDFBackground());
			writer.setPageEvent(new PDFPagination());
			document.setPageCount(1);

			document.open();

			Paragraph title = new Paragraph(
					Constants.TITLE_INFO_CLIENTS, Fonts.FONT_TITLE);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			Paragraph p = new Paragraph(" ");
			document.add(p);

			PdfPTable table = new PdfPTable(7);
			table.setTotalWidth(500);
			float widths[] = {30, 70, 70, 100, 70, 70, 90};

			table.setWidths(widths);
			table.setHeaderRows(1);

			PdfPCell cell = null;
			for (int i = 0; i < Constants.FIELDS_CLIENT_DTO.size(); i++) {
				cell = new PdfPCell(new Phrase(Constants.FIELDS_CLIENT_DTO.get(i)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new BaseColor(31, 236, 229));
				table.addCell(cell);
			}

			for (int i = 0; i < clients.size(); i++) {
				cell = new PdfPCell();
				cell.addElement(new Phrase(String.valueOf(i + 1), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Phrase(clients.get(i)
												  .getFirstName(), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Phrase(clients.get(i)
												  .getLastName(), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Phrase(clients.get(i)
												  .getEmail(), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Phrase(clients.get(i)
												  .getTelephone(), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Phrase(getDateForPDF(clients.get(i)
																.getBirthday()), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);

				cell = new PdfPCell();
				cell.addElement(new Phrase(getDateTimeForPDF(clients.get(i)
																	.getRegistrationDate()), Fonts.FONT_LINE_SIZE_8));
				table.addCell(cell);
			}
			document.add(table);
			document.close();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Сохраняет Клиента в pdf файл
	 * Задает фон страницы, а также
	 * создает на странице дату создания документа и номер страницы
	 *
	 * @param client Клиент
	 * @param pathForSave путь куда сохранить pdf
	 * @param fileName имя файла
	 */
	@Override
	public void writeClientToPDF(ClientDto client, String pathForSave, String fileName) {
		Document document = new Document(PageSize.A4, 36, 72, 150, 50);

		try {
			File directory = new File(pathForSave);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			String fileNameForSave = "";
			if (fileName == null || fileName.isEmpty()) {
				fileNameForSave =  client.getFirstName() + client.getLastName() + "_info_" + getDateTimeForFileName();
			} else {
				fileNameForSave = fileName;
			}

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream((
					pathForSave + "/" + fileNameForSave + ".pdf")));
			writer.setPageEvent(new PDFBackground());
			writer.setPageEvent(new PDFPagination());
			document.setPageCount(1);

			document.open();

			Paragraph title = new Paragraph(
					Constants.TITLE_INFO_CLIENT
							+ client.getFirstName() + " " + client.getLastName(), Fonts.FONT_TITLE);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			Paragraph p = new Paragraph(" ");
			document.add(p);

			Paragraph age = new Paragraph(
					Constants.INFO_CLIENT_AGE +
							Period.between(client.getBirthday(), LocalDate.now())
								  .getYears(), Fonts.FONT_LINE_SIZE_12);

			document.add(age);

			Paragraph email = new Paragraph(
					Constants.INFO_CLIENT_EMAIL + client.getEmail(), Fonts.FONT_LINE_SIZE_12);
			document.add(email);

			Paragraph telephone = new Paragraph(
					Constants.INFO_CLIENT_TELEPHONE + client.getTelephone(), Fonts.FONT_LINE_SIZE_12);
			document.add(telephone);

			Paragraph registrationDate = new Paragraph(
					Constants.INFO_CLIENT_REGISTRATION_DATE
							+ getDateTimeForPDF(client.getRegistrationDate()), Fonts.FONT_LINE_SIZE_12);
			document.add(registrationDate);

			document.close();

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private static String getDateTimeForFileName() {
		return LocalDateTime.now()
							.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN_FOR_FILE_NAME));
	}

	private static String getDateTimeForPDF(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN_FOR_PDF));
	}

	private static String getDateForPDF(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN_FOR_PDF));
	}

}
