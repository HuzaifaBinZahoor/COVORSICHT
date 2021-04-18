package scs.covid.covorsicht.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.mashape.unirest.http.Unirest;

import org.springframework.web.servlet.ModelAndView;
import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.models.CityCases;
import scs.covid.covorsicht.models.CityCasesModel;
import scs.covid.covorsicht.services.CityCasesService;
import scs.covid.covorsicht.services.CityService;
import scs.covid.covorsicht.utility.ErrorUtility;
import scs.covid.covorsicht.utility.JsonResponse;

@Controller
@RequestMapping("/citycases")
public class CityCasesController {

	@Autowired
	CityCasesService cityCasesService;

	@Autowired
	CityService cityService;

	/**
	 * This API is used to manually add city cases records
	 * 
	 * @param totalC
	 * @param newC
	 * @param alert
	 * @param date
	 * @param cityId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static Logger logger = LoggerFactory.getLogger(CityCases.class);
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody JsonResponse add(@RequestParam(value = "totalC", required = true) int totalC,
			@RequestParam(value = "newC", required = true) int newC,
			@RequestParam(value = "alert", required = true) boolean alert,
			@RequestParam(value = "date", required = true) String date,
			@RequestParam(value = "cityId", required = true) int cityId)

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		// int[] cIds = citiesIds.split(",");

		CityCases cityCases = new CityCases();
		cityCases.setAlert(alert);
		City city = cityService.get((long) cityId);
		cityCases.setCity(city);
//		cityCases.setTotalCases(totalC);
		cityCases.setNewCases(newC);
//		cityCases.setDate(Long.valueOf(date));

		cityCases = cityCasesService.save(cityCases);

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(cityCases);
		return response;
	}

//	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
//	public @ResponseBody JsonResponse getCurrent()
//
//			throws UnsupportedEncodingException {
//
//		JsonResponse response = new JsonResponse();
//
//		List<CityCases> cityCases = cityCasesService.getCurent(0, 0);
//		// List<City> cityCases = cityService.getCurent();
//
//		response.setStatus("SUCCESS");
//		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
//		response.setStatus(ErrorUtility.NO_ERROR.getName());
//		response.setPayLoad(cityCases);
//		return response;
//	}

	/**
	 * This API is used to get current cases of a single city
	 * @param cityId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getCurrentSingle", method = RequestMethod.GET)
	public @ResponseBody JsonResponse getCurrentSingle(@RequestParam(value = "cityId", required = true) int cityId)

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		CityCases cityCases = cityCasesService.getSingle(cityService.get((long) cityId));
		// List<City> cityCases = cityService.getCurent();

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(cityCases);
		return response;
	}

	/**
	 * This API is used to get all data of a specific city
	 * @param cityId
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping(value = "/getByCity", method = RequestMethod.GET)
	public @ResponseBody JsonResponse getByCity(@RequestParam(value = "cityId", required = true) int cityId)

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		// CityCases cityCases = cityCasesService.getSingle(cityService.get((long)
		// cityId));
		List<CityCases> cityCases = cityCasesService.getAllByCityDateDesc(cityService.get((long) cityId));

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(cityCases);
		return response;
	}

	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = { "/get" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		String startTable = "";
		String fianlTable = "";
		try {
			com.mashape.unirest.http.HttpResponse<String> response = Unirest
					.get("https://www.citypopulation.de/en/germany/covid/")
					.header("cache-control", "no-cache").header("postman-token", "3247dd2d-3b96-4326-3e68-f228c3c37c50")
					.asString();

			if (response.getBody() != null || response.getBody() != "") {
				startTable = response.getBody().substring(response.getBody().indexOf("<table") + 0,
						response.getBody().length());
			}
			if (startTable != "") {
				fianlTable = startTable.substring(0, startTable.indexOf("</table>") + 8);
				model.addAttribute("fianlTable", fianlTable);
			}


		} catch (Exception e) {

		}

		return "cityCases";

	}


//	@RequestMapping(value="/saveCity", method = RequestMethod.POST)
//	public @ResponseBody JsonResponse saveCities(@RequestParam(required = true) String name) throws Exception {
//			System.out.println(name);
//		JsonResponse response = new JsonResponse();
//		//CityCases cityCases = cityCasesService.getSingle(cityService.get((long) cityId));
//		// List<City> cityCases = cityService.getCurent();
//
//		response.setStatus("SUCCESS");
//		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
//		response.setStatus(ErrorUtility.NO_ERROR.getName());
//		//response.setPayLoad(cityCases);
//		return response;
//	}


	@RequestMapping(path = "/saveCity", method = RequestMethod.POST)
	public @ResponseBody JsonResponse saveCity(@RequestBody String[] itemIDs) throws Exception{
		JsonResponse response = new JsonResponse();

		//code
		cityService.saveCities(itemIDs);
		response.setStatus("Data has been saved successfully!");
		return response;
	}

	@RequestMapping(path = "/saveCityCases", method = RequestMethod.POST)
	public @ResponseBody JsonResponse saveCityCases(@RequestBody List<CityCasesModel> cityCasesList) throws Exception{
		JsonResponse response = new JsonResponse();

		cityCasesService.saveCityCases(cityCasesList);
		response.setStatus("Data has been saved successfully!");
		return response;
	}
	@RequestMapping(value = { "/printAll" }, method = RequestMethod.GET)
	public ModelAndView printAll() {

		Map criteria = new HashMap<String,String>();
//		criteria.put("salaryType",salaryType.toString());
//		criteria.put("pinNo",pinNo.toString());
//		criteria.put("salaryMonth",salaryMonth.toString());
//		criteria.put("salaryYear",salaryYear.toString());
//		criteria.put("printOrEmail",printOrEmail.toString());
//		criteria.put("departmentId",departmentId.toString());

		return cityCasesService.getCityCases(criteria);

	}
}
