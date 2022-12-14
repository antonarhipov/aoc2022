fun main() {
    fun CharSequence.allUnique() = toSet().count() == length

    fun solve(input: String, windowSize: Int) =
        input.windowed(windowSize).indexOfFirst  { it.allUnique() } + windowSize

    fun solveNaively(input: String, windowSize: Int): Int {
        val windowed = input.windowed(windowSize)
        for ((index, window) in windowed.withIndex()) {
            if (window.toSet().count() == window.count()) {
                return index + windowSize
            }
        }
        return -1
    }

    val input = readInput("Day06")

    println(solve(input[0], 4)) //1531
    println(solve(input[0], 14)) //2518
}


