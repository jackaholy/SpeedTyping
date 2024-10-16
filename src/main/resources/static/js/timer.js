let [milliseconds, seconds, minutes] = [0, 0, 0];
let timeRef = document.querySelector(".timer-display");
let interval = null;

const targetContent = document.getElementById("targetContent");
const userContent = document.getElementById("userContent");

// document.getElementById("pause-timer").addEventListener("click", () => {
//     clearInterval(int);
// });
//
// document.getElementById("reset-timer").addEventListener("click", () => {
//     clearInterval(int);
//     [milliseconds, seconds, minutes] = [0, 0, 0];
//     timeRef.innerHTML = "00 : 00 : 000 ";
// });

function displayTimer() {
    milliseconds += 10;
    if (milliseconds == 1000) {
        milliseconds = 0;
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
    }

    let m = minutes < 10 ? "0" + minutes : minutes;
    let s = seconds < 10 ? "0" + seconds : seconds;
    let ms =
        milliseconds < 10
            ? "00" + milliseconds
            : milliseconds < 100
                ? "0" + milliseconds
                : milliseconds;

    timeRef.innerHTML = `${m} : ${s} : ${ms}`;
}
    // Start timer when user begins typing
    userContent.addEventListener("input", () => {
        if (interval === null) {
            interval = setInterval(displayTimer, 10);
        }
        // Stop the timer if the text matches
        if (userContent.value === targetContent.value) {
            clearInterval(interval);
            interval = null;
        }
    });
