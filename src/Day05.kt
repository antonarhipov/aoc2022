fun main() {

    val input = readInput("Day05")
    val split = input.indexOfFirst { it.isBlank() }
    val stackLines = input.take(split - 1)
    val moves = input.drop(split + 1)

    println("Stacks:")
    stackLines.forEach { println(it) }
    println("-----------")
    println("Moves:")
    moves.forEach { println(it) }

    println("-----------")
    val stacksA = createStacks(stackLines)
    val stacksB = createStacks(stackLines)


    makeMovesWithCrateMover9000(moves, stacksA)
    println("Moved with 9000: ${stacksA.toSortedMap().values.map { it.last() }.joinToString("")}")

    makeMovesWithCrateMover9001(moves, stacksB)
    println("Moved with 9001: ${stacksB.toSortedMap().values.map { it.last() }.joinToString("")}")

}

fun makeMovesWithCrateMover9000(moves: List<String>, stacks: HashMap<Int, ArrayList<Char>>) {
    for (move in moves) {
        val (count, from, to) = Regex("\\d+").findAll(move).toList().map { it.value.toInt() }
        repeat(count) {
            stacks[to]!!.add(stacks[from]!!.removeLast())
        }
    }
}
fun makeMovesWithCrateMover9001(moves: List<String>, stacks: HashMap<Int, ArrayList<Char>>) {
    for ((i, move) in moves.withIndex()) {
        val (count, from, to) = Regex("\\d+").findAll(move).toList().map { it.value.toInt() }
        stacks[to]!!.addAll(stacks[from]!!.takeLast(count))
        stacks[from] = ArrayList(stacks[from]!!.dropLast(count))
    }
}

fun createStacks(input: List<String>): HashMap<Int, ArrayList<Char>> {
    val crates = input.map { row ->
        row.chunked(4).map {
            it.replace(Regex("[\\[\\]\\s]"), "").firstOrNull()
        }
    }

    val stacks = hashMapOf<Int, ArrayList<Char>>()

    crates.forEach { row: List<Char?> ->
        row.forEachIndexed { i, c ->
            if (c != null) {
                stacks.compute(i + 1) { _, current ->
                    current?.apply { add(0, c) } ?: arrayListOf(c)
                }
            }
        }
    }

    return stacks
}
