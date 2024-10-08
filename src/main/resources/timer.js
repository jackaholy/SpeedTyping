// Got with help from chatgpt.com.
let timer;
let startTime;
let elapsedTime = 0;

const typedContentArea = document.getElementById("typedContent");
const contentArea = document.querySelector("textarea[name='contentArea']").innerText;
const timeInput = document.getElementById("timeInput");

// Start timer when user types for the first time
typedContentArea.addEventListener("input", function () {
    if (!timer) {
        startTime = new Date(); // Record the start time
        timer = setInterval(updateElapsedTime, 10); // Update time every 10 milliseconds (0.01s)
    }

    // Stop the timer when the typed content matches the target content
    if (typedContentArea.value === contentArea) {
        clearInterval(timer); // Stop the timer
        timer = null; // Reset timer reference
        alert("You finished typing! Time: " + (elapsedTime / 100).toFixed(2) + " seconds.");
    }
});

// Update the elapsed time
function updateElapsedTime() {
    const now = new Date();
    elapsedTime = now - startTime; // Elapsed time in milliseconds
    timeInput.value = (elapsedTime / 1000).toFixed(2); // Update the hidden input field in seconds
}
