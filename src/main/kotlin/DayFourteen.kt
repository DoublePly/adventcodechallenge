import java.io.File

var polymerTemplate: String = ""
var polymerInjectionInstructionsRaw: MutableList<String> = mutableListOf()
var polymerInjectionInstructions: MutableMap<String, String> = mutableMapOf()
var mapOfPolymerInstances: MutableMap<String, Int> = mutableMapOf()


fun main() {
    //adventFourteenPartOne()
    adventFourteenPartTwo()
}

fun adventFourteenPartOne() {
    readFileDayFourteen("src/DayFourteenInput.txt")
    initiatePolymerInjection()
    countElements()
}

fun adventFourteenPartTwo() {
    readFileDayFourteen("src/DayFourteenInput.txt")
    initiatePolymerInjectionTwo()
}

fun initiatePolymerInjection() {
    for (i in 1..10) {
        injectPolymer()
    }
}

fun initiatePolymerInjectionTwo() {
    for (i in 0 until polymerTemplate.length - 1) {
        injectPolymerTwo(polymerTemplate.substring(i,i+2), 20)
    }
    mapOfPolymerInstances.forEach{ instance ->
        polymerInjectionInstructions.forEach{
            if (it.key == instance.key) {
                println(it.value + "count: " + instance.value)
            }
        }
    }
    println(mapOfPolymerInstances)

}

fun injectPolymer() {
    var newPolymerTemplate: String  = ""
    for (i in 0 until polymerTemplate.length - 1) {
        var polymerKey = polymerTemplate.substring(i,i+2)
        newPolymerTemplate += polymerKey.substring(0,1) + polymerInjectionInstructions.getValue(polymerKey)
    }
    newPolymerTemplate += polymerTemplate.substring(polymerTemplate.length - 1, polymerTemplate.length)
    polymerTemplate = newPolymerTemplate
    println("Printing...")
}


fun injectPolymerTwo(polymerPair: String, injectionCounter: Int) {
    if (injectionCounter > 0) {
        println("On Count: $injectionCounter")
        if (mapOfPolymerInstances.containsKey(polymerPair)) {
            var value = mapOfPolymerInstances.getValue(polymerPair)
            mapOfPolymerInstances.put(polymerPair, value + 1)
        } else {
            mapOfPolymerInstances.put(polymerPair, 1)
        }
        injectPolymerTwo(polymerPair.substring(0, 1) + polymerInjectionInstructions.getValue(polymerPair), injectionCounter - 1)
        injectPolymerTwo(polymerInjectionInstructions.getValue(polymerPair) + polymerPair.substring(1, 2), injectionCounter - 1)
    }

}

fun countElements() {
    var countResults: MutableMap<Char, Long> = mutableMapOf()
    polymerTemplate.forEach{ initialChar ->
        var counter: Long = 0
        polymerTemplate.forEach{
            if (it == initialChar) {
                counter += 1
            }
        }
        if (!countResults.containsKey(initialChar)) {
            countResults.put(initialChar, counter)
        }
    }
    println(countResults)
}

fun readFileDayFourteen(fileName: String) {
    var result: MutableList<String> = mutableListOf()
    File(fileName).forEachLine {
        result.add(it)
    }
    polymerTemplate = result[0]
    for (i in 2 until result.size) {
        polymerInjectionInstructionsRaw.add(result[i])
    }
    polymerInjectionInstructionsRaw.forEach{
        var result = it.split(spaceDelimiter)
        polymerInjectionInstructions.put(result[0], result[2])
    }

}