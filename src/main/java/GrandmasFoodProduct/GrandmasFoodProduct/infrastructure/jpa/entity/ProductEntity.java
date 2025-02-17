package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.entity;

import GrandmasFoodProduct.GrandmasFoodProduct.domain.models.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;
    @Column(name = "fantasy_name", nullable = false, length = 255)
    private String fantasyName;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    @Column(name = "description", nullable = false, length = 511)
    private String description;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "available", nullable = false)
    private boolean available;

}
