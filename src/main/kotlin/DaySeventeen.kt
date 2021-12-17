var listOfVelocities: MutableList<Pair<Int, Int>> = mutableListOf()
var hitCounter: Long = 0

fun main() {
    //adventSeventeenPartOne()
    adventSeventeenPartTwo()
}

fun adventSeventeenPartOne(){
    calcInitialVelocities()
    listOfVelocities.forEach{
        calcTrajectoryHeight(it.first, it.second)
    }

}

fun adventSeventeenPartTwo(){
    calcInitialVelocitiesPartTwo()
    println(hitCounter)
}

fun calcTrajectoryHeight(x: Int, y: Int){

    var velocityX: Int = x
    var velocityY: Int = y

    var positionX = 0
    var positionY = 0

    while (velocityY >= 0) {
        if (velocityX != 0) {
            velocityX -= 1
        }
        velocityY -= 1

        positionX += velocityX
        positionY += velocityY
        if (velocityY == 0) {
            println("APEX: $positionY")
        }

    }
}

fun calcInitialVelocitiesPartTwo() {
    for (x in -1000..1000) {
        for (y in -1000..1000) {
            var hitTargetArea = false
            var missTargetArea = false

            var velocityX = x
            var velocityY = y

            var positionX = 0
            var positionY = 0

            while (!hitTargetArea && !missTargetArea) {
                if (velocityX != 0 && velocityX > 0) {
                    velocityX -= 1
                } else if (velocityX != 0 && velocityX < 0) {
                    velocityX += 1
                }
                velocityY -= 1

                positionX += velocityX
                positionY += velocityY

                if ((positionX in 201..230) && (positionY in -99..-65)) {
                    hitTargetArea = true
                    hitCounter++
                    println("$x , $y")
                }
                if (velocityX == 0 && positionX < 201) {
                    missTargetArea = true
                }
                if (positionY < -99 || positionX > 230) {
                    missTargetArea = true
                }
            }
        }
    }
}

fun calcInitialVelocities() {
    for (x in -1000..1000) {
        for (y in -1000..1000) {
            var hitTargetArea = false
            var missTargetArea = false

            var velocityX = x
            var velocityY = y

            var positionX = 0
            var positionY = 0

            while (!hitTargetArea && !missTargetArea) {
                if (velocityX != 0 && velocityX > 0) {
                    velocityX -= 1
                } else if (velocityX != 0 && velocityX < 0) {
                    velocityX += 1
                }
                velocityY -= 1

                positionX += velocityX
                positionY += velocityY

                if ((positionX in 201..230) && (positionY in -99..-66)) {
                    hitTargetArea = true
                    listOfVelocities.add(Pair(x,y))
                }
                if (velocityX == 0 && positionX < 201) {
                    missTargetArea = true
                }
                if (positionY < -99 || positionX > 230) {
                    missTargetArea = true
                }
            }
        }
    }
}