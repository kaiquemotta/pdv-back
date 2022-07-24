package com.pdv.resources;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lowagie.text.DocumentException;
import com.pdv.entities.Venda;
import com.pdv.reports.VendaPDF;
import com.pdv.services.PrinterService;
import com.pdv.services.VendaService;

import pers.pete.printer.ThermalPrinter;
import pers.pete.printer.consts.Const;
import pers.pete.printer.enums.Align;
import pers.pete.printer.pojo.BaseData;
import pers.pete.printer.pojo.ImageData;
import pers.pete.printer.pojo.LineData;
import pers.pete.printer.pojo.QrcodeData;
import pers.pete.printer.pojo.RowData;
import pers.pete.printer.pojo.SpaceData;
import pers.pete.printer.pojo.TitleData;
import pers.pete.printer.pojo.WordData;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/venda")
public class VendaResource {

	@Autowired
	private VendaService vendaService;

	@GetMapping()
	public ResponseEntity<List<Venda>> findAll() {
		List<Venda> vendas = vendaService.findAll();
		return ResponseEntity.ok().body(vendas);
	}

	@GetMapping(value = "/emAberto")
	public ResponseEntity<List<Venda>> findAllVendaAbertas() {
		List<Venda> vendas = vendaService.findVendasEmAberto();
		return ResponseEntity.ok().body(vendas);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Venda> findById(@PathVariable Long id) {
		Venda venda = vendaService.findById(id);
		return ResponseEntity.ok().body(venda);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Venda venda) {
		venda = vendaService.insert(venda);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(venda.getId()).toUri();
		System.out.println(venda.toString());
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Venda> update(@PathVariable Long id, @RequestBody Venda venda) {
		venda = vendaService.update(id, venda);
		return ResponseEntity.ok().body(venda);
	}

	@PutMapping(value = "finaliza/{id}")
	public ResponseEntity<Venda> finalizaVenda(@PathVariable Long id) {
		Venda venda = vendaService.finalizaVenda(id);
		return ResponseEntity.ok().body(venda);
	}

	@GetMapping("/vendas/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		// caso queira realiazr download, apenas descomentar essa linha
		// response.setHeader(headerKey, headerValue);

		List<Venda> vendas = vendaService.findVendasFinalizasDia();

		VendaPDF exporter = new VendaPDF(vendas);
		exporter.export(response);

	}
	
	@GetMapping("/vendas/download/pdf")
	public void downloadPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		//caso queira realiazr download, apenas descomentar essa linha
		response.setHeader(headerKey, headerValue);

		List<Venda> vendas = vendaService.findVendasFinalizasDia();

		VendaPDF exporter = new VendaPDF(vendas);
		exporter.export(response);

	}
	
	@GetMapping("/getComprovante")
	public void getComprovante() throws DocumentException, IOException {
	PrinterService printerService = new PrinterService();
	// cut that paper!

	this.test();
	
	//System.out.println(printerService.getPrinters());
	//		
	//print some stuff
	printerService.printString("EPSON", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

	// cut that paper!
	//byte[] cutP = new byte[] { 0x1d, 'V', 1 };

//	printerService.printBytes("EPSON-TM-T20II", cutP);


	}
	
	  public void test() {
		    ThermalPrinter a = new ThermalPrinter(145, true);
		    
		    List<BaseData> list = new ArrayList<>();

		    list.add(new WordData("编号: 1001", Align.LEFT, Font.BOLD, 5));

		    list.add(new TitleData("标题"));
		    list.add(new LineData(true));

		    list.add(new LineData("普通文本", false));
		    list.add(new WordData("左对齐，超出纸张宽度自动折行，可设置字体样式、对齐方式、字号", Align.LEFT));
		    list.add(new LineData());
		    list.add(new WordData("居中对齐，超出纸张宽度自动折行，可设置字体样式、对齐方式、字号", Align.CENTER, Font.PLAIN, Const.FONTSIZE_10));
		    list.add(new LineData());
		    list.add(new WordData("右对齐，超出纸张宽度自动折行，可设置字体样式、对齐方式、字号", Align.RIGHT, Font.ITALIC, Const.FONTSIZE_8));
		    list.add(new SpaceData());

		    list.add(new LineData("键值文本", false));
		    list.add(new RowData("房间号", "1001"));
		    list.add(new RowData("入住房型", "豪华大床房"));
		    list.add(new RowData("入住人", "张三"));
		    list.add(new RowData("入住日期", "2019-06-20"));
		    list.add(new RowData("离店日期", "2019-06-21"));
		    list.add(new RowData("空字段", ""));
		    list.add(new RowData("备注", "超过纸张宽度折到下一行但标题不会"));

		    list.add(new LineData("二维码", false));
		    list.add(new QrcodeData("https://www.baidu.com/s?wd=Wow!%20You%20find%20me!"));
		    list.add(new LineData("本地图片", false));

		    File file = new File("D:\\test.png");
		    InputStream is;
		    BufferedImage bi = null;
		    try {
		      is = new FileInputStream(file);
		      bi = ImageIO.read(is);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    list.add(new ImageData(bi));

		    list.add(new SpaceData(10));
		    list.add(new WordData("打印时间: 2019-06-21", Align.RIGHT, Font.BOLD, 5));

		    try {
		      a.print(list);
		    } catch (PrinterException e) {
		      e.printStackTrace();
		    }
		  }
		
}
