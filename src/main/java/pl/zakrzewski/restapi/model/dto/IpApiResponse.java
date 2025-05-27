package pl.zakrzewski.restapi.model.dto;

import lombok.Data;

@Data
public class IpApiResponse {

    private String ip;
    private String network;
    private String version;
    private String city;
    private String region;
    private String region_code;
    private String country;
    private String country_name;
    private String country_code;
    private String country_code_iso3;
    private String country_capital;
    private String country_tld;
    private String continent_code;
    private Boolean in_eu;
    private String postal;
    private Double latitude;
    private Double longitude;
    private String timezone;
    private String utc_offset;
    private String country_calling_code;
    private String currency;
    private String currency_name;
    private String languages;
    private Double country_area;
    private Integer country_population;
    private String asn;
    private String org;
    private Boolean error;
    private String reason;
    private Boolean reserved;

}
