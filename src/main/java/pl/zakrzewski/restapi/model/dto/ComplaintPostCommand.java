package pl.zakrzewski.restapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintPostCommand {

    private long userId;

    private long productId;

    private String content;

    private LocalDateTime created;

    @NotBlank
    private String country;

}
