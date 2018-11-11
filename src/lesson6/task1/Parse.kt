@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */

val MONTHS = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря")

fun spl(str: String) = str.split(delimiters = *arrayOf(" "))
fun splDot(str: String) = str.split(delimiters = *arrayOf("."))
fun good(str: String) = str.replace(Regex("""[^\d+)(]"""), "")

fun dateStrToDigit(str: String) =
        if (spl(str).size != 3 ||
                spl(str)[1] !in MONTHS ||
                spl(str)[0].toIntOrNull() == null ||
                spl(str)[2].toIntOrNull() == null ||
                spl(str)[2].toInt() < 0 ||
                spl(str)[0].toInt() !in 1..daysInMonth(MONTHS.indexOf(spl(str)[1]) + 1, spl(str)[2].toInt()))
            ""
        else {
            val answer = spl(str).toMutableList()
            if (answer[0].length < 2)
                answer[0] = "0" + answer[0]
            answer[1] = (MONTHS.indexOf(answer[1]) + 1).toString()
            if (answer[1].length < 2)
                answer[1] = "0" + answer[1]
            answer.joinToString(separator = ".")
        }

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String) =
        if (splDot(digital).size != 3 ||
                splDot(digital)[0].toIntOrNull() == null ||
                splDot(digital)[1].toIntOrNull() == null ||
                splDot(digital)[2].toIntOrNull() == null ||
                splDot(digital)[2].toInt() < 0 ||
                splDot(digital)[1].toInt() !in 1..12 ||
                splDot(digital)[0].toInt() !in 1..daysInMonth(splDot(digital)[1].toInt(), splDot(digital)[2].toInt()))
            ""
        else {
            val answer = splDot(digital).toMutableList()
            if (answer[0].startsWith("0"))
                answer[0] = answer[0].substring(1)
            answer[1] = MONTHS[answer[1].toInt() - 1]
            answer.joinToString(separator = " ")
        }

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */

fun flattenPhoneNumber(phone: String) =
        if (!phone.dropWhile { it in listOf(' ', '-') }.matches(Regex("""^[+\d-][\d\s-]*\(?[\d\s-]*\)?[\d\s-]*[\d-]$""")) &&
                !phone.matches(Regex("""\d""")) ||
                phone.indexOf(')') < phone.indexOf('(') ||
                good(phone).indexOf('(')
                    - good(phone).indexOf('+') == 1 ||
                good(phone).indexOf(')')
                    - good(phone).indexOf('(') == 1)
            ""
        else
            phone.replace(Regex("""[^+\d]"""), "")

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String) =
        if (!jumps.matches(Regex("""[\d-%][\d\s%-]*[\d-%]$""")) &&
                !jumps.matches(Regex("""\d""")) ||
                spl(jumps).none { it.toIntOrNull() != null })
            -1
        else
            spl(jumps).filter { it.toIntOrNull() != null }.maxBy { it.toInt() }!!.toInt()

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val splitJumps = spl(jumps)
    if (!jumps.matches(Regex("""^\d[\d\s+%-]*[+%-]$""")) ||
            splitJumps.size % 2 != 0 ||
            splitJumps.isEmpty())
        return -1
    val successfulJumps = mutableListOf<Int>()
    for (i in 0..splitJumps.size - 2 step 2) {
        if (splitJumps[i].contains(Regex("""\D""")) ||
                splitJumps[i + 1].contains(Regex("""[^-%+]"""))) return -1
        if (splitJumps[i + 1].contains('+')) successfulJumps.add(splitJumps[i].toInt())
    }
    return if (successfulJumps.isNotEmpty()) successfulJumps.max()!! else -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * При нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val split = spl(expression)
    if (!expression.matches(Regex("""^\d[\d\s+-]+\d$""")) &&
            !expression.matches(Regex("""\d""")) ||
            split.size % 2 == 0 ||
            split[0].toIntOrNull() == null ||
            split[0].toInt() < 0)
        throw IllegalArgumentException("Incorrect input format")
    var answer = spl(expression)[0].toInt()
    for (i in 0 until split.size - 2 step 2) {
        if (split[i + 2].toIntOrNull() == null || split[i + 2].toInt() < 0 || split[i + 1] !in listOf("+", "-"))
            throw IllegalArgumentException("Incorrect input format")
        answer += if (split[i + 1] == "+")
            split[i + 2].toInt()
        else
            -split[i + 2].toInt()
    }
    return answer
}


/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val strSplit = spl(str.toLowerCase())
    var answer = 0
    for (i in 0 until strSplit.size - 1) {
        if (strSplit.size != 1 && strSplit[i + 1] == strSplit[i])
            return answer
        answer += strSplit[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */

fun mostExpensive(description: String): String {
    val split = spl(description)
    if (!description.matches(Regex("""^\S.*\d$""")) ||
            split.size % 2 != 0 ||
            split.isEmpty())
        return ""
    val pairDescription = mutableListOf<Pair<String, Double>>()
    for (i in 1 until split.size step 2) {
        if (!split[i].endsWith(";") && i != split.size - 1 ||
                split[i].dropLast(1).toDoubleOrNull() == null && split[i].dropLast(1).toIntOrNull() == null)
            return ""
        pairDescription.add(split[i - 1] to
                if (split[i].dropLast(1).toDoubleOrNull() != null)
                    split[i].dropLast(1).toDouble()
                else split[i].dropLast(1).toInt().toDouble())
    }
    return pairDescription.maxBy { it.second }!!.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
