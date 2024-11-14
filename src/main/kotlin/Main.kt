const val DAILY_LIMIT = 150000
const val MONTHLY_LIMIT = 600000
const val MASTERCARD_MONTHLY_FREE_LIMIT = 75000
const val MASTERCARD_COMMISSION_RATE = 0.006
const val MASTERCARD_FIXED_COMMISSION = 20.0
const val VISA_COMMISSION_RATE: Double = 0.0075
const val VISA_MIN_COMMISSION = 35.0

fun main(){
    val result = calculateCommission("Mastercard", 0.0, 50000.0, 100000.0)
    println("Ваша комиссия составит $result")
}
fun calculateCommission(
    cardType: String, monthlyTransferid: Double, dailyTransferid: Double, transferAmount: Double
): Double {
    // прверяем дневной лимит (сумма перевода за день + текущий)
    if (dailyTransferid + transferAmount > DAILY_LIMIT) {
        println("Операция заблокированна, превышен дневной лимит перевода")
    }
    if (monthlyTransferid + transferAmount > MONTHLY_LIMIT) {
        println("Операция заблокированна, превышен месячный лимит перевода")
    }

    // Теперь расчитываем комисию в зависимости от типа карт
    return when (cardType) {
        "Mastercard" -> {  // TODO исправить, чтобы в минус не ухходило
            val totalAmount = monthlyTransferid + transferAmount
            if (totalAmount > MASTERCARD_MONTHLY_FREE_LIMIT) {
                if (monthlyTransferid < MASTERCARD_MONTHLY_FREE_LIMIT) {
                    val remainsLimit = MASTERCARD_MONTHLY_FREE_LIMIT - monthlyTransferid
                    (totalAmount - remainsLimit) * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_COMMISSION
                } else ((totalAmount - monthlyTransferid) - MASTERCARD_MONTHLY_FREE_LIMIT) * MASTERCARD_COMMISSION_RATE + MASTERCARD_FIXED_COMMISSION
            } else 0.0

        }
        "Visa" -> {
            val calculatedCommission = transferAmount * VISA_COMMISSION_RATE
            if (calculatedCommission < VISA_MIN_COMMISSION) VISA_MIN_COMMISSION
             else calculatedCommission
        }
        "Мир" -> 0.0

        else -> {
            println("Тип карты не поддерживается")
            0.0
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
