@file:Suppress("UNUSED_PARAMETER")
package lesson1.task1

import lesson2.task1.ageDescription
import kotlin.math.*
import java.lang.Math

val SECONDS_IN_MINUTE = 60
val MINUTES_IN_HOUR = 60
val SECONDS_IN_HOUR = 3600
val CENTIMETERS_IN_VERSHOK = 4.445
val VERSHOKS_IN_ARSHIN = 16
val VERSHOKS_IN_SAGENE = 48
val CENTIMETERS_IN_METER = 100
val RADIANS_IN_DEGREES = Math.PI / 180
val RADIANS_IN_MINUTE = Math.PI / 10800
val RADIANS_IN_SECOND = Math.PI / 648000

/**
 * Пример
 *
 * Вычисление квадрата целого числа
 */
fun sqr(x: Int) = x * x

/**
 * Пример
 *
 * Вычисление квадрата вещественного числа
 */
fun sqr(x: Double) = x * x

/**
 * Пример
 *
 * Вычисление дискриминанта квадратного уравнения
 */
fun discriminant(a: Double, b: Double, c: Double) = sqr(b) - 4 * a * c

/**
 * Пример
 *
 * Поиск одного из корней квадратного уравнения
 */
fun quadraticEquationRoot(a: Double, b: Double, c: Double) =
        (-b + sqrt(discriminant(a, b, c))) / (2 * a)

/**
 * Пример
 *
 * Поиск произведения корней квадратного уравнения
 */
fun quadraticRootProduct(a: Double, b: Double, c: Double): Double {
    val sd = sqrt(discriminant(a, b, c))
    val x1 = (-b + sd) / (2 * a)
    val x2 = (-b - sd) / (2 * a)
    return x1 * x2 // Результат
}

/**
 * Пример главной функции
 */
fun main(args: Array<String>) {
    val x1x2 = quadraticRootProduct(1.0, 13.0, 42.0)
    println("Root product: $x1x2")
}

/**
 * Тривиальная
 *
 * Пользователь задает время в часах, минутах и секундах, например, 8:20:35.
 * Рассчитать время в секундах, прошедшее с начала суток (30035 в данном случае).
 */
fun seconds(hours: Int, minutes: Int, seconds: Int) = hours * SECONDS_IN_HOUR + minutes * SECONDS_IN_MINUTE + seconds

/**
 * Тривиальная
 *
 * Пользователь задает длину отрезка в саженях, аршинах и вершках (например, 8 саженей 2 аршина 11 вершков).
 * Определить длину того же отрезка в метрах (в данном случае 18.98).
 * 1 сажень = 3 аршина = 48 вершков, 1 вершок = 4.445 см.
 */
fun lengthInMeters(sagenes: Int, arshins: Int, vershoks: Int) = (CENTIMETERS_IN_VERSHOK * vershoks + CENTIMETERS_IN_VERSHOK * VERSHOKS_IN_ARSHIN * arshins + CENTIMETERS_IN_VERSHOK * VERSHOKS_IN_SAGENE * sagenes) / CENTIMETERS_IN_METER

/**
 * Тривиальная
 *
 * Пользователь задает угол в градусах, минутах и секундах (например, 36 градусов 14 минут 35 секунд).
 * Вывести значение того же угла в радианах (например, 0.63256).
 */
fun angleInRadian(grad: Int, min: Int, sec: Int) = grad * RADIANS_IN_DEGREES + min * RADIANS_IN_MINUTE + sec * RADIANS_IN_SECOND

/**
 * Тривиальная
 *
 * Найти длину отрезка, соединяющего точки на плоскости с координатами (x1, y1) и (x2, y2).
 * Например, расстояние между (3, 0) и (0, 4) равно 5
 */
fun trackLength(x1: Double, y1: Double, x2: Double, y2: Double) = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))

/**
 * Простая
 *
 * Пользователь задает целое число, большее 100 (например, 3801).
 * Определить третью цифру справа в этом числе (в данном случае 8).
 */
fun thirdDigit(number: Int) = number % 1000 / 100

/**
 * Простая
 *
 * Поезд вышел со станции отправления в h1 часов m1 минут (например в 9:25) и
 * прибыл на станцию назначения в h2 часов m2 минут того же дня (например в 13:01).
 * Определите время поезда в пути в минутах (в данном случае 216).
 */
fun travelMinutes(hoursDepart: Int, minutesDepart: Int, hoursArrive: Int, minutesArrive: Int) = hoursArrive * MINUTES_IN_HOUR + minutesArrive - hoursDepart * MINUTES_IN_HOUR - minutesDepart

/**
 * Простая
 *
 * Человек положил в банк сумму в s рублей под p% годовых (проценты начисляются в конце года).
 * Сколько денег будет на счету через 3 года (с учётом сложных процентов)?
 * Например, 100 рублей под 10% годовых превратятся в 133.1 рубля
 */
fun accountInThreeYears(initial: Int, percent: Int) = initial.toDouble() * (1.0 + percent.toDouble() / 100.0)* (1.0 + percent.toDouble() / 100.0)* (1.0 + percent.toDouble() / 100.0)

/**
 * Простая
 *
 * Пользователь задает целое трехзначное число (например, 478).
 * Необходимо вывести число, полученное из заданного перестановкой цифр в обратном порядке (например, 874).
 */
fun numberRevert(number: Int) = number / 100 + number % 100 / 10 * 10 + number % 10 * 100
