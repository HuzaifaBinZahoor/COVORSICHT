package scs.covid.covorsicht.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.services.SubCitiesService;
import scs.covid.covorsicht.services.SubscriberService;
import scs.covid.covorsicht.utility.ErrorUtility;
import scs.covid.covorsicht.utility.JsonResponse;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

	@Autowired
	SubscriberService subscriberService;

	@Autowired
	SubCitiesService subCitiesService;
	
	/**
	 * This API is used to send report to subscriber through email
	 * 
	 * @param id
	 * @return
	 * @throws DocumentException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@RequestMapping(value = "/emailReport", method = RequestMethod.GET)
	public @ResponseBody JsonResponse emailReport(@RequestParam(value = "id", required = true) String id)
			throws DocumentException, URISyntaxException, IOException {

		JsonResponse response = new JsonResponse();
		subCitiesService.emailReport(id);

		// subCitiesService.email();
		return response;
	}

	/**
	 * This API is used to get the pdf form of report that can also be downloaded
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/getReport", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity customersReport(@RequestParam(value = "id", required = true) String id) throws IOException {

		try {
			ByteArrayInputStream bis = subCitiesService.customerPDFReport(id);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=customers.pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		} catch (Exception e) {
			System.out.println(e);
		}
		// List customers = (List) customerRepository.findAll();

		return null;
	}

	/**
	 * This API is used to add subscribe in db
	 * @param email
	 * @param citiesIds
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody JsonResponse addSubscriber(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "citiesIds", required = true) int[] citiesIds) throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		// int[] cIds = citiesIds.split(",");

		Subscriber subscriber = new Subscriber();
		subscriber.setEmail(email);

		subscriber = subscriberService.save(subscriber);

		subCitiesService.addSubCities(citiesIds, subscriber);
		
		subCitiesService.sendSimpleMessage(email, "subject", "text");

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(subscriber);
		return response;
	}

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	public @ResponseBody JsonResponse sendmail() throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		subCitiesService.sendSimpleMessage("to", "subject", "text");

		return response;
	}

}
