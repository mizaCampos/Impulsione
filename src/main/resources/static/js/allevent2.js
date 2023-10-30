var header = document.getElementById('header');
var navigationHeader = document.getElementById('navigation_header');
var content = document.getElementById('content');
var showSidebar = false;

function toggleSidebar() {
    showSidebar = !showSidebar;
    if (showSidebar) {
        navigationHeader.style.marginLeft = '-10vw';
        navigationHeader.style.animationName = 'showSidebar';
        content.style.filter = 'blur(2px)';
    }
    else {
        navigationHeader.style.marginLeft = '-100vw';
        navigationHeader.style.animationName = '';
        content.style.filter = '';
    }
}

function closeSidebar() {
    if (showSidebar) {
        showSidebar = true;
        toggleSidebar();
    }
}

window.addEventListener('resize', function (event) {
    if (window.innerWidth > 768 && showSidebar) {
        showSidebar = true;
        toggleSidebar();
    }
});


// Seletor das seções
const sections = document.querySelectorAll('section');

// Função para rolar as seções para a direita
function scrollRight() {
    sections.forEach((section) => {
        section.scrollLeft += 100; // Ajuste a quantidade de rolagem desejada
    });
}

// Função para rolar as seções para a esquerda
function scrollLeft() {
    sections.forEach((section) => {
        section.scrollLeft -= 100; // Ajuste a quantidade de rolagem desejada
    });
}




class MobileNavbar {
    constructor(mobileMenu, navList, navLinks) {
        this.mobileMenu = document.querySelector(mobileMenu);
        this.navList = document.querySelector(navList);
        this.navLinks = document.querySelectorAll(navLinks);
        this.activeClass = "active";

        this.handleClick = this.handleClick.bind(this);
    }

    animateLinks() {
        this.navLinks.forEach((link, index) => {
            link.style.animation
                ? (link.style.animation = "")
                : (link.style.animation = `navLinkFade 0.5s ease forwards ${index / 7 + 0.3
                    }s`);
        });
    }

    handleClick() {
        this.navList.classList.toggle(this.activeClass);
        this.mobileMenu.classList.toggle(this.activeClass);
        this.animateLinks();
    }

    addClickEvent() {
        this.mobileMenu.addEventListener("click", this.handleClick);
    }

    init() {
        if (this.mobileMenu) {
            this.addClickEvent();
        }
        return this;
    }
}

const mobileNavbar = new MobileNavbar(
    ".mobile-menu",
    ".nav-list",
    ".nav-list li",
);
mobileNavbar.init();


// Função para exibir a imagem ampliada
function showImageOverlay(imageSrc) {
    const overlay = document.getElementById("overlay");
    const overlayImage = document.getElementById("overlay-image");

    overlayImage.src = imageSrc;
    overlay.style.display = "flex";

    // Impede o scroll da página de fundo quando a imagem ampliada está aberta
    document.body.style.overflow = "hidden";
}

// Função para ocultar a imagem ampliada
function hideImageOverlay() {
    const overlay = document.getElementById("overlay");

    overlay.style.display = "none";

    // Restaura o scroll da página de fundo quando a imagem ampliada é fechada
    document.body.style.overflow = "auto";
}


// Adicione um evento de clique à imagem ampliada para fechá-la quando clicar nela
const overlay = document.getElementById("overlay");
overlay.addEventListener("click", () => {
    hideImageOverlay();
});


// Função para exibir a imagem ampliada com informações
function showImageOverlay(imageSrc, title, time, description, address) {
    const overlay = document.getElementById("overlay");
    const overlayImage = document.getElementById("overlay-image");
    const imageTitle = document.getElementById("image-title");
    const imageTime = document.getElementById("image-time");
    const imageDescription = document.getElementById("image-description");
    const imageAddress = document.getElementById("image-address");

    overlayImage.src = imageSrc;
    imageTitle.textContent = title;
    imageTime.textContent = `Horário: ${time}`;
    imageDescription.textContent = `Descrição: ${description}`;
    imageAddress.textContent = `Endereço: ${address}`;
    overlay.style.display = "flex";
}

// Adicione um evento de clique às imagens existentes para mostrar a imagem ampliada
const images = document.querySelectorAll("img");
images.forEach((img) => {
    img.addEventListener("click", (event) => {
        const imageSrc = event.target.src;
        const title = "Título da Imagem"; // Substitua pelo título correto
        const time = "00:00"; // Substitua pelo horário correto
        const description = "Descrição da Imagem"; // Substitua pela descrição correta
        const address = "Endereço da Imagem"; // Substitua pelo endereço correto
        showImageOverlay(imageSrc, title, time, description, address);
    });
});


