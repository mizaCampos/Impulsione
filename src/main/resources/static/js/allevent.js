// Função de inicialização do mapa
function initMap() {
    // Coordenadas de exemplo para o centro do mapa
    var centerCoords = { lat: -23.5505, lng: -46.6333 };

    // Opções do mapa
    var mapOptions = {
        zoom: 10,
        center: centerCoords
    };

    // Criar o mapa
    var map = new google.maps.Map(document.getElementById('map'), mapOptions);

    // Adicionar marcadores de exemplo (substitua por seus próprios dados)
    var marker1 = new google.maps.Marker({
        position: { lat: -23.6000, lng: -46.7000 },
        map: map,
        title: 'Abrigo Feliz'
    });

    var marker2 = new google.maps.Marker({
        position: { lat: -23.5800, lng: -46.6500 },
        map: map,
        title: 'Abrigo Amigo dos Bichos'
    });
}

// Seu código JavaScript adicional pode ser adicionado aqui
document.querySelectorAll('nav ul li a').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();

        const targetId = this.getAttribute('href').substring(1);
        const targetElement = document.getElementById(targetId);

        window.scrollTo({
            top: targetElement.offsetTop - 50, // Ajuste o valor conforme necessário para compensar o cabeçalho
            behavior: 'smooth'
        });
    });
});

// Selecione todos os botões "Leia Mais"
const leiaMaisButtons = document.querySelectorAll('.leia-mais-btn');

// Adicione um ouvinte de evento de clique a cada botão
leiaMaisButtons.forEach(button => {
    button.addEventListener('click', () => {
        // Encontre o elemento irmão 'texto-completo' do botão clicado
        const textoCompleto = button.nextElementSibling;

        // Verifique se o texto completo está visível ou oculto
        const isVisible = textoCompleto.style.display === 'block';

        // Alterne a visibilidade do texto completo ao clicar no botão
        if (isVisible) {
            textoCompleto.style.display = 'none';
            button.textContent = 'Leia Mais';
        } else {
            textoCompleto.style.display = 'block';
            button.textContent = 'Fechar';
        }
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const categorySelect = document.getElementById("category-select");
    const sortSelect = document.getElementById("sort-select");

    // Função para filtrar os cards por categoria
    categorySelect.addEventListener("change", function () {
        const selectedCategory = categorySelect.value;

        const cards = document.querySelectorAll(".card");

        cards.forEach(function (card) {
            const cardCategory = card.getAttribute("data-category");

            if (selectedCategory === "all" || selectedCategory === cardCategory) {
                card.style.display = "block";
            } else {
                card.style.display = "none";
            }
        });
    });

    // Função para ordenar os cards por data ou nome
    sortSelect.addEventListener("change", function () {
        const selectedSort = sortSelect.value;

        const cardContainer = document.querySelector(".card-container");

        const cards = Array.from(cardContainer.querySelectorAll(".card"));

        if (selectedSort === "date") {
            cards.sort(function (a, b) {
                const dateA = new Date(a.getAttribute("data-date"));
                const dateB = new Date(b.getAttribute("data-date"));
                return dateA - dateB;
            });
        } else if (selectedSort === "name") {
            cards.sort(function (a, b) {
                const nameA = a.getAttribute("data-name").toLowerCase();
                const nameB = b.getAttribute("data-name").toLowerCase();
                return nameA.localeCompare(nameB);
            });
        }

        cards.forEach(function (card) {
            cardContainer.appendChild(card);
        });
    });
});



