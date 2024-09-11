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


$(function () {
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if (month < 10)
        month = '0' + month.toString();
    if (day < 10)
        day = '0' + day.toString();
    var hour = dtToday.getHours();
    if (hour < 10)
        hour = '0' + hour.toString();

    var minutes = dtToday.getMinutes();
    if (minutes < 10)
        minutes = '0' + minutes.toString();
    var maxDate = year + '-' + month + '-' + day + 'T' + hour + ':' + minutes;
    $('#vremePocetka').attr('min', maxDate);
    $('#vremeZavrsetka').attr('min', maxDate);
});




function convertToMinutes(timeStr) {
    // Split the string into partslet
    parts = timeStr.split(' ');
    let totalMinutes = 0;     // Iterate through each part and calculate the total minutes   
    parts.forEach(part => {
        if (part.includes('h')) {
            // Extract hours and convert to minutes
            let hours = parseInt(part.replace('h', ''));
            totalMinutes += hours * 60;
        }
        if (part.includes('min')) {             // Extract minutes
            let minutes = parseInt(part.replace('min', ''));
            totalMinutes += minutes;
        }
    });
    return totalMinutes;
}

function updateEndTime() {
    // Get the start time and duration values
    const startTimeInput = document.getElementById('vremePocetka');
    const durationSelect = document.getElementById('tem');
    const endTimeInput = document.getElementById('vremeZavrsetka');


    // Parse the start time
    const startTime = new Date(startTimeInput.value);
    console.log(startTime);
    let minutes = convertToMinutes(durationSelect.value);

    console.log('Minuti: ' + minutes);

    let duration = durationSelect.value;
    let endTime = new Date(startTime.getTime() + (120 + minutes) * 60000);
    console.log(endTime);
    endTimeInput.value = endTime.toISOString().slice(0, 16);
    console.log(endTimeInput.value);

}


function filterComboBox(minuteLimit, strVremePocetka) {
    const selectElement = document.getElementById('tem');
    const options = selectElement.options;


    console.log(minuteLimit);


    for (let i = options.length - 1; i >= 0; i--) {
        const option = options[i];
        const optionMinutes = convertToMinutes(option.value);
        const vremePocetka = new Date(strVremePocetka);
        const novoVreme = new Date(vremePocetka.getTime() + optionMinutes * 60000);
        const krajDana = new Date(strVremePocetka);
        krajDana.setDate(krajDana.getDate() + 1); // Dodaj jedan dan
        krajDana.setHours(0, 0, 0, 0); // Postavi na ponoć sledećeg dan
        console.log()
        if (optionMinutes > minuteLimit || novoVreme > krajDana) {
            selectElement.removeChild(option);
        }
    }
}



      