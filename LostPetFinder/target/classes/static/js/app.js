// =====================================================
// LOST PET REPORT
// =====================================================

const lostPetForm =
    document.getElementById("lostPetForm");

if (lostPetForm) {

    lostPetForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const species =
                document.getElementById("species").value;

            const breed =
                document.getElementById("breed").value;

            const colour =
                document.getElementById("colour").value;

            const locality =
                document.getElementById("locality").value;

            const userId =
                document.getElementById("userId").value;


            const report = {
                species: species,
                breed: breed,
                colour: colour,
                locality: locality,
                status: "ACTIVE",
                user: {
                    id: Number(userId)
                }
            };


            try {

                const response =
                    await fetch("/api/lost-pets", {

                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify(report)
                    });


                const message =
                    document.getElementById("message");


                if (response.ok) {

                    const data =
                        await response.json();

                    message.textContent =
                        "Lost pet report submitted successfully! Report ID: "
                        + data.id;

                    lostPetForm.reset();

                } else {

                    message.textContent =
                        "Failed to submit the report.";

                }

            } catch (error) {

                console.error(error);

                document.getElementById("message")
                    .textContent =
                    "Unable to connect to the server.";
            }
        }
    );
}


// =====================================================
// FOUND ANIMAL REPORT
// =====================================================

const foundAnimalForm =
    document.getElementById("foundAnimalForm");

if (foundAnimalForm) {

    foundAnimalForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const species =
                document.getElementById("foundSpecies").value;

            const breed =
                document.getElementById("foundBreed").value;

            const colour =
                document.getElementById("foundColour").value;

            const locality =
                document.getElementById("foundLocality").value;

            const userId =
                document.getElementById("foundUserId").value;


            const report = {
                species: species,
                breed: breed,
                colour: colour,
                locality: locality,
                status: "ACTIVE",
                user: {
                    id: Number(userId)
                }
            };


            try {

                const response =
                    await fetch("/api/found-animals", {

                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify(report)
                    });


                const message =
                    document.getElementById("foundMessage");


                if (response.ok) {

                    const data =
                        await response.json();

                    message.textContent =
                        "Found animal report submitted successfully! Report ID: "
                        + data.id;

                    foundAnimalForm.reset();

                } else {

                    message.textContent =
                        "Failed to submit the report.";

                }

            } catch (error) {

                console.error(error);

                document.getElementById("foundMessage")
                    .textContent =
                    "Unable to connect to the server.";
            }
        }
    );
}


// =====================================================
// FIND POSSIBLE MATCHES
// =====================================================

async function findMatches() {

    const lostPetId =
        document.getElementById("lostPetId").value;

    const result =
        document.getElementById("matchesResult");


    if (!lostPetId) {

        result.innerHTML =
            "<p>Please enter a lost pet report ID.</p>";

        return;
    }


    try {

        const response =
            await fetch(
                "/api/lost-pets/" + lostPetId + "/matches"
            );


        if (!response.ok) {

            result.innerHTML =
                "<p>Unable to find matches.</p>";

            return;
        }


        const matches =
            await response.json();


        if (matches.length === 0) {

            result.innerHTML =
                "<p>No possible matches found.</p>";

            return;
        }


        let html =
            "<h3>Possible Matches</h3>";


        matches.forEach(function (animal) {

            html += `
                <div class="menu-card">

                    <h3>
                        🐾 Found Animal #${animal.id}
                    </h3>

                    <p>
                        <strong>Species:</strong>
                        ${animal.species}
                    </p>

                    <p>
                        <strong>Breed:</strong>
                        ${animal.breed}
                    </p>

                    <p>
                        <strong>Colour:</strong>
                        ${animal.colour}
                    </p>

                    <p>
                        <strong>Locality:</strong>
                        ${animal.locality}
                    </p>

                    <p>
                        <strong>Status:</strong>
                        ${animal.status}
                    </p>

                </div>
            `;
        });


        result.innerHTML = html;


    } catch (error) {

        console.error(error);

        result.innerHTML =
            "<p>Unable to connect to the server.</p>";
    }
}


// =====================================================
// SEARCH LOST AND FOUND REPORTS
// =====================================================

async function searchReports() {

    const locality =
        document.getElementById("searchLocality").value;

    const results =
        document.getElementById("searchResults");


    if (!locality) {

        results.innerHTML =
            "<p>Please enter a locality.</p>";

        return;
    }


    try {

        const lostResponse =
            await fetch(
                "/api/lost-pets/search?locality="
                + encodeURIComponent(locality)
            );


        const foundResponse =
            await fetch(
                "/api/found-animals/search?locality="
                + encodeURIComponent(locality)
            );


        if (!lostResponse.ok ||
            !foundResponse.ok) {

            results.innerHTML =
                "<p>Unable to search reports.</p>";

            return;
        }


        const lostReports =
            await lostResponse.json();


        const foundReports =
            await foundResponse.json();


        let html = "";


        // ---------------------------------------------
        // LOST PET REPORTS
        // ---------------------------------------------

        html += "<h3>Lost Pet Reports</h3>";


        if (lostReports.length === 0) {

            html +=
                "<p>No lost pet reports found.</p>";

        } else {

            lostReports.forEach(function (report) {

                html += `
                    <div class="menu-card">

                        <h3>
                            🐕 Lost Pet #${report.id}
                        </h3>

                        <p>
                            <strong>Species:</strong>
                            ${report.species}
                        </p>

                        <p>
                            <strong>Breed:</strong>
                            ${report.breed}
                        </p>

                        <p>
                            <strong>Colour:</strong>
                            ${report.colour}
                        </p>

                        <p>
                            <strong>Locality:</strong>
                            ${report.locality}
                        </p>

                        <p>
                            <strong>Status:</strong>
                            ${report.status}
                        </p>

                        <button
                            onclick="resolveLostReport(${report.id})">
                            Mark as Resolved
                        </button>

                    </div>
                `;
            });
        }


        // ---------------------------------------------
        // FOUND ANIMAL REPORTS
        // ---------------------------------------------

        html += "<h3>Found Animal Reports</h3>";


        if (foundReports.length === 0) {

            html +=
                "<p>No found animal reports found.</p>";

        } else {

            foundReports.forEach(function (report) {

                html += `
                    <div class="menu-card">

                        <h3>
                            🐾 Found Animal #${report.id}
                        </h3>

                        <p>
                            <strong>Species:</strong>
                            ${report.species}
                        </p>

                        <p>
                            <strong>Breed:</strong>
                            ${report.breed}
                        </p>

                        <p>
                            <strong>Colour:</strong>
                            ${report.colour}
                        </p>

                        <p>
                            <strong>Locality:</strong>
                            ${report.locality}
                        </p>

                        <p>
                            <strong>Status:</strong>
                            ${report.status}
                        </p>

                        <button
                            onclick="resolveFoundReport(${report.id})">
                            Mark as Resolved
                        </button>

                    </div>
                `;
            });
        }


        results.innerHTML = html;


    } catch (error) {

        console.error(error);

        results.innerHTML =
            "<p>Unable to connect to the server.</p>";
    }
}


// =====================================================
// RESOLVE LOST PET REPORT
// =====================================================

async function resolveLostReport(id) {

    try {

        const response =
            await fetch(
                "/api/lost-pets/" + id + "/resolve",
                {
                    method: "PUT"
                }
            );


        if (response.ok) {

            alert(
                "Lost pet report resolved."
            );

            searchReports();

        } else {

            alert(
                "Failed to resolve lost pet report."
            );
        }


    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}


// =====================================================
// RESOLVE FOUND ANIMAL REPORT
// =====================================================

async function resolveFoundReport(id) {

    try {

        const response =
            await fetch(
                "/api/found-animals/" + id + "/resolve",
                {
                    method: "PUT"
                }
            );


        if (response.ok) {

            alert(
                "Found animal report resolved."
            );

            searchReports();

        } else {

            alert(
                "Failed to resolve found animal report."
            );
        }


    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}