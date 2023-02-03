package com.crisalis.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crisalis.app.model.Cliente;
import com.crisalis.app.model.DetallePedido;
import com.crisalis.app.model.Empresa;
import com.crisalis.app.model.Pedido;
import com.crisalis.app.model.Producto;
import com.crisalis.app.utils.JWTUtil;
import com.crisalis.app.services.ClienteService;
import com.crisalis.app.services.DetallePedidoService;
import com.crisalis.app.services.ImpuestoService;
import com.crisalis.app.services.PedidoService;
import com.crisalis.app.services.ProductoService;


@RestController
public class PedidoController {
	
	
	@Autowired
	private PedidoService pedidoService;
	
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private DetallePedidoService detallePedidoService;
	@Autowired
	private ImpuestoService impuestoService;
	@Autowired
	private ClienteService clienteService;

	
	@Autowired
    private JWTUtil jwtUtil;
	
	 private boolean validarToken(String token) {
	        String usuarioId = jwtUtil.getKey(token);
	        return usuarioId != null;
	 }
	 
	 @RequestMapping(value = "api/pedidos", method = RequestMethod.POST)
	 public void createPedido(@RequestBody Map<String, Object> payload) {
	     Pedido pedidoNew = new Pedido();
	     Integer clienteId = null;
	     if (payload.get("cliente") != null) {
	         clienteId = Integer.valueOf(payload.get("cliente").toString());
	     }
	     Integer empresaId = null;
	     if (payload.get("empresa") != null) {
	         empresaId = Integer.valueOf(payload.get("empresa").toString());
	     }
	     if (clienteId != null && clienteId instanceof Integer) {
	         Cliente cliente = new Cliente();
	         cliente.setId((Integer) clienteId);
	         pedidoNew.setCliente(cliente);
	     }
	     if (empresaId != null && empresaId instanceof Integer) {
	         Empresa empresa = new Empresa();
	         empresa.setId((Integer) empresaId);
	         pedidoNew.setEmpresa(empresa);
	     }
	     double precioTotal = 0;
	     double impuestoIva = impuestoService.findImpuestosByID(1).get().getPorcentaje();
	     double impuestoIibb = impuestoService.findImpuestosByID(2).get().getPorcentaje();
	     double soporte = impuestoService.findImpuestosByID(3).get().getPorcentaje();
	     List<Map<String, Object>> productos = (List<Map<String, Object>>) payload.get("productos");
	     List<DetallePedido> detalles = new ArrayList<>();
	     for (Map<String, Object> producto : productos) {
	         DetallePedido detallePedido = new DetallePedido();
	         Integer productoId = Integer.valueOf(producto.get("id").toString());
	         Optional<Producto> p = productoService.findProductoByID(productoId);
	         detallePedido.setProducto(p.get());
	         detallePedido.setPrecio(p.get().getPrecio());
	         if(p.get().getSoporte() == true) {
	        	 precioTotal += p.get().getPrecio() * soporte + p.get().getPrecio();
	         }else {
	         precioTotal += p.get().getPrecio();
	         }
	         detallePedido.setPedido(pedidoNew);
	         detalles.add(detallePedido);
	         List<Producto> serviciosContratados = clienteService.getServiciosContratados((Integer)clienteId);
	         if (!serviciosContratados.isEmpty()) {
	        	    // agregar el 10% de descuento a precioTotal
	        	    precioTotal = precioTotal - (precioTotal * 0.10);
	        	}
	     }
	     precioTotal += precioTotal * impuestoIva + precioTotal * impuestoIibb;
	     pedidoService.savePedido(pedidoNew);
	     for (DetallePedido detalle : detalles) {
	     detalle.setPedido(pedidoNew);
	     }
	     pedidoNew.setPrecio(precioTotal);
	     pedidoNew.setDetalles(detalles);
	     pedidoService.savePedido(pedidoNew);
	     }

	@RequestMapping(value = "api/pedidos", method = RequestMethod.GET)
	public ListIterator<Pedido> getPedidos(String token) {
		//if (!validarToken(token)) { return null; }
		return pedidoService.getAllPedidos().listIterator();
	}
	
	@RequestMapping(value = "api/pedidos/{id}", method = RequestMethod.GET)
	public Optional<Pedido> getPedidoId(@PathVariable Integer id) {
		//if (!validarToken(token)) { return null; }
		return pedidoService.findPedidoByID(id);
		
	}
	
	@RequestMapping(value = "api/pedidos/productos/{id}", method = RequestMethod.GET)
	public Iterable<Producto> getProductosContratados(@PathVariable Integer id) {
	        List<Producto> productosContratados = clienteService.getProductosContratados(id);
	        return productosContratados;
	}
	
	
	@RequestMapping(value = "api/pedidos/{id}", method = RequestMethod.DELETE)
	public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Integer id) {
		Pedido pedido = this.pedidoService.findPedidoByID(id).orElse(null);
	    if (pedido == null) {
	        return;
	    }
	    List<DetallePedido> detalles = pedido.getDetalles();
	    for (DetallePedido detalle : detalles) {
	        this.detallePedidoService.deleteDetallePedido(detalle.getId());
	    }
	    if (this.pedidoService.existsById(id)) {
	    this.pedidoService.deletePedido(id);
	    }
	}
	
	
	@RequestMapping(value = "api/pedidos/edit/{id}", method = RequestMethod.PUT)
	public void editarCliente(@PathVariable Integer id, @RequestBody Pedido pedido) {
		pedido.setId(id);
		pedidoService.savePedido(pedido);
		
	}
}
