import java.io.File
import kotlin.math.absoluteValue

var lanternFishDataRaw: MutableList<String> = mutableListOf()
var lanternFishData: MutableList<Int> = mutableListOf()
var lanternFishDataCalculatedTotals: MutableMap<Int, Long> = mutableMapOf()
var spawnCounter: Long = 0
var totalLanternFishCounter: Long = 0

fun main() {
    adventSixPartOne()
    //adventSixPartTwo()
}

fun adventSixPartOne() {
    readFileDaySix("src/DaySixInput.txt")
    setUpLanternFishData()

    for (item in lanternFishData) {
        addDay(80, item)
        totalLanternFishCounter += spawnCounter
        spawnCounter = 0
        println("running...")
    }
    println(totalLanternFishCounter + lanternFishData.size)


}

fun adventSixPartTwo() {
    readFileDaySix("src/DaySixInput.txt")
    setUpLanternFishData()

    lanternFishDataCalculatedTotals.put(1, 0)
    lanternFishDataCalculatedTotals.put(2, 0)
    lanternFishDataCalculatedTotals.put(3, 0)
    lanternFishDataCalculatedTotals.put(4, 0)
    lanternFishDataCalculatedTotals.put(5, 0)

    for (item in lanternFishDataCalculatedTotals) {
        addDay(256, item.key)
        lanternFishDataCalculatedTotals[item.key] = spawnCounter
        spawnCounter = 0
    }

    for (item in lanternFishData) {
        if (lanternFishDataCalculatedTotals.containsKey(item)) {
            totalLanternFishCounter += lanternFishDataCalculatedTotals[item]!!.absoluteValue
        } else {
            addDay(256, item)
            totalLanternFishCounter += spawnCounter
            spawnCounter = 0
        }
        println("running...")
    }
    println(totalLanternFishCounter + lanternFishData.size)
}


fun readFileDaySix(fileName: String) = File(fileName).forEachLine {
    lanternFishDataRaw.add(it)
}

fun setUpLanternFishData() {
    var holdingList: MutableList<String> = mutableListOf()
    holdingList = lanternFishDataRaw[0].split(commaDelimiter) as MutableList<String>
    for (item in holdingList) {
        lanternFishData.add(item.toInt())
    }
}


fun addDay(days: Int, fishAge: Int) {
    var fishAge: Int = fishAge
    for (i in 1..days) {
        when (fishAge) {
            0 -> {
                fishAge = 6
                spawnLanternFish(days - i)
            }
            in 1..8 -> fishAge -= 1
        }
    }
}

fun spawnLanternFish(remainingDays: Int) {
    spawnCounter++
    addDay(remainingDays, 8)
}


