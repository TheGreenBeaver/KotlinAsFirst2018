@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    if (n == 0) return 1
    var temp = Math.abs(n)
    var answer = 0
    while (temp > 0) {
        temp /= 10
        answer++
    }
    return answer
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var previousNum = 1
    var prePreviousNum = 1
    var answer = 0
    if (n in 1..2) return 1
    for (i in 3..n) {
        answer = previousNum + prePreviousNum
        prePreviousNum = previousNum
        previousNum = answer
    }
    return answer
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun gcd(m: Int, n: Int): Int {
    return if (n == 0) m else gcd(n, m % n)
}

fun lcm(m: Int, n: Int) = m / gcd(m, n) * n

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var answer = if (n % 2 == 0) 2 else 3
    while (answer <= sqrt(n.toDouble()) + 1)
        if (n % answer == 0) return answer else answer += 2
    return if (n % answer == 0) answer else n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int) = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int) = gcd(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int) = Math.ceil(Math.sqrt(m.toDouble())) <= Math.floor(Math.sqrt(n.toDouble()))

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var temp = x
    var answer = 0
    while (temp != 1) {
        if (temp % 2 == 0) temp /= 2 else temp = temp * 3 + 1
        answer++
    }
    return answer
}

fun trigonometry(start: Double, x: Double, eps: Double, plus: Double): Double {
    var i = 0
    var answer = start
    var addition: Double
    do {
        i++
        addition = pow(-1.0, i.toDouble()) * pow(x, i * 2.0 + plus) / factorial(i * 2 + plus.toInt())
        answer += addition
    } while (abs(addition) >= eps)
    return answer
}

fun lesserAngle(angle: Double) = angle % (2 * PI)
/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double) =  trigonometry(lesserAngle(x), lesserAngle(x), eps, 1.0)

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double) = trigonometry(1.0, lesserAngle(x), eps, 0.0)

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var temp = n
    var answer = 0
    var multiplier = 1
    var divisor = Math.pow(10.0, digitNumber(n).toDouble() - 1.0).toInt()
    while (temp > 0) {
        answer += temp / divisor * multiplier
        temp %= divisor
        multiplier *= 10
        divisor /= 10
    }
    return answer
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int) = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var temp = n / 10
    var answer = false
    var previous = n % 10
    while (temp > 0)
        if (previous == temp % 10) {
            previous = temp % 10
            temp /= 10
        } else {
            answer = true
            break
        }
    return answer
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var digitsAlreadyWritten = 0
    var lastSqr = 0
    var currentNumber = 1
    while (digitsAlreadyWritten < n) {
        lastSqr = sqr(currentNumber)
        digitsAlreadyWritten += digitNumber(lastSqr)
        currentNumber++
    }
    val index = digitsAlreadyWritten - n + 1
    return lastSqr % Math.pow(10.0, index.toDouble()).toInt() / Math.pow(10.0, (index - 1).toDouble()).toInt()
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    if (n <= 2) return 1
    var digitsAlreadyWritten = 2
    var previousNum = 1
    var prePreviousNum = 1
    var lastFib = 0
    while (digitsAlreadyWritten < n) {
        lastFib = previousNum + prePreviousNum
        digitsAlreadyWritten += digitNumber(lastFib)
        prePreviousNum = previousNum
        previousNum = lastFib
    }
    val index = digitsAlreadyWritten - n + 1
    return lastFib % Math.pow(10.0, index.toDouble()).toInt() / Math.pow(10.0, (index - 1).toDouble()).toInt()
}
