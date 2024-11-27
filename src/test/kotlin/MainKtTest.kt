import org.junit.Assert.*
import org.junit.Test

class MainKtTest {
    @Test
    //комиссия Mastercard на бесплатный месячный лимит
    fun testNoCommissionMastercard() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 50000.00, dailyTransferid = 30000.00,
            transferAmount = 10000.00
        )
        assertEquals(0.00, result, 0.00000001)
    }
    @Test
    //комиссия Mastercard на сумму, превышающую бесплатный месячный лимит
    fun testCommissionMastercard() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 50000.00, dailyTransferid = 50000.00,
            transferAmount = 5000.00
        )
        assertEquals(0.0, result, 0.00000001) // 5000*0.006 +20 (фактическая комиссия)
    }
    @Test
    // комиссия Mastercard на перевод, который превысил бесплатный лимит
    fun testCommissionMastercardExceededLimit() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 74000.00, dailyTransferid = 0.00,
            transferAmount = 4000.00
        )
        assertEquals(38.0, result, 0.00000001) // 4000 * 0.006 + 20 (фактическая комиссия)
    }

    @Test
    // комиссия Mastercard при переводе, где лимит месячного перевода будет превышен
    fun testCommissionMastercardMonthlyLimitExceeded() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 600000.00, dailyTransferid = 0.00,
            transferAmount = 1000.00
        )
        assertEquals(0.00, result, 0.00000001) // Превышен месячный лимит
    }

    @Test
    // комиссия Visa на сумму, превышающую минимальную комиссию
    fun testCommissionVisa() {
        val result = calculateCommission(
            cardType = "Visa", monthlyTransferid = 10000.00, dailyTransferid = 10000.00,
            transferAmount = 10000.00
        )
        assertEquals(75.0, result, 0.00000001) // 10000 * 0.0075 = 75.0
    }

    @Test
    // минимальная комиссия Visa, если сумма комиссии меньше 35 рублей
    fun testMinimumCommissionVisa() {
        val result = calculateCommission(
            cardType = "Visa", monthlyTransferid = 10000.00, dailyTransferid = 10000.00,
            transferAmount = 1000.00
        )
        assertEquals(75.0, result, 0.00000001) // Комиссия не может быть меньше 35 рублей
    }

    @Test
    // комиссия для карты Мир (должна быть 0)
    fun testCommissionMir() {
        val result = calculateCommission(
            cardType = "Мир", monthlyTransferid = 50000.00, dailyTransferid = 30000.00,
            transferAmount = 10000.00
        )
        assertEquals(0.00, result, 0.00000001) // Комиссия для карты Мир всегда 0
    }

    @Test
    // блокировка операции при превышении дневного лимита
    fun testDailyLimitExceeded() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 50000.00, dailyTransferid = 151000.00,
            transferAmount = 10000.00
        )
        assertEquals(0.00, result, 0.00000001) // Превышен дневной лимит
    }

    @Test
    // блокировка операции при превышении месячного лимита
    fun testMonthlyLimitExceeded() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 600000.00, dailyTransferid = 0.00,
            transferAmount = 10000.00
        )
        assertEquals(0.00, result, 0.00000001) // Превышен месячный лимит
    }

    @Test
    // блокировка операции при неподдерживаемом типе карты
    fun testUnsupportedCardType() {
        val result = calculateCommission(
            cardType = "UnsupportedCard", monthlyTransferid = 0.00, dailyTransferid = 0.00,
            transferAmount = 10000.00
        )
        assertEquals(0.00, result, 0.00000001) // Неподдерживаемый тип карты
    }
}