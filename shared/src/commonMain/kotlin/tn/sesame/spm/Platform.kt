package tn.sesame.spm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform