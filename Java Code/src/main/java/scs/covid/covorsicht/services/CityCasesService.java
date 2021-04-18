package scs.covid.covorsicht.services;

import java.text.NumberFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.servlet.ModelAndView;
import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.models.CityCases;
import scs.covid.covorsicht.models.CityCasesModel;
import scs.covid.covorsicht.models.Country;
import scs.covid.covorsicht.repositories.CityCasesRepository;

@Service
@Transactional
public class CityCasesService {

	@Autowired
	CityCasesRepository cityCasesRepository;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	CityService cityService;
	public CityCases save(CityCases cityCases) {

		return cityCasesRepository.save(cityCases);
	}

	public CityCases update(CityCases cityCases) {

		return cityCasesRepository.saveAndFlush(cityCases);
	}

	public CityCases get(Long id) {
		return cityCasesRepository.findById(id).orElse(null);
	}

	public List<CityCases> getAll() {
		return cityCasesRepository.findAll();
	}

	public List<CityCases> getCurent(long id, int limit) {

		return cityCasesRepository.getCurrent(id, PageRequest.of(0, 1));
	}

	public CityCases getSingle(City c) {

		return cityCasesRepository.findTopByCityOrderByDateDesc(c);
	}

	public boolean getCitySituation(City c) {

		CityCases cityCases = cityCasesRepository.findTopByCityOrderByDateDesc(c);

		if (cityCases.isAlert())
			return true;
		else
			return false;

	}

	public List<CityCases> getAllByCityDateDesc(City c) {

		return cityCasesRepository.findByCityOrderByDateDesc(c);
	}

	public void delete(CityCases c) {
		cityCasesRepository.delete(c);
	}

	public boolean saveCityCases(List<CityCasesModel> cityCasesModels) throws Exception{

		List<City> cityList = cityService.getAll();
		for (int i=0;i<cityCasesModels.size(); i++){
			boolean isTrue = false; //containsCityCases(cityList, cityCasesModels);
			if(isTrue==false){
				City city = getCity(cityList,cityCasesModels.get(i).getCity());
				CityCases cityCases = new CityCases();
				cityCases.setCity(city);
				cityCases.setAlert(false);
//				cityCases.setDate(new Date());
				Integer number = NumberFormat.getNumberInstance(Locale.ENGLISH).parse(cityCasesModels.get(i).getNewCases()).intValue();
				cityCases.setNewCases(number);
				cityCases.setTotalCases(number);
				save(cityCases);
			}
		}

		return true;
	}
	public static boolean containsCityCases(List<City> c, List<CityCasesModel> cityCasesModels) {
		for(City o : c) {
//			if(o != null && o.getName().equals(name)) {
//				return true;
//			}
		}
		return false;
	}

	public static City getCity(List<City> c, String name) {
		for(City o : c) {
			if(o != null && o.getName().equals(name)) {
				return o;
			}
		}
		return null;
	}
	public ModelAndView getCityCases(Map criteria){
//		JasperReportsPdfView view = new JasperReportsPdfView();
		Map<String, Object> params = new HashMap<>();
//		view.setUrl("classpath:reports/covidcases.jasper");
//		view.setApplicationContext(appContext);
//		params.put("masterReportTitle", "Requisition List");
//		params.put("masterCurrentUser", "Nahid Hasan");
//		params.put("SUBREPORT_DIR", "classpath:reports/subreports/");
//		params.put("transactionDateFromDMY", "01-01-2017");
//		params.put("transactionDateToDMY", "10-01-2017");
//		params.put("title", "test");
//		params.put("companyLogo", getClass().getClassLoader().getResource("reports/fuas-logo.png").getFile());
//		//params.put("datasource", new JRResultSetDataSource(paySlipService.findAll(pathVariable)));
//		return 	new ModelAndView(view, params);
		return 	new ModelAndView("", params);
	}
}
