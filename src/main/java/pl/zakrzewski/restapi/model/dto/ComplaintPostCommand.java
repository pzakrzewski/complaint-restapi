package pl.zakrzewski.restapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintPostCommand {

    private Long userId;

    private Long productId;

    private String content;

    @NotBlank
    private String country;

}
