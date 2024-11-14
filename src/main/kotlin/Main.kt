    const val COMMISSION_RATE: Double = 0.0075
    const val MIN_COMMISSION = 35.0
    const val MASTERCARD_LIMIT = 75000
    const val MASTERCARD_COMMISSION_RATE = 0.006
    const val MASTERCARD_EXTRA_FEE = 20.0
    const val DAILY_LIMIT = 150000
    const val MONTHLY_LIMIT = 600000

        fun main() {

        val amount = 150000
        var dailyTransferid = 50000
        var monthlyTransferid = 400000
        val typeCard = "Мир"
            if (dailyTransferid + amount > DAILY_LIMIT){
                println("Ошибка: Превышен дневной лимит перевода")
                return
            }
            if (monthlyTransferid + amount > MONTHLY_LIMIT){
                println("Ошибка: Превышен месячный лимит перевода")
                return
            }
        val commission = when (typeCard) {
            "Mastercard" -> {
                val totalSum = amount + dailyTransferid
                if (totalSum > MASTERCARD_LIMIT) {
                    (totalSum - MASTERCARD_LIMIT) * MASTERCARD_COMMISSION_RATE + MASTERCARD_EXTRA_FEE
                } else 0.0

            }
            "Visa" -> {
                val calculatedCommission = amount * COMMISSION_RATE
                if (calculatedCommission < MIN_COMMISSION)
                    MIN_COMMISSION
                else calculatedCommission
            }
            "Мир" -> 0.0
            else -> {
                println("Неизвестный тип карты")
                return
            }
        }
            // обновим суммы перевода за день и за месяц
            dailyTransferid += amount
            monthlyTransferid += amount
            println("Комиссия: $commission рублей")
            println("Общая сумма переводов за сегодня $dailyTransferid рублей")
            println("Общая сумма переводов за месяц $monthlyTransferid рублей")
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
