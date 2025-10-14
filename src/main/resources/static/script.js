// Espera a que todo el HTML esté cargado
document.addEventListener("DOMContentLoaded", () => {

    const content = document.getElementById("mainContent");

    document.getElementById("btnInicio").addEventListener("click", () => {
        content.innerHTML = `
            <div class="inner-content">
                <h1>Bienvenido a mi negocio</h1>
                <p>Este es un ejemplo con una barra lateral y botones que cambian de color al pasar el mouse.</p>
            </div>
        `;
    });
    document.getElementById("btnVenta").addEventListener("click", () => {
        fetch("venta.html")
            .then(res => res.text())
            .then(html => {
                content.innerHTML = html;
                initScanner();
                initBotonesVenta(); // 🔹 Llamamos a la función que configura los botones
            })
            .catch(err => console.error("Error cargando venta.html:", err));
    });

    function initBotonesVenta() {

        const botonPagar = document.getElementById("pago");
        const modalPago = document.getElementById("PagarModal");
        const btnPagoEfectivo = document.getElementById("btnPagoEfectivo");
        const btnPagoTarjeta = document.getElementById("btnPagoTarjeta");
        const btnPagoQR = document.getElementById("btnPagoQR");
        const qrModal = document.getElementById("QRModal");
        const qrImage = document.getElementById("qrImage");
        const btnCerrarQR = document.getElementById("btnCerrarQR");


        if (botonPagar) {
            botonPagar.addEventListener("click", () => {
                console.log("Botón Pagar presionado ✅");
                modalPago.style.display = "block"; // abrir el modal
            });
        }
        if (btnPagoEfectivo) {
            btnPagoEfectivo.addEventListener("click", () => {
                modalPago.style.display = "none";
                console.log("Pago en efectivo seleccionado");
                // Aquí podés llamar a la función de procesar pago efectivo
            });
        }

        if (btnPagoTarjeta) {
            btnPagoTarjeta.addEventListener("click", () => {
                modalPago.style.display = "none";
                console.log("Pago con tarjeta seleccionado");
                // Aquí podés abrir otra función o modal de tarjeta
            });
        }

        if (btnPagoQR) {
            btnPagoQR.addEventListener("click", () => {
                console.log("Pago con QR seleccionado");
                modalPago.style.display = "none";
                qrModal.style.display = "flex";

                enviarListaProductos().then(preferenceId => {
                    console.log("Preference ID:", preferenceId);
                    const mpUrl = `https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=${preferenceId}`;
                    const qrUrl = `https://api.qrserver.com/v1/create-qr-code/?data=${encodeURIComponent(mpUrl)}&size=250x250`;

                    qrImage.src = qrUrl;
                });
            });
        }
        if (btnCerrarQR) {
            btnCerrarQR.addEventListener("click", () => {
                qrModal.style.display = "none";
            });
        }

        // --- Botón Manual + Modal ---
        const btnManual = document.getElementById("manual");
        const modal = document.getElementById("manualModal");
        const inputManual = document.getElementById("manualInput");
        const btnAceptarManual = document.getElementById("btnAceptarManual");
        const btnCancelarManual = document.getElementById("btnCancelarManual");
        const scannerInput = document.getElementById("scannerInput");


        const btnBorrar = document.getElementById("borrar");
        const modalBorrar = document.getElementById("borrarModal");
        const inputBorrar = document.getElementById("borrarInput");
        const btnCancelarBorrar = document.getElementById("btnCancelarBorrar");
        if (btnManual && modal && inputManual) {
            btnManual.addEventListener("click", () => {
                console.log("Botón Manual clickeado ✅");
                modal.style.display = "block";
                inputManual.value = "";
                inputManual.focus();
            });
        }

        if (btnCancelarManual) {
            btnCancelarManual.addEventListener("click", () => {
                modal.style.display = "none";
                scannerInput.focus();
            });
        }

        if (btnAceptarManual) {
            btnAceptarManual.addEventListener("click", () => {
                const codigo = inputManual.value.trim();
                if (codigo) {
                    buscarProducto(codigo);
                    modal.style.display = "none";
                    scannerInput.focus();
                } else {
                    alert("Ingrese un código válido");
                }
            });
        }

        if (btnBorrar && modalBorrar && inputBorrar) {
            btnBorrar.addEventListener("click", () => {
                console.log("Modal Borrar abierto 🗑️");
                modalBorrar.style.display = "block";
                inputBorrar.value = "";
                inputBorrar.focus();
            });

            inputBorrar.addEventListener("keypress", e => {
                if (e.key === "Enter") {
                    const codigo = inputBorrar.value.trim();
                    if (codigo) {
                        console.log("Código escaneado para borrar:", codigo);
                        eliminarProductoTabla(codigo);
                        modalBorrar.style.display = "none";
                        scannerInput.focus();
                    }
                }
            });
        }

        if (btnCancelarBorrar) {
            btnCancelarBorrar.addEventListener("click", () => {
                modalBorrar.style.display = "none";
                scannerInput.focus();
            });
        }
    }



    function initScanner() {
        const scannerInput = document.getElementById("scannerInput");
        scannerInput.focus();

        scannerInput.addEventListener("keypress", function(e) {
            if (e.key === "Enter") {
                const codigo = scannerInput.value.trim();
                if (codigo) buscarProducto(codigo);
                scannerInput.value = "";
            }
        });
    }


    document.getElementById("btnPago").addEventListener("click", () => {
        content.innerHTML = `
        <div class="inner-content">
        <h1>Registrar Pago</h1>
        <p>Aquí iría el formulario para registrar pagos.</p>
        </div>
    `;
    });

    function buscarProducto(codigo) {
        fetch(`/productos/${codigo}`) //esta url es relativo a la url actual
            .then(res => res.json()) //promesa , => es la notacion de funcio anonima
            .then(producto => {
                if(producto) {
                    agregarProductoTabla(producto);
                } else {
                    alert("Producto no encontrado");
                }
            })
            .catch(err => console.error(err));
    }

    function agregarProductoTabla(p) {
        const tbody = document.getElementById("ventaTabla").querySelector("tbody");
        const filas = tbody.querySelectorAll("tr");
        let filaExistente = null;

        filas.forEach(fila => {
            const codigo = fila.querySelector("td").textContent; // el primer <td> es el barcode
            if (codigo === p.barcode) {
                filaExistente = fila;
            }
        });

        if (filaExistente) {
            const celdas = filaExistente.querySelectorAll("td");
            let cantidad = parseInt(celdas[4].textContent);
            cantidad++;
            celdas[4].textContent = cantidad;
            const precioUnitario = parseFloat(p.cost);
            const nuevoTotal = cantidad * precioUnitario;
            celdas[5].textContent = `$${nuevoTotal.toFixed(2)}`;
        } else {
            const fila = document.createElement("tr");
            fila.innerHTML = `
            <td>${p.barcode}</td>         
            <td>${p.name}</td>
            <td>${p.category}</td>
            <td>$${p.cost}</td>
            <td>1</td>
            <td>$${p.cost}</td>
        `;
            tbody.appendChild(fila);
        }
    }
    function eliminarProductoTabla(codigo){
        const tbody = document.getElementById("ventaTabla").querySelector("tbody");
        const filas = tbody.querySelectorAll("tr");
        let filaExistente = null;

        filas.forEach(fila => {
            const barcode = fila.querySelector("td").textContent.trim();
            if (codigo === barcode) {
                filaExistente = fila;
            }
        });

        if (filaExistente) {
            const celdas = filaExistente.querySelectorAll("td");
            let cantidad = parseInt(celdas[4].textContent);
            let total = parseFloat(celdas[5].textContent.replace('$',''));
            const precioUnitario = parseFloat(celdas[3].textContent.replace('$',''));
            if (cantidad > 1) {
                cantidad--;
                const nuevoTotal = cantidad * precioUnitario;
                celdas[4].textContent = cantidad;
                celdas[5].textContent = `$${nuevoTotal.toFixed(2)}`;
            } else {
                filaExistente.remove();
            }
        }
    }


    function enviarListaProductos() {
        const tabla = document.getElementById("ventaTabla");
        const filas = tabla.querySelectorAll("tbody tr");
        const productos = [];

        filas.forEach(fila => {
            const celdas = fila.querySelectorAll("td");
            productos.push({
                codigo: celdas[0].textContent,
                nombre: celdas[1].textContent,
                cantidad: parseInt(celdas[4].textContent),
                precio: parseFloat(celdas[3].textContent.replace("$", "")),
                categoria: celdas[2].textContent
            });
        });
        return fetch('/pagos/crear-pago', {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(productos)
        })
            .then(res => res.json())
            .then(data => data.preferenceId);
    }





});
