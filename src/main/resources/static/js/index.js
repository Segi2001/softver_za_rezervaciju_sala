
document.getElementById('a_log').addEventListener('click', function (event) {
    event.preventDefault(); // Sprečava default ponašanje linka
    document.getElementById('postForm').submit(); // Podnosi formu
});






function toggleClasses(elements, className) {
    elements.forEach(function (element) {
        element.classList.toggle(className);
    });
}

function removeClasses(elements, ...classNames) {
    elements.forEach(function (element) {
        classNames.forEach(function (className) {
            element.classList.remove(className);
        });
    });
}

document.querySelectorAll(".btn1").forEach(function (btn) {
    btn.addEventListener("click", function () {
        this.classList.toggle("click");
        document.querySelector(".sidebar").classList.toggle("show");
    });
});

document.querySelectorAll(".register-btn").forEach(function (btn) {
    btn.addEventListener("click", function () {
        toggleClasses(document.querySelectorAll("nav ul .register-show"), "show");

    });
});
document.querySelectorAll(".sala-btn").forEach(function (btn) {
    btn.addEventListener("click", function () {
        toggleClasses(document.querySelectorAll("nav ul .sala-show"), "show");
    });
});

document.querySelectorAll(".unesi-salu-btn").forEach(function (btn) {
    btn.addEventListener("click", function (event) {
        event.stopPropagation(); 
        toggleClasses(btn.querySelectorAll(".unesi-salu-show"), "show");
    });
});



document.querySelectorAll(".user").forEach(function (korisnik) {
    korisnik.addEventListener("click", function () {
        removeClasses(
                document.querySelectorAll("nav ul .register-show"),
                "show"
                );
        removeClasses(document.querySelectorAll("nav ul .sala-show"), "show");

    });
});

document.querySelectorAll("nav ul li").forEach(function (item) {
    item.addEventListener("click", function () {
        this.classList.add("active");
        Array.from(this.parentNode.children).forEach(function (sibling) {
            if (sibling !== item) {
                sibling.classList.remove("active");
            }
        });
    });
});



const images = [
    "https://www.gradnja.rs/wp-content/uploads/2022/06/FON-nova-zgrada-NASLOVNA-sara-pilipovic.jpg",
    "https://live.staticflickr.com/8448/7949111814_e1ac80ee0a_b.jpg",
    "https://lh3.googleusercontent.com/proxy/_28-9_T0gOlmJrdJ_ip4-wcyFCct89C_PaHgjTfCB_kkVtNt6NjJ27ugl2iO97L7xgtwKtqCYC7e8NrkovWUcriO6s0Fl4lhFnVtlXNLHfWAqxjVwx5lNAYrkwutcB6Ha5lX8Q",
    "https://www.netokracija.rs/wp-content/uploads/sites/4/2023/04/1-2-1024x614.png"
];

function preloadImages(urls) {
    urls.forEach(url => {
        const img = new Image();
        img.src = url;
    });
}








preloadImages(images);