import java.io.File
import java.lang.Float.POSITIVE_INFINITY

var pathMatrix: MutableList<Int> = mutableListOf()
var pathRowLength: Int = 0
var queuedNodes: MutableList<Node> = mutableListOf()
var listOfAllNodes: MutableList<Node> = mutableListOf()
var listOfAllNodesNew: MutableList<Node> = mutableListOf()
var newMatrix: MutableList<Int> = mutableListOf()



fun main() {
    //adventFifteenPartOne()
    adventFifteenPartTwo()
}

fun adventFifteenPartOne(){
    readFileDayFifteen("src/DayFifteenInput.txt")
    listOfAllNodes[0].totalRisk = 0F
    println(listOfAllNodes[0])
    queuedNodes.addAll(listOfAllNodes)
    while (queuedNodes.isNotEmpty()) {
        findShortestPathDjik()
    }
    println(listOfAllNodes[pathMatrix.size - 1])

}

fun adventFifteenPartTwo() {
    readFileDayFifteen("src/DayFifteenInput.txt")
    listOfAllNodesNew[0].totalRisk = 0F
    pathRowLength *= 5
    queuedNodes.addAll(listOfAllNodesNew)
    while (queuedNodes.isNotEmpty()) {
        findShortestPathDjik()
    }
    println(listOfAllNodesNew[newMatrix.size - 1])

}

fun readFileDayFifteen(fileName: String){
    File(fileName).forEachLine { Line ->
        if (pathRowLength == 0) {
            pathRowLength = Line.length
        }
        Line.forEach{
            pathMatrix.add(it.toString().toInt())
        }
    }
    for (i in 0 until pathMatrix.size) {
        listOfAllNodes.add(Node(i, pathMatrix[i], POSITIVE_INFINITY, false))
    }
    constructEntireCaveMap()
    for (i in 0 until newMatrix.size) {
        listOfAllNodesNew.add(Node(i, newMatrix[i], POSITIVE_INFINITY, false))
    }
}


fun findShortestPathDjik() {
    var currentNode = queuedNodes.minByOrNull {
        it.totalRisk
    }

    queuedNodes.remove(currentNode)
    println("CURRENT NODE: $currentNode")

    if (currentNode == null) {
        println("DANGER DANGER WILL ROBINSON")
    } else {
        var horizontalMin: Int = currentNode.index - currentNode.index.mod(pathRowLength)
        var horizontalMax: Int = currentNode.index + ((pathRowLength - 1) - currentNode.index.mod(pathRowLength))
        var potentialNeighbours: MutableList<Int> = mutableListOf(currentNode.index
                + pathRowLength, currentNode.index - pathRowLength)

        if (currentNode.index - 1 >= horizontalMin) {
            potentialNeighbours.add(currentNode.index - 1)
        }
        if (currentNode.index + 1 <= horizontalMax) {
            potentialNeighbours.add(currentNode.index + 1)
        }
        queuedNodes.forEach{
            if (potentialNeighbours.contains(it.index)) {
                if (it.totalRisk > currentNode.totalRisk + it.risk) {
                    it.totalRisk = currentNode.totalRisk + it.risk
                }
            }
        }
    }
}

fun constructEntireCaveMap() {
    var holdingList: MutableList<Int> = mutableListOf()
    var loopCheck: Int = 0
    for (i in 0 until pathRowLength) {
        holdingList.add(pathMatrix[i])
    }

   for (i in 1..pathRowLength) {
       var minL = pathRowLength * (i - 1)
       var maxL = pathRowLength * i
       for (i in 1..5) {
           when (i) {
               1 -> newMatrix.addAll(pathMatrix.subList(minL, maxL))
               2 -> newMatrix.addAll(translateMatrix(pathMatrix.subList(minL, maxL), 1))
               3 -> newMatrix.addAll(translateMatrix(pathMatrix.subList(minL, maxL), 2))
               4 -> newMatrix.addAll(translateMatrix(pathMatrix.subList(minL, maxL), 3))
               5 -> newMatrix.addAll(translateMatrix(pathMatrix.subList(minL, maxL), 4))
           }
       }
   }
    var holdingMatrix = newMatrix.toMutableList()

    for (i in 1..4) {
        when (i) {
            1 -> newMatrix.addAll(translateMatrix(holdingMatrix, 1))
            2 -> newMatrix.addAll(translateMatrix(holdingMatrix, 2))
            3 -> newMatrix.addAll(translateMatrix(holdingMatrix, 3))
            4 -> newMatrix.addAll(translateMatrix(holdingMatrix, 4))
        }
    }


}

fun translateMatrix(list: MutableList<Int>, modifier: Int): MutableList<Int>{
    var holdingList: MutableList<Int> = mutableListOf()
    list.forEach{
        if (it + modifier > 9) {
            holdingList.add(0 + ((it + modifier) - 9))
        } else {
            holdingList.add(it + modifier)
        }
    }
    return holdingList
}


data class Node(val index: Int, var risk: Int, var totalRisk: Float, var visited: Boolean)