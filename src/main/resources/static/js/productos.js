// Call the dataTables jQuery plugin
$(document).ready(function() {
	cargarProductos();
  $('#productosTable').DataTable();
  actualizarEmailDelUsuario()
  //cargarImpuesto();
});

function actualizarEmailDelUsuario() {
  document.getElementById('txt-email-usuario').outerHTML = localStorage.mail;
}

   async function cargarProductos() {
  const request = await fetch('api/productos', {
    method: 'GET',
    headers: getHeaders()
  }); 

    
    const productos = await request.json();


  let listadoHtml = '';
  for (let producto of productos) {
    let botonEliminar = '<a href="#" onclick="eliminarProducto(' + producto.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let botonEditar = '<a href="#" onclick="cargarProducto(' + producto.id + ')" id="botonUpdate" data-bs-toggle="modal" data-bs-target="#staticBackdrop2" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>'
    let productoHtml = '<tr><td>'+producto.id+'</td><td>'+producto.nombre+'</td><td>'+ producto.tipo+'</td><td>'
                    + producto.precio+'</td><td class="d-flex justify-content-evenly">' + botonEditar + botonEliminar+ '</td></tr>';
    listadoHtml += productoHtml;
  }

document.querySelector('#productosTable tbody').outerHTML = listadoHtml;

}

const flexRadioDefault = document.querySelectorAll('input[name="flexRadioDefault"]');
const flexSwitchCheckDefault = document.querySelector('#flexSwitchCheckDefault');

flexRadioDefault.forEach(radio => {
  radio.addEventListener('change', function() {
    if (this.value === 'Servicio') {
      flexSwitchCheckDefault.style.display = 'inline-block';
    } else {
      flexSwitchCheckDefault.style.display = 'none';
    }
  });
});


async function cargarImpuesto() {
  const request = await fetch('api/impuestos/' + 1, {
    method: 'GET',
    headers: getHeaders()
  }); 
    const impuesto = await request.json();
    document.getElementById('iva').value = impuesto.iva;
   	document.getElementById('iibb').value = impuesto.iibb;
    document.getElementById('soporte').value = impuesto.soporte;
    console.log(impuesto.iva)  
    console.log(impuesto.iibb) 
    console.log(impuesto.soporte) 
  };

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}

async function registrarProdOServi() {
  let datos = {};
  datos.nombre = document.getElementById('formProdNombre').value;
  datos.tipo = document.querySelector('input[name="flexRadioDefault"]:checked').value;
  datos.soporte = document.getElementById('flexSwitchCheckDefault').checked;
  datos.precio = document.getElementById('formProdPrecio').value;


 
  const request = await fetch('api/productos', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token
    },
    body: JSON.stringify(datos)
  });
  alert("El Producto/Servicio fue creada con exito!");
  window.location.href = 'productos.html'

}

async function cargarProducto(id) {
  const request = await fetch('api/productos/' + id, {
    method: 'GET',
    headers: getHeaders()
  }); 
    const producto = await request.json();
    document.getElementById('formEditProductoId').value = producto.id;
   	document.getElementById('formEditProductoNombre').value = producto.nombre;
    document.getElementById('formEditProductoTipo').value = producto.tipo ;
    document.getElementById('formEditProductoPrecio').value = producto.precio;
  	console.log(producto.id)
  };



  async function updateProducto() {
    const id = document.getElementById('formEditProductoId').value;
    const nombre = document.getElementById('formEditProductoNombre').value;
    const tipo = document.getElementById('formEditProductoTipo').value;
    const precio = document.getElementById('formEditProductoPrecio').value;
    const data = {id, nombre, tipo, precio};
    console.log(data);
    try {
      const response = await fetch(`api/productos/edit/${id}`, {
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.token
        },
        body: JSON.stringify(data)
      });
      if (response.ok) {
        alert('Producto actualizado con éxito!');
        window.location.href = 'productos.html';
      } else {
        throw new Error('Hubo un error al actualizar el producto. Por favor, inténtalo de nuevo.');
      }
    } catch (error) {
      console.error(error);
      alert(error.message);
    }
  }


async function eliminarProducto(id) {

  if (!confirm('¿Desea eliminar este Producto/Servicio?')) {
    return;
  }

 const request = await fetch('api/productos/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

