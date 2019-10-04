package com.diamante.emailaddressapi.controller

import com.diamante.emailaddressapi.service.EmailAddressService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ExtendWith(SpringExtension::class)
@WebMvcTest(EmailAddressController::class)
@ComponentScan(basePackages = ["com.diamante.emailaddressapi.service"])
class EmailAddressControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val mockEmailAddressService: EmailAddressService = mockk()

    private val emailAddresses = listOf(
            "steven.diamante+spam@gmail.com", "bob.smith@gmail.com", "susanyang+something@gmail.com",
            "bob.smith+tank@gmail.com", "susanyang@gmail.com", "stevendiamante@gmail.com")


    private val emailAddressesJson = "{ \"emailAddresses\": [\"steven.diamante+spam@gmail.com\", \"bob.smith@gmail.com\", \"susanyang+something@gmail.com\",\n" +
            "\"bob.smith+tank@gmail.com\", \"susanyang@gmail.com\", \"stevendiamante@gmail.com\"] }"

    private val expectedResponseJson = "{ \"emailAddressCount\": 3}"

    @Test
    fun `returns emailAddressCount of 3 when emails have decimals and pluses in a list of 6`() {
        every { mockEmailAddressService.getCount(emailAddresses) } returns 3

        mockMvc.perform(post("/email/count")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(emailAddressesJson))
                .andExpect(status().isOk)
                .andExpect(content().json(expectedResponseJson))
    }
}