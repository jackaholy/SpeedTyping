document.addEventListener('DOMContentLoaded', function () {
    let [milliseconds, seconds, minutes] = [0, 0, 0];
    let timeRef = document.querySelector(".timer-display");
    let interval = null;

    const targetContent = document.getElementById("targetContent");
    const userContent = document.getElementById("userContent");
    // Thought about adding a totalTime const below to help grab the time in seconds.
    // const totalTime = document.getElementById("totalTime");

    function displayTimer() {
        milliseconds += 10;
        if (milliseconds == 1000) {
            milliseconds = 0;
            // ALSO thought about adding totalTime below.
            // seconds++;
            total_seconds++;
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

    // MORE submitting the time functionality.
    // function submitTime() {
    //     // Set the total_seconds in the hidden input field
    //     document.getElementById("total_seconds_input").value = totalTime;
    //     document.getElementById("testForm").submit();
    // }
    // if (userContent.value === targetContent.value) {
    //     submitTime();

    //     (This may not need to be here)
    //     clearInterval(interval);
    //     interval = null;
    //     totalTime.value = seconds;

    // Start timer when user begins typing
    userContent.addEventListener("input", () => {
        if (interval === null) {
            interval = setInterval(displayTimer, 10);
        }
        // Stop the timer if the text matches
        if (userContent.value === targetString) {
            clearInterval(interval);
            interval = null;
        }
    });
});