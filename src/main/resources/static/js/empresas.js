// Call the dataTables jQuery plugin
$(document).ready(function() {
	cargarEmpresas();
	cargarClientesEnEmpresas();
  $('#empresasTable').DataTable();
  actualizarEmailDelUsuario()
});

function actualizarEmailDelUsuario() {
  document.getElementById('txt-email-usuario').outerHTML = localStorage.mail;
}

   async function cargarEmpresas() {
  const request = await fetch('api/empresas', {
    method: 'GET',
    headers: getHeaders()
  }); 
    
    const empresas = await request.json();


  let listadoHtml = '';
  for (let empresa of empresas) {
    let botonEliminar = '<a href="#" onclick="eliminarEmpresa(' + empresa.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let botonContratado = '<a href="#" onclick="cargarContratadoEmpresa(' + empresa.id + ')" id="botonContratado" data-bs-toggle="modal" data-bs-target="#modalTable" class="btn btn-success btn-circle btn-sm"><i class="bi bi-card-list"></i></a>'
    let botonEditar = '<a href="#" onclick="cargarEmpresa(' + empresa.id + ')" id="botonUpdate" data-bs-toggle="modal" data-bs-target="#staticBackdrop2Edit" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>'
    let empresaHtml = '<tr><td>'+empresa.id+'</td><td>'+empresa.nombre+'</td><td>'+ empresa.razonsocial+'</td><td>'
                    + empresa.cuit+'</td><td>'+empresa.fechainicio+'</td><td class="d-flex justify-content-evenly">' + botonEditar + botonEliminar+ botonContratado+'</td></tr>';
    listadoHtml += empresaHtml;
  }

document.querySelector('#empresasTable tbody').outerHTML = listadoHtml;

}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}


async function registrarEmpresa() {
  let clienteId = document.getElementById('inputGroupSelect01').value;
  const clienteRequest = await fetch(`api/clientes/${clienteId}`, {
    method: 'GET',
    headers: getHeaders()
  }); 
  const cliente = await clienteRequest.json();

  let datos = {};
  datos.nombre = document.getElementById('formEmpresaNombre').value;
  datos.razonsocial = document.getElementById('formEmpresaRazonSocial').value;
  datos.cuit = document.getElementById('formEmpresaCuit').value;
  datos.fechainicio = document.getElementById('formEmpresaFechaInicio').value;
  datos.cliente = cliente;

  const request = await fetch('api/empresas/', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  console.log(request)
  alert("La empresa fue creada con éxito!");
  window.location.href = 'empresas.html'
}


async function cargarContratadoEmpresa(id) {
  const request = await fetch('api/empresas/contratados/' + id, {
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


async function cargarEmpresa(id) {
  const request = await fetch('api/empresas/' + id, {
    method: 'GET',
    headers: getHeaders()
  }); 
    const empresa = await request.json();
    document.getElementById('formEditEmpresaId').value = empresa.id;
   	document.getElementById('formEditEmpresaNombre').value = empresa.nombre;
    document.getElementById('formEditEmpresaCuit').value = empresa.cuit;
    document.getElementById('formEditEmpresaRazonSocial').value = empresa.razonsocial ;
    document.getElementById('formEditEmpresaFechaInicio').value = empresa.fechainicio ;
    document.getElementById('EditinputGroupSelect01').value = empresa.cliente.nombre ;

  	console.log(empresa.cliente.nombre)
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

  async function updateEmpresa() {
    const id = document.getElementById('formEditEmpresaId').value;
    const nombre = document.getElementById('formEditEmpresaNombre').value;
    const cuit = document.getElementById('formEditEmpresaCuit').value;
    const razonsocial = document.getElementById('formEditEmpresaRazonSocial').value;
    const fechainicio = document.getElementById('formEditEmpresaFechaInicio').value;
    const cliente = document.getElementById('EditinputGroupSelect01').value;

    const data = {id, nombre, cuit, razonsocial, fechainicio, cliente};
    console.log(data);
    try {
      const response = await fetch(`api/empresas/edit/${id}`, {
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': localStorage.token
        },
        body: JSON.stringify(data)
      });
      if (response.ok) {
        alert('Empresa actualizado con éxito!');
        window.location.href = 'empresas.html';
      } else {
        throw new Error('Hubo un error al actualizar la Empresa. Por favor, inténtalo de nuevo.');
      }
    } catch (error) {
      console.error(error);
      alert(error.message);
    }
  }


async function eliminarEmpresa(id) {

  if (!confirm('¿Desea eliminar esta empresa?')) {
    return;
  }

 const request = await fetch('api/empresas/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function cargarClientesEnEmpresas() {
  const request = await fetch('api/clientes', {
    method: 'GET',
    headers: getHeaders()
  }); 

    
    const clientes = await request.json();


  let listadoHtml = '';
  for (let cliente of clientes) {
    let clienteHtml = '<option value="'+cliente.id+'">'+cliente.nombre+'</option>';
    listadoHtml += clienteHtml;
  }
console.log(listadoHtml)
document.getElementById('inputGroupSelect01').innerHTML = listadoHtml;
}