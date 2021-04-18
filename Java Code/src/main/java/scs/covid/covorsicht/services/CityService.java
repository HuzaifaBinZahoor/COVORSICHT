package scs.covid.covorsicht.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.models.CityCases;
import scs.covid.covorsicht.models.Country;
import scs.covid.covorsicht.models.SubCities;
import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.repositories.CityCasesRepository;
import scs.covid.covorsicht.repositories.CityRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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


@Service
@Transactional
public class CityService {
	
	
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	CountryService countryService;
	@Autowired
	SubscriberService subscriberService;
	@Autowired
	CityCasesRepository cityCasesRepository;
	

	public City save(City city) {

		return cityRepository.save(city);
	}

	public City update(City city) {

		return cityRepository.saveAndFlush(city);
	}

	public City get(Long id) {
		return cityRepository.findById(id).orElse(null);
	}

	public List<City> getAll() {
		return cityRepository.findAll();
	}

	public void delete(City c) {
		cityRepository.delete(c);
	}

	public List<City> getCurent() {

		return null;// cityRepository.getCurrent();
	}

	public boolean saveCities(String[] cities){

		List<City> cityList = getAll();
		Country country = countryService.get(Long.parseLong("1"));
		for (int i=0;i<cities.length; i++){
			boolean isTrue = containsCity(cityList, cities[i]);
			if (isTrue==false){
				City city = new City();
				city.setName(cities[i]);
				city.setCountry(country);
				save(city);

			}
		}

		return true;
	}

	public static boolean containsCity(List<City> c, String name) {
		for(City o : c) {
			if(o != null && o.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	}
