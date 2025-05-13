package pl.zakrzewski.restapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.zakrzewski.restapi.model.Complaint;
import pl.zakrzewski.restapi.model.ComplaintDtoMapper;
import pl.zakrzewski.restapi.model.dto.ComplaintDto;
import pl.zakrzewski.restapi.model.dto.ComplaintPostCommand;
import pl.zakrzewski.restapi.model.dto.ComplaintPutCommand;
import pl.zakrzewski.restapi.service.ComplaintService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintDto>> getComplaints() {
        List<Complaint> complaints = complaintService.getComplaints();
        List<ComplaintDto> complaintDtos = ComplaintDtoMapper.mapToComplaintDtos(complaints);
        return new ResponseEntity<>(complaintDtos, HttpStatus.OK);
    }

    @GetMapping("/complaints/{id}")
    public ResponseEntity<ComplaintDto> getSingleComplaint(@PathVariable("id") long id) {
        Complaint complaint = complaintService.getSingleComplaint(id);
        ComplaintDto complaintDto = ComplaintDtoMapper.mapToComplaintDto(complaint);
        return new ResponseEntity<>(complaintDto, HttpStatus.OK);
    }

    @PostMapping("/complaints")
    public ResponseEntity<ComplaintDto> addComplaint(@RequestBody @Valid ComplaintPostCommand complaintPostCommand, HttpServletRequest request) {
        Complaint complaint = complaintService.addComplaint(complaintPostCommand, request.getRemoteAddr());
        ComplaintDto complaintDto = ComplaintDtoMapper.mapToComplaintDto(complaint);
        return new ResponseEntity<>(complaintDto, HttpStatus.CREATED);
    }

    @PutMapping("/complaints/{id}")
    public ResponseEntity<ComplaintDto> editComplaint(@PathVariable long id, @RequestBody @Valid ComplaintPutCommand complaint) {
        Complaint editedComplaint = complaintService.editComplaint(id, complaint);
        ComplaintDto complaintDto = ComplaintDtoMapper.mapToComplaintDto(editedComplaint);
        return new ResponseEntity<>(complaintDto, HttpStatus.OK);
    }

    @DeleteMapping("/complaints/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable long id) {
        complaintService.deleteComplaint(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
