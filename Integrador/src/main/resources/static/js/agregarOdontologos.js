const form = document.getElementById("agregarForm");

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const apellido = document.getElementById("apellido").value;
  const nombre = document.getElementById("nombre").value;
  const nroMatricula = document.getElementById("nroMatricula").value;

  const datosFormulario = {
    apellido,
    nombre,
    nroMatricula,
  };

  fetch("odontologo/guardar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(datosFormulario),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error en la petición");
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
      alert("Odontólogo agregado con éxito");
      form.reset(); // Resetear el formulario
    })
    .catch((error) => {
      console.error("Error agregando odontólogo:", error);
      alert("Error al agregar odontólogo. Intente de nuevo.");
    });
});
