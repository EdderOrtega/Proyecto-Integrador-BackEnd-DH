document.addEventListener("DOMContentLoaded", () => {
  fetchOdontologos();
});

function fetchOdontologos() {
  fetch("odontologo/listarTodos")
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then((data) => {
      const tableBody = document.querySelector("#odontologoTable tbody");
      tableBody.innerHTML = "";
      data.forEach((odontologo) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${odontologo.id}</td>
          <td>${odontologo.apellido}</td>
          <td>${odontologo.nombre}</td>
          <td>${odontologo.nroMatricula}</td>
          <td>
            <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.apellido}', '${odontologo.nombre}', '${odontologo.nroMatricula}')">Modificar</button>
            <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
          </td>
        `;
        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

function editOdontologo(id, apellido, nombre, nroMatricula) {
  const editModal = new bootstrap.Modal(document.getElementById("editModal"));
  document.getElementById("editApellido").value = apellido;
  document.getElementById("editNombre").value = nombre;
  document.getElementById("editNroMatricula").value = nroMatricula;
  document.getElementById("editForm").onsubmit = function (event) {
    event.preventDefault();
    const updatedOdontologo = {
      id: id,
      apellido: document.getElementById("editApellido").value,
      nombre: document.getElementById("editNombre").value,
      nroMatricula: document.getElementById("editNroMatricula").value,
    };
    fetch(`odontologo/actualizar`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedOdontologo),
    })
      .then((response) => response.json())
      .then((data) => {
        alert("Odontólogo modificado con éxito");
        fetchOdontologos();
        editModal.hide();
      })
      .catch((error) => {
        console.error("Error editing odontologo:", error);
      });
  };
  editModal.show();
}

function deleteOdontologo(id) {
  if (confirm("¿Está seguro de que desea eliminar este odontólogo?")) {
    fetch(`odontologo/borrar/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            throw new Error(
              `HTTP error! status: ${response.status}, message: ${text}`
            );
          });
        }
        return response.text();
      })
      .then((data) => {
        alert("Odontólogo eliminado con éxito");
        fetchOdontologos();
      })
      .catch((error) => {
        console.error("Error deleting odontologo:", error);
      });
  }
}
