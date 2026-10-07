const api = "/api/applications";

const list = document.querySelector("#application-list");
const message = document.querySelector("#message");
const dialog = document.querySelector("#application-dialog");
const form = document.querySelector("#application-form");
const filter = document.querySelector("#status-filter");
const searchInput = document.querySelector("#search-input");

let applications = [];


async function request(url, options = {}) {

    const response = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
            ...options.headers
        },
        ...options
    });

    if (!response.ok) {

        const body = await response
            .json()
            .catch(() => ({}));

        throw new Error(
            body.error || "Request failed."
        );
    }

    return response.status === 204
        ? null
        : response.json();
}


async function load() {

    try {

        const status = filter.value;
        const search = searchInput.value.trim();

        const params = new URLSearchParams();

        if (status) {
            params.append("status", status);
        }

        if (search) {
            params.append("search", search);
        }

        const queryString = params.toString();

        const url = queryString
            ? `${api}?${queryString}`
            : api;

        applications = await request(url);

        render();
        metrics();

    } catch (error) {

        notice(error.message, true);
    }
}


function render() {

    if (!applications.length) {

        list.innerHTML =
            '<div class="empty">No applications match your search.</div>';

        return;
    }

    list.innerHTML = applications
        .map(x => `
            <article class="application-card">

                <div>

                    <h2>${safe(x.role)}</h2>

                    <p>${safe(x.company)}</p>

                    <div class="meta">

                        <span class="badge">
                            ${pretty(x.status)}
                        </span>

                        <span>
                            Applied ${x.appliedDate}
                        </span>

                        ${
            x.notes
                ? `<span>${safe(x.notes)}</span>`
                : ""
        }

                    </div>

                </div>

                <div class="card-actions">

                    <button
                        data-action="edit"
                        data-id="${x.id}">
                        Edit
                    </button>

                    <button
                        class="delete"
                        data-action="delete"
                        data-id="${x.id}">
                        Delete
                    </button>

                </div>

            </article>
        `)
        .join("");
}


function metrics() {

    document.querySelector("#total-count")
        .textContent = applications.length;

    document.querySelector("#interview-count")
        .textContent = applications
        .filter(x => x.status === "INTERVIEW")
        .length;

    document.querySelector("#offer-count")
        .textContent = applications
        .filter(x => x.status === "OFFER")
        .length;
}


function openForm(x = null) {

    form.reset();

    document.querySelector("#application-id")
        .value = x?.id || "";

    document.querySelector("#dialog-title")
        .textContent = x
        ? "Edit application"
        : "Add application";

    document.querySelector("#company")
        .value = x?.company || "";

    document.querySelector("#role")
        .value = x?.role || "";

    document.querySelector("#status")
        .value = x?.status || "SAVED";

    document.querySelector("#applied-date")
        .value = x?.appliedDate ||
        new Date().toISOString().slice(0, 10);

    document.querySelector("#notes")
        .value = x?.notes || "";

    dialog.showModal();
}


form.addEventListener(
    "submit",
    async event => {

        event.preventDefault();

        const id =
            document.querySelector("#application-id").value;

        const payload = {

            company:
                document.querySelector("#company")
                    .value.trim(),

            role:
                document.querySelector("#role")
                    .value.trim(),

            status:
            document.querySelector("#status")
                .value,

            appliedDate:
            document.querySelector("#applied-date")
                .value,

            notes:
                document.querySelector("#notes")
                    .value.trim()
        };

        try {

            await request(
                id
                    ? `${api}/${id}`
                    : api,
                {
                    method: id ? "PUT" : "POST",
                    body: JSON.stringify(payload)
                }
            );

            dialog.close();

            notice(
                id
                    ? "Application updated."
                    : "Application added."
            );

            await load();

        } catch (error) {

            notice(error.message, true);
        }
    }
);


list.addEventListener(
    "click",
    async event => {

        const button =
            event.target.closest(
                "button[data-action]"
            );

        if (!button) {
            return;
        }

        const id =
            Number(button.dataset.id);

        const x =
            applications.find(
                item => item.id === id
            );

        if (button.dataset.action === "edit") {
            openForm(x);
        }

        if (
            button.dataset.action === "delete" &&
            confirm(
                `Delete the application at ${x.company}?`
            )
        ) {

            try {

                await request(
                    `${api}/${id}`,
                    {
                        method: "DELETE"
                    }
                );

                notice(
                    "Application deleted."
                );

                await load();

            } catch (error) {

                notice(
                    error.message,
                    true
                );
            }
        }
    }
);


function notice(text, error = false) {

    message.textContent = text;

    message.style.color =
        error
            ? "#9c2f2f"
            : "#2d6a68";

    setTimeout(() => {

        if (message.textContent === text) {
            message.textContent = "";
        }

    }, 3500);
}


function pretty(x) {

    return x[0] +
        x.slice(1).toLowerCase();
}


function safe(value) {

    const e =
        document.createElement("div");

    e.textContent = value;

    return e.innerHTML;
}


// Buttons

document.querySelector("#new-button")
    .addEventListener(
        "click",
        () => openForm()
    );

document.querySelector("#close-button")
    .addEventListener(
        "click",
        () => dialog.close()
    );

document.querySelector("#cancel-button")
    .addEventListener(
        "click",
        () => dialog.close()
    );


// Filters

filter.addEventListener(
    "change",
    load
);


// Search while typing

let searchTimer;

searchInput.addEventListener(
    "input",
    () => {

        clearTimeout(searchTimer);

        searchTimer = setTimeout(
            load,
            300
        );
    }
);


// Initial application load

load();