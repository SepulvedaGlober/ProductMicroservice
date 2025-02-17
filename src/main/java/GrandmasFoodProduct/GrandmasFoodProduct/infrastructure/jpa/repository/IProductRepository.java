package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.repository;

import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByFantasyName(String fantasyName);
    Optional<ProductEntity> findByUuid(String uuid);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.fantasyName) LIKE LOWER(CONCAT('%', :keyword, '%'))ORDER BY p.fantasyName ASC ")
    List<ProductEntity> findByFantasyNameContainingIgnoreCase(@Param("keyword") String keyword);
}
