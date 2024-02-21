    let formEmEdicao = null;

document.addEventListener("DOMContentLoaded", function () {
    function habilitarEdicao(formId, editarBtnId, atualizarBtnId, cancelarBtnId) {
        const editarBtn = document.getElementById(editarBtnId);
        const atualizarBtn = document.getElementById(atualizarBtnId);
        const cancelarBtn = document.getElementById(cancelarBtnId);

        editarBtn.addEventListener('click', function() {
            document.querySelectorAll(`#${formId} input, #${formId} select`).forEach(function(element) {
           if (element.id !== 'dataEntrada' && element.id !== 'cpf') {
                element.removeAttribute('readonly');
                element.removeAttribute('disabled');
                }
            });

            atualizarBtn.classList.remove('d-none');
            cancelarBtn.classList.remove('d-none');
            editarBtn.classList.add('d-none');

            formEmEdicao = formId; // Armazena o ID do formulário em edição
        });
    }

    function adicionarEventoAtualizar(atualizarBtnId) {
        const atualizarBtn = document.getElementById(atualizarBtnId);
        if (!atualizarBtn.getAttribute('listener')) {
            atualizarBtn.setAttribute('listener', 'true');
            atualizarBtn.addEventListener('click', function(event) {
                event.preventDefault(); // Impede a submissão do formulário
                var modal = new bootstrap.Modal(document.getElementById('meuModal'));
                modal.show();
            });
        }
    }

    function cancelarEdicao(formId, editarBtnId, atualizarBtnId, cancelarBtnId) {
        const cancelarBtn = document.getElementById(cancelarBtnId);
        cancelarBtn.addEventListener('click', function() {
            window.location.reload();
        });
    }

    // Inicializa os eventos de habilitar e cancelar edição para cada formulário
    habilitarEdicao('dadosPessoaisForm', 'editarBtn', 'atualizarBtn', 'cancelarBtn');
    cancelarEdicao('dadosPessoaisForm', 'editarBtn', 'atualizarBtn', 'cancelarBtn');
    habilitarEdicao('enderecoForm', 'editarEndereco', 'atualizarEndereco', 'cancelarEndereco');
    cancelarEdicao('enderecoForm', 'editarEndereco', 'atualizarEndereco', 'cancelarEndereco');
    habilitarEdicao('dadosEmForm', 'editarDE', 'atualizarDE', 'cancelarDE');
    cancelarEdicao('dadosEmForm', 'editarDE', 'atualizarDE', 'cancelarDE');

    // Adiciona o evento de atualizar para cada botão de atualizar após a edição ser habilitada
    adicionarEventoAtualizar('atualizarBtn');
    adicionarEventoAtualizar('atualizarEndereco');
    adicionarEventoAtualizar('atualizarDE');
});

document.getElementById('salvarMudancasBtn').addEventListener('click', function() {
    if (formEmEdicao) {
        document.getElementById(formEmEdicao).submit(); // Submete o formulário que está sendo editado
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const cargoSelect = document.getElementById("cargo");
    const vencimentoInput = document.getElementById("vencimento");

    // Função para atualizar o vencimento base com base na seleção do cargo
    function atualizarVencimento() {
        const selectedOption = cargoSelect.options[cargoSelect.selectedIndex];
        const vencimento = selectedOption.getAttribute("data-vencimento");
        vencimentoInput.value = vencimento ? vencimento : 'Vencimento não disponível';
    }

    // Adicione um ouvinte de evento para a seleção de cargo
    cargoSelect.addEventListener("change", atualizarVencimento);

    // Execute a função inicialmente para definir o vencimento com base na seleção inicial (se houver)
    atualizarVencimento();
});


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



