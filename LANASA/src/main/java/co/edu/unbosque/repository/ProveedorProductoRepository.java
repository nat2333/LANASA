package co.edu.unbosque.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.ProveedorProducto;

public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto, Integer>{

	boolean existsByProducto_IdProductoAndProveedor_IdProveedor(Integer idProducto, Integer idProveedor);
    
	Optional<ProveedorProducto> findByProducto_IdProductoAndProveedor_IdProveedor(Integer idProducto, Integer idProveedor);

    List<ProveedorProducto> findByProducto_IdProducto(Integer idProducto);
    
    List<ProveedorProducto> findByProveedor_IdProveedor(Integer idProveedor);
    
    List<ProveedorProducto> findByEstado(Boolean estado);
}
