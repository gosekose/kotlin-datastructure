package stack

class IntStack(
    private val capacity: Int
) {
    private val stack = IntArray(capacity)
    private var point = 0

    init {
        if (capacity <= 0) throw InvalidIntStackException(IntStackErrorException.INVALID_CAPACIRT)
    }

    fun push(x: Int): Int {
        if (point >= capacity) throw OverflowIntStackException(IntStackErrorException.OVERFLOW)
        stack[point++] = x
        return x
    }

    fun pop(): Int {
        if (point <= 0) throw EmptyIntStackException(IntStackErrorException.EMPTY)
        return stack[--point]
    }

    fun peek(): Int {
        if (point <= 0) throw EmptyIntStackException(IntStackErrorException.EMPTY)
        return stack[point - 1]
    }

    fun clear() {
        point = 0
    }

    fun indexOf(x: Int): Int {
        return stack.indexOfFirst { it == x }
    }

    val size: Int
        get() = point

    val isEmpty: Boolean
        get() = point <= 0

    val isFull: Boolean
        get() = point >= capacity

    fun printFromBottomToTop() {
        when {
            isEmpty -> println(IntStackErrorException.EMPTY.message)
            else -> stack.forEachIndexed { index, value ->
                if (index < point) {
                    println(value)
                }
            }
        }
    }

    class InvalidIntStackException(exception: IntStackErrorException) : RuntimeException(exception.message)
    class OverflowIntStackException(exception: IntStackErrorException) : RuntimeException(exception.message)
    class EmptyIntStackException(exception: IntStackErrorException) : RuntimeException(exception.message)
}

enum class IntStackErrorException(
    val message: String,
) {
    INVALID_CAPACIRT("용량은 0이상입니다."), OVERFLOW("오버플로우가 발생했습니다."), EMPTY("스택이 비었습니다.");
}

fun main() {
    val stack = IntStack(12)

    stack.push(100)
    stack.push(1)
    stack.push(2)
    stack.push(12)
    stack.push(15)

    println(stack.pop() == 15)
    println(stack.pop() == 12)
    println(stack.peek() == 2)
    println(stack.indexOf(1) == 1)
    println(!stack.isEmpty)
    println(!stack.isFull)

    stack.clear()
    println(stack.size == 0)

    stack.printFromBottomToTop()
}