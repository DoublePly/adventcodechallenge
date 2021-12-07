import java.io.File

var crabSubmarinePositionsRaw: MutableList<String> = mutableListOf()
var crabSubmarinePositions: MutableList<Int> = mutableListOf()
var crabSubmarinePositionAndFuelCost: MutableMap<Int, Int> = mutableMapOf()
var lowestPositionValue: Int = 0
var highestPositionValue: Int = 0
var lowestFuelCost: Int = 0

fun main() {
    adventSevenPartOne()
    //adventSevenPartTwo()
}

fun adventSevenPartOne() {
    readFileDaySeven("src/DaySevenInput.txt")
    setUpCrabSubmarineData()
    calcLowestValuePosition()
    calcHighestValuePosition()
    calcFuelAndPositionChoices()
    findLowestFuel()
    println(lowestFuelCost)
}

fun adventSevenPartTwo() {
    readFileDaySeven("src/DaySevenInput.txt")
    setUpCrabSubmarineData()
    calcLowestValuePosition()
    calcHighestValuePosition()
    calcFuelAndPositionChoicesPartTwo()
    findLowestFuel()
    println(lowestFuelCost)
}

fun readFileDaySeven(fileName: String) = File(fileName).forEachLine {
    crabSubmarinePositionsRaw.add(it)
}

fun setUpCrabSubmarineData() {
    var holdingList: MutableList<String> =
        crabSubmarinePositionsRaw[0].toString().split(commaDelimiter) as MutableList<String>
    for (item in holdingList) {
        crabSubmarinePositions.add(item.toInt())
    }
    println(crabSubmarinePositions.size)
}

fun calcFuelAndPositionChoices() {
    var totalFuelCost: Int = 0
    for (i in lowestPositionValue..highestPositionValue) {
        for (item in crabSubmarinePositions) {
            if (item > i) {
                totalFuelCost += item - i
            } else if (item < i) {
                totalFuelCost += i - item
            }
        }
        crabSubmarinePositionAndFuelCost.put(i, totalFuelCost)
        totalFuelCost = 0
    }
}

fun calcFuelAndPositionChoicesPartTwo() {
    var totalFuelCost: Int = 0
    for (i in lowestPositionValue..highestPositionValue) {
        for (item in crabSubmarinePositions) {
            if (item > i) {
                totalFuelCost += crabEngineBurn(item - i)
            } else if (item < i) {
                totalFuelCost += crabEngineBurn(i - item)
            }
        }
        crabSubmarinePositionAndFuelCost.put(i, totalFuelCost)
        totalFuelCost = 0
    }
}

fun crabEngineBurn(numberOfSteps: Int): Int {
    var holdingValue: Int = 0
    for (i in 0 until numberOfSteps) {
        holdingValue += 1 + i
    }
    return holdingValue

}

fun calcLowestValuePosition() {
    var holdingValue: Int = crabSubmarinePositions[0]
    for (item in crabSubmarinePositions) {
        if (item < holdingValue) {
            holdingValue = item
        }
    }
    lowestPositionValue = holdingValue
}

fun calcHighestValuePosition() {
    var holdingValue: Int = crabSubmarinePositions[0]
    for (item in crabSubmarinePositions) {
        if (item > holdingValue) {
            holdingValue = item
        }
    }
    highestPositionValue = holdingValue
}


fun findLowestFuel() {
    var holdingPositionValues: MutableList<Int> = mutableListOf()
    crabSubmarinePositionAndFuelCost.forEach {
        holdingPositionValues.add(it.value)
    }
    lowestFuelCost = holdingPositionValues[0]
    for (item in holdingPositionValues) {
        if (item < lowestFuelCost) {
            lowestFuelCost = item
        }
    }

}