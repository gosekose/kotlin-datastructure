package queue

class RingQueueEx(
    private val capacity: Int
) {
    private val queue = IntArray(capacity)
    private var front = 0
    private var rear = 0
    private var num = 0

    init {
        if (capacity <= 0) throw InvalidCapacityRingQueueException(RingQueueExceptionCode.INVALID_CAPACITY)
    }

    fun enqueue(value: Int) {
        assignIfRearEqualsCapacity()
        validateOverflow()

        queue[rear] = value
        num++
        rear++
    }

    private fun validateOverflow() {
        if (isFull) throw OverflowRingQueueException(RingQueueExceptionCode.OVERFLOW)
    }

    private fun assignIfRearEqualsCapacity() {
        if (rear == capacity) rear %= capacity
    }

    fun dequeue(): Int {
        validateEmpty()

        val result = queue[front]
        num--
        front++

        assignFrontIfFrontEqualsCapacity()

        return result
    }

    private fun validateEmpty() {
        if (isEmpty) throw EmptyRingQueueException(RingQueueExceptionCode.EMPTY)
    }

    private fun assignFrontIfFrontEqualsCapacity() {
        if (front == capacity) front %= capacity
    }

    fun peek(): Int {
        validateEmpty()
        return queue[front]
    }

    fun indexOf(value: Int): Int {

        for (i in 0..<num) {
            val idx = (i + front) % capacity
            if (queue[idx] == value) {
                return idx
            }
        }

        return -1
    }

    fun printAllValues() {
        if (isEmpty) {
            println("큐가 비었습니다.")
            return
        }

        val builder = StringBuilder(PRINT_ALL_QUEUE_PREFIX)

        for (i in 0..<num) {
            val idx = (i + front) % capacity
            builder.append(queue[idx]).append(", ")
        }

        if (builder.length > PRINT_ALL_QUEUE_PREFIX.length) {
            builder.setLength(builder.length - 2)
        }

        println(builder)
    }

    fun clear() {
        front = 0
        rear = 0
        num = 0
        queue.fill(0)
    }

    val size: Int
        get() = num

    val isEmpty: Boolean
        get() = num <= 0

    val isFull: Boolean
        get() = num >= capacity


    companion object {
        const val PRINT_ALL_QUEUE_PREFIX = "queue values = "
    }


    class InvalidCapacityRingQueueException(code: RingQueueExceptionCode) : RuntimeException(code.message)
    class OverflowRingQueueException(code: RingQueueExceptionCode) : RuntimeException(code.message)
    class EmptyRingQueueException(code: RingQueueExceptionCode) : RuntimeException(code.message)
}

enum class RingQueueExceptionCode(
    val message: String,
) {
    EMPTY("큐가 비었습니다."), OVERFLOW("스택 오버플로우 발생했습니다."), INVALID_CAPACITY("유효하지 않은 용량입니다.");
}

fun main() {
    val queue = RingQueueEx(5)

    queue.enqueue(10)
    queue.enqueue(12)
    queue.enqueue(13)
    queue.enqueue(9)
    queue.enqueue(8)

    println("queue peek = 10, result = ${queue.peek() == 10}")
    println("12 index is 1, result = ${queue.indexOf(12) == 1}")
    queue.printAllValues()

    println("dequeue result is 10, result = ${queue.dequeue() == 10}")
    println("dequeue result is 12, result = ${queue.dequeue() == 12}")

    queue.enqueue(100)

    println("peek is 13, result = ${queue.peek() == 13}")
    println("100 index is 0, result = ${queue.indexOf(100) == 0}")
    queue.printAllValues()

    println("queue size = 4, result = ${queue.size == 4}")
    println("queue is not empty, result = ${!queue.isEmpty}")
    println("queue is not full, result = ${!queue.isFull}")

    queue.clear()
    println("queue is empty, result = ${queue.isEmpty}")
}