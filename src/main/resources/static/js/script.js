$(document).ready(function () {
    checkPageSizes();
    changePageAndSize();
    searchUsersOnEnterKeyPressed();
    changeActiveLinks();
    keepSearchParametersAfterPageRefresh();
});

function changeActiveLinks() {
    let qs = decodeURIComponent(location);
    let menuItem = qs.replace("http://localhost:5000", "");
    $("a[href='" + menuItem + "']")
        .parent("li")
        .addClass("active");

    if (menuItem.includes("admin")) {
        $("a[href='/admin']").parent("li").addClass("active");
    }
    if (menuItem.includes("users")) {
        $("a[href='/admin/users']").parent("li").addClass("active");
    }
    if (menuItem.includes("roles")) {
        $("a[href='/admin/roles']").parent("li").addClass("active");
    }
}

function changePageAndSize() {
    $("#pageSizeSelect").change(function () {
        let selectedProperty = $("#search-user-dropdown option:selected").text();
        let value = $("#searchUserBar").val();

        if (value != null && value !== "") {
            window.location.replace("/admin/users?usersProperty=" + selectedProperty + "&propertyValue=" + value + "&pageSize=" + this.value + "&page=1");
        } else {
            window.location.replace("/admin/users?pageSize=" + this.value + "&page=1");
        }
    });
}

function searchUsersOnEnterKeyPressed() {
    $("#searchUserBar").keypress(function (event) {
        if (event.which === 13) {
            searchUserByProperty();
        }
    });
}

function saveSearchParameters(e) {
    let id = e.id;
    let val = e.value;
    localStorage.setItem(id, val);
}

function keepSearchParametersAfterPageRefresh() {
    $("#searchUserBar").val(getSavedValueForTextBox("searchUserBar"));
    $("#search-user-dropdown").val(getSavedValueForDropDown("search-user-dropdown"));

    function getSavedValueForTextBox(v) {
        let usersPropertyParam = new URL(location.href).searchParams.get("usersProperty");
        if (localStorage.getItem(v) === null) {
            return "";
        } else if (usersPropertyParam === null) {
            return "";
        }

        return localStorage.getItem(v);
    }

    function getSavedValueForDropDown(v) {
        let propertyValue = new URL(location.href).searchParams.get("propertyValue");
        if (localStorage.getItem(v) === null) {
            return "ID";
        } else if (propertyValue === null) {
            return "ID";
        }

        return localStorage.getItem(v);
    }
}

function checkPageSizes() {
    let pageSizesToShow = $("#pageSizesToShow").data("pagesizestoshow");

    $("#pageSizeSelect option").each(function (i, option) {
        if ($.inArray(parseInt(option.value), pageSizesToShow) === -1) {
            option.disabled = true;
        }
    });
}

function sortTable(n) {
    let table,
        rows,
        switching,
        i,
        x,
        y,
        shouldSwitch,
        dir,
        count = 0;
    table = document.getElementById("user-table");
    switching = true;
    dir = "asc";

    while (switching) {
        switching = false;
        rows = table.getElementsByTagName("TR");
        for (i = 1; i < rows.length - 1; i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            if (dir === "asc") {
                if (n === 0) {
                    if (Number(x.innerHTML) > Number(y.innerHTML)) {
                        shouldSwitch = true;
                        break;
                    }
                } else if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            } else if (dir === "desc") {
                if (n === 0) {
                    if (Number(x.innerHTML) < Number(y.innerHTML)) {
                        shouldSwitch = true;
                        break;
                    }
                } else if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch === true) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            count++;
        } else {
            if (count === 0 && dir === "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}
