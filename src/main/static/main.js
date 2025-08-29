const state = {
    x: 0,
    y: 0,
    r: 1.0,
};

const error = document.getElementById("error");
const possibleXs = new Set([-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]);
const possibleRs = new Set([1.0, 1.5, 2.0, 2.5, 3.0]);

const validateState = (state) => {
    if (isNaN(state.x) || !possibleXs.has(state.x)) {
        error.hidden = false;
        error.innerText = `x must be in [${[...possibleXs].join(" ,")}]`;
        throw new Error("Invalid state");
    }

    if (isNaN(state.y) || state.y < -3 || state.y > 3) {
        error.hidden = false;
        error.innerText = "y must be in range [-3, 3]";
        throw new Error("Invalid state");
    }

    if (isNaN(state.r) || !possibleRs.has(state.r)) {
        error.hidden = false;
        error.innerText = `r must be in [${[...possibleRs].join(" ,")}]`;
        throw new Error("Invalid state");
    }

    error.hidden = true;
}    

document.getElementById("y-input").addEventListener("change", (ev) => {
    state.y = parseFloat(ev.target.value);
});

document.getElementById("x-input").addEventListener("click", (ev) => {
    state.r = parseFloat(ev.target.value);
});

document.getElementById("data_form").addEventListener("submit", async function (ev) {
    // ev.preventDefault();
    //
    // validateState(state);
    //
    // const params = new URLSearchParams(state);
    //
    // const response = await fetch("/fcgi-bin/app.jar?" + params.toString());
    //
    // const results = {
    //     x: state.x,
    //     y: state.y,
    //     r: state.r,
    //     execTime: "",
    //     time: "",
    //     result: false,
    // };
    //
    // if (response.ok) {
    //     const result = await response.json();
    //     results.time = new Date(result.now).toLocaleString();
    //     results.execTime = `${result.time} ns`;
    //     results.result = result.result.toString();
    // } else if (response.status === 400) {
    //     const result = await response.json();
    //     results.time = new Date(result.now).toLocaleString();
    //     results.execTime = "N/A";
    //     results.result = `error: ${result.reason}`;
    // } else {
    //     results.time = "N/A";
    //     results.execTime = "N/A";
    //     results.result = "error"
    // }
    //
    // const prevResults = JSON.parse(localStorage.getItem("results") || "[]");
    // localStorage.setItem("results", JSON.stringify([...prevResults, results]));
});

const prevResults = JSON.parse(localStorage.getItem("results") || "[]");

prevResults.forEach(result => {
    const table = document.getElementById("result_table").getElementsByTagName('tbody')

    const resultRow = document.createElement("tr")
    const rowX = document.createElement("td")
    const rowY = document.createElement("td")
    const rowR = document.createElement("td")
    const rowTime = document.createElement("td")
    const rowAim = document.createElement("td")

    rowX.innerText = result.x.toString();
    rowY.innerText = result.y.toString();
    rowR.innerText = result.r.toString();
    rowTime.innerText = result.time;
    rowAim.innerText = result.result;

    resultRow.append(rowX, rowY, rowR, rowTime, rowAim)

    if(result.result === "true"){
        resultRow.setAttribute("class", "got")
    }else{
        resultRow.setAttribute("class", "missed")
    }

    table.append(resultRow)
});
