package com.lightspark

fun isDomainLocalhost(domain: String): Boolean {
    val domainWithoutPort = domain.split(":")[0]
    val tld = domainWithoutPort.split(".").last()
    return domainWithoutPort == "localhost" ||
        tld == "local" ||
        tld == "internal" ||
        domainWithoutPort == "127.0.0.1"
}
