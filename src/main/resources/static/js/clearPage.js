// This will run every time the page loads
window.addEventListener('load', function() {
    // Find the textbox and clear its content
    const userContent = document.getElementById('userContent');
    if (userContent) {
        userContent.value = ''; // Clear the textbox
    }
});
