$(document).ready(function() {
   // on ready
});


async function iniciarSesion() {
  let datos = {};
  datos.mail = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;

  
  const request = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  
  const respuesta = await request.text();
  if (respuesta != 'FAIL') {
    localStorage.token = respuesta;
    localStorage.mail = datos.mail;
    window.location.href = 'usuarios.html'
  } else {
    alert("Las credenciales son incorrectas. Por favor intente nuevamente.");
  }
  //window.location.href = 'login.html'

}
