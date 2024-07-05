document.addEventListener('DOMContentLoaded', function () {
    const langBtn = document.getElementById('lang-btn');
    const langMenu = document.getElementById('lang-menu');

    langBtn.addEventListener('click', function () {
        langMenu.classList.toggle('show');
    });

    window.addEventListener('click', function (e) {
        if (!langBtn.contains(e.target)) {
            langMenu.classList.remove('show');
        }
    });
});
