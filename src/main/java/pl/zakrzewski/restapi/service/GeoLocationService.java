package pl.zakrzewski.restapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zakrzewski.restapi.model.dto.IpApiResponse;

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

            // Send GET request and map the response to custom object
            IpApiResponse response = restTemplate.getForObject(url, IpApiResponse.class);

            if (response.getCountry_name() != null) {
                return response.getCountry_name();
            } else {
                return "Country not found";
            }
        } catch (Exception e) {
            // Log error in real application
            return "Error retrieving country: " + e.getMessage();
        }
    }

}
