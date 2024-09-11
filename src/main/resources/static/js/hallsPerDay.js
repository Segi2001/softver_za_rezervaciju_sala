



document.getElementById('a_log').addEventListener('click', function (event) {
    event.preventDefault(); // Sprečava default ponašanje linka
    document.getElementById('postForm').submit(); // Podnosi formu
});






function adjustCellDimensions() {
    const table = document.getElementById("salesTable");
    if (table) {
        let maxWidth = 0;
        let maxHeight = 0;

        // Calculate the maximum width and height of cells in the table
        const cells = table.getElementsByTagName("td");
        for (let i = 0; i < cells.length; i++) {
            const cellWidth = cells[i].offsetWidth;
            const cellHeight = cells[i].offsetHeight;
            maxWidth = Math.max(maxWidth, cellWidth);
            maxHeight = Math.max(maxHeight, cellHeight);
        }

        // Set width and height for all cells
        for (let i = 0; i < cells.length; i++) {
            cells[i].style.width = maxWidth + "px";
            cells[i].style.height = maxHeight + "px";
        }
    }
}



window.addEventListener("load", adjustCellDimensions);




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



function generateTimeHeaders() {
    const startHour = 8;
    const endHour = 23;
    const interval = 15; // interval in minutes
    let timeHeaders = [];

    for (let hour = startHour; hour < endHour; hour++) {
        for (let minute = 0; minute < 60; minute += interval) {
            let time = `${hour}h`;
            if (minute > 0) {
                time += ` ${minute}min`;
            }
            timeHeaders.push(time);
        }
    }
    // Add the final "24h" header

    timeHeaders.push("23h");
    return timeHeaders;
}

document.addEventListener("DOMContentLoaded", function () {
    const theadRow = document.querySelector('thead tr');
    const timeHeaders = generateTimeHeaders();
    timeHeaders.forEach(header => {
        const th = document.createElement('th');
        th.textContent = header;
        theadRow.appendChild(th);
    });
     mergeCellsByTime();
});





document.addEventListener("DOMContentLoaded", function () {
    // Prikupimo sve elemente koji sadrže tekst "Zauzeto"
    const zauzetiElementi = document.querySelectorAll('.zauzetost');
    console.log(zauzetiElementi);
    // Modal elementi
    const modal = document.getElementById("modal");
    const spanClose = document.getElementsByClassName("close")[0];
    const modalOrganizator = document.getElementById("modal-organizator");
    const modalSvrha = document.getElementById("modal-svrha");
    const modalVremePocetka = document.getElementById("modal-vreme");

    // Funkcija za otvaranje modalnog prozora
    zauzetiElementi.forEach(function (element) {
        element.addEventListener("click", function () {
            const organizator = element.parentElement.querySelector('.organizator').innerText;
            const svrha = element.parentElement.querySelector('.svrha').innerText;
            const vremePocetka = element.parentElement.querySelector(".vremePocetka").innerText;
            const vremeZavrsetka = element.parentElement.querySelector(".vremeZavrsetka").innerText;

            
            const strVremePoc = vremePocetka.split(' ')[1].substring(0, 5);
            const strVremeZav = vremeZavrsetka.split(' ')[1].substring(0, 5);
            console.log(strVremePoc);
            console.log(strVremeZav);
            modalOrganizator.textContent = organizator;
            modalSvrha.textContent = svrha;
            modalVremePocetka.textContent = strVremePoc + ' - ' + strVremeZav;
            modal.style.display = "block";
        });
    });

    // Funkcija za zatvaranje modalnog prozora
    spanClose.onclick = function () {
        modal.style.display = "none";
    }

    // Zatvori modal ako korisnik klikne izvan prozora
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});



function mergeCellsByTime() {
    const rows = document.querySelectorAll('#salesTable tbody tr');

    rows.forEach(row => {
        const cells = row.querySelectorAll('td');
        let startIndex = null;
        let colspan = 1;

        cells.forEach((cell, index) => {
            const vremePocetka = cell.querySelector('.vremePocetka')?.innerText;
            const vremeZavrsetka = cell.querySelector('.vremeZavrsetka')?.innerText;

            if (vremePocetka && vremeZavrsetka) {
                if (startIndex === null) {
                    startIndex = index;
                } else {
                    colspan++;
                }

                if (index === cells.length - 1 || cells[index + 1]?.querySelector('.vremePocetka')?.innerText !== vremePocetka) {
                    const startCell = cells[startIndex];
                    startCell.setAttribute('colspan', colspan);
                    for (let i = startIndex + 1; i < startIndex + colspan; i++) {
                        cells[i].remove();
                    }
                    startIndex = null;
                    colspan = 1;
                }
            } else {
                startIndex = null;
                colspan = 1;
            }
        });
    });
}


/*document.getElementById("search").addEventListener("keyup", filterTable);*/

            function pagination() {

                let pageSize = 6;
                let totalRows = $('tbody tr').length;
                let totalPages = Math.ceil(totalRows / pageSize);
                let currentPage = 0;

                function updatePageInfo() {
                    $('#pageInfo').text('Page ' + (currentPage + 1) + ' of ' + totalPages);
                }

                function paginateTable(page) {
                    $('tbody tr').hide();
                    let start = page * pageSize;
                    let end = start + pageSize;
                    $('tbody tr').slice(start, end).show();
                    currentPage = page;
                    updatePageInfo();
                    generatePaginationButtons();
                }

                function generatePaginationButtons() {
                    let paginationButtons = $('#paginationButtons');
                    paginationButtons.empty();

                    let maxButtons = 6;
                    let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
                    let endPage = Math.min(totalPages, startPage + maxButtons - 1);

                    if (endPage - startPage < maxButtons - 1) {
                        startPage = Math.max(1, endPage - maxButtons + 1);
                    }

                    if (startPage > 1) {
                        paginationButtons.append('<button class="btn btn-sm btn-light page-button" data-page="0">1</button>');
                        if (startPage > 2) {
                            paginationButtons.append('<span>...</span>');
                        }
                    }

                    for (let i = startPage; i <= endPage; i++) {
                        paginationButtons.append('<button class="btn btn-sm btn-light page-button" data-page="' + (i - 1) + '">' + i + '</button>');
                    }

                    if (endPage < totalPages) {
                        if (endPage < totalPages - 1) {
                            paginationButtons.append('<span>...</span>');
                        }
                        paginationButtons.append('<button class="btn btn-sm btn-light page-button" data-page="' + (totalPages - 1) + '">' + totalPages + '</button>');
                    }

                    $('.page-button').removeClass('btn-primary').addClass('btn-light');
                    $('.page-button[data-page="' + currentPage + '"]').removeClass('btn-light').addClass('btn-primary');
                }

                $('#prevPage').click(function () {
                    if (currentPage > 0) {
                        paginateTable(currentPage - 1);
                    }
                });

                $('#nextPage').click(function () {
                    if (currentPage < totalPages - 1) {
                        paginateTable(currentPage + 1);
                    }
                });

                $(document).on('click', '.page-button', function () {
                    let page = $(this).data('page');
                    paginateTable(page);
                });


                function findPageBySaleName(saleName) {
                    let pageIndex = -1;
                    //console.log(saleName);
                    $('tbody tr').each(function (index) {
                        if ($(this).find('td').eq(0).text().toLowerCase().includes(saleName.toLowerCase())) {
                            pageIndex = Math.floor(index / pageSize);
                            return false; // break the loop
                        }
                    });
                    return pageIndex;
                }

                $('#search').on('input', function () {
                    let saleName = $(this).val().trim();
                    if (saleName !== '') {
                        let pageIndex = findPageBySaleName(saleName);
                        console.log(pageIndex);
                        if (pageIndex !== -1) {
                            paginateTable(pageIndex);
                        }
                    } else {
                      
                        paginateTable(0);
                    }
                });

                paginateTable(0);
            }
            $(document).ready(function () {

                pagination();


            });
