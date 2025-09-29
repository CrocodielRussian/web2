const state = {
    x: 0.0,
    y: 0.0,
    r: NaN,
    action: "",
};

const error = document.getElementById("error");
const possibleXs = new Set([-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]);
const possibleRs = new Set([1.0, 1.5, 2.0, 2.5, 3.0]);

const validateState = (state) => {
    if (isNaN(state.x) || !possibleXs.has(state.x)) {
        error.hidden = false;
        error.innerText = `x must be in [${[...possibleXs].join(", ")}]`;
        throw new Error("Invalid x value");
    }

    if (isNaN(state.y) || Math.abs(state.y) > 3) {
        error.hidden = false;
        error.innerText = "y must be in range [-3, 3]";
        throw new Error("Invalid y value");
    }

    if (isNaN(state.r) || !possibleRs.has(state.r)) {
        error.hidden = false;
        error.innerText = `r must be in [${[...possibleRs].join(", ")}]`;
        throw new Error("Invalid r value");
    }

    error.hidden = true;
};

async function handlerRequest(values) {
    const params = new URLSearchParams(values).toString();
    console.log("Sending POST request with body:", params);

    const response = await fetch('./controller', { // Relative URL for context path
        method: 'POST',
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: params,
    });

    console.log("Response status:", response.status, "Redirected:", response.redirected);

    if (response.ok) {
        if (values.action === "submitForm") {
            const html = await response.text();
            console.log("Received HTML:", html);
            document.open();
            document.write(html);
            document.close();
        } else {
            const results = await response.json();
            console.log("Received JSON:", results);
            drawGraph(state.r);
            drawPoint(results);


            const table = document.getElementById("result_table");
            const newRow = table.insertRow(-1);
            const colX = newRow.insertCell(0);
            const colY = newRow.insertCell(1);
            const colR = newRow.insertCell(2);
            const colAim = newRow.insertCell(3); // Adjusted index (5 seems incorrect)
            colX.innerText = results.x.toString();
            colY.innerText = results.y.toString();
            colR.innerText = results.r.toString();
            colAim.innerText = (results.result) ? "Попал" : "Мимо"

            newRow.setAttribute("class", (results.result) ? "got" : "missed")
        }
    } else {
        const errorData = await response.json();
        console.error("Error response:", errorData);
        error.textContent = errorData.error || 'Ошибка отправки на сервер!';
        error.hidden = false;
    }
}

async function handleSubmitForm(event) {
    event.preventDefault();
    const rInput = document.getElementsByName("choice")[0];

    if (!rInput.value || isNaN(rInput.value)) {
        error.hidden = false;
        error.textContent = "Please enter a valid R value";
        return;
    }

    state.r = parseFloat(rInput.value);
    console.log(`State: x=${state.x}, y=${state.y}, r=${state.r}`);

    try {
        validateState(state);
        state.action = "submitForm";
        error.hidden = true;
        await handlerRequest(state);
    } catch (e) {
        error.hidden = false;
        error.textContent = e.message;
        console.error("Validation error:", e.message);
    }
}

const canvas = document.getElementById('graph');
const ctx = canvas.getContext('2d');
const canvasSize = 300;
const centerX = canvasSize / 2;
const centerY = canvasSize / 2;
const scale = 100;

function drawGraph(r) {
    ctx.fillStyle = "black"
    ctx.clearRect(0, 0, canvasSize, canvasSize);
    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(canvasSize, centerY);
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, canvasSize);
    ctx.strokeStyle = 'black';
    ctx.stroke();

    for (let i = -3; i <= 3; i++) {
        if (i !== 0) {
            ctx.fillText(i, centerX + i * scale / 2 - 5, centerY + 15);
            ctx.fillText(i, centerX - 15, centerY - i * scale / 2 + 5);
        }
    }

    if (r) {
        ctx.fillStyle = 'rgba(0, 0, 255, 0.3)';
        ctx.beginPath();
        ctx.arc(centerX, centerY, r / 2 * scale / 2, Math.PI / 2, Math.PI);
        ctx.lineTo(centerX, centerY);
        ctx.fill();
        ctx.closePath();

        ctx.beginPath();
        ctx.rect(centerX, centerY, -r * scale / 2, -r * scale / 2);
        ctx.fill();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX, centerY - r * scale / 2);
        ctx.lineTo(centerX + r / 2 * scale, centerY);
        ctx.fill();
        ctx.closePath();
    }
}

function drawPoint(result) {
    const px = centerX + result.x * scale;
    const py = centerY - result.y * scale;
    ctx.beginPath();
    ctx.arc(px, py, 3, 0, 2 * Math.PI);
    ctx.fillStyle = result.result ? 'green' : 'red';
    ctx.fill();
}

document.addEventListener("DOMContentLoaded", function () {
    canvas.addEventListener('click', async function (event) {
        error.textContent = '';
        error.hidden = true;

        if (isNaN(state.r)) {
            error.hidden = false;
            error.textContent = 'Невозможно определить координаты: радиус R не установлен!';
            return;
        }

        const rect = canvas.getBoundingClientRect();
        const clickX = event.clientX - rect.left;
        const clickY = event.clientY - rect.top;
        const x = (clickX - centerX) / scale;
        const y = (centerY - clickY) / scale;

        console.log(`${x} ${y}`)

        state.x = x.toFixed(2);
        state.y = y.toFixed(2);

        if (Math.abs(x) > 3 || Math.abs(y) > 3) {
            error.hidden = false;
            error.textContent = 'Точка вне диапазона!';
            return;
        }

        try {
            // validateState(state);
            state.action = "checkPoint";
            await handlerRequest(state);
        } catch (e) {
            error.hidden = false;
            error.textContent = e.message;
            console.error("Canvas click error:", e.message);
        }
    });

    const form = document.getElementById("data_form");
    if (form) {
        form.addEventListener('submit', (event) => {
            handleSubmitForm(event);
        });
    } else {
        console.error("Form with id 'data_form' not found");
    }

    document.getElementById("submit_button").addEventListener('click', (event) => {
        handleSubmitForm(event);
    });

    document.getElementById("y-input").addEventListener("input", (ev) => {
        state.y = parseFloat(ev.target.value) || 0;
        console.log("Updated y:", state.y);
    });

    document.getElementsByName("x-value").forEach((button) => {
        button.addEventListener("click", (ev) => {
            state.x = parseFloat(ev.target.value);
            console.log("Updated x:", state.x);
        });
    });

    document.getElementsByName("choice")[0].addEventListener('input', function() {
        state.r = parseFloat(this.value) || NaN;
        drawGraph(state.r);
        console.log("Updated r:", state.r);
    });
});

drawGraph(null);