package pl.zakrzewski.restapi.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintPutCommand {

    private String content;

}
