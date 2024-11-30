package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess: String): Boolean = secret == currentGuess.replace(" ", "")

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    var neuUserWord = currentUserWord

    for (i in secret) {
        if (i != guess) {
            println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
        } else {
            neuUserWord = generateNewUserWord(secret, guess, currentUserWord)
            println("Great! This letter is in the word! The current word is $neuUserWord")
        }
    }
    return neuUserWord
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    val separator = ' '
    var result = StringBuilder()

    for (i in secret.indices) {
        if (secret[i] == guess) {
            result.append("${secret[i]}$separator")
        } else {
            result.append("${currentUserWord[i * 2]}$separator")
        }
    }
    return result.toString().removeSuffix(" ")
}

fun generateSecret(): String = words.random()

fun getHiddenSecret(wordLength: Int): String = List(wordLength) { underscore }.joinToString(" ")

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    } else if (!userInput[0].isLetter()) {
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun safeUserInput(): Char {
    var isCorrect: Boolean
    var userInput: String

    do {
        println("Please input your guess.")
        userInput = safeReadLine()
        isCorrect = isCorrectInput(userInput)
    } while (!isCorrect)
    return userInput.uppercase()[0]
}

fun playGame(secret: String, maxAttemptsCount: Int) {
    var attempts = 0
    var hiddenSecret = getHiddenSecret(secret.length)
    var currentGuess = ""
    println("I guessed a word: $hiddenSecret")
    do {
        val guess = safeUserInput()
        attempts++
        currentGuess = getRoundResults(secret, guess, hiddenSecret)
    } while (attempts <= maxAttemptsCount)

    if (isLost(isComplete(secret, currentGuess), attempts, maxAttemptsCount)) {
        println("Sorry, you lost! My word is $secret")
    } else if (isWon(isComplete(secret, currentGuess), attempts, maxAttemptsCount)) {
        println("Congratulations! You guessed it!")
    }
}

fun main() {
    println(getGameRules(wordLength, maxAttemptsCount))
    playGame(generateSecret(), maxAttemptsCount)
    
}
