document.addEventListener("DOMContentLoaded", () => {

    const contenedorLista = document.getElementById("listaDeudores");
    const btnListar = document.getElementById("btnListar");
    const btnGuardar = document.getElementById("guardarBtn");

    function mostrarEnLista(html) {
        if (contenedorLista) contenedorLista.innerHTML = html;
    }

    // ELIMINAR
    async function eliminarDeudor(dni) {
        if (!confirm(`¿Seguro que querés eliminar al deudor con DNI ${dni}?`)) return;
        try {
            const resp = await fetch(`/deudores/baja/${dni}`, { method: "DELETE" });
            if (!resp.ok) {
                const txt = await resp.text();
                alert("❌ Error al eliminar: " + (txt || resp.statusText));
                return;
            }
            const mensaje = await resp.text();
            alert("✅ " + mensaje);
            listarDeudores(); // refrescar lista
        } catch (err) {
            console.error("Error fetch eliminar:", err);
            alert("🚨 No se pudo conectar con el servidor.");
        }
    }

    // LISTAR
    async function listarDeudores() {
        mostrarEnLista("<p>Cargando...</p>");
        try {
            const resp = await fetch("/deudores/listar", { headers: { "Accept": "application/json" } });
            if (!resp.ok) {
                mostrarEnLista(`<p>Error ${resp.status}: ${resp.statusText}</p>`);
                console.error("HTTP error al listar:", resp);
                return;
            }
            const deudores = await resp.json();
            if (!Array.isArray(deudores) || deudores.length === 0) {
                mostrarEnLista("<p>No hay deudores.</p>");
                return;
            }

            let html = '<table border="1"><thead><tr><th>Nombre</th><th>DNI</th><th>Teléfono</th><th>Saldo</th><th>Acciones</th></tr></thead><tbody>';
            deudores.forEach(d => {
                const nombre = d?.nombre ?? "(sin nombre)";
                const dni = d?.dni ?? "(sin dni)";
                const tel = d?.telefono ?? "(sin teléfono)";
                const saldo = Number(d?.saldoDeudor ?? d?.saldo ?? 0).toFixed(2);

                html += `<tr>
                    <td>${nombre}</td>
                    <td>${dni}</td>
                    <td>${tel}</td>
                    <td>$${saldo}</td>
                    <td><button onclick="eliminarDeudor('${dni}')">Eliminar</button></td>
                </tr>`;
            });
            html += "</tbody></table>";
            mostrarEnLista(html);

        } catch (err) {
            console.error("Error fetch listar:", err);
            mostrarEnLista("<p>Error al conectar con el servidor. Mirá la consola (F12).</p>");
        }
    }

    // Hacer la función eliminarDeudor accesible globalmente para el onclick
    window.eliminarDeudor = eliminarDeudor;

    if (btnListar) btnListar.addEventListener("click", (ev) => { ev && ev.preventDefault(); listarDeudores(); });

    if (btnGuardar) {
        btnGuardar.addEventListener("click", async (ev) => {
            ev && ev.preventDefault();
            const nombre = document.getElementById("nombre")?.value?.trim() ?? "";
            const dni = document.getElementById("dni")?.value?.trim() ?? "";
            const telefono = document.getElementById("telefono")?.value?.trim() ?? "";
            const deudaVal = document.getElementById("deuda")?.value?.trim() ?? "";
            const deuda = parseFloat(deudaVal);

            if (!nombre || !dni || !telefono || isNaN(deuda)) {
                alert("⚠️ Completá todos los campos correctamente.");
                return;
            }

            const nuevoDeudor = { nombre, dni, telefono, saldoDeudor: deuda, activo: true };

            try {
                const resp = await fetch("/deudores/alta", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(nuevoDeudor)
                });
                if (!resp.ok) {
                    const txt = await resp.text();
                    console.error("Error servidor guardar:", resp.status, txt);
                    alert("❌ Error al guardar: " + (txt || resp.statusText));
                    return;
                }
                const mensaje = await resp.text();
                alert("✅ " + mensaje);

                document.getElementById("nombre").value = "";
                document.getElementById("dni").value = "";
                document.getElementById("telefono").value = "";
                document.getElementById("deuda").value = "";

                listarDeudores();
            } catch (err) {
                console.error("Error fetch guardar:", err);
                alert("🚨 No se pudo conectar con el servidor.");
            }
        });
    }

});
