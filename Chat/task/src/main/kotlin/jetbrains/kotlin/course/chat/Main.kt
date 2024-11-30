package jetbrains.kotlin.course.chat

fun main() {
    println("Hello! I'm glad to meet you, let me get to know you better! What is your name?")
    val userName: String? = readlnOrNull()
    println("Nice to meet you, $userName! My name is Kotlin Bot! I am a young programming language created in 2010. How old are you?")
    val userAge: String? = readlnOrNull()
    println("$userAge is great! I hope you successfully complete this course! Anyone can learn programming at any age!")
}
