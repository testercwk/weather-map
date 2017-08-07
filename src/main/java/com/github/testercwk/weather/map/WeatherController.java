package com.github.testercwk.weather.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.testercwk.weather.map.http.HttpService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private HttpService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String initialHomePage(ModelMap model) {

        model.addAttribute("message", "Spring 3 MVC Hello World");
        return "home";

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestBody String city, ModelMap model) {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper mapper = new ObjectMapper();
        try {
            city = StringUtils.substringAfter(city, "=");
            String url = "http://api.openweathermap.org/data/2.5/weather?appid=2eed2146552e839cd04731a71ead6cbf&q=" + city;
            String result = service.callOpenWeather(url);

            Map map = mapper.readValue(result, Map.class);

            city = city.replace("+", " ");
            if (map.get("name") != null && StringUtils.equalsIgnoreCase(city, map.get("name").toString())) {
                model.addAttribute("now", format.format(now));
                model.addAttribute("city", city);
                model.addAttribute("weather", ((List<Map>) map.get("weather")).get(0).get("description"));
                NumberFormat numberFormat = new DecimalFormat("#0.00");
                Double celsius = ((Double) ((Map) map.get("main")).get("temp")) - 273.15;
                Double fahrenheit = ((Double) ((Map) map.get("main")).get("temp")) - 459.67;
                model.addAttribute("temperature", numberFormat.format(celsius));
                model.addAttribute("temperature2", numberFormat.format(fahrenheit));

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                long sunriseLong = Long.valueOf((Integer) ((Map) map.get("sys")).get("sunrise")) * 1000;
                Date sunriseDate = new Date(sunriseLong);
                model.addAttribute("sunrise", timeFormat.format(sunriseDate));

                long sunsetLong = Long.valueOf((Integer) ((Map) map.get("sys")).get("sunset")) * 1000;
                Date sunsetDate = new Date(sunsetLong);
                model.addAttribute("sunset", timeFormat.format(sunsetDate));
            } else {
                model.addAttribute("errorMessage", "city not found");
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result";
    }


}
