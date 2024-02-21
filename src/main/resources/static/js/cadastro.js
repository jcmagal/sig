
document.addEventListener('DOMContentLoaded', function() {
    const estadoSelect = document.getElementById('estado');
    const cidadeSelect = document.getElementById('cidade');

    estadoSelect.addEventListener('change', function() {
        const estadoId = this.value;
        fetch(`/administrative/estados/${estadoId}/cidades`)
            .then(response => response.json())
            .then(data => {
                cidadeSelect.innerHTML = '<option value="">Selecione uma Cidade</option>';
                data.forEach(cidade => {
                    cidadeSelect.innerHTML += `<option value="${cidade.id}">${cidade.nome}</option>`;
                });
            });
    });

    cidadeSelect.addEventListener('change', function() {
        const cidadeId = this.value;
        fetch(`/administrative/cidades/${cidadeId}/estado`)
            .then(response => response.json())
            .then(data => {
                estadoSelect.value = data.id;
            });
    });
});

