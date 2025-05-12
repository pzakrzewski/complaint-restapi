package pl.zakrzewski.restapi.controller;

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
        if (complaint != null) {
            ComplaintDto complaintDto = ComplaintDtoMapper.mapToComplaintDto(complaint);
            return new ResponseEntity<>(complaintDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/complaints")
    public ResponseEntity<ComplaintDto> addComplaint(@RequestBody @Valid ComplaintPostCommand complaint) {
        ComplaintDto complaintDto = ComplaintDtoMapper.mapToComplaintDto(complaintService.addComplaint(complaint));
        return new ResponseEntity<>(complaintDto, HttpStatus.CREATED);
    }

    @PutMapping("/complaints/{id}")
    public ResponseEntity<ComplaintDto> editComplaint(@PathVariable long id, @RequestBody ComplaintPutCommand complaint) {
        Complaint editedComplaint = complaintService.editComplaint(id, complaint);
        if (editedComplaint != null) {
            ComplaintDto complaintDto = ComplaintDtoMapper.mapToComplaintDto(editedComplaint);
            return new ResponseEntity<>(complaintDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/complaints/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable long id) {
        Complaint complaint = complaintService.getSingleComplaint(id);
        if (complaint != null) {
            complaintService.deleteComplaint(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
