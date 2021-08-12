package com.pdv.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pdv.entities.Venda;

public class FechamentoCaixaPDF {

	
	private List<Venda> listVendas;

	public FechamentoCaixaPDF(List<Venda> listVendas) {
		this.listVendas = listVendas;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setPadding(2);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Nome Comanda", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Total", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Data", font));
		table.addCell(cell);
	}

	private void writeTableFooter(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("", font));
		table.addCell(cell);
		

		cell.setPhrase(new Phrase("%:"+"R$ "+String.valueOf(this.listVendas.stream().mapToDouble(f -> f.getValorTotal()).sum() * 10 /100), font)  );
		cell.setPadding(10);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Total:"+"R$ "+String.valueOf(this.listVendas.stream().mapToDouble(f -> f.getValorTotal()).sum()), font));
		table.addCell(cell);

	}


	private void writeTableData(PdfPTable table) {
		for (Venda v : this.listVendas) {
			table.addCell(String.valueOf(v.getId()));
			table.addCell(v.getNomeComanda());
			table.addCell(String.valueOf(v.getValorTotal()));
			table.addCell(String.valueOf(v.getDataFechamentoVenda()));
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setSize(18);
		font.setColor(Color.BLACK);

		Paragraph p = new Paragraph("Vendas do dia", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 5f, 5f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);
		writeTableFooter(table);
		document.add(table);

		document.close();

	}
}
