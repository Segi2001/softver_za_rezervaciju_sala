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


function filterReservations() {
    var status = document.getElementById("statusFilter").value;
    var date = document.getElementById("dateFilter").value;
    var organizer = document.getElementById("organizerFilter").value.toUpperCase();

    var rows = document.getElementsByClassName("table-body")[0].getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        var statusCell = row.getElementsByTagName("td")[4].innerText.toUpperCase();
        var dateCell = row.getElementsByTagName("td")[2].innerText;
        var organizerCell = row.getElementsByTagName("td")[7].innerText.toUpperCase();

        var statusMatch = status === "svi" || statusCell.includes(status.toUpperCase());
        var dateMatch = date === "" || dateCell.includes(date);
        var organizerMatch = organizer === "" || organizerCell.includes(organizer);

        if (statusMatch && dateMatch && organizerMatch) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    }
}



$(function () {
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if (month < 10)
        month = '0' + month.toString();
    if (day < 10)
        day = '0' + day.toString();
    var maxDate = year + '-' + month + '-' + day;
    $('#dateFilter').attr('min', maxDate);
});










$(document).ready(function () {
    $('.delete-button').click(function (e) {
        e.preventDefault();
        var deleteUrl = $(this).attr('href');
        $('#confirmModal').modal('show');
        $('#confirmDelete').click(function () {
            window.location.href = deleteUrl;
        });
    });
});








