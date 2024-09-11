/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


       
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

         $(document).ready(function () {
             
              var brojMestaInput = document.getElementById("brojMesta");
        if (brojMestaInput.value === "0") {
            brojMestaInput.value = "";
        }
             
         });