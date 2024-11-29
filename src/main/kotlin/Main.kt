const val DAILY_LIMIT = 150000 //дневной лимит
const val MONTHLY_LIMIT = 600000  //месячный лимит
const val MASTERCARD_MONTHLY_FREE_LIMIT = 75000  // безпроцентный месячный лимит на Mastercard
const val MASTERCARD_COMMISSION_RATE = 0.006  // комиссия Mastercard на сумму превышающую 75000
const val MASTERCARD_FIXED_COMMISSION = 20.0  // комиссия Mastercard на сумму превышающую 75000
const val VISA_COMMISSION_RATE: Double = 0.0075  // комиссия Visa, минимальная сумма комиссии 35 руб.
const val VISA_MIN_COMMISSION = 35.0    //мин. комиссия  Visa


fun main() {
    val result = calculateCommission("Mastercard", 0.0, 0.0, 150000.0)
    println("Ваша комиссия составит $result")
}

fun calculateCommission(
    cardType: String, monthlyTransferid: Double, dailyTransferid: Double, transferAmount: Double
): Pair<Double, String> {
    // прверяем дневной лимит (сумма перевода за день + текущий)
    if (dailyTransferid + transferAmount > DAILY_LIMIT) {
        return Pair (0.0, "Операция заблокированна, превышен дневной лимит перевода")
    }
    if (monthlyTransferid + transferAmount > MONTHLY_LIMIT) {
        return Pair(0.0, "Операция заблокированна, превышен месячный лимит перевода")
    }
    return when (cardType) {
        // Теперь расчитываем комисию в зависимости от типа карт
        "Mastercard" -> {
            val totalMonthlyTransfer = monthlyTransferid + transferAmount // сумма перевода с историей
            val commission =
             when {
                totalMonthlyTransfer <= MASTERCARD_MONTHLY_FREE_LIMIT ->
                    0.0 // комиссия не взимается
                monthlyTransferid >= MASTERCARD_MONTHLY_FREE_LIMIT -> {
                    //лимит уже превышен, комиссия на весь текущий перевод
                    transferAmount * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_COMMISSION
                }
                else -> {
                    // лимит будет превышен текущим переводом
                    val taxablAmount = totalMonthlyTransfer - MASTERCARD_MONTHLY_FREE_LIMIT
                    taxablAmount * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_COMMISSION
                }
            }
            Pair (commission, "Комиссия для Mastercard:$commission")
        }
        "Visa" -> {
            val calculatedCommission = transferAmount * VISA_COMMISSION_RATE
                val commission =
            if (calculatedCommission < VISA_MIN_COMMISSION) VISA_MIN_COMMISSION
            else
            calculatedCommission
            Pair (commission, "Комиссия для Visa:$commission")
        }
        "Мир" -> {
            Pair (0.0, "Комиссия для Мир: 0.0")
        }

        // если карта не известна, блокируем операцию
        else -> {
            Pair (0.0, "Тип карты не поддерживается.")
        }
    }
}


/*
За переводы с карты Mastercard комиссия не взимается, пока не превышен месячный лимит в 75 000 руб.
Если лимит превышен, комиссия составит 0,6% + 20 руб.
За переводы с карты Visa комиссия составит 0,75%, минимальная сумма комиссии 35 руб.
За переводы с карты Мир комиссия не взимается.
Кроме того, введём лимиты на суммы перевода за сутки и за месяц. Максимальная сумма перевода с одной карты:

150 000 руб. в сутки
600 000 руб. в месяц
Комиссия в лимитах не учитывается.

Т. е. если пользователь решит перевести матери 150 000 руб. с карты Mastercard впервые за месяц, то его мать
получит всю сумму, а комиссия будет удержана сверх этого. Сумма комиссии составит 75 000 * 0,006 + 20 = 470 руб.
 (т. к. с первых 75 000 руб. комиссия не взимается).

Напишите алгоритм расчёта в виде функции, передавая в функцию:

тип карты (по умолчанию Мир);
сумму предыдущих переводов в этом месяце (по умолчанию 0 рублей);
сумму совершаемого перевода.
В случае превышения какого-либо из лимитов операция должна блокироваться.

Итог: у вас должен быть репозиторий на GitHub, в котором будет ваш Gradle-проект. */
