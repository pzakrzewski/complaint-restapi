package pl.zakrzewski.restapi.model;

import pl.zakrzewski.restapi.model.dto.ComplaintDto;

import java.util.List;
import java.util.stream.Collectors;

public class ComplaintDtoMapper {

    private ComplaintDtoMapper() {

    }

    public static List<ComplaintDto> mapToComplaintDtos(List<Complaint> complaints) {
        return complaints.stream()
                .map(complaint -> mapToComplaintDto(complaint))
                .collect(Collectors.toList());
    }

    public static ComplaintDto mapToComplaintDto(Complaint complaint) {
        return ComplaintDto.builder()
                .id(complaint.getId())
                .userId(complaint.getUser().getId())
                .productId(complaint.getProduct().getId())
                .content(complaint.getContent())
                .created(complaint.getCreated())
                .country(complaint.getCountry())
                .counter(complaint.getCounter())
                .build();
    }

}
