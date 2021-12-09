import java.io.File
import kotlin.math.absoluteValue

const val lineDelimiter: String = "|"
var outputValueListRaw: MutableList<String> = mutableListOf()
var uniqueValueCounter: Int = 0
var testData: MutableList<String> = mutableListOf(
    "fdgacbe cefdb cefbgd gcbe", "fcgedb cgb dgebacf gc",
    "cg cg fdcagb cbg", "efabcd cedba gadfec cb", "gecf egdcabf bgf bfgea", "gebdcfa ecba ca fadegcb",
    "cefg dcbef fcge gbcadfe", "ed bcgafe cdgba cbgef", "gbdfcae bgc cg cgb", "fgae cfgab fg bagce"
)
var zeroSegments: String = ""
var oneSegments: String = ""
var twoSegments: String = ""
var threeSegments: String = ""
var fourSegments: String = ""
var fiveSegments: String = ""
var sixSegments: String = ""
var sevenSegments: String = ""
var eightSegments: String = ""
var nineSegments: String = ""

var fiveSegmentList: MutableList<String> = mutableListOf()
var sixSegmentList: MutableList<String> = mutableListOf()


var topSegmentDisplay: String = ""
var upperRightSegmentDisplay: String = ""
var upperLeftSegmentDisplay: String = ""
var lowerLeftSegmentDisplay: String = ""
var lowerRightSegmentDisplay: String = ""
var midSegmentDisplay: String = ""
var bottomSegmentDisplay: String = ""

var total: Long = 0


fun main() {
    //adventEightPartOne()
    adventEightPartTwo()
}

fun adventEightPartOne() {
    readFileDayEightPartOne("src/DayEightInput.txt")
    lookForUniqueSegments()
    println(uniqueValueCounter)
}

fun adventEightPartTwo() {
    readFileDayEightPartTwo("src/DayEightInput.txt")
    println(outputValueListRaw.size)
}

fun readFileDayEightPartOne(fileName: String) = File(fileName).forEachLine {
    var result: MutableList<String> = it.split(lineDelimiter) as MutableList<String>
    outputValueListRaw.add(result[1])
}

fun readFileDayEightPartTwo(fileName: String) = File(fileName).forEachLine {
    var result: MutableList<String> = it.trim().split(lineDelimiter) as MutableList<String>
    var partOne: MutableList<String> = result[0].trim().split(spaceDelimiter) as MutableList<String>
    var partTwo: MutableList<String> = result[1].trim().split(spaceDelimiter) as MutableList<String>
    decipherValues(partOne, partTwo)
}

fun decipherValues(partOne: MutableList<String>, partTwo: MutableList<String>) {
    lookForUniqueSegmentsPartTwo(partOne)
    deduceValues()
    createRemainingSegments()
    calculateOutputTotal(partTwo)
    println(total)
    resetSegments()
}

fun calculateOutputTotal(outputToCalc: MutableList<String>) {
    var outputTotal: String = ""
    println(outputToCalc)
    outputToCalc.forEach {
        when (it.length) {
            2 -> outputTotal += "1"
            4 -> outputTotal += "4"
            3 -> outputTotal += "7"
            7 -> outputTotal += "8"
            5 -> outputTotal += findDigitLengthFive(it)
            6 -> outputTotal += findDigitLengthSix(it)
        }
    }
    sumUpTotal(outputTotal)

}

fun sumUpTotal(outputValueAsString: String) {
    total += outputValueAsString.toLong()
}

fun findDigitLengthFive(output: String): String {
    var twoCounter: Int = 0
    var threeCounter: Int = 0
    var fiveCounter: Int = 0
    var answer: String = ""
    output.forEach{
        if (twoSegments.contains(it.toString())) {
            twoCounter += 1
        }
        if (threeSegments.contains(it.toString())) {
            threeCounter += 1
        }
        if (fiveSegments.contains(it.toString())) {
            fiveCounter += 1
        }
    }

    if (twoCounter == 5) {
        answer = "2"
    } else if (threeCounter == 5) {
        answer = "3"
    } else if (fiveCounter == 5) {
        answer = "5"
    }
    return answer
}

fun findDigitLengthSix(output: String): String {
    var zeroCounter: Int = 0
    var sixCounter: Int = 0
    var nineCounter: Int = 0
    var answer: String = ""


    output.forEach{
        if (zeroSegments.contains(it.toString())) {
            zeroCounter += 1
        }
        if (sixSegments.contains(it.toString())) {
            sixCounter += 1
        }
        if (nineSegments.contains(it.toString())) {
            nineCounter += 1
        }
    }
    if (zeroCounter == 6) {
        answer = "0"
    } else if (sixCounter == 6) {
        answer = "6"
    } else if (nineCounter == 6) {
        answer = "9"
    }
    //println("SIX ANSWER: $answer")
    return answer
}

fun createRemainingSegments() {
    zeroSegments =
        topSegmentDisplay + upperRightSegmentDisplay + upperLeftSegmentDisplay + lowerRightSegmentDisplay + lowerLeftSegmentDisplay + bottomSegmentDisplay
    twoSegments =
        topSegmentDisplay + upperRightSegmentDisplay + midSegmentDisplay + lowerLeftSegmentDisplay + bottomSegmentDisplay
    threeSegments =
        topSegmentDisplay + upperRightSegmentDisplay + midSegmentDisplay + lowerRightSegmentDisplay + bottomSegmentDisplay
    fiveSegments =
        topSegmentDisplay + upperLeftSegmentDisplay + midSegmentDisplay + lowerRightSegmentDisplay + bottomSegmentDisplay
    sixSegments =
        topSegmentDisplay + upperLeftSegmentDisplay + midSegmentDisplay + lowerLeftSegmentDisplay + lowerRightSegmentDisplay + bottomSegmentDisplay
    nineSegments =
        topSegmentDisplay + upperLeftSegmentDisplay + upperRightSegmentDisplay + midSegmentDisplay + lowerRightSegmentDisplay + bottomSegmentDisplay
}

fun resetSegments() {
    zeroSegments = ""
    oneSegments = ""
    twoSegments = ""
    threeSegments = ""
    fourSegments = ""
    fiveSegments = ""
    sixSegments = ""
    sevenSegments = ""
    eightSegments = ""
    nineSegments = ""

    topSegmentDisplay = ""
    upperRightSegmentDisplay = ""
    upperLeftSegmentDisplay = ""
    lowerLeftSegmentDisplay = ""
    lowerRightSegmentDisplay = ""
    midSegmentDisplay = ""
    bottomSegmentDisplay = ""

    fiveSegmentList.clear()
    sixSegmentList.clear()
}

fun lookForUniqueSegments() {
    outputValueListRaw.forEach {
        var result: MutableList<String> = it.trim().split(spaceDelimiter) as MutableList<String>
        for (item in result) {
            var outPutAsCharArray = item.toCharArray()
            if (outPutAsCharArray.distinct().size == outPutAsCharArray.size) {
                if (isOneFourSevenOrEight(item.length)) {
                    println(item)
                    uniqueValueCounter++
                }
            }
        }
    }
}

fun lookForUniqueSegmentsPartTwo(listToDecipher: MutableList<String>) {
    listToDecipher.forEach {
        var result: MutableList<String> = it.trim().split(spaceDelimiter) as MutableList<String>
        for (item in result) {
            var outPutAsCharArray = item.toCharArray()
            if (outPutAsCharArray.distinct().size == outPutAsCharArray.size) {
                when (item.length) {
                    2 -> oneSegments = item
                    3 -> sevenSegments = item
                    4 -> fourSegments = item
                    7 -> eightSegments = item
                    5 -> fiveSegmentList.add(item)
                    6 -> sixSegmentList.add(item)
                }
            }
        }
    }
}

fun isOneFourSevenOrEight(outputValueLength: Int): Boolean {
    return when (outputValueLength) {
        2 -> true
        3 -> true
        4 -> true
        7 -> true
        else -> false
    }
}

fun deduceValues() {
    //top segment
        sevenSegments.forEach {
            if (!oneSegments.contains(it)) {
                topSegmentDisplay = it.toString()
            }
        }

    //lower left segment
    var mapOfLetterCounters: MutableMap<String, Int> = mutableMapOf()
    var aCounter = 0
    var bCounter = 0
    var cCounter = 0
    var dCounter = 0
    var eCounter = 0
    var fCounter = 0
    var gCounter = 0
    for (item in fiveSegmentList) {
        item.forEach {
            when (it.toString()) {
                "a" -> aCounter++
                "b" -> bCounter++
                "c" -> cCounter++
                "d" -> dCounter++
                "e" -> eCounter++
                "f" -> fCounter++
                "g" -> gCounter++
            }
        }
    }
    mapOfLetterCounters.put("a", aCounter)
    mapOfLetterCounters.put("b", bCounter)
    mapOfLetterCounters.put("c", cCounter)
    mapOfLetterCounters.put("d", dCounter)
    mapOfLetterCounters.put("e", eCounter)
    mapOfLetterCounters.put("f", fCounter)
    mapOfLetterCounters.put("g", gCounter)


    var lowerLeftAndUpperLeft: String = ""
    mapOfLetterCounters.forEach {
        if (it.value == 1) {
            lowerLeftAndUpperLeft += it.key
        }
        }
    lowerLeftAndUpperLeft.forEach {
        if (!fourSegments.contains(it.toString())) {
            lowerLeftSegmentDisplay = it.toString()
        }
    }

    //Bottom

    var fourSegmentPlusTopPlusLowerLeftSegmentDisplay: String = fourSegments + topSegmentDisplay + lowerLeftSegmentDisplay
    eightSegments.forEach {
        if (!fourSegmentPlusTopPlusLowerLeftSegmentDisplay.contains(it.toString())){
            bottomSegmentDisplay = it.toString()
        }
    }

    //UpperLeft

    var midAndUpperLeft: String = ""
    var fiveholder: MutableList<String> = mutableListOf()
    var forLater: String = ""
    fourSegments.forEach{
        if (!oneSegments.contains(it.toString())){
            midAndUpperLeft += it.toString()
        }
    }
    fiveSegmentList.forEach {
        if (it.contains(midAndUpperLeft[0]) && it.contains(midAndUpperLeft[1])) {
            forLater = it
    } else {
        fiveholder.add(it)
    }
    }

    fiveholder.forEach{
        if (it.contains(lowerLeftSegmentDisplay)) {
            for (i in 0 until midAndUpperLeft.length) {
                if (!it.contains(midAndUpperLeft[i])) {
                    upperLeftSegmentDisplay = midAndUpperLeft[i].toString()
                }
            }
        }
    }

    //Mid

    midAndUpperLeft.forEach {
        if (it.toString() != upperLeftSegmentDisplay) {
            midSegmentDisplay = it.toString()
        }
    }

    //Upper Right

    val forNowAgain: String = forLater + lowerLeftSegmentDisplay

    eightSegments.forEach{
        if (!forNowAgain.contains(it.toString())) {
            upperRightSegmentDisplay = it.toString()
        }
    }
    //LowerRight

    val lastly: String = topSegmentDisplay + midSegmentDisplay + bottomSegmentDisplay +
            upperRightSegmentDisplay + upperLeftSegmentDisplay + lowerLeftSegmentDisplay

    eightSegments.forEach{
        if (!lastly.contains(it.toString())) {
            lowerRightSegmentDisplay = it.toString()
        }
    }

}