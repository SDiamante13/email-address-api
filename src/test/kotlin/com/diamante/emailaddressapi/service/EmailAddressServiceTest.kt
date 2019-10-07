package com.diamante.emailaddressapi.service

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Test

class EmailAddressServiceTest {

    private val emailAddressService: EmailAddressService = spyk(EmailAddressService())

    private val expectedEmailAddresses = listOf(
            "stevendiamante@gmail.com", "bobsmith@gmail.com", "susanyang@gmail.com",
            "bobsmith@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com")

    @Test
    fun `filterEmails will ignore the placement of decimal in the username email address`() {
        val emailAddresses = listOf(
                "steven.diamante@gmail.com", "bob.smith@gmail.com", "susan.yang@gmail.com",
                "bobsmith@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com")

        val result = emailAddressService.filterEmails(emailAddresses)

        assertThat(result).isEqualTo(expectedEmailAddresses)
    }

    @Test
    fun `filterEmails will ignore any portion of the username after +`() {
        val emailAddresses = listOf(
                "stevendiamante+spam@gmail.com", "bobsmith@gmail.com", "susanyang@gmail.com",
                "bobsmith@gmail.com", "susanyang+junk@gmail.com", "stevendiamante@gmail.com")

        val result = emailAddressService.filterEmails(emailAddresses)

        assertThat(result).isEqualTo(expectedEmailAddresses)
    }

    @Test
    fun `filterEmails will ignore + and decimals combined`() {
        val emailAddresses = listOf(
                "steven.diamante+spam@gmail.com", "bob.smith@gmail.com", "susanyang+something@gmail.com",
                "bob.smith+tank@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com")

        val result = emailAddressService.filterEmails(emailAddresses)

        assertThat(result).isEqualTo(expectedEmailAddresses)
    }

    @Test
    fun `filterEmails will drop any strings not containing @`() {
        val emailAddresses = listOf(
                "stevendiamante+gmail.com", "bobsmith@gmail.com")
        val expectedEmailAddress = listOf("bobsmith@gmail.com")

        val result = emailAddressService.filterEmails(emailAddresses)

        assertThat(result).isEqualTo(expectedEmailAddress)
    }

    @Test
    fun `getCount should return 3 when given a list of 3 email addresses with no duplications`() {
        val emailAddresses = listOf("steven@gmail.com", "bob@gmail.com", "susan@gmail.com")

        val result = emailAddressService.getCount(emailAddresses)

        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `getCount should return 3 when given a list of 5 emails with 2 duplications`() {
        val emailAddresses = listOf("steven@gmail.com", "bob@gmail.com", "susan@gmail.com", "bob@gmail.com", "susan@gmail.com")

        val result = emailAddressService.getCount(emailAddresses)

        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `getCount should return 3 when email addresses have decimals and pluses in them and are duplicated`() {
        val emailAddresses = listOf(
                "steven.diamante+spam@gmail.com", "bob.smith@gmail.com", "susanyang+something@gmail.com",
                "bob.smith+tank@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com")

        every { emailAddressService.filterEmails(emailAddresses) } returns expectedEmailAddresses

        val result = emailAddressService.getCount(emailAddresses)

        assertThat(result).isEqualTo(3)
    }
}