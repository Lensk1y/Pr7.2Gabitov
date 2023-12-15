import java.util.concurrent.Semaphore
import java.util.concurrent.ThreadLocalRandom

class Philosopher(private val leftFork: Semaphore, private val rightFork: Semaphore, private val name: String) : Thread() {
    override fun run() {
        println("$name садится за стол")

        leftFork.acquire()
        rightFork.acquire()

        println("$name начинает обедать")

        sleep(ThreadLocalRandom.current().nextInt(1000, 5000).toLong())

        leftFork.release()
        rightFork.release()

        println("$name закончил обедать и начинает размышлять")
    }
}

fun main() {
    val numberOfPhilosophers = 5
    val forks = List(numberOfPhilosophers) { Semaphore(1) }

    val philosophers = List(numberOfPhilosophers) { index ->
        Philosopher(forks[index], forks[(index + 1) % numberOfPhilosophers], "Философ ${index + 1}")
    }

    philosophers.shuffled().forEach { it.start() }
}