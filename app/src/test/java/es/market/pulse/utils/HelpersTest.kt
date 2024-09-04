package es.market.pulse.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test


class HelpersTest {

    @Test
    fun testHashPassword_success() {
        // Dado
        val password = "mypassword"

        // Cuando
        val hashedPassword = Helpers.hashPassword(password)

        // Entonces
        assertNotNull(hashedPassword)
        assertEquals(64, hashedPassword.length) // Verifica que la longitud del hash es 64 (SHA-256 produce un hash de 256 bits = 64 caracteres en hexadecimal)
    }

    @Test
    fun testVerifyPassword_success() {
        // Dado
        val password = "mypassword"
        val hashedPassword = Helpers.hashPassword(password)

        // Cuando
        val result = Helpers.verifyPassword(password, hashedPassword)

        // Entonces
        assertTrue(result) // Verifica que la contraseña coincida con el hash generado
    }

    @Test
    fun testVerifyPassword_failure() {
        // Dado
        val password = "mypassword"
        val wrongPassword = "wrongpassword"
        val hashedPassword = Helpers.hashPassword(password)

        // Cuando
        val result = Helpers.verifyPassword(wrongPassword, hashedPassword)

        // Entonces
        assertFalse(result) // Verifica que una contraseña incorrecta no coincida con el hash generado
    }
}
