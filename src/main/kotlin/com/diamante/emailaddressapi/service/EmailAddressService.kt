package com.diamante.emailaddressapi.service

import org.springframework.stereotype.Service

@Service
class EmailAddressService {

    fun filterEmails(emailAddresses: List<String>): List<String> {
        return emailAddresses
                .filter { it.contains("@") }
                .map { emailAddress ->
                    emailAddress
                            .ignoreDecimalInUsername()
                            .addDomainExtensionBack(emailAddress)
                            .ignoreTextAfterPlus()
                            .addDomainExtensionBack(emailAddress)
                }
    }

    fun getCount(emailAddresses: List<String>): Int {
        return filterEmails(emailAddresses).toSet().size
    }

    fun String.ignoreDecimalInUsername(): String {
        return split('@')[0]
                .replace(".", "")
                .plus('@')
                .plus(this.split('@')[1])
    }

    fun String.ignoreTextAfterPlus(): String {
        return split('@')[0]
                .split("+")[0]
    }

    fun String.addDomainExtensionBack(originalEmailAddress: String): String {
        return plus('@')
                .plus(originalEmailAddress.split("@")[1])
    }

}