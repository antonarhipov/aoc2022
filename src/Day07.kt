fun main() {

    fun String.isCd() = startsWith("$ cd")

    fun buildDirectoryTree(input: List<String>): Directory {
        val root = Directory("/", null)
        var currentDirectory = root

        input.drop(1).forEach { line ->
            if (line.isCd()) {
                val (_, _, name) = line.split(" ")
                currentDirectory = if (name.startsWith("..")) {
                    currentDirectory.parent ?: currentDirectory
                } else {
                    val dir = Directory(name, currentDirectory)
                    currentDirectory.dirs.add(dir)
                    dir
                }
            }

            if (line[0].isDigit()) {
                val (size, name) = line.split(" ")
                currentDirectory.files.add(FileEntry(name, size.toInt()))
            }
        }
        return root
    }

    fun findDirectories(dir: Directory, threshold: Int) : List<Directory> {
        val result = mutableListOf<Directory>()

        if(dir.size< threshold) result.add(dir)

        dir.dirs.forEach {
            if(it.size < threshold) result.add(it)
            result.addAll(findDirectories(it, threshold))
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val result = findDirectories(buildDirectoryTree(testInput), 100000).distinct().sumOf { it.size }
    println("test result = $result")
    check(result == 95437)

    val input = readInput("Day07")
    val tree = buildDirectoryTree(input)

    val directories = findDirectories(tree, 100000).distinct()
    val resultPart1 = directories.sumOf { it.size }
    println("part 1: $resultPart1")

    val diskSpace = 70000000
    val requiredSpace = 30000000
    val freeSpace = diskSpace - tree.size
    val needToDelete = requiredSpace - freeSpace

    println("Disk space: $diskSpace, Used: ${tree.size}, Free space: $freeSpace, Required space: $requiredSpace")
    println("Need to delete: $needToDelete")

    val sortedBySize = findDirectories(tree, diskSpace).distinct().sortedBy { it.size }
    for (d in sortedBySize) {
        println(d)
    }

    val toDelete = sortedBySize.first {
        it.size > needToDelete
    }

    println("Directory to delete: $toDelete")

}

class FileEntry(val name: String, val size: Int) {
    override fun toString(): String = "File $name: $size]"
}

class Directory(val name: String, var parent: Directory?) {
    val dirs: MutableList<Directory> = mutableListOf()
    val files: MutableList<FileEntry> = mutableListOf()
    val size: Int
        get() = files.sumOf { it.size } + dirs.sumOf { it.size }

    override fun toString(): String = "Directory $name: $size"
}