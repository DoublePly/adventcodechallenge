import java.io.File


val sonarValues: MutableList<Int> = mutableListOf()


fun main() {
    adventOnePartOne()
   //adventOnePartTwo()

}

fun adventOnePartOne(){
    readFileDayOne("src/DayOneInput.txt")
    var increasedCounter: Int = 0
    val startingIndex: Int = 1
    val endIndex: Int = sonarValues.size - 1
    for (i in (startingIndex..endIndex)) {
        if (sonarValues[i] > sonarValues[i - 1]) {
            increasedCounter++
        }
    }
    println(increasedCounter)
}

fun adventOnePartTwo(){
    readFileDayOne("src/DayOneInput.txt")
    var increasedCounter: Int = 0
    val startingIndex: Int = 1
    val endIndex: Int = sonarValues.size - 3
    for (i in (startingIndex..endIndex)) {
        if (calcTupleSum(i) > calcTupleSum(i - 1)) {
            increasedCounter++
        }
    }
    println(increasedCounter)
}

fun readFileDayOne(fileName: String)
        = File(fileName).forEachLine {
            sonarValues.add(it.toInt())
        }


fun calcTupleSum(startingIndex: Int): Int {
    return sonarValues[startingIndex] + sonarValues[startingIndex + 1] + sonarValues[startingIndex + 2]
}
