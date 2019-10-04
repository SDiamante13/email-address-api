package com.diamante.emailaddressapi.controller

import com.diamante.emailaddressapi.model.Request
import com.diamante.emailaddressapi.model.Response
import com.diamante.emailaddressapi.service.EmailAddressService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailAddressController(val emailAddressService: EmailAddressService) {

    @PostMapping("/email/count")
    fun getEmailAddressCount(@RequestBody request: Request): Response {
        return Response(emailAddressService.getCount(request.emailAddresses))
    }

}