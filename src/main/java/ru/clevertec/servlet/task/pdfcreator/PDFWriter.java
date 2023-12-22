package ru.clevertec.servlet.task.pdfcreator;


import ru.clevertec.servlet.task.entity.dto.ClientDto;

import java.util.List;

public interface PDFWriter {

	void writeClientsToPDF(List<ClientDto> clients, String pathForSave, String fileName);
	void writeClientToPDF(ClientDto client, String pathForSave, String fileName);

}
