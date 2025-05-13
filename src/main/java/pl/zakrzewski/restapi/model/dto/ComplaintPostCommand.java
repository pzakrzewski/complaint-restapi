package pl.zakrzewski.restapi.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintPostCommand {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    private String content;

}
