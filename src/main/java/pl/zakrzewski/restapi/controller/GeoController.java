package pl.zakrzewski.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zakrzewski.restapi.service.GeoLocationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geo")
public class GeoController {

    private GeoLocationService geoLocationService;

    @GetMapping("/country")
    public String getCountry(@RequestParam String ip) {
        return geoLocationService.getCountryByIp(ip);
    }

}
