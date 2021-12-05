import java.io.File


var mapMarkers: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
val lineSegmentInformation: MutableList<String> = mutableListOf()
var startCoordinate: String = ""
var endCoordinate: String = ""
const val arrowDelimiter: String = "->"


fun main() {
    //adventFivePartOne()
    adventFivePartTwo()
}

fun adventFivePartOne() {
    readFileDayFive("src/DayFiveInput.txt")
    extractCoordinates()
    getResults()


}

fun getResults() {
    var holder = (mapMarkers.filter {
        it.value > 1
    })
    println(holder.size)
}

fun adventFivePartTwo() {
    readFileDayFive("src/DayFiveInput.txt")
    extractCoordinatesPartTwo()
    getResults()

}

fun plotHorizontalLine(yPoint: Int, xPointOne: Int, xPointTwo: Int) {
    if (xPointOne > xPointTwo) {
        for (i in xPointTwo..xPointOne) {
            if (mapMarkers.containsKey(Pair(i, yPoint))) {
                var holdingValue = mapMarkers[Pair(i, yPoint)]
                if (holdingValue != null) {
                    mapMarkers.replace(Pair(i, yPoint), holdingValue, holdingValue + 1)
                }
            } else {
                mapMarkers.put(Pair(i, yPoint), 1)
            }
        }
    } else {
        for (i in xPointOne..xPointTwo) {
            if (mapMarkers.containsKey(Pair(i, yPoint))) {
                var holdingValue = mapMarkers[Pair(i, yPoint)]
                if (holdingValue != null) {
                    mapMarkers.replace(Pair(i, yPoint), holdingValue, holdingValue + 1)
                }
            } else {
                mapMarkers.put(Pair(i, yPoint), 1)
            }
        }
    }

}

fun plotVerticalLine(xPoint: Int, yPointOne: Int, yPointTwo: Int) {
    if (yPointOne > yPointTwo) {
        for (i in yPointTwo..yPointOne) {
            if (mapMarkers.containsKey(Pair(xPoint, i))) {
                var holdingValue = mapMarkers[Pair(xPoint, i)]
                if (holdingValue != null) {
                    mapMarkers.replace(Pair(xPoint, i), holdingValue, holdingValue + 1)
                }
            } else {
                mapMarkers[Pair(xPoint, i)] = 1
            }
        }
    } else {
        for (i in yPointOne..yPointTwo) {
            if (mapMarkers.containsKey(Pair(xPoint, i))) {
                var holdingValue = mapMarkers[Pair(xPoint, i)]
                if (holdingValue != null) {
                    mapMarkers.replace(Pair(xPoint, i), holdingValue, holdingValue + 1)
                }
            } else {
                mapMarkers[Pair(xPoint, i)] = 1
            }
        }
    }
}

fun plotDiagonalLine(xPointOne: Int, xPointTwo: Int, yPointOne: Int, yPointTwo: Int) {
    var xDifference: Boolean = false
    var yDifference: Boolean = false
    xDifference = xPointOne > xPointTwo
    yDifference = yPointOne > yPointTwo
    var xNewValue: Int = xPointOne
    var yNewValue: Int = yPointOne
    var count: Int = 0
    var loopControl: Int = 0
    count = if (xDifference) {
        xPointOne - xPointTwo
    } else {
        xPointTwo - xPointOne
    }

    while (loopControl <= count) {
        if (mapMarkers.containsKey(Pair(xNewValue, yNewValue))) {
            var holdingValue = mapMarkers[Pair(xNewValue, yNewValue)]
            if (holdingValue != null) {
                mapMarkers.replace(Pair(xNewValue, yNewValue), holdingValue, holdingValue + 1)
            }
        } else {
            mapMarkers[Pair(xNewValue, yNewValue)] = 1
        }
        xNewValue = if (xDifference) {
            xNewValue - 1
        } else {
            xNewValue + 1
        }
        yNewValue = if (yDifference) {
            yNewValue - 1
        } else {
            yNewValue + 1
        }
        loopControl++
    }

}


fun readFileDayFive(fileName: String) = File(fileName).forEachLine {
    lineSegmentInformation.add(it)
}

fun extractCoordinates() {
    var coordinateHolder: MutableList<String> = mutableListOf()
    for (item in lineSegmentInformation) {
        coordinateHolder = item.split(arrowDelimiter) as MutableList<String>
        startCoordinate = coordinateHolder[0].trim()
        endCoordinate = coordinateHolder[1].trim()
        formatCoordinates(startCoordinate, endCoordinate)
    }
}

fun extractCoordinatesPartTwo() {
    var coordinateHolder: MutableList<String> = mutableListOf()
    for (item in lineSegmentInformation) {
        coordinateHolder = item.split(arrowDelimiter) as MutableList<String>
        startCoordinate = coordinateHolder[0].trim()
        endCoordinate = coordinateHolder[1].trim()
        formatCoordinatesPartTwo(startCoordinate, endCoordinate)
    }
}

fun formatCoordinates(startCoordinate: String, endCoordinate: String) {
    var xOne: Int = 0
    var xTwo: Int = 0
    var yOne: Int = 0
    var yTwo: Int = 0
    var startCoordinateHolder: MutableList<String> = mutableListOf()
    var endCoordinateHolder: MutableList<String> = mutableListOf()

    startCoordinateHolder = startCoordinate.split(commaDelimiter) as MutableList<String>
    endCoordinateHolder = endCoordinate.split(commaDelimiter) as MutableList<String>

    xOne = startCoordinateHolder[0].toInt()
    xTwo = endCoordinateHolder[0].toInt()
    yOne = startCoordinateHolder[1].toInt()
    yTwo = endCoordinateHolder[1].toInt()

    if (xOne == xTwo) {
        plotVerticalLine(xOne, yOne, yTwo)
    } else if (yOne == yTwo) {
        plotHorizontalLine(yOne, xOne, xTwo)
    }

}

fun formatCoordinatesPartTwo(startCoordinate: String, endCoordinate: String) {
    var xOne: Int = 0
    var xTwo: Int = 0
    var yOne: Int = 0
    var yTwo: Int = 0
    var startCoordinateHolder: MutableList<String> = mutableListOf()
    var endCoordinateHolder: MutableList<String> = mutableListOf()

    startCoordinateHolder = startCoordinate.split(commaDelimiter) as MutableList<String>
    endCoordinateHolder = endCoordinate.split(commaDelimiter) as MutableList<String>

    xOne = startCoordinateHolder[0].toInt()
    xTwo = endCoordinateHolder[0].toInt()
    yOne = startCoordinateHolder[1].toInt()
    yTwo = endCoordinateHolder[1].toInt()

    if (xOne == xTwo) {
        plotVerticalLine(xOne, yOne, yTwo)
    } else if (yOne == yTwo) {
        plotHorizontalLine(yOne, xOne, xTwo)
    } else {
        plotDiagonalLine(xOne, xTwo, yOne, yTwo)
    }

}


