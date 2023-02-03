// Call the dataTables jQuery plugin
$(document).ready(function () {
  cargarClientesEnPedidos();
  cargarEmpresasEnPedidos();
  cargarProductosEnPedidos();
  cargarPedidos()
  $("#pedidosTable").DataTable();
  actualizarEmailDelUsuario();
  cargarImpuestos()
  cargarProductosImp()
});

function actualizarEmailDelUsuario() {
  document.getElementById("txt-email-usuario").outerHTML = localStorage.mail;
}

async function registrarPedido() {
  let datos = {};

   if (document.getElementById("selectCliente").value !== "Cliente...") {
     datos.cliente = document.getElementById("selectCliente").value;
   } else if (document.getElementById("selectEmpresa").value !== "Empresa...") {
     datos.empresa = document.getElementById("selectEmpresa").value;
   }

  datos.productos = Array.from(
    document.querySelectorAll("#tabla-productos tbody tr")
  ).map((tr) => ({
    id: tr.querySelectorAll("td")[0].textContent.replace("id: ", ""),
  }));
  datos.precio = document.getElementById("total").value;
  datos.cantidad = 1;
  const request = await fetch("api/pedidos/", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(datos),
  });
  alert("El Pedido fue creada con exito!");
  window.location.href = "pedidos.html";
}

async function cargarPedidos() {
  const request = await fetch("api/pedidos", {
    method: "GET",
    headers: getHeaders(),
  });

  const pedidos = await request.json();

  let listadoHtml = "";
  for (let pedido of pedidos) {
    let botonEliminar =
      '<a href="#" onclick="eliminarPedido(' +
      pedido.id +
      ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let botonEditar =
      '<a href="#" onclick="cargarPedido(' +
      pedido.id +
      ')" id="botonUpdate" data-bs-toggle="modal" data-bs-target="#staticBackdrop2Edit" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';
      let nombre = "";
      if (pedido.cliente) {
        nombre = pedido.cliente.nombre + " " + pedido.cliente.apellido;
      } else if (pedido.empresa) {
        nombre = pedido.empresa.nombre;
      }
      let pedidoHtml =
      "<tr><td>" +
      pedido.id +
      "</td><td>" +
      nombre +
      "</td><td>" +
      pedido.cantidad +
      "</td><td>" +
      pedido.precio +
      "</td><td>" +
      pedido.fechaCreacion +
      "</td><td class='d-flex justify-content-evenly'>" +
      botonEditar +
      botonEliminar +
      "</td></tr>";
    listadoHtml += pedidoHtml;
  }

  document.querySelector("#pedidosTable tbody").outerHTML = listadoHtml;
}

async function cargarServicioContratado(id) {
  const request = await fetch('api/clientes/productos/' + id, {
    method: 'GET',
    headers: getHeaders()
  }); 
    const servicios = await request.json();
    if (servicios.length > 0) {
      alert("Aplica descuento de 10%");
    }
    console.log(servicios)
  };


async function eliminarPedido(id) {
  if (!confirm("¿Desea eliminar Pedido?")) {
    return;
  }

  const request = await fetch("api/pedidos/" + id, {
    method: "DELETE",
    headers: getHeaders(),
  });
  console.log(request);

  location.reload();
}

function getHeaders() {
  return {
    Accept: "application/json",
    "Content-Type": "application/json",
    Authorization: localStorage.token,
  };
}

 async function cargarPedido(id) {
   const request = await fetch('api/pedidos/' + id, {
     method: 'GET',
     headers: getHeaders()
   });
     const pedido = await request.json();

     console.log(pedido)

 };

async function cargarClientesEnPedidos() {
  const request = await fetch("api/clientes", {
    method: "GET",
    headers: getHeaders(),
  });
  const clientes = await request.json();

  let listadoHtml = "<option selected>Cliente...</option>";
  for (let cliente of clientes) {
    let clienteHtml =
      '<option value="' + cliente.id + '">' + cliente.nombre + "</option>";
    listadoHtml += clienteHtml;
  }
  console.log(listadoHtml);
  document.getElementById("selectCliente").innerHTML = listadoHtml;
}

async function cargarEmpresasEnPedidos() {
  const request = await fetch("api/empresas", {
    method: "GET",
    headers: getHeaders(),
  });
  const empresas = await request.json();

  let listadoHtml = "<option selected>Empresas...</option>";
  for (let empresa of empresas) {
    let empresaHtml =
      '<option value="' + empresa.id + '">' + empresa.nombre + "</option>";
    listadoHtml += empresaHtml;
  }
  console.log(listadoHtml);
  document.getElementById("selectEmpresa").innerHTML = listadoHtml;
}

async function cargarImpuestos() {
  const request = await fetch("api/impuestos", {
    method: "GET",
    headers: getHeaders(),
  });
  const impuestos = await request.json();
  return impuestos;
}
async function cargarProductosImp() {
  const request = await fetch("api/productosimp", {
    method: "GET",
    headers: getHeaders(),
  });
  const productos = await request.json();
  console.log(productos)
}
let productos;
function obtenerPrecioProducto(idProducto, productos) {
  for (let producto of productos) {
    if (producto.id === idProducto) {
      return producto.precio;
    }
  }
  return null;
}

async function cargarProductosEnPedidos() {
  const request = await fetch("api/productosimp", {
    method: "GET",
    headers: getHeaders(),
  });
  productos = await request.json();
  let listadoHtml = "<option selected>Productos & Servicios...</option>";

  for (let producto of productos) {
    let productoHtml =
      '<option value="' +
      producto.id +
      '">' +
      producto.nombre +
      " " +
      producto.tipo +
      "</option>";
    listadoHtml += productoHtml;
  }
  document.getElementById("selectProducto").innerHTML = listadoHtml;
}

let productosSeleccionados = [];

$("#agregar-producto").click(async function () {
  let productoSeleccionado = $("#selectProducto").val();
  productosSeleccionados.push(productoSeleccionado);
  let producto = productos.find((p) => p.id == productoSeleccionado);
  let id = producto.id;
  let nombre = producto.nombre;
  let tipo = producto.tipo;
  let precio = producto.precio;



  let fila = "<tr><td>" + id + "</td><td>" + nombre + "</td>";
  if (tipo === "Producto") {
    fila += '<td><input class="form-check-input ms-4" type="checkbox" role="switch" id="flexSwitchCheckDefault"></td>'; 
  } else{
    fila += "<td>"+"</td>"
  }
  fila += "<td>" + precio + '</td><td><button class="eliminar-producto btn btn-danger btn-sm" data-producto="' + productoSeleccionado + '">Eliminar</button></td></tr>';
  $("#tabla-productos tbody").append(fila);
  actualizarTotal();
});

$(document).on("click", ".eliminar-producto", function () {
  let producto = $(this).data("producto");
  let index = productosSeleccionados.indexOf(producto);
  productosSeleccionados.splice(index, 1);
  $(this).closest("tr").remove();
  actualizarTotal();
});

function actualizarTotal() {
  let total = 0;
  $("#tabla-productos tbody tr").each(function () {
    total += parseFloat($(this).find("td:nth-child(4)").text());
  });
  $("#total").text(total);
}
$("#selectCliente").change(function () {
  $("#selectEmpresa").prop("disabled", true);
});

$("#selectEmpresa").change(function () {
  $("#selectCliente").prop("disabled", true);
});

//   //Deshabilitar select no usado
//   document.getElementById("selectCliente").addEventListener("change", function(){
//    var selectedValue = this.value;
//    if(selectedValue !== "Cliente..."){
//      document.getElementById("selectCliente").disabled = true;
//    } else {
//      document.getElementById("selectCliente").disabled = false;
//    }
//  });

//  document.getElementById("selectEmpresa").addEventListener("change", function(){
//    var selectedValue = this.value;
//    if(selectedValue !== "Empresas..."){
//      document.getElementById("selectEmpresa").disabled = true;
//   } else {
//     document.getElementById("selectEmpresa").disabled = false;
//   }
//  });
