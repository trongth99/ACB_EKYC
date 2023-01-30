package fis.com.vn.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import fis.com.vn.component.ConfigProperties;
import fis.com.vn.entities.EkycDoanhNghiep;
import java.nio.file.Paths;

@Component
public class PdfHandling {
	@Value("${KY_SO_FOLDER}")
	String KY_SO_FOLDER;
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfHandling.class);
	@Autowired
	ConfigProperties configProperties;

	public static final String FONT = "/FreeSans.ttf";

	public static void main(String[] args) throws IOException {

//		  //Loading an existing document
//	      File file = new File("C:\\Users\\MSI Bravo\\Downloads\\ACCOUNT-OPENING-FORM_Mar2021_compressed.pdf");
//	      PDDocument doc = PDDocument.load(file);
//	        
//	      //Retrieving the page
//	      PDPage page = doc.getPage(1);
//	       
//	      //Creating PDImageXObject object
//	      PDImageXObject pdImage = PDImageXObject.createFromFile("C:\\Users\\MSI Bravo\\Downloads\\5ee23448-7b4f-428f-92fa-c92634a1c75b.png",doc);
//	       
//	      //creating the PDPageContentStream object
//	      PDPageContentStream contents = new PDPageContentStream(doc, page);
//
//	      //Drawing the image in the PDF document
//	      contents.drawImage(pdImage, 70, 250);
//
//	      System.out.println("Image inserted");
//	      
//	      //Closing the PDPageContentStream object
//	      contents.close();		
//			
//	      //Saving the document
//	      doc.save("C:\\Users\\MSI Bravo\\Downloads\\ACCOUNT-OPENING-FORM_Mar2021_edithhhh.pdf");
//	            
//	      //Closing the document
//	      doc.close();

//		File initialFile = new File("C:\\Users\\MSI Bravo\\Downloads\\ACCOUNT-OPENING-FORM_Mar2021_compressed .pdf");
//		InputStream targetStream = new FileInputStream(initialFile);
//		final PDDocument document = PDDocument.load(targetStream);
//		final PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
//		final Iterator<PDField> it = acroForm.getFieldIterator();
//
//		
//		
		try {
			String inputFilePath = "C:\\Users\\MSI Bravo\\Downloads\\ACCOUNT-OPENING-FORM_Mar2021_compressed .pdf";
			OutputStream fos = new FileOutputStream(
					new File("C:/Users/MSI Bravo/Downloads/ACCOUNT-OPENING-FORM_Mar2021_edit.pdf"));
			byte[] array = Files
					.readAllBytes(Paths.get("C:/image/log/web/24_9_2022/2ca22a7b-de88-4d7b-830b-c5768afc6618.jpg"));
			PdfReader pdfReader = new PdfReader(inputFilePath);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				if (i == 1) {

					PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
					Image image = Image.getInstance(array);
					PdfImage stream = new PdfImage(image, "", null);
					stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
					PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
					image.setDirectReference(ref.getIndirectReference());
					image.setAbsolutePosition(0, 450);
					pdfContentByte.addImage(image);
				}
			}

			pdfStamper.close();
		} catch (Exception e) {
			LOGGER.error("editContentPdf error: {}", e);
			e.printStackTrace();
		}

	}

	public void nhapThongTinForm(String pathFileEdit, EkycDoanhNghiep ekycDoanhNghiep, String pathPdfFillForm)
			throws IOException {
		File initialFile = new File(pathPdfFillForm);
		InputStream targetStream = new FileInputStream(initialFile);
		final PDDocument document = PDDocument.load(targetStream);
		final PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
		System.out.println("pdf vao :" + pathPdfFillForm);
		System.out.println("pdf ra :" + pathFileEdit);

		try {
//			final Iterator<PDField> it = acroForm.getFieldIterator();
			PDFont font = PDType0Font.load(document, new FileInputStream(KY_SO_FOLDER + "SVN-Times New Roman 2.ttf"),
					false);
			PDResources dr = acroForm.getDefaultResources();
			COSName fontName = dr.add(font);

			if (ekycDoanhNghiep.getAllInOne().equals("yes")) {
				for (int y = 0; y < ekycDoanhNghiep.getLegalRepresentator().size(); y++) {

					if (y == 0) {
						PDPage page = document.getPage(13);
						PDImageXObject pdImage = PDImageXObject
								.createFromFile(ekycDoanhNghiep.getLegalRepresentator().get(y).getAnhChuKy(), document);
						PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND, true);
						contents.drawImage(pdImage, 120, 260, 75, 60);
						contents.close();
					}

				}

				for (int y = 0; y < ekycDoanhNghiep.getChiefAccountant().size(); y++) {

					if (y == 0) {
						PDPage page = document.getPage(13);
						PDImageXObject pdImage = PDImageXObject
								.createFromFile(ekycDoanhNghiep.getChiefAccountant().get(y).getAnhChuKy(), document);
						PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND, true);
						contents.drawImage(pdImage, 400, 260, 75, 60);
						contents.close();
					}

				}

				for (PDField f : acroForm.getFields()) {

					if (f instanceof PDTextField) {
						PDTextField textField = (PDTextField) f;
						setFont(fontName, textField);

						switch (f.getFullyQualifiedName()) {
						case "Text Field11":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 0) {
									
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}

							break;
						case "Text Field13":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}
							break;
						case "Text Field15":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}
							break;
						case "Text Field17":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}
							break;
						case "Text Field12":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;
						case "Text Field14":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;
						case "Text Field16":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;
						case "Text Field18":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;

						case "Text1":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text2":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text3":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text4":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text Field57":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getHoVaTen());
									break;
								}
							}
							break;
						case "Text Field59":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getNamSinh());
									break;
								}
							}
							break;
						case "Text Field61":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getQuocTich());
									break;
								}
							}
							break;
						case "Text Field63":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getSoCmt());
									break;
								}
							}
							break;

						case "Text Field65":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getNgayCap());
									break;
								}
							}
							break;
						case "Text Field67":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getNoiCap());
									break;
								}
							}
							break;

						case "Text Field71":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getMaSoThue());
									break;
								}
							}

							break;
						case "Text Field77":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getDiaChiNha());
									break;
								}
							}

							break;
						case "Text Field79":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getPhone());
									break;
								}
							}

							break;
						case "Text Field81":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getVanPhong());
									break;
								}
							}

							break;
						case "Text Field85":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getHoKhau());
									break;
								}
							}

							break;
						case "Text Field971":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field91":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getDiaChiNha());
									break;
								}
							}
							break;
						//// ke toan
						case "Text Field58":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getHoVaTen());
									break;
								}
							}
							break;
						case "Text Field60":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getNamSinh());
									break;
								}
							}
							break;
						case "Text Field62":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getQuocTich());
									break;
								}
							}
							break;
						case "Text Field64":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field66":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getNgayCap());
									break;
								}
							}
							break;
						case "Text Field68":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getNoiCap());
									break;
								}
							}
							break;
						case "Text Field72":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getMaSoThue());
									break;
								}
							}
							break;
						case "Text Field78":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getDiaChiNha());
									break;
								}
							}
							break;
						case "Text Field80":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getPhone());
									break;
								}
							}
							break;
						case "Text Field82":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getVanPhong());
									break;
								}
							}
							break;
						case "Text Field94":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getDiaChiNha());
									break;
								}
							}
							break;
						case "Text Field981":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field88":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getHoKhau());
									break;
								}
							}
							break;
						/// step9
						case "txtHoten1":
							System.out.println("ho va tenb");
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								System.out.println("ho va tenb:"+ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "txtcmt1":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field185":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "txtHoten2":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "txtcmt2":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field186":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field187":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field188":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field197":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field189":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field190":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field198":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field199":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 4) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field200":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 4) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field209":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 4) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field201":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 5) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field202":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 5) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field210":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 5) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field43":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						case "Text Field0":
							f.setValue(ekycDoanhNghiep.getNameCompany());
							break;
						case "Text Field1":
							f.setValue(ekycDoanhNghiep.getRegisteredAddress());
							break;
						case "Text Field2":
							f.setValue(ekycDoanhNghiep.getOperatingAddress());
							break;
						case "Text Field3":
							f.setValue(ekycDoanhNghiep.getCountryOfIncorporation());
							break;
						case "Text Field4":
							f.setValue(ekycDoanhNghiep.getRegistrationNumber());
							break;
						case "Text Field5":
							f.setValue(ekycDoanhNghiep.getStraight2BankGroupID());
							break;
						case "Text Field6":
							f.setValue(ekycDoanhNghiep.getMailingAddress());
							break;
						case "Text Field7":
							f.setValue(ekycDoanhNghiep.getSwiftBankIDCode());
							break;
						case "Text Field8":
							f.setValue(ekycDoanhNghiep.getMobileOfficeTelephone());
							break;
						case "Text Field9":
							f.setValue(ekycDoanhNghiep.getContactPerson());
							break;
						case "Text Field10":
							f.setValue(ekycDoanhNghiep.getEmailAddress());
							break;
						case "Text Field27":
							f.setValue(ekycDoanhNghiep.getShortName());
							break;
						case "Text Field28":
							f.setValue(ekycDoanhNghiep.getNameInEnglish());
							break;
						case "Text Field29":
							f.setValue(ekycDoanhNghiep.getFaxNumber());
							break;
						case "Text Field30":
							f.setValue(ekycDoanhNghiep.getTaxCode());
							break;
						case "Text Field31":
							f.setValue(ekycDoanhNghiep.getBusinessActivities());
							break;
						case "Text Field32":
							f.setValue(ekycDoanhNghiep.getYearlyAveragenumber());
							break;
						case "Text Field33":
							f.setValue(ekycDoanhNghiep.getTotalSalesTurnover());
							break;
						case "Text Field34":
							f.setValue(ekycDoanhNghiep.getTotalCapital());
							break;
						case "Text Field35":
							f.setValue(ekycDoanhNghiep.getNamePlus1());
							break;
						case "Text Field36":
							f.setValue(ekycDoanhNghiep.getNamePlus2());
							break;
						case "Text Field37":
							f.setValue(ekycDoanhNghiep.getDatePlus2());
							break;
						case "Text Field38":
							f.setValue(ekycDoanhNghiep.getDatePlus1());
							break;
						case "Text Field53":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						case "Text Field211":
							f.setValue(ekycDoanhNghiep.getSpecialInstructions());
							break;
						case "Text Field73":
							f.setValue(ekycDoanhNghiep.getRegisterUser());
							break;
						case "Text Field218":
							f.setValue(ekycDoanhNghiep.getRegisteringEmailAddress());
							break;

//					case "Text Field91":
//						f.setValue(ekycDoanhNghiep.getRegisteringEmailAddress());
//						break;
						case "Text Field142":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						case "Text Field106":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						default:
							f.setValue("");
							break;
						}
					}
					if (f instanceof PDCheckBox) {
						switch (f.getFullyQualifiedName()) {
						case "Check Box14":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0 && ekycDoanhNghiep.getUserDesignation().get(i).getTaoLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;
						case "Check Box20":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0 && ekycDoanhNghiep.getUserDesignation().get(i).getBaoCao().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;

						case "Check Box26":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0
										&& ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenh().equals("Y")) {
									((PDCheckBox) f).check();
 
								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;
						case "Check Box32":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0 && ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenhDongThoi()
										.equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;

						case "Check Box15":
							
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
							
								if (i == 1) {
									
									if(ekycDoanhNghiep.getUserDesignation().get(i).getTaoLenh().equals("Y")) {
										
										((PDCheckBox) f).check();
									}
									

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box21":
							//System.out.println("hjsadhsajdhj");
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1 && ekycDoanhNghiep.getUserDesignation().get(i).getBaoCao().equals("Y")) {
									((PDCheckBox) f).check();
									
								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box27":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1
										&& ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box33":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1 && ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenhDongThoi()
										.equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;

						case "Check Box16":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2 && ekycDoanhNghiep.getUserDesignation().get(i).getTaoLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box22":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2 && ekycDoanhNghiep.getUserDesignation().get(i).getBaoCao().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box28":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2
										&& ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box1111":

							if (ekycDoanhNghiep.getTaxMode().equals("Direct / Trực tiếp khai nộp thuế")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;

						case "Check Box1112":
							if (ekycDoanhNghiep.getTaxMode()
									.equals("Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box1113":
							if (ekycDoanhNghiep.getResidentStatus().equals("Resident / Người Cư Trú")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box1114":
							if (ekycDoanhNghiep.getResidentStatus().equals("Non-Resident / Người Không Cư Trú")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box3111":
							if (ekycDoanhNghiep.getApplicableAccountingSystems()
									.equals("Vietnamese Accounting Regime/Chế độ kế toán Việt Nam")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box3112":
							if (ekycDoanhNghiep.getApplicableAccountingSystems()
									.equals("Others (Please specify)/Khác (Đề nghị nêu cụ thể)")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box34":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2 && ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenhDongThoi()
										.equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;
						default:
							break;
						}
					}

				}

			} else {
				for (int y = 0; y < ekycDoanhNghiep.getLegalRepresentator().size(); y++) {

					if (y == 0) {
						PDPage page = document.getPage(13);
						PDImageXObject pdImage = PDImageXObject
								.createFromFile(ekycDoanhNghiep.getLegalRepresentator().get(y).getAnhChuKy(), document);
						PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND, true);
						contents.drawImage(pdImage, 120, 260, 75, 60);
						contents.close();
					}

				}

				for (int y = 0; y < ekycDoanhNghiep.getChiefAccountant().size(); y++) {

					if (y == 0) {
						PDPage page = document.getPage(13);
						PDImageXObject pdImage = PDImageXObject
								.createFromFile(ekycDoanhNghiep.getChiefAccountant().get(y).getAnhChuKy(), document);
						PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND, true);
						contents.drawImage(pdImage, 400, 260, 75, 60);
						contents.close();
					}

				}
				if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
					for (int y = 0; y < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); y++) {

						if (y == 0) {
							PDPage page = document.getPage(16);
							PDImageXObject pdImage = PDImageXObject.createFromFile(
									ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(y).getAnhChuKy(), document);
							PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND,
									true);
							contents.drawImage(pdImage, 120, 500, 75, 60);
							contents.close();
						}
						if (y == 1) {
							PDPage page = document.getPage(16);
							PDImageXObject pdImage = PDImageXObject.createFromFile(
									ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(y).getAnhChuKy(), document);
							PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND,
									true);
							contents.drawImage(pdImage, 400, 500, 75, 60);
							contents.close();
						}

					}
				}

				if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
					for (int y = 0; y < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); y++) {

						if (y == 0) {
							PDPage page = document.getPage(18);
							PDImageXObject pdImage = PDImageXObject.createFromFile(
									ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(y).getAnhChuKy(),
									document);
							PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND,
									true);
							contents.drawImage(pdImage, 120, 450, 75, 60);
							contents.close();
						}
						if (y == 1) {
							PDPage page = document.getPage(16);
							PDImageXObject pdImage = PDImageXObject.createFromFile(
									ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(y).getAnhChuKy(),
									document);
							PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND,
									true);
							contents.drawImage(pdImage, 400, 450, 75, 60);
							contents.close();
						}

					}
				}

				for (PDField f : acroForm.getFields()) {

					if (f instanceof PDTextField) {
						PDTextField textField = (PDTextField) f;
						setFont(fontName, textField);

						switch (f.getFullyQualifiedName()) {
						case "Text Field43":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						case "Text Field0":
							f.setValue(ekycDoanhNghiep.getNameCompany());
							break;
						case "Text Field1":
							f.setValue(ekycDoanhNghiep.getRegisteredAddress());
							break;
						case "Text Field2":
							f.setValue(ekycDoanhNghiep.getOperatingAddress());
							break;
						case "Text Field3":
							f.setValue(ekycDoanhNghiep.getCountryOfIncorporation());
							break;
						case "Text Field4":
							f.setValue(ekycDoanhNghiep.getRegistrationNumber());
							break;
						case "Text Field5":
							f.setValue(ekycDoanhNghiep.getStraight2BankGroupID());
							break;
						case "Text Field6":
							f.setValue(ekycDoanhNghiep.getMailingAddress());
							break;
						case "Text Field7":
							f.setValue(ekycDoanhNghiep.getSwiftBankIDCode());
							break;
						case "Text Field8":
							f.setValue(ekycDoanhNghiep.getMobileOfficeTelephone());
							break;
						case "Text Field9":
							f.setValue(ekycDoanhNghiep.getContactPerson());
							break;
						case "Text Field10":
							f.setValue(ekycDoanhNghiep.getEmailAddress());
							break;
						case "Text Field27":
							f.setValue(ekycDoanhNghiep.getShortName());
							break;
						case "Text Field28":
							f.setValue(ekycDoanhNghiep.getNameInEnglish());
							break;
						case "Text Field29":
							f.setValue(ekycDoanhNghiep.getFaxNumber());
							break;
						case "Text Field30":
							f.setValue(ekycDoanhNghiep.getTaxCode());
							break;
						case "Text Field31":
							f.setValue(ekycDoanhNghiep.getBusinessActivities());
							break;
						case "Text Field32":
							f.setValue(ekycDoanhNghiep.getYearlyAveragenumber());
							break;
						case "Text Field33":
							f.setValue(ekycDoanhNghiep.getTotalSalesTurnover());
							break;
						case "Text Field34":
							f.setValue(ekycDoanhNghiep.getTotalCapital());
							break;
						case "Text Field35":
							f.setValue(ekycDoanhNghiep.getNamePlus1());
							break;
						case "Text Field36":
							f.setValue(ekycDoanhNghiep.getNamePlus2());
							break;
						case "Text Field37":
							f.setValue(ekycDoanhNghiep.getDatePlus2());
							break;
						case "Text Field38":
							f.setValue(ekycDoanhNghiep.getDatePlus1());
							break;
						case "Text Field53":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						case "Text Field211":
							f.setValue(ekycDoanhNghiep.getSpecialInstructions());
							break;
						case "Text Field73":
							f.setValue(ekycDoanhNghiep.getRegisterUser());
							break;
						case "Text Field218":
							f.setValue(ekycDoanhNghiep.getRegisteringEmailAddress());
							break;

						case "Text Field11":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}

							break;
						case "Text Field13":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}
							break;
						case "Text Field15":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}
							break;
						case "Text Field17":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getCurrency());
									break;
								}
							}
							break;
						case "Text Field12":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;
						case "Text Field14":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;
						case "Text Field16":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;
						case "Text Field18":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountTitle());
									break;
								}
							}
							break;

						case "Text1":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text2":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text3":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text4":
							for (int i = 0; i < ekycDoanhNghiep.getListAccount().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getListAccount().get(i).getAccountType());
									break;
								}
							}
							break;
						case "Text Field57":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getHoVaTen());
									break;
								}
							}
							break;
						case "Text Field59":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getNamSinh());
									break;
								}
							}
							break;
						case "Text Field61":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getQuocTich());
									break;
								}
							}
							break;
						case "Text Field63":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getSoCmt());
									break;
								}
							}
							break;

						case "Text Field65":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getNgayCap());
									break;
								}
							}
							break;
						case "Text Field67":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getNoiCap());
									break;
								}
							}
							break;

						case "Text Field71":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getMaSoThue());
									break;
								}
							}

							break;
						case "Text Field77":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getDiaChiNha());
									break;
								}
							}

							break;
						case "Text Field79":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getPhone());
									break;
								}
							}

							break;
						case "Text Field81":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getVanPhong());
									break;
								}
							}

							break;
						case "Text Field85":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getHoKhau());
									break;
								}
							}

							break;
						case "Text Field971":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field91":
							for (int i = 0; i < ekycDoanhNghiep.getLegalRepresentator().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getLegalRepresentator().get(i).getDiaChiNha());
									break;
								}
							}
							break;
						//// ke toan
						case "Text Field58":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getHoVaTen());
									break;
								}
							}
							break;
						case "Text Field60":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getNamSinh());
									break;
								}
							}
							break;
						case "Text Field62":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getQuocTich());
									break;
								}
							}
							break;
						case "Text Field64":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field66":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getNgayCap());
									break;
								}
							}
							break;
						case "Text Field68":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getNoiCap());
									break;
								}
							}
							break;
						case "Text Field72":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getMaSoThue());
									break;
								}
							}
							break;
						case "Text Field78":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getDiaChiNha());
									break;
								}
							}
							break;
						case "Text Field80":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getPhone());
									break;
								}
							}
							break;
						case "Text Field82":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getVanPhong());
									break;
								}
							}
							break;
						case "Text Field94":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getDiaChiNha());
									break;
								}
							}
							break;
						case "Text Field981":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field88":
							for (int i = 0; i < ekycDoanhNghiep.getChiefAccountant().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getChiefAccountant().get(i).getHoKhau());
									break;
								}
							}
							break;

						//// uy quyen chu tai khoan
						case "Text Field107":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 0) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getHoVaTen());
										break;
									}
								}
							}

							break;
//					case "Text Field109":
//						for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
//							if (i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getC());
//								break;
//							}
//						}
//						break;
						case "Text Field111":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 0) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getSoCmt());
										break;
									}
								}
							}

							break;
						case "Text Field113":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 0) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNgayCap());
										break;
									}
								}
							}

							break;
						case "Text Field115":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 0) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNoiCap());
										break;
									}
								}
							}

							break;
						case "Text Field108":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 1) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getHoVaTen());
										break;
									}
								}
							}

							break;
//					case "Text Field110":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size() ; i++) {
//							if(i == 1) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getMaSoThue());
//								break;
//							}
//						}
//						break;
						case "Text Field112":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 1) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getSoCmt());
										break;
									}
								}
							}

							break;
						case "Text Field114":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 1) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNgayCap());
										break;
									}
								}
							}

							break;
						case "Text Field116":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 1) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNoiCap());
										break;
									}
								}
							}

							break;
						case "Text Field117":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 2) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getHoTen());
										break;
									}
								}
							}

							break;
//					case "Text Field119":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field121":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 2) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getSoCmt());
										break;
									}
								}
							}

							break;
						case "Text Field123":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 2) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNgayCap());
										break;
									}
								}
							}

							break;
						case "Text Field125":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 2) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNoiCap());
										break;
									}
								}
							}

							break;
						case "Text Field118":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 3) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getHoVaTen());
										break;
									}
								}
							}

							break;
//					case "Text Field120":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field122":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 3) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getSoCmt());
										break;
									}
								}
							}

							break;
						case "Text Field124":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 3) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNgayCap());
										break;
									}
								}
							}

							break;
						case "Text Field126":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 3) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNoiCap());
										break;
									}
								}
							}

							break;
						case "Text Field127":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 4) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getHoVaTen());
										break;
									}
								}
							}

							break;
//					case "Text Field129":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field131":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 4) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getSoCmt());
										break;
									}
								}
							}

							break;
						case "Text Field133":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 4) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNgayCap());
										break;
									}
								}
							}

							break;
						case "Text Field135":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 4) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNoiCap());
										break;
									}
								}
							}

							break;
						case "Text Field128":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 5) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getHoVaTen());
										break;
									}
								}
							}

							break;
//					case "Text Field130":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size() ; i++) {
//							if(i == 5) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field132":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 5) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getSoCmt());
										break;
									}
								}
							}

							break;
						case "Text Field134":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 5) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNgayCap());
										break;
									}
								}
							}

							break;
						case "Text Field136":
							if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(); i++) {
									if (i == 5) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedAccountHolder().get(i).getNoiCap());
										break;
									}
								}
							}

							break;
						case "Text Field143":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 0) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getHoVaTen());
										break;
									}
								}
							}
							
							break;
//					case "Text Field145":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field147":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 0) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getSoCmt());
										break;
									}
								}
							}
							
							break;
						case "Text Field149":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 0) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNgayCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field151":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 0) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNoiCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field144":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 1) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getHoVaTen());
										break;
									}
								}
							}
							
							break;
//					case "Text Field146":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field148":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 1) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getSoCmt());
										break;
									}
								}
							}
							
							break;
						case "Text Field150":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 1) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNgayCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field152":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 1) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNoiCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field153":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 2) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getHoVaTen());
										break;
									}
								}
							}
							
							break;
//					case "Text Field154":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getEmail());
//								break;
//							}
//						}
						case "Text Field155":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 2) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getSoCmt());
										break;
									}
								}
							}
							
							break;
						case "Text Field156":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 2) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNgayCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field157":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 2) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNoiCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field158":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 3) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getHoTen());
										break;
									}
								}
							}
							
							break;
//					case "Text Field159":
//						for(int i = 0 ; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size() ; i++) {
//							if(i == 0) {
//								f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getEmail());
//								break;
//							}
//						}

						case "Text Field160":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 3) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getSoCmt());
										break;
									}
								}
							}
							
							break;
						case "Text Field161":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 3) {
										f.setValue(
												ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNgayCap());
										break;
									}
								}
							}
							
							break;
						case "Text Field162":
							if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
								for (int i = 0; i < ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(); i++) {
									if (i == 3) {
										f.setValue(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().get(i).getNoiCap());
										break;
									}
								}
							}
							
							break;
						/// step9
						case "txtHoten1":
							System.out.println("co ho ten khong: 111");
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								System.out.println("co ho ten khong: "+ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "txtcmt1":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field185":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "txtHoten2":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "txtcmt2":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field186":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field187":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field188":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field197":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field189":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field190":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field198":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 3) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field199":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 4) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field200":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 4) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field209":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 4) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
						case "Text Field201":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 5) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getHoTen());
									break;
								}
							}
							break;
						case "Text Field202":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 5) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getSoCmt());
									break;
								}
							}
							break;
						case "Text Field210":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 5) {
									f.setValue(ekycDoanhNghiep.getUserDesignation().get(i).getEmail());
									break;
								}
							}
							break;
//					case "Text Field91":
//						f.setValue(ekycDoanhNghiep.getRegisteringEmailAddress());
//						break;
						case "Text Field142":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						case "Text Field106":
							f.setValue(ekycDoanhNghiep.getNameOfTheAccountHolder());
							break;
						default:
							f.setValue("");
							break;
						}
					}
					if (f instanceof PDCheckBox) {
						switch (f.getFullyQualifiedName()) {
						case "Check Box14":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0 && ekycDoanhNghiep.getUserDesignation().get(i).getTaoLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;
						case "Check Box20":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0 && ekycDoanhNghiep.getUserDesignation().get(i).getBaoCao().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;

						case "Check Box26":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0
										&& ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;
						case "Check Box32":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 0 && ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenhDongThoi()
										.equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;

						case "Check Box15":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1 && ekycDoanhNghiep.getUserDesignation().get(i).getTaoLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box21":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1 && ekycDoanhNghiep.getUserDesignation().get(i).getBaoCao().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box27":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1
										&& ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box33":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 1 && ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenhDongThoi()
										.equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;

						case "Check Box16":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2 && ekycDoanhNghiep.getUserDesignation().get(i).getTaoLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box22":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2 && ekycDoanhNghiep.getUserDesignation().get(i).getBaoCao().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box28":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2
										&& ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenh().equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								
							}
							break;
						case "Check Box1111":

							if (ekycDoanhNghiep.getTaxMode().equals("Direct / Trực tiếp khai nộp thuế")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;

						case "Check Box1112":
							if (ekycDoanhNghiep.getTaxMode()
									.equals("Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box1113":
							if (ekycDoanhNghiep.getResidentStatus().equals("Resident / Người Cư Trú")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box1114":
							if (ekycDoanhNghiep.getResidentStatus().equals("Non-Resident / Người Không Cư Trú")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box3111":
							if (ekycDoanhNghiep.getApplicableAccountingSystems()
									.equals("Vietnamese Accounting Regime/Chế độ kế toán Việt Nam")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box3112":
							if (ekycDoanhNghiep.getApplicableAccountingSystems()
									.equals("Others (Please specify)/Khác (Đề nghị nêu cụ thể)")) {
								((PDCheckBox) f).check();

							} else {
								((PDCheckBox) f).unCheck();
							}
							break;
						case "Check Box34":
							for (int i = 0; i < ekycDoanhNghiep.getUserDesignation().size(); i++) {
								if (i == 2 && ekycDoanhNghiep.getUserDesignation().get(i).getChapThuanLenhDongThoi()
										.equals("Y")) {
									((PDCheckBox) f).check();

								} else {
									((PDCheckBox) f).unCheck();
								}
								break;
							}
							break;
						default:
							break;
						}
					}

				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		document.save(new File(pathFileEdit));
		document.close();
	}

	public byte[] addImageToPDF(byte[] pdfTemplate, String imgBase64, int pageAddImg, int x, int y, int height,
			int width) throws Exception {
		PDDocument doc = PDDocument.load(pdfTemplate);
		PDPage page = doc.getPage(pageAddImg);
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imgBase64);
		PDImageXObject pdImage = PDImageXObject.createFromByteArray(doc, imageBytes, imgBase64);
		PDPageContentStream contents = new PDPageContentStream(doc, page, AppendMode.APPEND, true);
		contents.drawImage(pdImage, x, y, height, width);
		contents.close();
		ByteArrayOutputStream newDoc = new ByteArrayOutputStream();
		doc.save(newDoc);
		doc.close();
		return newDoc.toByteArray();

	}

	private void setFont(COSName fontName, PDTextField textField) {
		String da = textField.getDefaultAppearance();
		Pattern pattern = Pattern.compile("\\/(\\w+)\\s.*");
		Matcher matcher = pattern.matcher(da);
		if (!matcher.find() || matcher.groupCount() < 2) {
			// oh-oh
		}
		String oldFontName = matcher.group(1);
		da = da.replaceFirst(oldFontName, fontName.getName());

		textField.setDefaultAppearance(da);

	}

	public String layThongTinNgayCap(String pdfBase64) {
		try {
			String str = getFullTextFromPdf(pdfBase64);
			return getStrPatterFirst(str, "Ngày cấp[ :]*([^:]+)Tại[ :]+").trim();
		} catch (Exception e) {
		}
		return "";
	}

	public static String getStrPatterFirst(String str, String pattern) {
		try {
			Pattern r = Pattern.compile(pattern);

			Matcher m = r.matcher(str);
			while (m.find()) {
				return m.group(1).replaceAll("[\\s]+", " ").trim();
			}
		} catch (Exception e) {
			LOGGER.error("ERROR: {}", e.getMessage());
		}
		return null;
	}

	private static void addText1(String text, int x, int y, PdfContentByte pdfContentByte)
			throws DocumentException, IOException {
		pdfContentByte.beginText();
		pdfContentByte.setFontAndSize(
				BaseFont.createFont("D:\\image\\kyso\\" + FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12);
		pdfContentByte.setTextMatrix(x, y);
		pdfContentByte.showText(text);

		pdfContentByte.endText();
	}

	public void themDanhDauCoUyQuyen(String inputFilePath, String outputFilePath) {
		String[] arr = configProperties.getConfig().getToa_do_check_co_uy_quyen().split(",");
		themDanhDauUyQuyen(inputFilePath, outputFilePath, Integer.valueOf(arr[0].trim()),
				Integer.valueOf(arr[1].trim()));
	}

	public void themDanhDauKhongCoUyQuyen(String inputFilePath, String outputFilePath) {
		String[] arr = configProperties.getConfig().getToa_do_check_khong_co_uy_quyen().split(",");
		themDanhDauUyQuyen(inputFilePath, outputFilePath, Integer.valueOf(arr[0].trim()),
				Integer.valueOf(arr[1].trim()));
	}

	public void themDanhDauUyQuyen(String inputFilePath, String outputFilePath, int x, int y) {
		try {
			BaseFont base = BaseFont.createFont(KY_SO_FOLDER + "/wingding.ttf", BaseFont.IDENTITY_H, false);
			String checked = "ü";

			try {
				OutputStream fos = new FileOutputStream(new File(outputFilePath));

				PdfReader pdfReader = new PdfReader(inputFilePath);
				PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
				for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
					if (i == Integer.valueOf(configProperties.getConfig().getTrang_danh_dau_uy_quyen())) {
						PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
						pdfContentByte.beginText();
						pdfContentByte.setFontAndSize(base, 20);
						pdfContentByte.setTextMatrix(x, y);
						pdfContentByte.showText(checked);
						pdfContentByte.endText();
					}
				}
				pdfStamper.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}

	public void themNgayThangNam(String inputFilePath, String outputFilePath) {
		try {
			OutputStream fos = new FileOutputStream(new File(outputFilePath));
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			PdfReader pdfReader = new PdfReader(inputFilePath);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
			String dateString = dateFormat.format(new Date());
			String[] arrDate = dateString.split("/");
			String ngay = arrDate[0];
			String thang = arrDate[1];
			String nam = arrDate[2];
			String toaDo = configProperties.getConfig().getToa_do_ngay_thang_nam();
			String[] arrToaDo = toaDo.split("-");
			Map<Integer, String[]> mapToaDo = new HashMap<Integer, String[]>();
			for (String string : arrToaDo) {
				String[] arr = string.split("\\|");
				if (!mapToaDo.containsKey(Integer.valueOf(arr[0])))
					mapToaDo.put(Integer.valueOf(arr[0]), arr);
				else
					mapToaDo.put(Integer.valueOf(arr[0]) + 99, arr);
			}

			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				if (mapToaDo.containsKey(i)) {
					String[] arr = mapToaDo.get(i);
					PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
					addText(ngay, Integer.valueOf(arr[1].split(",")[0]), Integer.valueOf(arr[1].split(",")[1]),
							pdfContentByte);
					addText(thang, Integer.valueOf(arr[2].split(",")[0]), Integer.valueOf(arr[2].split(",")[1]),
							pdfContentByte);
					addText(nam, Integer.valueOf(arr[3].split(",")[0]), Integer.valueOf(arr[3].split(",")[1]),
							pdfContentByte);
				}
				if (mapToaDo.containsKey((i + 99))) {
					String[] arr = mapToaDo.get((i + 99));
					PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
					addText(ngay, Integer.valueOf(arr[1].split(",")[0]), Integer.valueOf(arr[1].split(",")[1]),
							pdfContentByte);
					addText(thang, Integer.valueOf(arr[2].split(",")[0]), Integer.valueOf(arr[2].split(",")[1]),
							pdfContentByte);
					addText(nam, Integer.valueOf(arr[3].split(",")[0]), Integer.valueOf(arr[3].split(",")[1]),
							pdfContentByte);
				}
			}

			pdfStamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editContentPdf(String outputFilePath, String anhChuKy) {

		try {
			String inputFilePath = KY_SO_FOLDER + getNameFileEsign();
			OutputStream fos = new FileOutputStream(new File(outputFilePath));

			PdfReader pdfReader = new PdfReader(inputFilePath);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
			byte[] array = Files.readAllBytes(Paths.get(anhChuKy));
			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				if (i == 1) {

					PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
					Image image = Image.getInstance(array);
					PdfImage stream = new PdfImage(image, "", null);
					stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
					PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
					image.setDirectReference(ref.getIndirectReference());
					image.setAbsolutePosition(0, 450);
					pdfContentByte.addImage(image);
				}
			}

			pdfStamper.close();
		} catch (Exception e) {
			LOGGER.error("editContentPdf error: {}", e);
			e.printStackTrace();
		}
	}

	public String getBase64Esign() {
		String inputFilePathEsign = KY_SO_FOLDER + getNameFileEsign();
		return Utils.encodeFileToBase64Binary(new File(inputFilePathEsign));
	}

	public String getNameFileEsign() {
		return "ACCOUNT-OPENING-FORM_Dummy.pdf";
	}

	public String getFullTextFromPdf(String pdfBase64) throws IOException {
		byte[] pdfByte;
		Base64.Decoder decoder = Base64.getDecoder();
		pdfByte = decoder.decode(pdfBase64);
		ByteArrayInputStream bis = new ByteArrayInputStream(pdfByte);
		PdfReader reader = new PdfReader(bis);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		StringBuilder stringBuilder = new StringBuilder();
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			stringBuilder.append(strategy.getResultantText());
		}
		reader.close();
		return stringBuilder.toString();
	}

	public byte[] decodeToImage(String imageString) {
		BufferedImage image = null;
		byte[] imageByte;
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			imageByte = decoder.decode(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);

			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

			result = ImageScaler.resizeImage(result, 100, 100);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(result, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			bis.close();

			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void addText(String text, int x, int y, PdfContentByte pdfContentByte)
			throws DocumentException, IOException {
		pdfContentByte.beginText();
		pdfContentByte.setFontAndSize(BaseFont.createFont(KY_SO_FOLDER + FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED),
				10);
		pdfContentByte.setTextMatrix(x, y);
		pdfContentByte.showText(text);

		pdfContentByte.endText();
	}
}
