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

    function countSpaces(str) {
        const matches = str.match(/ /g);
        return matches ? matches.length : 0;
    }

    // Start timer when user begins typing
    userContent.addEventListener("input", () => {
        if (interval === null) {
            interval = setInterval(displayTimer, 10);
        }

    let userSpaces = countSpaces(userContent.value);
    let targetSpaces = countSpaces(targetString);

    // Check to see if spaces match
    if (userSpaces >= targetSpaces) {
        // Split both strings into arrays of words
        let userWords = userContent.value.split(' ');
        let targetWords = targetString.split(' ');

        // Get the last word from each array
        let userLastWord = userWords[userWords.length - 1];
        let targetLastWord = targetWords[targetWords.length - 1];

        // Check if the lengths of the last words match
        if ((userWords.length === targetWords.length && userLastWord.length === targetLastWord.length) || userWords.length > targetWords.length + 1) {
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
            // Stop the timer
            clearInterval(interval);
            console.log('Typing complete. Timer has stopped.');
            interval = null;
        }
    }
    });
});