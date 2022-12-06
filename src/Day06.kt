fun main() {
    fun CharSequence.allUnique() = toSet().count() == length

    fun solve(input: String, windowSize: Int) = input.windowedSequence(windowSize, partialWindows = true) {
        it.allUnique()
    }.indexOf(true) + windowSize

    fun solveNaively(input: String, windowSize: Int): Int {
        val windowed = input.windowed(windowSize, partialWindows = true)
        for ((index, window) in windowed.withIndex()) {
            if (window.toSet().count() == window.count()) {
                return index + windowSize
            }
        }
        return -1
    }

    val input = readInput("Day06_part1")

    println(solve(input[0], 4)) //1531
    println(solve(input[0], 14)) //2518
}


