document.addEventListener("DOMContentLoaded", () => {
  fetchPacientes();
});

function fetchPacientes() {
  fetch("paciente/buscartodos")
    .then((response) => response.json())
    .then((data) => {
      const tableBody = document.querySelector("#pacienteTable tbody");
      tableBody.innerHTML = "";
      data.forEach((paciente) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${paciente.id}</td>
          <td>${paciente.apellido}</td>
          <td>${paciente.nombre}</td>
          <td>${paciente.dni}</td>
          <td>${paciente.fechaIngreso}</td>
          <td>${paciente.domicilio.calle}</td>
          <td>${paciente.domicilio.numero}</td>
          <td>${paciente.domicilio.localidad}</td>
          <td>${paciente.domicilio.provincia}</td>
          <td>
            <button class="btn btn-primary btn-sm" onclick="editPaciente(${paciente.id}, '${paciente.apellido}', '${paciente.nombre}', '${paciente.dni}', '${paciente.fechaIngreso}', '${paciente.domicilio.id}', '${paciente.domicilio.calle}', '${paciente.domicilio.numero}', '${paciente.domicilio.localidad}', '${paciente.domicilio.provincia}')">Modificar</button>
            <button class="btn btn-danger btn-sm" onclick="deletePaciente(${paciente.id})">Eliminar</button>
          </td>
        `;
        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

function editPaciente(
  id,
  apellido,
  nombre,
  dni,
  fechaIngreso,
  idDomicilio,
  calle,
  numero,
  localidad,
  provincia
) {
  const editModal = new bootstrap.Modal(document.getElementById("editModal"));
  document.getElementById("editApellido").value = apellido;
  document.getElementById("editNombre").value = nombre;
  document.getElementById("editDni").value = dni;
  document.getElementById("editFecha").value = fechaIngreso;
  document.getElementById("editCalle").value = calle;
  document.getElementById("editNumero").value = numero;
  document.getElementById("editLocalidad").value = localidad;
  document.getElementById("editProvincia").value = provincia;
  document.getElementById("editForm").onsubmit = function (event) {
    event.preventDefault();
    const updatedPaciente = {
      id: id,
      apellido: document.getElementById("editApellido").value,
      nombre: document.getElementById("editNombre").value,
      dni: document.getElementById("editDni").value,
      fechaIngreso: document.getElementById("editFecha").value,
      domicilio: {
        id: idDomicilio,
        calle: document.getElementById("editCalle").value,
        numero: document.getElementById("editNumero").value,
        localidad: document.getElementById("editLocalidad").value,
        provincia: document.getElementById("editProvincia").value,
      },
    };
    fetch(`paciente/modificar`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedPaciente),
    })
      .then((response) => response.json())
      .then((data) => {
        alert("Paciente modificado con éxito");
        fetchPacientes();
        editModal.hide();
      })
      .catch((error) => {
        console.error("Error editing paciente:", error);
      });
  };
  editModal.show();
}

function deletePaciente(id) {
  if (confirm("¿Está seguro de que desea eliminar este paciente?")) {
    fetch(`paciente/eliminar/${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        alert("Paciente eliminado con éxito");
        fetchPacientes();
      })
      .catch((error) => {
        console.error("Error deleting paciente:", error);
      });
  }
}
