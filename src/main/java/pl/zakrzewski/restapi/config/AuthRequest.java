package pl.zakrzewski.restapi.config;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

}
