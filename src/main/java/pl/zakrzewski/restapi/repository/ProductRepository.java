package pl.zakrzewski.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zakrzewski.restapi.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

}
