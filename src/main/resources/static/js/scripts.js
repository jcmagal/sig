document.addEventListener('DOMContentLoaded', function() {
    // Marca o link atual como ativo na barra lateral e expande o menu pai, se necessário
    var currentPath = window.location.pathname;
    var links = document.querySelectorAll('.sb-sidenav-menu .nav-link');

    links.forEach(function(link) {
        if (currentPath.includes(link.getAttribute('href'))) {
            link.classList.add('active');
            var collapse = link.closest('.collapse');
            if (collapse) {
                collapse.classList.add('show');
                var parentLink = collapse.previousElementSibling;
                if (parentLink) {
                    parentLink.setAttribute('aria-expanded', 'true');
                }
            }
        }
    });

    // Alternância da barra lateral
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }
});

// Função adicional para persistir o estado da barra lateral entre as recargas da página
window.addEventListener('load', event => {
    const isToggled = localStorage.getItem('sb|sidebar-toggle') === 'true';
    if (isToggled) {
        document.body.classList.toggle('sb-sidenav-toggled');
    }
});
