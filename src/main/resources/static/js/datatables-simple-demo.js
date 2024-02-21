document.addEventListener("DOMContentLoaded", function() {
            new simpleDatatables.DataTable("#datatablesSimple", {
                labels: {
                    placeholder: "Buscar...",
                    perPage: "entradas por página",
                    noRows: "Nenhum registro encontrado",
                    info: "Mostrando {start} a {end} de {rows} entradas"
                },
                layout: {
                    top: "{search}",
                    bottom: "{info}{pager}"
                }
            });
        });