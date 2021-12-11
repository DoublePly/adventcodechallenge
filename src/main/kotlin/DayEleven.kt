import java.io.File

var octopusEnergyLevels: MutableList<Int> = mutableListOf()
var octoFlashCounter: Int = 0
var simultaneousOctoFlash: Int = 0
var stepNumber: Int = 0
var atZeroEnergyCounter: Int = 0

fun main() {
    //adventElevenPartOne()
    adventElevenPartTwo()
}


fun adventElevenPartOne() {
    readFileDayEleven("src/DayElevenInput.txt")
    for (i in 1..100) {
        increaseEnergyLevels()
    }
    println(octoFlashCounter)
}

fun adventElevenPartTwo() {
    readFileDayEleven("src/DayElevenInput.txt")
    while (simultaneousOctoFlash == 0) {
        increaseEnergyLevels()
    }
    println(simultaneousOctoFlash)
}

fun readFileDayEleven(fileName: String) = File(fileName).forEachLine { line ->
    line.forEach {
        octopusEnergyLevels.add(it.toString().toInt())
    }
}

fun increaseEnergyLevels() {
    stepNumber += 1
    for (i in 0 until octopusEnergyLevels.size) {
        octopusEnergyLevels[i] += 1
    }
    octopusFlashCheck(stepNumber)
}

fun octopusFlashCheck(stepNumber: Int) {

    var octoFlashUnavailable = false
    while (!octoFlashUnavailable) {
        var octoCounter = 0
        for (i in 0 until octopusEnergyLevels.size) {
            if (octopusEnergyLevels[i] > 9) {
                octopusFlash(i)
            } else {
                octoCounter += 1
            }
            if (octoCounter == octopusEnergyLevels.size) {
                octoFlashUnavailable = true
            }
        }
    }
    for (i in 0 until octopusEnergyLevels.size) {
        if (octopusEnergyLevels[i] == 0) {
            atZeroEnergyCounter += 1
        }
    }
    if (atZeroEnergyCounter == octopusEnergyLevels.size) {
        simultaneousOctoFlash = stepNumber
    } else {
        atZeroEnergyCounter = 0
    }

}

fun octopusFlash(index: Int) {
    var leftIndexLimit = index - index.mod(10)
    var rightIndexLimit = index + (9 - index.mod(10))
    var upperIndex = index - 10
    var lowerIndex = index + 10
    var leftIndex = index - 1
    var rightIndex = index + 1
    var upperLeftIndex = leftIndex - 10
    var upperRightIndex = rightIndex - 10
    var lowerLeftIndex = leftIndex + 10
    var lowerRightIndex = rightIndex + 10

    octopusEnergyLevels[index] = 0
    octoFlashCounter += 1

    if (leftIndex >= leftIndexLimit && octopusEnergyLevels[leftIndex] != 0) {
        octopusEnergyLevels[leftIndex] += 1
    }
    if (rightIndex <= rightIndexLimit && octopusEnergyLevels[rightIndex] != 0) {
        octopusEnergyLevels[rightIndex] += 1
    }
    if (upperIndex >= 0 && octopusEnergyLevels[upperIndex] != 0) {
        octopusEnergyLevels[upperIndex] += 1
    }
    if (lowerIndex < octopusEnergyLevels.size && octopusEnergyLevels[lowerIndex] != 0) {
        octopusEnergyLevels[lowerIndex] += 1
    }


    if (leftIndex >= leftIndexLimit && upperLeftIndex >= 0 && octopusEnergyLevels[upperLeftIndex] != 0) {
        octopusEnergyLevels[upperLeftIndex] += 1
    }
    if (leftIndex >= leftIndexLimit && lowerLeftIndex < octopusEnergyLevels.size && octopusEnergyLevels[lowerLeftIndex] != 0) {
        octopusEnergyLevels[lowerLeftIndex] += 1
    }
    if (rightIndex <= rightIndexLimit && upperRightIndex > 0 && octopusEnergyLevels[upperRightIndex] != 0) {
        octopusEnergyLevels[upperRightIndex] += 1
    }
    if (rightIndex <= rightIndexLimit && lowerRightIndex < octopusEnergyLevels.size && octopusEnergyLevels[lowerRightIndex] != 0) {
        octopusEnergyLevels[lowerRightIndex] += 1
    }
}

