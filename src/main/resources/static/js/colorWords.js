document.addEventListener('DOMContentLoaded', function() {
  // The variable 'targetString' will be defined in the HTML
  var targetText = document.getElementById('targetContent');

  // Initialize the target content with spans
  var targetHTML = '';
  for (var i = 0; i < targetString.length; i++) {
    var char = targetString.charAt(i);
    targetHTML += '<span id="char-' + i + '">' + char + '</span>';
  }
  targetText.innerHTML = targetHTML;

  // Event listener for user input
  var userContent = document.getElementById('userContent');
  userContent.addEventListener('input', function() {
    var userInput = userContent.value;
    var userLength = userInput.length;

    for (var i = 0; i < targetString.length; i++) {
      var charSpan = document.getElementById('char-' + i);
      if (charSpan) {
        if (i < userLength) {
          var targetChar = targetString.charAt(i);
          var userChar = userInput.charAt(i);
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