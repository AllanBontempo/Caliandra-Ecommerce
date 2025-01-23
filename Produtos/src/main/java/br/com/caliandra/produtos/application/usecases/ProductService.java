package br.com.caliandra.produtos.application.usecases;

import br.com.caliandra.produtos.application.dto.ProductDTO;
import br.com.caliandra.produtos.domain.entities.Product;
import br.com.caliandra.produtos.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductDTO> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return convertToDTO(product);
    }

    public ProductDTO create(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        return convertToDTO(repository.save(product));
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        product.setNome(productDTO.getNome());
        product.setDescricao(productDTO.getDescricao());
        product.setPreco(productDTO.getPreco());
        product.setEstoque(productDTO.getEstoque());
        product.setIdCategoria(productDTO.getIdCategoria());
        return convertToDTO(repository.save(product));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setPreco(entity.getPreco());
        dto.setEstoque(entity.getEstoque());
        dto.setIdCategoria(entity.getIdCategoria());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product entity = new Product();
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setEstoque(dto.getEstoque());
        entity.setIdCategoria(dto.getIdCategoria());
        return entity;
    }
    
}
