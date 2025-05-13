package pl.zakrzewski.restapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintPutCommand {

    @NotBlank
    private String content;

}
