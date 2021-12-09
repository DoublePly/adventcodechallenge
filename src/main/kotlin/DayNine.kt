import java.io.File


var heightMapRaw: MutableList<String> = mutableListOf()
var heightMap: MutableList<Int> = mutableListOf()
var lowPoints: MutableList<Int> = mutableListOf()
var lowPointsIndices: MutableList<Int> = mutableListOf()
var basinSizeLists: MutableList<Int> = mutableListOf()
var pointsInBasinMap: MutableMap<Int, Int> = mutableMapOf()
var heightMapRowLength: Int = 0
var sumOfRisk: Int = 0

fun main() {
    //adventNinePartOne()
    adventNinePartTwo()
}

fun adventNinePartOne() {
    readFileDayNine("src/DayNineInput.txt")
    setupHeightMap()
    findLowPoints()
}

fun adventNinePartTwo() {
    readFileDayNine("src/DayNineInput.txt")
    setupHeightMap()
    findLowPoints()

    calcBasinSizes()
    calcHighestBasins()
    println(sumOfRisk)
}

fun calcBasinSizes() {

    lowPointsIndices.forEach {
        findBasinSizes(it)
        basinSizeLists.add(pointsInBasinMap.size)
        pointsInBasinMap.clear()
    }
}


fun calcHighestBasins() {
    var findThreeHighestBasins: MutableList<Int> = mutableListOf()
    var basinSizeListsCopy: MutableList<Int> = basinSizeLists
    var holdValue: Int = 0
    for (i in 0..2) {
        basinSizeListsCopy.forEach {
            if (holdValue < it) {
                holdValue = it
            }
        }
        findThreeHighestBasins.add(holdValue)
        basinSizeListsCopy.remove(holdValue)
        holdValue = 0
    }
    sumOfRisk = findThreeHighestBasins[0] * findThreeHighestBasins[1] * findThreeHighestBasins[2]

}


fun setupHeightMap() {
    heightMapRowLength = heightMapRaw[0].length
    heightMapRaw.forEach {
        for (i in 0 until heightMapRowLength) {
            heightMap.add(it[i].toString().toInt())
        }
    }
}

fun readFileDayNine(fileName: String) = File(fileName).forEachLine {
    heightMapRaw.add(it)
}

fun findLowPoints() {
    for (i in 0 until heightMap.size) {
        when (i) {
            0 -> checkCorner(i)
            heightMapRowLength - 1 -> checkCorner(i)
            heightMap.size - 1 -> checkCorner(i)
            heightMap.size - heightMapRowLength -> checkCorner(i)

            in 1..heightMapRowLength - 2 -> checkEdge(i)
            in heightMap.size - heightMapRowLength..heightMap.size -> checkEdge(i)
            in heightMapRowLength..heightMap.size - heightMapRowLength step heightMapRowLength -> checkEdge(i)
            in heightMapRowLength - 1..heightMap.size - heightMapRowLength step heightMapRowLength -> checkEdge(i)

            else -> checkOther(i)
        }
    }
}

fun checkOther(index: Int) {
    if (heightMap[index] < heightMap[index - 1] && heightMap[index] < heightMap[index + 1]
        && heightMap[index] < heightMap[index + heightMapRowLength] && heightMap[index] < heightMap[index - heightMapRowLength]
    ) {
        lowPoints.add(heightMap[index])
        lowPointsIndices.add(index)
    }
}

fun checkCorner(index: Int) {
    when (index) {
        0 -> {
            if (heightMap[index] < heightMap[index + 1] && heightMap[index] < heightMap[index + heightMapRowLength]) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }
        }
        heightMapRowLength - 1 -> {
            if (heightMap[index] < heightMap[index - 1]
                && heightMap[index] < heightMap[index + heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }
        }
        heightMap.size - 1 -> {
            if (heightMap[index] < heightMap[index] - 1
                && heightMap[index] < heightMap[index - heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }

        }
        heightMap.size - heightMapRowLength -> {
            if (heightMap[index] < heightMap[index + 1]
                && heightMap[index] < heightMap[index - heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }

        }
    }
}

fun checkEdge(index: Int) {
    when (index) {
        in 1..heightMapRowLength - 2 -> {
            if (heightMap[index] < heightMap[index - 1]
                && heightMap[index] < heightMap[index + 1] && heightMap[index] < heightMap[index + heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }
        }
        in heightMap.size - heightMapRowLength..heightMap.size -> {
            if (heightMap[index] < heightMap[index - 1]
                && heightMap[index] < heightMap[index + 1] && heightMap[index] < heightMap[index - heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }
        }
        in heightMapRowLength..heightMap.size - heightMapRowLength step heightMapRowLength -> {
            if (heightMap[index] < heightMap[index + 1] && heightMap[index] < heightMap[index - heightMapRowLength] &&
                heightMap[index] < heightMap[index + heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }
        }
        in heightMapRowLength - 1..heightMap.size - heightMapRowLength step heightMapRowLength -> {
            if (heightMap[index] < heightMap[index - 1] && heightMap[index] < heightMap[index - heightMapRowLength] &&
                heightMap[index] < heightMap[index + heightMapRowLength]
            ) {
                lowPoints.add(heightMap[index])
                lowPointsIndices.add(index)
            }
        }

    }
}

fun findBasinSizes(index: Int) {
    var lowerLimit = 0
    lowerLimit = when (index) {
        in 0..9 -> 0
        in heightMap.size - heightMapRowLength..heightMap.size - 1 -> heightMap.size - heightMapRowLength
        else -> index - index.mod(heightMapRowLength)
    }
    var upperLimit = 0
    upperLimit = when (index) {
        in 0..9 -> 9
        in heightMap.size - heightMapRowLength..heightMap.size - 1 -> heightMap.size - 1
        else -> index + (heightMapRowLength - index.mod(heightMapRowLength) - 1)
    }

    if (heightMap[index] < 9) {
        pointsInBasinMap.put(index, heightMap[index])

        if (index + 1 <= upperLimit && !pointsInBasinMap.contains(index + 1)) {
            findBasinSizes(index + 1)
        }
        if (index - 1 >= lowerLimit && !pointsInBasinMap.contains(index - 1)) {
            findBasinSizes(index - 1)
        }
        if (index + heightMapRowLength < heightMap.size) {
            if (!pointsInBasinMap.contains(index + heightMapRowLength)) {
                findBasinSizes(index + heightMapRowLength)
            }

        }
        if (index - heightMapRowLength > 0) {
            if (!pointsInBasinMap.contains(index - heightMapRowLength)) {
                findBasinSizes(index - heightMapRowLength)
            }

        }
    }

}