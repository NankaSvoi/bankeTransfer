    const val сommission: Double = 0.0075
        fun main(args: Array<String>) {

        val amount = 80000
        val history = 0


        val typeСard: String = "Mastercard"
        when (typeСard) {

            "Mastercard" -> {
                val totalSum = amount + history
                if (totalSum > 75000)  {
                   println ((totalSum - 75000) * 0.006 + 20)

            } else println(0)
        }

        }

        when (typeСard) {

            "Visa" -> {
        var sumCommission = amount * 0.0075
        if (sumCommission < 35) {
            sumCommission = 35.0
        }
        println("$sumCommission рублей")
            }
        }

        when (typeСard) {
            "Мир" -> {
                println("0.0 рублей")

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
    }