package pl.zakrzewski.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zakrzewski.restapi.exception.ComplaintNotFoundException;
import pl.zakrzewski.restapi.exception.ProductNotFoundException;
import pl.zakrzewski.restapi.exception.UserNotFoundException;
import pl.zakrzewski.restapi.model.Complaint;
import pl.zakrzewski.restapi.model.Product;
import pl.zakrzewski.restapi.model.User;
import pl.zakrzewski.restapi.model.dto.ComplaintPostCommand;
import pl.zakrzewski.restapi.model.dto.ComplaintPutCommand;
import pl.zakrzewski.restapi.repository.ComplaintRepository;
import pl.zakrzewski.restapi.repository.ProductRepository;
import pl.zakrzewski.restapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final GeoLocationService geoLocationService;

    public List<Complaint> getComplaints() {
        List<Complaint> complaints = complaintRepository.findAllComplaints();
        return complaints;
    }

    public Complaint getSingleComplaint(long id) {
        Optional<Complaint> complaint = complaintRepository.findById(id);
        if (complaint.isEmpty()) {
            throw new ComplaintNotFoundException("Complaint not found with id: " + id);
        }
        return complaint.get();
    }

    public Complaint addComplaint(ComplaintPostCommand complaintPostCommand) {
        Optional<Product> product = productRepository.findById(complaintPostCommand.getProductId());
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id: " + complaintPostCommand.getProductId());
        }
        Optional<User> user = userRepository.findById(complaintPostCommand.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + complaintPostCommand.getUserId());
        }
        Optional<Complaint> complaint = complaintRepository
                .findByUserIdAndProductId(complaintPostCommand.getUserId(), complaintPostCommand.getProductId());
        Complaint complaintSaved;
        if (complaint.isPresent()) {
            complaintSaved = complaint.get();
            complaintSaved.increaseCounter();
        } else {
            complaintSaved = Complaint.builder()
                    .product(product.get())
                    .user(user.get())
                    .country(geoLocationService.getCountryByIp(complaintPostCommand.getCountry()))
                    .content(complaintPostCommand.getContent())
                    .created(complaintPostCommand.getCreated())
                    .build();
        }

        complaintRepository.save(complaintSaved);
        return complaintSaved;
    }

    @Transactional
    public Complaint editComplaint(long id, ComplaintPutCommand complaint) {
        Optional<Complaint> complaintEdited = complaintRepository.findById(id);
        if (complaintEdited.isEmpty()) {
            throw new ComplaintNotFoundException("Complaint not found with id: " + id);
        }
        complaintEdited.get().setContent(complaint.getContent());
        return complaintEdited.get();
    }

    @Transactional
    public void deleteComplaint(long id) {
        Optional<Complaint> complaintToDelete = complaintRepository.findById(id);
        if (complaintToDelete.isEmpty()) {
            throw new ComplaintNotFoundException("Complaint not found with id: " + id);
        }
        complaintRepository.deleteById(id);
    }

}
