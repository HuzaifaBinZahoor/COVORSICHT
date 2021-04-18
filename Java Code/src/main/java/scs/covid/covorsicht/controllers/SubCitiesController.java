package scs.covid.covorsicht.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import scs.covid.covorsicht.models.SubCities;
import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.services.CityCasesService;
import scs.covid.covorsicht.services.CityService;
import scs.covid.covorsicht.services.SubCitiesService;
import scs.covid.covorsicht.services.SubscriberService;
import scs.covid.covorsicht.utility.JsonResponse;

@RestController
@RequestMapping("/subcities")
public class SubCitiesController {

	@Autowired
	CityCasesService cityCasesService;

	@Autowired
	SubCitiesService subCitiesService;

	@Autowired
	CityService cityService;

	@Autowired
	SubscriberService subscriberService;

	/**
	 * This API is used to send alert to subscriber on his email
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody JsonResponse add()

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		List<Subscriber> subscribers = subscriberService.getAll();

		for (Subscriber sc : subscribers) {

			Set<SubCities> subCities = sc.getUserSubCities();

			for (SubCities subc : subCities) {


				boolean isWorse = cityCasesService.getCitySituation(subc.getCity());
				if (isWorse) {
					subCitiesService.sendSimpleMessage(sc.getEmail(), "Alert",
							subc.getCity().getName() + " is a red zone now. for : " + sc.getEmail());
				}


			}

		}

		// int[] cIds = citiesIds.split(",");
		/*
		 * CityCases cityCases = new CityCases(); cityCases.setAlert(alert);
		 * cityCases.setCity(cityService.get((long) cityId));
		 * cityCases.setTotalCases(totalC); cityCases.setNewCases(newC);
		 * cityCases.setDate(Long.valueOf(date));
		 * 
		 * cityCases = cityCasesService.save(cityCases);
		 * 
		 * response.setStatus("SUCCESS");
		 * response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		 * response.setStatus(ErrorUtility.NO_ERROR.getName());
		 * response.setPayLoad(cityCases);
		 */ return response;
	}

}
