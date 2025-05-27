package pl.zakrzewski.restapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private String country;

    private long counter;

    @Version
    private Long version;

    public void increaseCounter() {
        this.counter++;
    }

}
