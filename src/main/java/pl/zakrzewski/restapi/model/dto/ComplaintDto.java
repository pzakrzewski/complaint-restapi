package pl.zakrzewski.restapi.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ComplaintDto {

    private long id;

    private long userId;

    private long productId;

    private String content;

    private LocalDateTime created;

    private String country;

    private long counter;

}
