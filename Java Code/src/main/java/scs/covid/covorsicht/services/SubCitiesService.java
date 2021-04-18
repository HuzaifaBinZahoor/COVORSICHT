package scs.covid.covorsicht.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import scs.covid.covorsicht.models.CityCases;
import scs.covid.covorsicht.models.SubCities;
import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.repositories.CityCasesRepository;
import scs.covid.covorsicht.repositories.SubCitiesRepository;

@Service
@Transactional
public class SubCitiesService {


	@Autowired
	CityCasesRepository cityCasesRepository;

	@Autowired
	SubCitiesService subCitiesService;
	
	@Autowired
	SubscriberService subscriberService;
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	SubCitiesRepository subCitiesRepository;

	@Autowired
	CityService cityService;

	public SubCities save(SubCities subCities) {

		return subCitiesRepository.save(subCities);
	}

	public SubCities update(SubCities subCities) {

		return subCitiesRepository.saveAndFlush(subCities);
	}

	public SubCities get(Long id) {
		return subCitiesRepository.findById(id).orElse(null);
	}

	public List<SubCities> getAll() {
		return subCitiesRepository.findAll();
	}

	public void addSubCities(int[] citiesIds, Subscriber subs) {
		for (int i : citiesIds) {
			
	SubCities subCities = new SubCities();
			subCities.setSubscriber(subs);
			subCities.setCity(cityService.get((long) i));
			update(subCities);

		}
	}

	public void sendSimpleMessage(String to, String subject, String text) {
		
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("huzaifazahoor4@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText("Thank you for subscribing. We will inform you about all Covid-19-related news for your subscribed areas.\r\n"
				+ "\r\n"
				+ "If you do not want to receive any notifications, please unsubscribe.\r\n"
				+ "\r\n"
				+ "Take care of yourself and your beloved ones.");
		emailSender.send(message);
	}

	public void delete(SubCities c) {
		subCitiesRepository.delete(c);
	}
	
	
	
	public void emailReport(String id) {
		Subscriber subs = subscriberService.get(Long.valueOf(id));
		String smtpHost = "smtp.gmail.com"; // replace this with a valid host
		int smtpPort = 587; // replace this with a valid port

		String sender = "rajaue032@gmail.com"; // replace this with a valid sender email address
		String recipient = subs.getEmail(); // replace this with a valid recipient email address
		String content = "Hello,\r\n" + "\r\n"
				+ "Please find attached the report of the current data of the COVID-19 pandemic.\r\n" + "\r\n"
				+ "Take care of yourself and your beloved ones.\r\n" + "\r\n" + ""; // this will be the text of
																							// the email
		String subject = "Covorsicht Report"; // this will be the subject of the email

		Properties properties = new Properties();
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("your email", "password for the app");
			}
		});
		// null);

		ByteArrayOutputStream outputStream = null;

		try {
			// construct the text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(content);

			// now write the PDF content to the output stream
			outputStream = new ByteArrayOutputStream();
			// writePdf(outputStream);
			ByteArrayInputStream bytes = customerPDFReport(id);// outputStream.toByteArray();

			// construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));
			pdfBodyPart.setFileName("test.pdf");

			// construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);

			// create the sender/recipient addresses
			InternetAddress iaSender = new InternetAddress(sender);
			InternetAddress iaRecipient = new InternetAddress(recipient);

			// construct the mime message
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setSender(iaSender);
			mimeMessage.setSubject(subject);
			mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
			mimeMessage.setContent(mimeMultipart);

			// send off the email
			emailSender.send(mimeMessage);

			System.out.println(
					"sent from " + sender + ", to " + recipient + "; server = " + smtpHost + ", port = " + smtpPort);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// clean off
			if (null != outputStream) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (Exception ex) {
				}
			}
		}
	}

	public ByteArrayInputStream customerPDFReport(String id) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Subscriber sub = subscriberService.get(Long.valueOf(id));
		Set<SubCities> subCities = sub.getUserSubCities();
		List<CityCases> cc = new ArrayList<CityCases>();
		for (SubCities subc : subCities) {
			cc.add(cityCasesRepository.findTopByCityOrderByDateDesc(subc.getCity()));
		}

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			// Add Text to PDF file ->
			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph("Covorsicht-Covid Update", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(3);
			// Add PDF Table Header ->
			Stream.of("Name", "Cases", "Situation").forEach(headerTitle -> {
				PdfPCell header = new PdfPCell();
				Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setBorderWidth(2);
				header.setPhrase(new Phrase(headerTitle, headFont));
				table.addCell(header);
			});

			for (CityCases c : cc) {
				PdfPCell idCell = new PdfPCell(new Phrase(c.getCity().getName()));
				idCell.setPaddingLeft(4);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(idCell);

				PdfPCell firstNameCell = new PdfPCell(new Phrase(String.valueOf(c.getNewCases())));
				firstNameCell.setPaddingLeft(4);
				firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(firstNameCell);

				PdfPCell lastNameCell = new PdfPCell(new Phrase(c.isAlert() ? "Red Zone" : "Safe Zone"));
				lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lastNameCell.setPaddingRight(4);
				table.addCell(lastNameCell);
			}
			document.add(table);

			document.close();
		} catch (DocumentException e) {
			// logger.error(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}


}
