package pl.zakrzewski.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zakrzewski.restapi.model.Complaint;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findById(Long id);

    @Query("Select p From Complaint p")
    List<Complaint> findAllComplaints();

    Optional<Complaint> findByUserIdAndProductId(Long userId, Long productId);

}
