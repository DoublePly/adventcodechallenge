import java.io.File

var horizontalPosition: Int = 0
var depth: Int = 0
var aim: Int = 0

fun main() {
    adventTwoPartOne()
    //adventTwoPartTwo()

}

fun adventTwoPartOne() {
    readFileDayTwoPartOne("src/DayTwoInput.txt")
    println(horizontalPosition * depth)
}

fun adventTwoPartTwo() {
    readFileDayTwoPartTwo("src/DayTwoInput.txt")
    println(horizontalPosition * depth)
}

fun readFileDayTwoPartOne(fileName: String) = File(fileName).forEachLine {
    val delimiter = " "
    var result: List<String> = it.split(delimiter)
    when (result[0]) {
        "up" -> depth -= result[1].toInt()
        "down" -> depth += result[1].toInt()
        "forward" -> horizontalPosition += result[1].toInt()
        else -> println("Else executed. Check logic")
    }

}

fun readFileDayTwoPartTwo(fileName: String) = File(fileName).forEachLine {
    val delimiter = " "
    var result: List<String> = it.split(delimiter)
    when (result[0]) {
        "up" -> aim -= result[1].toInt()
        "down" -> aim += result[1].toInt()
        "forward" -> calcHorizontal(result[1].toInt())
        else -> println("Else executed. Check logic")
    }

}

fun calcHorizontal(forwardValue: Int) {
    horizontalPosition += forwardValue
    depth += (aim * forwardValue)
}
