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
val ROMAN_LETTERS = listOf('M', 'D', 'C', 'L', 'X', 'V', 'I')
val ROMAN_DIVIDERS = listOf(1000, 500, 100, 50, 10, 5, 1)

fun spl(str: String) = str.split(delimiters = *arrayOf(" "))
fun splDot(str: String) = str.split(delimiters = *arrayOf("."))

fun dateStrToDigit(str: String): String {
    val splStr = spl(str).toMutableList()
    return if (!str.matches(Regex("""\d+\s[а-я]+\s\d+""")) ||
            splStr[1] !in MONTHS ||
            splStr[0].toInt() !in 1..daysInMonth(MONTHS.indexOf(splStr[1]) + 1, splStr[2].toInt()))
        ""
    else
        String.format("%02d.%02d.%s", splStr[0].toInt(), MONTHS.indexOf(splStr[1]) + 1, splStr[2])
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
fun dateDigitToStr(digital: String): String {
    val splDig = splDot(digital).toMutableList()
    return if (!digital.matches(Regex("""\d{2}\.\d{2}\.\d+""")) ||
            splDig[1].toInt() !in 1..12 ||
            splDig[0].toInt() !in 1..daysInMonth(splDig[1].toInt(), splDig[2].toInt()))
        ""
    else
        String.format("${splDig[0].toInt()} ${MONTHS[splDig[1].toInt() - 1]} ${splDig[2]}")
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
        if (phone.replace(Regex("""[-\s]"""), "").
                        matches(Regex("""(^(\+(?=\d+)|\d)\d*(\(\d+\))?\d*\d$)|(\d)""")))
            phone.replace(Regex("""[^+\d]"""), "")
        else
            ""

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
        if (!jumps.matches(Regex("""([\d-%][\d\s%-]*[\d-%]$)|(\d)""")) ||
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
fun bestHighJump(jumps: String) =
    if (try {
                !"$jumps ".matches(Regex("""(\d+\s[-+%]+\s)+"""))
            } catch (e: StackOverflowError) {
                jumps.split(Regex("""(?<=\D)\s""")).any { !it.matches(Regex("""\d+\s[-+%]+""")) }
            } ||
            jumps.count { it == '+' } == 0)
        -1
    else
        ("$jumps ").
                replace(Regex("""\d+\s[-%]+(?!\+)\s"""), "").split(Regex("""\s\D+\s"""))
                .filter { it != "" }
                .maxBy { it.toInt() }!!
                .toInt()
/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * При нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String) =
        if (!expression.matches(Regex("""((\d+\s[+-]\s)+\d+)|(\d+)""")))
            throw IllegalArgumentException("Incorrect input format")
        else
            expression.
                    replace(Regex("""(?<=-\s)\d+""")) { "-${it.value}" }
                    .split(Regex("""\s[+-]\s""")).sumBy { it.toInt() }


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
        if (strSplit[i + 1] == strSplit[i])
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

fun mostExpensive(description: String) =
        if (!"$description; ".matches(Regex("""(\S+\s\d+(\.\d+)?;\s)+""")))
            ""
        else
            description.split("; ").map { spl(it) }.associate { it[0] to it[1].toDouble() }.maxBy { it.value }!!.key

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

fun fromRoman(roman: String): Int {
    if (!roman.matches(Regex("""M*(CM(?![CD]))?(CD(?!C)|D)?C{0,3}(XC(?![XL]))?(L|XL(?!X))?X{0,3}(IX(?!.))?(V|IV(?!.))?I{0,3}""")) ||
            roman == "")
        return -1
    var answer = ROMAN_DIVIDERS[ROMAN_LETTERS.indexOf(roman[roman.length - 1])]
    for (i in roman.length - 2 downTo 0)
        answer += if (ROMAN_DIVIDERS[ROMAN_LETTERS.indexOf(roman[i])] >= ROMAN_DIVIDERS[ROMAN_LETTERS.indexOf(roman[i + 1])])
            ROMAN_DIVIDERS[ROMAN_LETTERS.indexOf(roman[i])]
        else
            -ROMAN_DIVIDERS[ROMAN_LETTERS.indexOf(roman[i])]
    return answer
}

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
 * то есть если в программе приутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    if (commands.contains(Regex("""[^-+<>\s\[\]]""")))
        throw IllegalArgumentException("Illegal symbol in commands")
    val bracesStack = mutableListOf<Int>()
    val bracesPairs = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until commands.length) {
        if (commands[i] == '[')
            bracesStack.add(i)
        if (commands[i] == ']') {
            try { bracesPairs.add(bracesStack[bracesStack.size - 1] to i) }
            catch (e: IndexOutOfBoundsException)
            { throw IllegalArgumentException("Some braces in commands are unclosed") }
            bracesStack.removeAt(bracesStack.size - 1)
        }
    }
    if (!bracesStack.isEmpty())
        throw IllegalArgumentException("Some braces in commands are unclosed")
    val answer = Array(cells) {0}
    var position = cells / 2
    var actionsDone = 0
    var actionNumber = 0
    while (actionsDone < limit && actionNumber < commands.length) {
        when (commands[actionNumber]) {
            '+' -> try {
                answer[position]++
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalStateException("Operation on an non-existent cell commit")
            } finally {
                actionNumber++
            }
            '-' -> try {
                answer[position]--
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalStateException("Operation on an non-existent cell commit")
            } finally {
                actionNumber++
            }
            '>' -> {
                position++
                if (position >= cells)
                    throw IllegalStateException("Operation on an non-existent cell commit")
                actionNumber++
            }
            '<' -> {
                position--
                if (position < 0)
                    throw IllegalStateException("Operation on an non-existent cell commit")
                actionNumber++
            }
            ' ' -> actionNumber++
            '[' -> {
                try {
                    if (answer[position] == 0)
                        actionNumber = bracesPairs.find { it.first == actionNumber }!!.second + 1
                    else
                        actionNumber++
                } catch (e: IndexOutOfBoundsException) {
                    throw IllegalStateException("Operation on an non-existent cell commit")
                }
            }
            ']' -> {
                try {
                    if (answer[position] != 0)
                        actionNumber = bracesPairs.find { it.second == actionNumber }!!.first + 1
                    else
                        actionNumber++
                } catch (e: IndexOutOfBoundsException) {
                    throw IllegalStateException("Operation on an non-existent cell commit")
                }
            }
            else -> throw IllegalArgumentException("Illegal symbol in commands")
        }
        actionsDone++
    }
    return answer.toList()
}
