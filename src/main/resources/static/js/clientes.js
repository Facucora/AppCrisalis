// Call the dataTables jQuery plugin
$(document).ready(function() {
	cargarClientes();
  $('#clientesTable').DataTable();
  actualizarEmailDelUsuario()
});

function actualizarEmailDelUsuario() {
  document.getElementById('txt-email-usuario').outerHTML = localStorage.mail;
}

   async function cargarClientes() {
  const request = await fetch('api/clientes', {
    method: 'GET',
    headers: getHeaders()
  }); 

    const clientes = await request.json();

  let listadoHtml = '';
  for (let cliente of clientes) {
    let botonEliminar = '<a href="#" onclick="eliminarCliente(' + cliente.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let botonContratado = '<a href="#" onclick="cargarContratadoCliente(' + cliente.id + ')" id="botonContratado" data-bs-toggle="modal" data-bs-target="#modalTable" class="btn btn-success btn-circle btn-sm"><i class="bi bi-card-list"></i></a>'
    let botonEditar = '<a href="#" onclick="cargarCliente(' + cliente.id + ')" id="botonUpdate" data-bs-toggle="modal" data-bs-target="#staticBackdrop2" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>'
    let clienteHtml = '<tr><td>'+cliente.id+'</td><td>'+cliente.nombre+'</td><td>'+ cliente.apellido+'</td><td>'
                    + cliente.dni+'</td><td class="d-flex justify-content-evenly">' + botonEditar + botonEliminar+ botonContratado+ '</td></tr>';
    listadoHtml += clienteHtml;
  }

document.querySelector('#clientesTable tbody').outerHTML = listadoHtml;
}
            
function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}

async function registrarCliente() {
  let datos = {};
  datos.nombre = document.getElementById('formClienteNombre').value;
  datos.apellido = document.getElementById('formClienteApellido').value;
  datos.dni = document.getElementById('formClienteDni').value;

  const request = await fetch('api/clientes/', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  alert("El Cliente fue creada con exito!");
  window.location.href = 'clientes.html'

}

async function cargarContratadoCliente(id) {
  const request = await fetch('api/pedidos/contratados/' + id, {
    method: 'GET',
    headers: getHeaders()
  }); 
    const contratados = await request.json();

    let tableBody = document.querySelector("#tableContratado tbody");
  tableBody.innerHTML = "";

  contratados.forEach(item => {
    let row = document.createElement("tr");
    let idColumn = document.createElement("td");
    idColumn.innerText = item.id;
    row.appendChild(idColumn);

    let productColumn = document.createElement("td");
    let serviceColumn = document.createElement("td");
    if (item.tipo === "Producto") {
      productColumn.innerText = item.nombre;
      row.appendChild(productColumn);
      serviceColumn.innerText = "";
      row.appendChild(serviceColumn);
    } else if (item.tipo === "Servicio") {
      productColumn.innerText = "";
      row.appendChild(productColumn);
      serviceColumn.innerText = item.nombre;
      row.appendChild(serviceColumn);
    }

    let priceColumn = document.createElement("td");
    priceColumn.innerText = item.precio;
    row.appendChild(priceColumn);

    tableBody.appendChild(row);
  });
    
  };


async function cargarCliente(id) {
  const request = await fetch('api/clientes/' + id, {
    method: 'GET',
    headers: getHeaders()
  }); 
    const cliente = await request.json();
    document.getElementById('formEditClienteId').value = cliente.id;
   	document.getElementById('formEditClienteNombre').value = cliente.nombre;
    document.getElementById('formEditClienteApellido').value = cliente.apellido ;
    document.getElementById('formEditClienteDni').value = cliente.dni;
  	console.log(cliente.id)
  };

  async function cargarContratado(id) {
    const request = await fetch('api/clientes/productos/' + id, {
      method: 'GET',
      headers: getHeaders()
    }); 
      const productos = await request.json();
      
      console.log(productos)
    };

  async function updateCliente() {
    const id = document.getElementById('formEditClienteId').value;
    const nombre = document.getElementById('formEditClienteNombre').value;
    const apellido = document.getElementById('formEditClienteApellido').value;
    const dni = document.getElementById('formEditClienteDni').value;
    const data = {id, nombre, apellido, dni};
    console.log(data);
    try {
      const response = await fetch(`api/clientes/edit/${id}`, {
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.token
        },
        body: JSON.stringify(data)
      });
      if (response.ok) {
        alert('Cliente actualizado con éxito!');
        window.location.href = 'clientes.html';
      } else {
        throw new Error('Hubo un error al actualizar el cliente. Por favor, inténtalo de nuevo.');
      }
    } catch (error) {
      console.error(error);
      alert(error.message);
    }
  }

async function eliminarCliente(id) {

  if (!confirm('¿Desea eliminar este cliente?')) {
    return;
  }

 const request = await fetch('api/clientes/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}



