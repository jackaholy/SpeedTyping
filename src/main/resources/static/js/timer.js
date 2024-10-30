document.addEventListener('DOMContentLoaded', function () {
    let [milliseconds, seconds, minutes] = [0, 0, 0];
    let timeRef = document.querySelector(".timer-display");
    let interval = null;

    const targetContent = document.getElementById("targetContent");
    const userContent = document.getElementById("userContent");
    // Thought about adding a totalTime const below to help grab the time in seconds.
    // const totalTime = document.getElementById("totalTime");

    function getFormattedTime(minutes, seconds, milliseconds) {
        let m = minutes < 10 ? "0" + minutes : minutes;
        let s = seconds < 10 ? "0" + seconds : seconds;
        let ms =
            milliseconds < 10
                ? "00" + milliseconds
                : milliseconds < 100
                    ? "0" + milliseconds
                    : milliseconds;

        return {m, s, ms};
    }

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

        // Call the new function to get formatted time
        let {m, s, ms} = getFormattedTime(minutes, seconds, milliseconds);

        timeRef.innerHTML = `${m} : ${s} : ${ms}`;
    }

    // Start timer when user begins typing
    userContent.addEventListener("input", () => {
        if (interval === null) {
            interval = setInterval(displayTimer, 10);
        }
        // Stop the timer if the text length matches
        if (userContent.value.length === targetString.length) {
            let form = document.getElementById('testForm');
            let event = new Event('submit', {
                bubbles: true,
                cancelable: true
            });
            let totalTime = minutes * 60 + seconds + milliseconds / 1000;
            console.log("total time: ", totalTime);

            const timeInput = document.createElement('input');
            timeInput.type = 'hidden';
            timeInput.name = 'time';
            timeInput.value = totalTime;
            form.appendChild(timeInput);

            form.dispatchEvent(event);
            console.log('typing complete, submit test now');
            clearInterval(interval);
            interval = null;
        }
    });
});