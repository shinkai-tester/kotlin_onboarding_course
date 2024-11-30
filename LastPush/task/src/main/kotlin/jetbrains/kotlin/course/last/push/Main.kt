package jetbrains.kotlin.course.last.push

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }

            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }

            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {

    if (height == 1) {
        return repeatHorizontally(pattern, width, getPatternWidth(pattern)) + newLineSymbol
    }
    val firstLine = repeatHorizontally(pattern, width, getPatternWidth(pattern))
    val canvasWithoutFirst = mutableListOf<String>()
    for (i in 1 until height) {
        canvasWithoutFirst.add(
            dropTopLine(
                repeatHorizontally(pattern, width, getPatternWidth(pattern)),
                width,
                getPatternHeight(pattern),
                getPatternWidth(pattern)
            )
        )
    }
    return firstLine + newLineSymbol + canvasWithoutFirst.joinToString(newLineSymbol) + newLineSymbol
}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    val result = mutableListOf<String>()
    val patternWidth = getPatternWidth(pattern)

    for (i in 0 until height) {
        for (row in pattern.lines()) {
            val repeatedRow = if (width == 1) {
                fillPatternRow(row, patternWidth)
            } else {
                (0 until width).joinToString("") { index ->
                    if ((i % 2 == 0 && index % 2 == 0) || (i % 2 != 0 && index % 2 != 0)) {
                        fillPatternRow(row, patternWidth)
                    } else {
                        " ".repeat(patternWidth)
                    }
                }
            }
            result.add(repeatedRow)
        }
    }

    return result.joinToString(newLineSymbol) + newLineSymbol
}


fun getPatternHeight(pattern: String): Int = pattern.lines().size

fun fillPatternRow(patternRow: String, patternWidth: Int): String {
    when {
        patternRow.length < patternWidth -> {
            val updatedPatternRow = patternRow + "$separator".repeat(patternWidth - patternRow.length)
            return updatedPatternRow
        }

        patternRow.length == patternWidth -> return patternRow
        else -> throw IllegalStateException("The length of patternRow exceeds the specified patternWidth.")
    }
}

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int): String {
    val arrayOfPatternRows = mutableListOf<String>()

    for (row in pattern.lines()) {
        arrayOfPatternRows.add(fillPatternRow(row, patternWidth).repeat(n))
    }

    return arrayOfPatternRows.joinToString(newLineSymbol)
}

fun dropTopLine(image: String, width: Int, patternHeight: Int, patternWidth: Int): String {
    val totalWidth = patternWidth * width
    val lines = image.lines()

    return if (patternHeight > 1 && lines.isNotEmpty() && lines[0].length >= totalWidth) {
        lines.drop(1).joinToString(newLineSymbol)
    } else {
        image
    }
}


// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }

            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int): String {
    return when (generatorName) {
        "canvas" -> canvasGenerator(pattern, width, height)
        "canvasGaps" -> canvasWithGapsGenerator(pattern, width, height)
        else -> error("Unexpected filter name!")
    }
}

fun main() {
    // Uncomment this code on the last step of the game

    val pattern = getPattern()
    val generatorName = chooseGenerator()
    println("Please input the width of the resulting picture:")
    val width = safeReadLine().toInt()
    println("Please input the height of the resulting picture:")
    val height = safeReadLine().toInt()

    println("The pattern:$newLineSymbol${pattern.trimIndent()}")

    println("The generated image:")
    println(applyGenerator(pattern, generatorName, width, height))
}
