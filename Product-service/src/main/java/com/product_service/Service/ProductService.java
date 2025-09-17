package com.product_service.Service;

import com.product_service.Exceptions.ProductNotFoundException;
import com.product_service.Model.Product;
import com.product_service.Model.ProductDTO;
import com.product_service.Dao.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //get all products
    public Page<Product> getallproducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    //get product by ID
    public Optional<Product> getproductbyid(Long id){
        return productRepository.findById(id);
    }

    // Add new product//used optional
    public Product addProduct(ProductDTO productdto) {
        Product product = Product.builder()
                .name(productdto.getName())
                .price(productdto.getPrice())
                .description(productdto.getDescription())
                .quantity(productdto.getQuantity())
                .build();
        return productRepository.save(product);
    }


    //delete product
    public void deletebyid(Long id){
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Cannot delete. Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    //save bulk//used stream api
    public List<Product> savebulk(List<ProductDTO> productDTOList) {
        List<Product> products =productDTOList.stream().map(dto ->{
            Product product =new Product();
            product.setPrice(dto.getPrice());
            product.setName(dto.getName());
            product.setQuantity(dto.getQuantity());
            product.setDescription(dto.getDescription());
            return product;
        }).toList();
        return productRepository.saveAll(products);
    }

    public Product updateProduct(Long id, ProductDTO productDto) {
        return productRepository.findById(id)
                .map(product -> {
                    // Update fields using setters
                    product.setName(productDto.getName());
                    product.setPrice(productDto.getPrice());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }




}
