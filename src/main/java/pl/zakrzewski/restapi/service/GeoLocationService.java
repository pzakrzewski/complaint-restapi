package pl.zakrzewski.restapi.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zakrzewski.restapi.exception.CountryNotFoundException;
import pl.zakrzewski.restapi.model.dto.IpApiResponse;

@Service
public class GeoLocationService {

    private final RestTemplate restTemplate;

    public GeoLocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "ipapi", fallbackMethod = "fallbackCountry")
    @CircuitBreaker(name = "ipapi", fallbackMethod = "fallbackCountry")
    public String getCountryByIp(String ip) {
        // API URL for IP info
        String url = "https://ipapi.co/" + ip + "/json/";

        // Send GET request and map the response to custom object
        IpApiResponse response = restTemplate.getForObject(url, IpApiResponse.class);

        if (response.getCountry_name() != null) {
            return response.getCountry_name();
        } else {
            throw new CountryNotFoundException("Invalid IP response");
        }
    }

    public String fallbackCountry(String ip, Throwable t) {
        // Log the error if needed
        return "Country not found";
    }

}
