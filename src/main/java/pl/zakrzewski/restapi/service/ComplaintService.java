package pl.zakrzewski.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return complaintRepository.findById(id).orElse(null);
    }

    public Complaint addComplaint(ComplaintPostCommand complaintPostCommand) {
        Optional<Product> product = productRepository.findById(complaintPostCommand.getProductId());
        if (product.isEmpty()) {
            throw new RuntimeException();
        }
        Optional<User> user = userRepository.findById(complaintPostCommand.getUserId());
        if (user.isEmpty()) {
            throw new RuntimeException();
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
        Complaint complaintEdited = complaintRepository.findById(id).orElse(null);
        if (complaintEdited != null) {
            complaintEdited.setContent(complaint.getContent());
            return complaintEdited;
        }
        return complaintEdited;
    }

    @Transactional
    public void deleteComplaint(long id) {
        complaintRepository.deleteById(id);
    }

}
