import java.io.File

var epsilonRateBinary: String = ""
var gammaRateBinary: String? = ""
val submarineCondition: MutableList<String> = mutableListOf()
var submarineOxygenRating: MutableList<String> = mutableListOf()
var submarineCo2Rating: MutableList<String> = mutableListOf()
var oneCounter: Int = 0
var zeroCounter: Int = 0
var oneOxyCounter: Int = 0
var zeroOxyCounter: Int = 0
var oneCo2Counter: Int = 0
var zeroCo2Counter: Int = 0
var binaryValues: MutableList<Char>? = mutableListOf()
var binaryOxyValuesList: MutableList<Char>? = mutableListOf()
var binaryCoValuesList: MutableList<Char>? = mutableListOf()




fun main () {
    adventThreePartOne()
    //adventThreePartTwo()
}


fun adventThreePartOne() {
    readFileDayThreePartOne("src/DayThreeInput.txt")
    var check: Int = 0
    while (check < 12) {
        binaryValues = getBinaryList(check)
        calcCounters()
        calcBinaryRates()
        oneCounter = 0
        zeroCounter = 0
        check++
    }
    println(epsilonRateBinary)
    println(gammaRateBinary)

}

fun adventThreePartTwo() {
    readFileDayThreePartOne("src/DayThreeInput.txt")
    var check: Int = 0
    while (check < 12) {
        if (check == 0) {
            binaryValues = getBinaryList(check)
            calcCounters()
            preliminarySetup(check)
        } else {
            findOxygenRating(check)
            findCo2Rating(check)
        }
        oneCounter = 0
        zeroCounter = 0
        oneOxyCounter = 0
        zeroOxyCounter = 0
        oneCo2Counter = 0
        zeroCo2Counter = 0
        check++
    }
    println(submarineOxygenRating)
    println(submarineCo2Rating)
}


fun preliminarySetup(check: Int) {
    setUpOxygen(check)
    setUpCo(check)
}

fun findOxygenRating(check: Int) {
    binaryOxyValuesList = getBinaryListOxygenRating(check)
    calcOxygenCounters()
    getOxygenRating(check)

}
fun findCo2Rating(check: Int) {
    binaryCoValuesList = getBinaryListCoRating(check)
    calcCo2Counters()
    getCo2Rating(check)
}

fun setUpOxygen(check: Int) {
    if (oneCounter >= zeroCounter) {
        for (item in submarineCondition) {
            if (item[check].toString() == "1") {
                submarineOxygenRating.add(item)
            }

        }
    }
    else {
        for (item in submarineCondition) {
            if (item[check].toString() == "0") {
                submarineOxygenRating.add(item)
            }

        }
    }
}

fun setUpCo(check: Int) {
    if (oneCounter >= zeroCounter) {
        for (item in submarineCondition) {
            if (item[check].toString() == "0") {
                submarineCo2Rating.add(item)
            }

        }
    }
    else{
        for (item in submarineCondition) {
            if (item[check].toString() == "1") {
                submarineCo2Rating.add(item)
            }

        }
    }
}

fun getOxygenRating(check: Int) {
    var toRemove: MutableList<String> = mutableListOf()
    if (oneOxyCounter >= zeroOxyCounter) {
        for (item in submarineOxygenRating) {
            if (item[check].toString() == "0") {
                toRemove.add(item)
            }
        }
    }
    else {
        for (item in submarineOxygenRating) {
            if (item[check].toString() == "1") {
                toRemove.add(item)
            }
        }
    }
    if (submarineOxygenRating.size != 1) {
        submarineOxygenRating.removeAll(toRemove)
    }
}

fun getCo2Rating(check: Int) {
    var toRemove: MutableList<String> = mutableListOf()
    if (oneCo2Counter >= zeroCo2Counter) {
        for (item in submarineCo2Rating) {
            if (item[check].toString() == "1") {
                toRemove.add(item)
            }
        }
    }
    else {
        for (item in submarineCo2Rating) {
            if (item[check].toString() == "0") {
                toRemove.add(item)
            }
        }
    }
    if (submarineCo2Rating.size != 1) {
        submarineCo2Rating.removeAll(toRemove)
    }
}

fun readFileDayThreePartOne(fileName: String)
        = File(fileName).forEachLine {
    submarineCondition.add(it)
}

fun getBinaryList(check: Int): MutableList<Char> {
    var binaryListToReturn : MutableList<Char> = mutableListOf()
    for (item in submarineCondition) {
        binaryListToReturn?.add(item[check])
    }
    return binaryListToReturn
}

fun getBinaryListOxygenRating(check: Int): MutableList<Char> {
    var binaryListToReturn : MutableList<Char> = mutableListOf()
    for (item in submarineOxygenRating) {
        binaryListToReturn?.add(item[check])
    }
    return binaryListToReturn
}

fun getBinaryListCoRating(check: Int): MutableList<Char> {
    var binaryListToReturn : MutableList<Char> = mutableListOf()
    for (item in submarineCo2Rating) {
        binaryListToReturn?.add(item[check])
    }
    return binaryListToReturn
}


fun calcCounters() {
    for (item in binaryValues!!) {
        if (item.toString() == "1") {
            oneCounter++
        } else if (item.toString() == "0") {
            zeroCounter++
        }
    }
}

fun calcOxygenCounters() {
    for (item in binaryOxyValuesList!!) {
        if (item.toString() == "1") {
            oneOxyCounter++
        } else if (item.toString() == "0") {
            zeroOxyCounter++
        }
    }
}

fun calcCo2Counters() {
    for (item in binaryCoValuesList!!) {
        if (item.toString() == "1") {
            oneCo2Counter++
        } else if (item.toString() == "0") {
            zeroCo2Counter++
        }
    }
}

fun calcBinaryRates(){
    if (zeroCounter > oneCounter) {
        gammaRateBinary = gammaRateBinary + "0"
        epsilonRateBinary = epsilonRateBinary + "1"
    } else if (zeroCounter < oneCounter){
        gammaRateBinary = gammaRateBinary + "1"
        epsilonRateBinary = epsilonRateBinary + "0"
    }
}
