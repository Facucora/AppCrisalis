// Call the dataTables jQuery plugin
$(document).ready(function() {
	cargarUsuarios();
  cargarImpuestos();
  $('#usuariosTable').DataTable();
   actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.mail;
}

async function cargarUsuarios() {
  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();


  let listadoHtml = '';
  for (let usuario of usuarios) {
    let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+'</td><td>'+ usuario.apellido+'</td><td>'
                    + usuario.mail+'</td><td>'+usuario.password
                    + '</td><td class="d-flex justify-content-evenly">' + botonEliminar + '</td></tr>';
    listadoHtml += usuarioHtml;
  }

document.querySelector('#usuariosTable tbody').outerHTML = listadoHtml;

}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}


async function eliminarUsuario(id) {

  if (!confirm('¿Desea eliminar este usuario?')) {
    return;
  }

 const request = await fetch('api/usuarios/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function cargarImpuestos() {
  const request = await fetch('api/impuestos', {
    method: 'GET',
    headers: getHeaders()
  });
  const impuestos = await request.json();

  let tableBody = document.querySelector("#impuestosTableBody");
  tableBody.innerHTML = "";

  impuestos.forEach(impuesto => {
    let row = document.createElement("tr");
    let idColumn = document.createElement("td");
    idColumn.setAttribute("id", "idTable");

    idColumn.innerText = impuesto.id;
    row.appendChild(idColumn);

    let nombreColumn = document.createElement("td");
    nombreColumn.innerText = impuesto.nombre;
    nombreColumn.setAttribute("id", "nombreTable");
    row.appendChild(nombreColumn);

    let porcentajeColumn = document.createElement("td");
    porcentajeColumn.innerText = impuesto.porcentaje + "%";
    porcentajeColumn.setAttribute("id", "porcentajeTable");

    row.appendChild(porcentajeColumn)

    let actionColumn = document.createElement("td");
    let editButton = document.createElement("button");
    editButton.innerText = "Editar";
    editButton.classList.add("btn", "btn-primary", "btn-sm", "me-1");
    editButton.onclick="updateImpuesto(' + cliente.id + ')";
    actionColumn.appendChild(editButton);
    let deleteButton = document.createElement("button");
    deleteButton.innerText = "Eliminar";
    deleteButton.classList.add("btn", "btn-danger", "btn-sm", "me-1");
    actionColumn.appendChild(deleteButton);
    row.appendChild(actionColumn);

    tableBody.appendChild(row);
  })

}

async function eliminarImpuesto(id) {

  if (!confirm('¿Desea eliminar este impuesto?')) {
    return;
  }

 const request = await fetch('api/impuestos/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
}

async function updateImpuesto() {
  const id = document.getElementById('idTable').value;
  const nombre = document.getElementById('nombreTable').value;
  const porcentaje = document.getElementById('porcentajeTable').value;
  const data = {id, nombre, porcentaje};
  console.log(data);
  try {
    const response = await fetch(`api/impuestos/${id}`, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
      },
      body: JSON.stringify(data)
    });
    if (response.ok) {
      alert('Impuesto actualizado con éxito!');
      window.location.href = 'usuarios.html';
    } else {
      throw new Error('Hubo un error al actualizar el impuesto. Por favor, inténtalo de nuevo.');
    }
  } catch (error) {
    console.error(error);
    alert(error.message);
  }
}