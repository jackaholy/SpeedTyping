<!-- Generated using ChatGPT: prompt paraphrase, 'when I press an HTML submit button, show a popup before redirecting'. Re-prompted and refactored answer for final code -->
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('testForm');
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the default form submission

        let username = '';
        while (true) {
            username = prompt("Please enter your username (>3 characters and only letters or numbers):");
            if (username === null) {
                // User clicked 'Cancel' or closed the prompt
                alert("Username is required to proceed.");
            } else if (isValidUsername(username)) {
                // Username is valid
                break;
            } else {
                alert("Invalid username. Please try again.");
            }
        }

        // Create a hidden input to include the username in the form data
        const usernameInput = document.createElement('input');
        usernameInput.type = 'hidden';
        usernameInput.name = 'username';
        usernameInput.value = username; // Overwrite existing value
        form.appendChild(usernameInput);

        // Now submit the form
        form.submit();
    });

    // Function to validate the username
    function isValidUsername(username) {
        // Define your validation criteria here
        // For example, check if the username is not empty and doesn't contain invalid characters
        if (username.trim() === '') {
            return false;
        }

        // Additional validation rules can be added here
        // Example: Username must be at least 3 characters and only contain letters and numbers
        const usernameRegex = /^[a-zA-Z0-9]{3,}$/;
        return usernameRegex.test(username);
    }
});
