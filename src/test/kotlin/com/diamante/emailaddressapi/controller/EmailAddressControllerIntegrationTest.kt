package com.diamante.emailaddressapi.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class EmailAddressControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `getEmailAddressCount returns the number of unique email accounts`() {
        val emailAddressesJson = "{ \"emailAddresses\": [\"steven.diamante+spam@gmail.com\", \"bob.smith@gmail.com\", \"susanyang+something@gmail.com\",\n" +
                "\"bob.smith+tank@gmail.com\", \"susanyang@gmail.com\", \"stevendiamante@gmail.com\", \"al.ex.new.man+spam@gmail.com\", \"alexnewman@gmail.com\"] }"

        val expectedResponseJson = "{ \"emailAddressCount\": 4}"

        mockMvc.perform(MockMvcRequestBuilders.post("/email/count")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(emailAddressesJson))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(expectedResponseJson))
    }

    @Test
    fun `getEmailAddressCount ignores email addresses that do not contain an @ sign`() {
        val emailAddressesJson = "{ \"emailAddresses\": [\"steven.diamante+spam@gmail.com\", \"bob.smith@gmail.com\", \"susanyang+something_gmail.com\"] }"

        val expectedResponseJson = "{ \"emailAddressCount\": 2}"


        mockMvc.perform(MockMvcRequestBuilders.post("/email/count")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(emailAddressesJson))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(expectedResponseJson))
    }
}
