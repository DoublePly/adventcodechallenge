import java.io.File
import java.lang.Math.ceil

var polymerTemplate: String = ""
var polymerInjectionInstructionsRaw: MutableList<String> = mutableListOf()
var polymerInjectionInstructions: MutableMap<String, String> = mutableMapOf()
var mapOfPolymerInstances: MutableMap<String, Long> = mutableMapOf()
var polymerInstanceCount: MutableList<Polymer> = mutableListOf()
var mapOfLetterCount: MutableMap<String, Long> = mutableMapOf()
var mapOfLetterCountHolder: MutableMap<String, Long> = mutableMapOf()

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
    polymerInjectionInstructions.forEach{
        polymerInstanceCount.add(Polymer(it.key, 0))
    }
    for (i in 0 until polymerTemplate.length - 1) {
        polymerInstanceCount.forEach{
            if (it.name == polymerTemplate.substring(i,i+2)){
                it.instanceCount +=1
            }
        }
    }


    for (i in 0 until 40) {
        injectPolymerThree()

    }

    polymerInstanceCount.forEach { instance ->
        for (i in 0 until instance.name.length) {
            if (mapOfLetterCountHolder.containsKey(instance.name[i].toString())) {
                var value = mapOfLetterCountHolder.getValue(instance.name[i].toString())
                mapOfLetterCountHolder.put(instance.name[i].toString(), value + instance.instanceCount)
            } else {
                mapOfLetterCountHolder.put(instance.name[i].toString(), instance.instanceCount)
            }
        }

    }
    mapOfLetterCountHolder.forEach{
        mapOfLetterCount.put(it.key, it.value/ 2)
    }

    var firstChar = polymerTemplate[0]
    var firstCharVal = mapOfLetterCount.getValue(firstChar.toString())
    var lastChar = polymerTemplate[polymerTemplate.length - 1]
    var lastCharVal = mapOfLetterCount.getValue(lastChar.toString())
    mapOfLetterCount.put(firstChar.toString(), firstCharVal + 1 )
    mapOfLetterCount.put(lastChar.toString(), lastCharVal + 1 )

    println(mapOfLetterCount)
    var min = mapOfLetterCount.getValue("B")
    var max: Long = 0
    mapOfLetterCount.forEach{
        if (it.value < min) {
            min = it.value
        }
        if (it.value > max) {
            max = it.value
        }
    }
    println(max - min)
    //println(polymerInstanceCount)


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



fun injectPolymerThree() {
        var holderMap: MutableMap<String, Long> = mutableMapOf()
        polymerInstanceCount.forEach{ polymer ->
                if (polymer.instanceCount > 0) {
                        if (holderMap.containsKey(polymer.name.substring(0,1) + polymerInjectionInstructions.getValue(polymer.name))) {
                            var value = holderMap.getValue(polymer.name.substring(0,1) + polymerInjectionInstructions.getValue(polymer.name))
                            holderMap.put(polymer.name.substring(0,1) + polymerInjectionInstructions.getValue(polymer.name),
                                value + polymer.instanceCount)

                        } else {
                            holderMap.put(polymer.name.substring(0,1) + polymerInjectionInstructions.getValue(polymer.name),
                                polymer.instanceCount)

                        }
                        if (holderMap.containsKey(polymerInjectionInstructions.getValue(polymer.name) + polymer.name.substring(1,2))) {
                            var value = holderMap.getValue(polymerInjectionInstructions.getValue(polymer.name) + polymer.name.substring(1,2))
                            holderMap.put(polymerInjectionInstructions.getValue(polymer.name) + polymer.name.substring(1,2),
                                value + polymer.instanceCount)

                        } else {
                            holderMap.put(polymerInjectionInstructions.getValue(polymer.name) + polymer.name.substring(1,2),
                                polymer.instanceCount)
                        }

                    polymer.instanceCount = 0
            }
        }
    holderMap.forEach{
        polymerInstanceCount.forEach{ polymer ->
            if (it.key == polymer.name) {
                polymer.instanceCount += it.value
            }
        }
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

data class Polymer(val name: String, var instanceCount: Long)