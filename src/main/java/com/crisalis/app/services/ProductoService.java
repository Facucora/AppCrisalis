package com.crisalis.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crisalis.app.dao.ProductoRepository;
import com.crisalis.app.model.Producto;

@Service
public class ProductoService {
	
	@Autowired
	private final ProductoRepository productoRepository;
	@Autowired
	private ImpuestoService impuestoService;
	
	public ProductoService(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	public Producto saveProducto(Producto producto) {
			return this.productoRepository.save(producto);
	}
	

	public Iterable<Producto> getAllProductos() {
		return this.productoRepository.findAll();
	}
	
	public Iterable<Producto> getAllProductosImpuestos() {
		float porcentajeIvaIibb = impuestoService.findImpuestosByID(1).get().getPorcentaje() + impuestoService.findImpuestosByID(2).get().getPorcentaje();
	    float procentajeSoporte = impuestoService.findImpuestosByID(3).get().getPorcentaje();
 
		Iterable<Producto> productos = this.productoRepository.findAll();
		    for (Producto producto : productos) {
		    	double precioRedondeado = (Math.round(producto.getPrecio() * (1 + porcentajeIvaIibb)));
		    	if (producto.getSoporte()) {
		            precioRedondeado = Math.round(producto.getPrecio() * (1 + procentajeSoporte+porcentajeIvaIibb));
		        }
		    	producto.setPrecio(precioRedondeado);
		    }
		    return productos;
	}


	public Optional<Producto> findProductoByID(Integer id) {
		return this.productoRepository.findById(id);
	}

	public void deleteProducto(Integer id) {
		this.productoRepository.deleteById(id);
	}

}
