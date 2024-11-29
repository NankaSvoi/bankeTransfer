import org.junit.Assert.*
import org.junit.Test

class MainKtTest {

    @Test
    // прверяем дневной лимит (сумма перевода за день + текущий)
    fun testDailyLimitExceeded(){
        val result = calculateCommission (
            cardType = "Mastercard", monthlyTransferid = 20000.00, dailyTransferid = 140000.00,
            transferAmount = 20000.00
                )
        assertEquals (Pair(0.0, "Операция заблокированна, превышен дневной лимит перевода"), result)
    }
    @Test
    // прверяем месячный лимит (сумма перевода за месяц + текущий)
    fun testMonthlyLimitExceeded() {
        val result = calculateCommission(
            cardType = "Visa", monthlyTransferid = 600000.00, dailyTransferid = 0.00,
            transferAmount = 20000.00
        )
        assertEquals(Pair(0.0, "Операция заблокированна, превышен месячный лимит перевода"), result)
    }
    @Test
    //комиссия Mastercard на бесплатный месячный лимит
    fun testNoCommissionMastercard() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 50000.00, dailyTransferid = 30000.00,
            transferAmount = 10000.00
        )
        assertEquals(Pair(0.0, "Комиссия для Mastercard:0.0"),result)
    }
    @Test
    //комиссия Mastercard на сумму, превышающую бесплатный месячный лимит
    fun testCommissionMastercard() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 50000.00, dailyTransferid = 0.00,
            transferAmount = 50000.00
        )
        assertEquals(Pair(170.0, "Комиссия для Mastercard:170.0"), result) // 25000*0.006 +20 (фактическая комиссия)
    }
    @Test
    // комиссия Mastercard на перевод, который превысил бесплатный лимит
    fun testCommissionMastercardExceededLimit() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 74000.00, dailyTransferid = 0.00,
            transferAmount = 4000.00
        )
        assertEquals(Pair(38.0, "Комиссия для Mastercard:38.0" ), result) // 3000 * 0.006 + 20 (фактическая комиссия)
    }
    @Test
    //лимит уже превышен, комиссия на весь текущий перевод
    fun testCommissionMastercardEntireTransfer(){
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 100000.00, dailyTransferid = 0.00,
            transferAmount = 10000.00
        )
        assertEquals(Pair(80.0, "Комиссия для Mastercard:80.0" ), result)
    }

    @Test
    // комиссия Visa на сумму, превышающую минимальную комиссию
    fun testCommissionVisa() {
        val result = calculateCommission(
            cardType = "Visa", monthlyTransferid = 10000.00, dailyTransferid = 10000.00,
            transferAmount = 10000.00
        )
        assertEquals(Pair(75.0, "Комиссия для Visa:75.0" ),result) // 10000 * 0.0075 = 75.0
    }

    @Test
    // минимальная комиссия Visa, если сумма комиссии меньше 35 рублей
    fun testMinimumCommissionVisa() {
        val result = calculateCommission(
            cardType = "Visa", monthlyTransferid = 10000.00, dailyTransferid = 10000.00,
            transferAmount = 1000.00
        )
        assertEquals(Pair(35.0, "Комиссия для Visa:35.0"), result) // Комиссия не может быть меньше 35 рублей
    }

    @Test
    // комиссия для карты Мир (должна быть 0)
    fun testCommissionMir() {
        val result = calculateCommission(
            cardType = "Мир", monthlyTransferid = 50000.00, dailyTransferid = 30000.00,
            transferAmount = 10000.00
        )
        assertEquals(Pair(0.0, "Комиссия для Мир: 0.0"), result) // Комиссия для карты Мир всегда 0
    }

    @Test
    // блокировка операции при неподдерживаемом типе карты
    fun testUnsupportedCardType() {
        val result = calculateCommission(
            cardType = "UnsupportedCard", monthlyTransferid = 0.00, dailyTransferid = 0.00,
            transferAmount = 10000.00
        )
        assertEquals(Pair(0.00, "Тип карты не поддерживается."), result) // Неподдерживаемый тип карты
    }
}