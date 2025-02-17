package GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.configuration;

import GrandmasFoodProduct.GrandmasFoodProduct.domain.api.IProductServicePort;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.spi.IProductPersistencePort;
import GrandmasFoodProduct.GrandmasFoodProduct.domain.usecase.ProductUseCase;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.adapter.ProductJpaAdapter;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.mapper.ProductEntityMapper;
import GrandmasFoodProduct.GrandmasFoodProduct.infrastructure.jpa.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Bean
    public IProductServicePort productServicePort() {
        return new ProductUseCase(productPersistencePort());
    }

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductJpaAdapter(productRepository, productEntityMapper);
    }

}
