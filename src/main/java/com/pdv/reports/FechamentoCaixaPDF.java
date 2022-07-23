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
import com.pdv.entities.Pagamento;

public class FechamentoCaixaPDF {

	
	private List<Pagamento> relatorioCaixa;

	public FechamentoCaixaPDF(List<Pagamento> listVendas) {
		this.relatorioCaixa = listVendas;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setPadding(2);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("ID Venda", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Valor Pagamento", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Quantidade Parcela", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Troco", font));
		table.addCell(cell);
	}



	private void writeTableData(PdfPTable table) {
		for (Pagamento v : this.relatorioCaixa) {
			table.addCell(String.valueOf(v.getIdVenda()));
			table.addCell(String.valueOf(v.getValorPagamento()));
			table.addCell(String.valueOf(v.getQuantidadeParcela()));
			table.addCell(String.valueOf(v.getTroco()));
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
		document.add(table);

		document.close();

	}
}
