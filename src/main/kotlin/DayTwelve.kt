import java.io.File



var uniqueCavePointNames: MutableList<String> = mutableListOf()
var stack: MutableList<String> = mutableListOf()
var listOfCavePaths: MutableList<MutableList<String>> = mutableListOf()
val caveFilter: MutableList<String> = mutableListOf()
var mapOfCavePointConnections: MutableMap<String, MutableList<String>> = mutableMapOf()
val dashDelimiter: String = "-"


fun main() {
    //adventTwelvePartOne()
    adventTwelvePartTwo()
}

fun adventTwelvePartOne() {
    readFileDayTwelve("src/DayTwelveInput.txt")
    findAllPathsOne("start", "end", stack)
    println(listOfCavePaths.size)
}

fun adventTwelvePartTwo() {
    readFileDayTwelve("src/DayTwelveInput.txt")
    caveFilter.remove("start")
    findAllPathsTwo("start", "end", stack, false)
    println(listOfCavePaths.size)
}

fun readFileDayTwelve(fileName: String) {
    var caveSystemInformationRawInputSplit: MutableList<String> = mutableListOf()
    var caveSystemInformationRawInput: MutableList<String> = mutableListOf()

    File(fileName).forEachLine { rawInput ->
        caveSystemInformationRawInput.add(rawInput)
        var result: List<String> = rawInput.split(dashDelimiter)
        caveSystemInformationRawInputSplit.add(result[0])
        caveSystemInformationRawInputSplit.add(result[1])
    }
    caveSystemInformationRawInputSplit.forEach { caveName ->
        if (!uniqueCavePointNames.contains(caveName)) {
            uniqueCavePointNames.add(caveName)
        }
        if (!uniqueCavePointNames.contains(caveName)) {
            uniqueCavePointNames.add(caveName)
        }
    }
    uniqueCavePointNames.forEach { caveName ->
        var resultList: MutableList<String> = mutableListOf()
        caveSystemInformationRawInput.forEach { rawInput ->
            var resultTwo: List<String> = rawInput.split(dashDelimiter)
            if (caveName == resultTwo[0]) {
                resultList.add(resultTwo[1])
            } else if (caveName == resultTwo[1]) {
                resultList.add(resultTwo[0])
            }
        }
        mapOfCavePointConnections.put(caveName, resultList)
    }

    uniqueCavePointNames.forEach{ cave ->
        if (cave[0].isLowerCase() && !caveFilter.contains(cave)) {
            caveFilter.add(cave)
        }
    }
    caveFilter.remove("end")

}


fun findAllPathsOne(startPoint: String, endPoint: String, path: MutableList<String>) {

    var currentCave = startPoint
    var currentPath = path.toMutableList()
    var connections = mapOfCavePointConnections.getValue(currentCave)
    currentPath.add(currentCave)


    if (currentCave == endPoint) {
        listOfCavePaths.add(currentPath)
    } else {
        if (connections.isNotEmpty()) {
            connections.forEach {

                if (currentPath.contains(it) && caveFilter.contains(it)) {
                    //println("ALREADY IN CURRENT PATH!!!")
                }
                else {
                    findAllPathsOne(it, "end", currentPath)
                }
            }
        }
    }
}

fun findAllPathsTwo(startPoint: String, endPoint: String, path: MutableList<String>, caveLimit: Boolean) {

    var smallCaveLimitReached = caveLimit
    var currentCave = startPoint
    var currentPath = path.toMutableList()
    var connections = mapOfCavePointConnections.getValue(currentCave)
    currentPath.add(currentCave)

    if (!smallCaveLimitReached) {
        currentPath.forEach{ cave ->
            if (caveFilter.contains(cave) && 2 == currentPath.count{it == cave}) {
                smallCaveLimitReached = true
            }
        }
    }

    if (currentCave == endPoint) {
        listOfCavePaths.add(currentPath)
    } else {

        if (connections.isNotEmpty()) {
            connections.forEach { cave ->
                if (cave != "start") {
                    if (smallCaveLimitReached) {
                        if (currentPath.contains(cave) && caveFilter.contains(cave)) {
                            //println("ALREADY IN CURRENT PATH!!!")
                        }
                        else {
                            findAllPathsTwo(cave, "end", currentPath, smallCaveLimitReached)
                        }
                    } else {
                        findAllPathsTwo(cave, "end", currentPath, smallCaveLimitReached)

                    }
            }}
        }
    }
}



















