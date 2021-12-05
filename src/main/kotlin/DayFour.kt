import java.io.File


var bingoCalls: List<String> = listOf()
var bingoBoardInformation: MutableList<String> = mutableListOf()
val spaceDelimiter: String = " "
val commaDelimiter: String = ","
var inputList: MutableList<String> = mutableListOf()
var bingoBoardList: MutableList<BingoBoard>? = mutableListOf()
var holdingList: MutableList<String> = mutableListOf()
var bingoCallsInt: MutableList<Int> = mutableListOf()
var BINGO: Boolean = false
var bingoCallsOneAtATime: MutableList<Int> = mutableListOf()
var answer: BingoBoard? = null
var unmarkedValuesAnswerList: MutableList<Int> = mutableListOf()
var letTheSquidWinWrongBingoBoardList: MutableList<BingoBoard> = mutableListOf()

fun main() {
    adventFourPartOne()
    //adventFourPartTwo()
}

fun adventFourPartOne() {
    var holdingBoard: BingoBoard? = null

    readFileDayFour("src/DayFourInput.txt")
    holdingList = inputList.filter {
        it.isNotEmpty()
    } as MutableList<String>

    bingoCalls = holdingList.removeAt(0).split(commaDelimiter)
    var x = 0
    for (i in 0 until holdingList.size % 5) {
        while (x < 5) {
            bingoBoardInformation.add(holdingList.removeAt(0))
            x++
        }
        holdingBoard = createBoard(bingoBoardInformation)
    }
    while (holdingList.size > 4) {
        setUpBingoBoardList()
    }
    for (item in bingoCalls) {
        if (item.isNotEmpty()) {
            bingoCallsInt.add(item.trim().toInt())
        }
    }
    bingoCallsOneAtATime.clear()
    for (item in bingoCallsInt) {
        bingoCallsOneAtATime.add(item)
        for (item in bingoBoardList!!) {
            if (item.checkRowsAndCols(bingoCallsOneAtATime)) {
                answer = item
                BINGO = true
                unmarkedValuesAnswerList = answer?.checkUnmarkedValues(bingoCallsOneAtATime) as MutableList<Int>
                println(unmarkedValuesAnswerList.sum() * bingoCallsOneAtATime[bingoCallsOneAtATime.size - 1])
            }
            if (BINGO) {
                return
            }
        }
    }
}

fun adventFourPartTwo() {
    BINGO = false

    var holdingBoard: BingoBoard? = null
    readFileDayFour("src/DayFourInput.txt")
    holdingList = inputList.filter {
        it.isNotEmpty()
    } as MutableList<String>

    bingoCalls = holdingList.removeAt(0).split(commaDelimiter)
    var x = 0
    for (i in 0 until holdingList.size % 5) {
        while (x < 5) {
            bingoBoardInformation.add(holdingList.removeAt(0))
            x++
        }
        holdingBoard = createBoard(bingoBoardInformation)
    }



    while (holdingList.size > 4) {
        setUpBingoBoardList()
    }

    for (item in bingoCalls) {
        if (item.isNotEmpty()) {
            bingoCallsInt.add(item.trim().toInt())
        }
    }
    bingoCallsOneAtATime.clear()
    for (item in bingoCallsInt) {
        if (!BINGO) {
            bingoCallsOneAtATime.add(item)
            for (item in bingoBoardList!!) {
                if (item.checkRowsAndCols(bingoCallsOneAtATime)) {
                    if (!letTheSquidWinWrongBingoBoardList.contains(item)) {
                        letTheSquidWinWrongBingoBoardList.add(item)
                    } else if (letTheSquidWinWrongBingoBoardList.containsAll(bingoBoardList!!)) {
                        BINGO = true
                        answer = letTheSquidWinWrongBingoBoardList[letTheSquidWinWrongBingoBoardList.size - 1]
                    }

                }
            }
        } else {
            unmarkedValuesAnswerList = answer?.checkUnmarkedValues(bingoCallsOneAtATime) as MutableList<Int>
            println(unmarkedValuesAnswerList.sum() * bingoCallsOneAtATime[bingoCallsOneAtATime.size - 1])
            return
        }
    }
}


fun setUpBingoBoardList() {
    var x = 0
    while (x < 5) {
        bingoBoardInformation.add(holdingList.removeAt(0))
        x++
    }
    bingoBoardList?.add(createBoard(bingoBoardInformation))
    bingoBoardInformation.clear()
}

fun createBoard(list: List<String>): BingoBoard {
    return BingoBoard(
        createRow(list[0]),
        createRow(list[1]),
        createRow(list[2]),
        createRow(list[3]),
        createRow(list[4])
    )

}

fun createRow(row: String): List<Int> {
    var list: List<String> = row.split(spaceDelimiter)
    var intList: MutableList<Int> = mutableListOf()
    for (item in list) {
        if (item.isNotEmpty()) {
            intList.add(item.toInt())
        }
    }
    return intList
}

data class BingoBoard(
    val rowOne: List<Int>,
    val rowTwo: List<Int>,
    val rowThree: List<Int>,
    val rowFour: List<Int>,
    val rowFive: List<Int>
) {
    fun checkRowsAndCols(bingoCalls: List<Int>): Boolean {
        return (this.checkRows(bingoCalls) || this.checkCols(bingoCalls))
    }

    private fun checkRows(bingoCalls: List<Int>): Boolean {
        return (bingoCalls.containsAll(this.rowOne) || bingoCalls.containsAll(this.rowTwo) || bingoCalls.containsAll(
            this.rowThree
        )
                || bingoCalls.containsAll(this.rowFour) || bingoCalls.containsAll(this.rowFive))
    }

    private fun checkCols(bingoCalls: List<Int>): Boolean {
        return (bingoCalls.containsAll(this.colOne) || bingoCalls.containsAll(this.colTwo) || bingoCalls.containsAll(
            this.colThree
        )
                || bingoCalls.containsAll(this.colFour) || bingoCalls.containsAll(this.colFive))
    }

    fun checkUnmarkedValues(bingoCalls: List<Int>): List<Int> {
        var unmarkedValuesRowList: MutableList<Int> = mutableListOf()
        for (item in this.rowOne) {
            if (!bingoCalls.contains(item)) {
                unmarkedValuesRowList.add(item)
            }
        }
        for (item in this.rowTwo) {
            if (!bingoCalls.contains(item)) {
                unmarkedValuesRowList.add(item)
            }
        }
        for (item in this.rowThree) {
            if (!bingoCalls.contains(item)) {
                unmarkedValuesRowList.add(item)
            }
        }
        for (item in this.rowFour) {
            if (!bingoCalls.contains(item)) {
                unmarkedValuesRowList.add(item)
            }
        }
        for (item in this.rowFive) {
            if (!bingoCalls.contains(item)) {
                unmarkedValuesRowList.add(item)
            }
        }
        return unmarkedValuesRowList
    }

    val colOne: List<Int> = listOf(rowOne[0], rowTwo[0], rowThree[0], rowFour[0], rowFive[0])
    val colTwo: List<Int> = listOf(rowOne[1], rowTwo[1], rowThree[1], rowFour[1], rowFive[1])
    val colThree: List<Int> = listOf(rowOne[2], rowTwo[2], rowThree[2], rowFour[2], rowFive[2])
    val colFour: List<Int> = listOf(rowOne[3], rowTwo[3], rowThree[3], rowFour[3], rowFive[3])
    val colFive: List<Int> = listOf(rowOne[4], rowTwo[4], rowThree[4], rowFour[4], rowFive[4])

}

fun readFileDayFour(fileName: String) = File(fileName).forEachLine {
    inputList.add(it)
}