package com.product_service.Controller;

import com.product_service.Model.Product;
import com.product_service.Model.ProductDTO;
import com.product_service.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getallproduct(Pageable pageable) {
        return ResponseEntity.ok(productService.getallproducts(pageable).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getproductbyid(@PathVariable Long id) {
        return productService.getproductbyid(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addproduct(@RequestBody @Valid ProductDTO productdto) {
        Product saved = productService.addProduct(productdto);
        return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deletebyid(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/add/bulk")
    public ResponseEntity<List<Product>> savebulk(@RequestBody @Valid List<ProductDTO> productDTO) {
        List<Product> saveproducts = productService.savebulk(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveproducts);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDto) {

        Product updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }


}
