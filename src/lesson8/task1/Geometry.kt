@file:Suppress("UNUSED_PARAMETER")
package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Примр
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))

}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle) =
            if (this.center.distance(other.center) - this.radius - other.radius > 0)
                this.center.distance(other.center) - this.radius - other.radius
            else
                0.0

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point) = p.distance(this.center) <= this.radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */

fun diameter(vararg points: Point): Segment {
    if (points.size == 1)
        throw IllegalArgumentException("Just one point is not enough")
    var max = 0.0
    var answer = Segment(points[0], points[1])
    for (i in 0 until points.size - 1)
        for (j in i + 1 until points.size)
            if (points[i].distance(points[j]) >= max) {
                max = points[i].distance(points[j])
                answer = Segment(points[i], points[j])
            }
    return answer

//    val sortedByX = points.sortedBy { it.x }
//    val sortedByY = points.sortedBy { it.y }
//
//    val xIsMaximal = sortedByX.filter { it.x == sortedByX.last().x }
//    val xIsMinimal = sortedByX.filter { it.x == sortedByX.first().x }
//
//    val yIsMaximal = sortedByY.filter { it.y == sortedByY.last().y }
//    val yIsMinimal = sortedByY.filter { it.y == sortedByY.first().y }
//
//    val probableByX: Segment
//    val xDistance: Double
//    if (xIsMaximal.maxBy { it.y }!!.distance(xIsMinimal.minBy { it.y }!!) >
//            xIsMaximal.minBy { it.y }!!.distance(xIsMinimal.maxBy { it.y }!!)) {
//        probableByX = Segment(xIsMaximal.maxBy { it.y }!!, xIsMinimal.minBy { it.y }!!)
//        xDistance = xIsMaximal.maxBy { it.y }!!.distance(xIsMinimal.minBy { it.y }!!)
//    }
//    else {
//        probableByX = Segment(xIsMaximal.minBy { it.y }!!, xIsMinimal.maxBy { it.y }!!)
//        xDistance = xIsMaximal.minBy { it.y }!!.distance(xIsMinimal.maxBy { it.y }!!)
//    }
//
//    val probableByY: Segment
//    val yDistance: Double
//    if (yIsMaximal.maxBy { it.x }!!.distance(yIsMinimal.minBy { it.x }!!) >
//            yIsMaximal.minBy { it.x }!!.distance(yIsMinimal.maxBy { it.x }!!)) {
//        probableByY = Segment(yIsMaximal.maxBy { it.x }!!, yIsMinimal.minBy { it.x }!!)
//        yDistance = yIsMaximal.maxBy { it.x }!!.distance(yIsMinimal.minBy { it.x }!!)
//    }
//    else {
//        probableByY = Segment(yIsMaximal.minBy { it.x }!!, yIsMinimal.maxBy { it.x }!!)
//        yDistance = yIsMaximal.minBy { it.x }!!.distance(yIsMinimal.maxBy { it.x }!!)
//    }
//
//    return if (yDistance > xDistance)
//        probableByY
//    else
//        probableByX
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val leftX = minOf(diameter.begin.x, diameter.end.x)
    val rightX = maxOf(diameter.begin.x, diameter.end.x)
    val centerX = leftX + (rightX - leftX) / 2
    val lowerY = minOf(diameter.begin.y, diameter.end.y)
    val upperY = maxOf(diameter.begin.y, diameter.end.y)
    val centerY = lowerY + (upperY - lowerY) / 2
    return Circle(Point(centerX, centerY), diameter.end.distance(diameter.begin) / 2)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x: Double
        val y: Double
        when {
            this.angle == other.angle -> throw IllegalArgumentException("Lines are parallel or overlapping")
            this.angle == PI / 2 -> {
                x = -this.b
                y = (other.b + x * sin(other.angle)) / cos(other.angle)
            }
            this.angle == 0.0 -> {
                y = this.b
                x = (y * cos(other.angle) - other.b) / sin(other.angle)
            }
            other.angle == PI / 2 -> {
                x = -other.b
                y = (this.b + x * sin(this.angle)) / cos(this.angle)
            }
            other.angle == 0.0 -> {
                y = other.b
                x = (y * cos(this.angle) - this.b) / sin(this.angle)
            }
            else -> {
                x = (other.b / cos(other.angle) - this.b / cos(this.angle)) / (tan(this.angle) - tan(other.angle))
                y = (this.b + x * sin(this.angle)) / cos(this.angle)
            }
        }
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    var angle: Double
    angle = when {
        s.begin.x == s.end.x -> PI / 2
        s.begin.y == s.end.y -> 0.0
        else -> {
            val upper = listOf(s.end, s.begin).maxBy { it.y }
            val lower = listOf(s.end, s.begin).minBy { it.y }
            val verticalCathetus = upper!!.y - lower!!.y
            val horizontalCathetus = upper.x - lower.x
            val hipot = sqrt(sqr(horizontalCathetus) + sqr(verticalCathetus))
            val cos = horizontalCathetus / hipot
            acos(cos)
        }
    }
    if (angle >= PI)
        angle -= PI
    return Line(s.begin, angle)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point) = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val leftX = minOf(a.x, b.x)
    val rightX = maxOf(a.x, b.x)
    val centerX = leftX + (rightX - leftX) / 2
    val lowerY = minOf(a.y, b.y)
    val upperY = maxOf(a.y, b.y)
    val centerY = lowerY + (upperY - lowerY) / 2
    val angle = lineByPoints(a, b).angle + if (lineByPoints(a, b).angle + PI / 2 >= PI)
        -PI / 2
    else
        PI / 2
    return Line(Point(centerX, centerY), angle)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> = TODO()

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = TODO()

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

