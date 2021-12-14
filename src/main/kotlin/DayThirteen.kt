import java.io.File


var foldingInstructionsRaw: MutableList<String> = mutableListOf()
var foldingInstructionsCoordRaw: MutableList<String> = mutableListOf()
var foldingInstructionsFoldRaw: MutableList<String> = mutableListOf()
var foldingInstructionsFoldSecondPartRaw: MutableList<String> = mutableListOf()

var startingCoordinates: MutableList<Coordinate> = mutableListOf()
var foldingInstructions: MutableList<Pair<String, Int>> = mutableListOf()
val equalsDelimiter: String = "="

fun main() {
    //adventThirteenPartOne()
    adventThirteenPartTwo()
}

fun adventThirteenPartOne() {
    readFileDayThirteen("src/DayThirteenInput.txt")
    setUpInstructionData()
    setUpCoordinates()
    setUpFoldInstructions()
    foldLeft(655)
}

fun adventThirteenPartTwo() {
    readFileDayThirteen("src/DayThirteenInput.txt")
    setUpInstructionData()
    setUpCoordinates()
    setUpFoldInstructions()
    startFolding()
}

fun readFileDayThirteen(fileName: String) = File(fileName).forEachLine {
    foldingInstructionsRaw.add(it)
    }

fun setUpInstructionData(){
    var result = foldingInstructionsRaw.indexOf("")
    for (x in 0 until result) {
        foldingInstructionsCoordRaw.add(foldingInstructionsRaw[x])
    }
    for (y in result + 1 until foldingInstructionsRaw.size) {
        foldingInstructionsFoldRaw.add(foldingInstructionsRaw[y])
    }
}

fun setUpCoordinates() {
    foldingInstructionsCoordRaw.forEach{
        var result = it.split(commaDelimiter)
        startingCoordinates.add(Coordinate(result[0].toInt(), result[1].toInt()))
    }
}

fun setUpFoldInstructions(){
    foldingInstructionsFoldRaw.forEach{
        var result = it.split(spaceDelimiter)
        foldingInstructionsFoldSecondPartRaw.add(result[2])
    }
    foldingInstructionsFoldSecondPartRaw.forEach{
        var result = it.split(equalsDelimiter)
        foldingInstructions.add(Pair(result[0], result[1].toInt()))
    }

}

fun startFolding() {
    foldingInstructions.forEach{
        if (it.first == "x") {
            foldLeft(it.second)
        } else {
            foldUp(it.second)
        }
    }
    var result = startingCoordinates.distinct()
    result.forEach{
        var x = it.x
        var y = it.y
        println("($x,$y)")
    }
}

fun foldUp(horizontalLine: Int) {
    startingCoordinates.forEach{
        if (horizontalLine < it.y) {
            it.y = (horizontalLine - (it.y - horizontalLine))
        }
    }
}

fun foldLeft(verticalLine: Int) {
    startingCoordinates.forEach{
        if (verticalLine < it.x) {
            it.x = (verticalLine - (it.x - verticalLine))
        }
    }
}

data class Coordinate(var x: Int, var y: Int)

