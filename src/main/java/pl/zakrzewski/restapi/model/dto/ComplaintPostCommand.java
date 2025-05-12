package pl.zakrzewski.restapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ComplaintPostCommand {

    private Long userId;

    private Long productId;

    private String content;

    private LocalDateTime created;

    @NotBlank
    private String country;

}
