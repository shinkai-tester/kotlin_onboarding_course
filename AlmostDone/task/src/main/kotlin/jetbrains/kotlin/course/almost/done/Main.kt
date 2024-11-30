package jetbrains.kotlin.course.almost.done

fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}

fun photoshop() {
    val picture = getPicture()
    val filter = chooseFilter()
    println("The old image: \n$picture")
    val transformedPicture = applyFilter(picture, filter)
    println("The transformed picture: \n$transformedPicture")
}

fun trimPicture(picture: String): String = picture.trimIndent()

fun safeReadLine(): String = readlnOrNull() ?: error("Please make an input!")

fun getPicture(): String {
    println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one.")
    var picture: String = ""
    var isSelected = false

    do {
        val answer = safeReadLine()
        when (answer) {
            "yes" -> {
                picture = choosePicture()
                isSelected = true
            }

            "no" -> {
                println("Please input a custom picture")
                picture = safeReadLine()
                isSelected = true
            }

            else -> {
                println("Please input 'yes' or 'no'.")
                isSelected = false
            }
        }
    } while (!isSelected)

    return picture
}

fun chooseFilter(): String {
    var filter: String = ""
    println("Please choose the filter: 'borders' or 'squared'.")
    do {
        when (val input = safeReadLine()) {
            "borders", "squared" -> {
                filter = input
            }

            else -> println("Please input 'borders' or 'squared'.")
        }
    } while (filter.isEmpty())

    return filter
}

fun choosePicture(): String {
    do {
        println("Please choose a picture. The possible options are: ${allPictures().joinToString()}.")
        val userInput = safeReadLine()

        getPictureByName(userInput)?.let {
            return it
        }
    } while (true)
}

fun applyBordersFilter(picture: String): String {
    val pictureWidth = getPictureWidth(picture)
    val edge = "$borderSymbol".repeat(pictureWidth + 4)
    val finalPicture = StringBuilder()

    finalPicture.append(edge).append(newLineSymbol)

    for (line in picture.lines()) {
        val newLine = StringBuilder()
        newLine.append(borderSymbol).append(separator).append(line)

        if (line.length < pictureWidth) {
            val lenDiff = pictureWidth - line.length
            newLine.append("$separator".repeat(lenDiff))
        }

        newLine.append(separator).append(borderSymbol)

        finalPicture.append(newLine).append(newLineSymbol)
    }

    finalPicture.append(edge)

    return finalPicture.toString()
}


fun applySquaredFilter(picture: String): String {
    val borderedPicture = applyBordersFilter(picture)
    val builder = StringBuilder()

    for (line in borderedPicture.lines().dropLast(1)) {
        builder.append(line.repeat(2)).append(System.lineSeparator())
    }

    val topPart = builder.toString()

    val lastLine = borderedPicture.lines().last().repeat(2) + System.lineSeparator()

    return topPart + topPart + lastLine
}


fun applyFilter(picture: String, filter: String): String {
    val newPicture = trimPicture(picture)

    return when (filter) {
        "borders" -> {
            applyBordersFilter(newPicture)
        }

        "squared" -> {
            applySquaredFilter(newPicture)
        }

        else -> error("Unknown filter!")
    }
}