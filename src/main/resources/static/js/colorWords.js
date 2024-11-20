document.addEventListener('DOMContentLoaded', function () {
    // The variable 'targetString' will be defined in the HTML
    const targetText = document.getElementById('targetContent');

    // Initialize the target content with spans for each word
    const targetWords = targetString.split(' ');
    let targetHTML = '';
    let charIndex = 0;

    // Got these lines with help from chatgpt.com
    targetWords.forEach((word, wordIndex) => {
        for (let i = 0; i < word.length; i++) {
            targetHTML += `<span id="char-${charIndex}">${word[i]}</span>`;
            charIndex++;
        }
        if (wordIndex < targetWords.length - 1) {
            targetHTML += `<span id="char-${charIndex}" class="space"> </span>`;
            charIndex++; // account for the space between words
        }
    });
    targetText.innerHTML = targetHTML;

    // Event listener for user input
    const userContent = document.getElementById('userContent');
    userContent.addEventListener('input', function () {
        const userInput = userContent.value;
        const userWords = userInput.split(' ');

        // Reset colors for all spans
        for (let i = 0; i < targetString.length; i++) {
            const charSpan = document.getElementById(`char-${i}`);
            if (charSpan) charSpan.className = '';
        }

        let charIdx = 0;
        targetWords.forEach((targetWord, wordIdx) => {
            const userWord = userWords[wordIdx] || '';
            const isFullyTyped = userWord.length === targetWord.length;
            const isCorrect = userWord === targetWord;

            // Color each character based on user input and target word
            for (let i = 0; i < targetWord.length; i++) {
                const charSpan = document.getElementById(`char-${charIdx}`);

                if (charSpan) {
                    if (i < userWord.length) {
                        const isCharCorrect = userWord[i] === targetWord[i];
                        charSpan.className = isCharCorrect ? 'correct' : 'incorrect';
                    } else {
                        charSpan.className = '';
                    }
                }
                charIdx++;
            }

            // Mark the word as correct or incorrect if fully typed
            if (isFullyTyped) {
                const wordColorClass = isCorrect ? 'correct' : 'incorrect';
                for (let i = 0; i < targetWord.length; i++) {
                    const charSpan = document.getElementById(`char-${charIdx - targetWord.length + i}`);
                    if (charSpan) charSpan.className = wordColorClass;
                }
            }

            // Move to the space character if there is one
            if (document.getElementById(`char-${charIdx}`)) {
                document.getElementById(`char-${charIdx}`).className = '';
                charIdx++;
            }
        });
    });
});
