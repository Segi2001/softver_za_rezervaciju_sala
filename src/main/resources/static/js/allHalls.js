document.getElementById('nameFilter').addEventListener('input', filterSala);
document.getElementById('seatsFilter').addEventListener('input', filterSala);

function filterSala() {
    var name = document.getElementById('nameFilter').value.toUpperCase();
    var seats = parseInt(document.getElementById('seatsFilter').value, 10);
    var rows = document.querySelectorAll('.table-body tr');

    rows.forEach(row => {
        var nameCell = row.cells[0].innerText.toUpperCase();
        var seatsCell = parseInt(row.cells[2].innerText, 10);

        var nameMatch = name === "" || nameCell.includes(name);
        var seatsMatch = isNaN(seats) || seatsCell >= seats;

        if (nameMatch && seatsMatch) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}




$(document).ready(function () {
    function pagination() {
        let pageSize = 5;
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
            $('tbody tr').each(function (index) {
                if ($(this).find('td').eq(0).text().toLowerCase().includes(saleName.toLowerCase())) {
                    pageIndex = Math.floor(index / pageSize);
                    return false;
                }
            });
            return pageIndex;
        }

        $('#nameFilter').on('input', function () {
            let saleName = $(this).val().trim();
            if (saleName !== '') {
                let pageIndex = findPageBySaleName(saleName);
                if (pageIndex !== -1) {
                    paginateTable(pageIndex);
                }
            } else {
                paginateTable(0);
            }
        });

        $('#seatsFilter').on('input', function () {
            let seats = $(this).val().trim();
            if (seats !== '') {
                $('tbody tr').hide();
                $('tbody tr').filter(function () {
                    let numberOfSeats = parseInt($(this).find('td').eq(2).text().trim(), 10);

                    return !isNaN(numberOfSeats) && numberOfSeats >= parseInt(seats, 10);
                }).show();
            } else {
                paginateTable(0);
            }
        });

        paginateTable(0);
    }



//    $('#deleteModal').on('show.bs.modal', function (event) {
//        var button = $(event.relatedTarget);
//        var id = button.data('id'); 
//        var href = '@{/hall/deleteHall(id=)}' + id;
//        var confirmDelete = $('#confirmDelete');
//        confirmDelete.attr('href', href);
//    });


    pagination();
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