package pl.zakrzewski.restapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeoLocationService {

    private final RestTemplate restTemplate;

    public GeoLocationService() {
        this.restTemplate = new RestTemplate();
    }

    public String getCountryByIp(String ip) {
        try {
            // API URL for IP info
            String url = "https://ipapi.co/" + ip + "/json/";

            // Send GET request and map the response to a Map
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("country_name")) {
                return (String) response.get("country_name");
            } else {
                return "Country not found";
            }
        } catch (Exception e) {
            // Log error in real application
            return "Error retrieving country: " + e.getMessage();
        }
    }

}
