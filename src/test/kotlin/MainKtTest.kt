import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

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
    //комиссия Mastercard на бесплатный месячный лимит
    fun testCommissionMastercard() {
        val result = calculateCommission(
            cardType = "Mastercard", monthlyTransferid = 10000.00, dailyTransferid = 30000.00,
            transferAmount = 100000.00
        )
        assertEquals(290.0, result)
    }

}