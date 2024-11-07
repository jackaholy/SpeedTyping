document.addEventListener('DOMContentLoaded', function() {
  // The variable 'targetString' will be defined in the HTML
  const targetText = document.getElementById('targetContent');
  console.log("Color words");
  // Initialize the target content with spans
  let targetHTML = '';
  for (let i = 0; i < targetString.length; i++) {
    const char = targetString.charAt(i);
    targetHTML += '<span id="char-' + i + '">' + char + '</span>';
  }
  targetText.innerHTML = targetHTML;

  // Event listener for user input
  const userContent = document.getElementById('userContent');
  userContent.addEventListener('input', function() {
    const userInput = userContent.value;
    const userLength = userInput.length;

    for (let i = 0; i < targetString.length; i++) {
      const charSpan = document.getElementById('char-' + i);
      if (charSpan) {
        if (i < userLength) {
          const targetChar = targetString.charAt(i);
          const userChar = userInput.charAt(i);
          if (userChar === targetChar) {
            charSpan.className = 'correct';
          } else {
            charSpan.className = 'incorrect';
          }
        } else {
          charSpan.className = '';
        }
      }
    }
  });
});