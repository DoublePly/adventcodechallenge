import java.io.File


var syntaxErrorInputRaw: MutableList<String> = mutableListOf()
val openBracket: String = "("
val closeBracket: String = ")"
val openSquareBracket: String = "["
val closeSquareBracket: String = "]"
val openCurlyBracket: String = "{"
val closeCurlyBracket: String = "}"
val openAngleBracket: String = "<"
val closeAngleBracket: String = ">"
var syntaxErrorList: MutableList<String> = mutableListOf()
var score: Long = 0

var incompleteLineList: MutableList<String> = mutableListOf()
var completeLineSolutionsList: MutableList<String> = mutableListOf()
var listOfLineCompleteScores: MutableList<Long> = mutableListOf()


fun main() {
    //adventTenPartOne()
    adventTenPartTwo()
}


fun adventTenPartOne() {
    readFileDayTen("src/DayTenInput.txt")
    syntaxErrorInputRaw.forEach {
        checkSyntaxLine(it)
    }
    calcErrorScore()
    println(score)
}

fun adventTenPartTwo() {
    readFileDayTen("src/DayTenInput.txt")
    syntaxErrorInputRaw.forEach {
        checkSyntaxLine(it)
    }
    calcErrorScore()
    incompleteLineList.forEach {
        completeLines(it)
    }
    completeLineSolutionsList.forEach {
        calcLineCompleteScore(it)
    }
    findMiddleValue()


}

fun findMiddleValue() {
    var listOfLineCompleteScoresCopy: MutableList<Long> = listOfLineCompleteScores.sorted() as MutableList<Long>
    var middleIndex = listOfLineCompleteScoresCopy.size / 2
    println(listOfLineCompleteScoresCopy[middleIndex])
}

fun calcLineCompleteScore(scoreToCalc: String) {
    var totalScore: Long = 0
    for (element in scoreToCalc) {
        totalScore *= 5
        when (element.toString()) {
            closeBracket -> totalScore += 1
            closeSquareBracket -> totalScore += 2
            closeCurlyBracket -> totalScore += 3
            closeAngleBracket -> totalScore += 4
        }
    }
    listOfLineCompleteScores.add(totalScore)
}

fun calcErrorScore() {
    syntaxErrorList.forEach {
        when (it) {
            closeBracket -> score += 3
            closeSquareBracket -> score += 57
            closeCurlyBracket -> score += 1197
            closeAngleBracket -> score += 25137
        }
    }
}


fun readFileDayTen(fileName: String) = File(fileName).forEachLine {
    syntaxErrorInputRaw.add(it)
}

fun checkSyntaxLine(syntaxLine: String) {
    var openList: MutableList<String> = mutableListOf()
    var expectedBrackets: MutableList<String> = mutableListOf()

    syntaxLine.forEach {
        when (it.toString()) {
            openBracket -> openList.add("(")
            closeBracket -> {
                if (openList.last() != openBracket) {
                    syntaxError(openList.last(), closeBracket)
                    expectedBrackets.add(closeBracket)
                } else if (openList.last() == openBracket) {
                    openList.removeLast()
                }
            }
            openSquareBracket -> openList.add("[")
            closeSquareBracket -> {
                if (openList.last() != openSquareBracket) {
                    syntaxError(openList.last(), closeSquareBracket)
                    expectedBrackets.add(closeSquareBracket)
                } else if (openList.last() == openSquareBracket) {
                    openList.removeLast()
                }
            }
            openCurlyBracket -> openList.add("{")
            closeCurlyBracket -> {
                if (openList.last() != openCurlyBracket) {
                    syntaxError(openList.last(), closeCurlyBracket)
                    expectedBrackets.add(closeCurlyBracket)
                } else if (openList.last() == openCurlyBracket) {
                    openList.removeLast()
                }
            }
            openAngleBracket -> openList.add("<")
            closeAngleBracket -> {
                if (openList.last() != openAngleBracket) {
                    syntaxError(openList.last(), closeAngleBracket)
                    expectedBrackets.add(closeAngleBracket)
                } else if (openList.last() == openAngleBracket) {
                    openList.removeLast()
                }
            }
        }
    }
    if (expectedBrackets.isNotEmpty()) {
        syntaxErrorList.add(expectedBrackets[0])
    } else {
        incompleteLineList.add(syntaxLine)
    }

}

fun completeLines(syntaxLine: String) {
    var openList: MutableList<String> = mutableListOf()
    var solutionsList: MutableList<String> = mutableListOf()

    syntaxLine.forEach {
        when (it.toString()) {
            openBracket -> openList.add(openBracket)
            closeBracket -> openList.removeLast()
            openSquareBracket -> openList.add(openSquareBracket)
            closeSquareBracket -> openList.removeLast()
            openCurlyBracket -> openList.add(openCurlyBracket)
            closeCurlyBracket -> openList.removeLast()
            openAngleBracket -> openList.add(openAngleBracket)
            closeAngleBracket -> openList.removeLast()
        }
    }

    var reversedOpenList: MutableList<String> = openList.asReversed()
    reversedOpenList.forEach {
        when (it) {
            openBracket -> solutionsList.add(closeBracket)
            openSquareBracket -> solutionsList.add(closeSquareBracket)
            openCurlyBracket -> solutionsList.add(closeCurlyBracket)
            openAngleBracket -> solutionsList.add(closeAngleBracket)
        }
    }
    var solution: String = ""
    solutionsList.forEach {
        solution += it
    }
    completeLineSolutionsList.add(solution)


}

fun syntaxError(expected: String, found: String) {
    println("Expected the opposite of: $expected BUT FOUND: $found")
}

