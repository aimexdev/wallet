package org.AimEx.infrastructure

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.AimEx.data.tokens.CurrentTokenProvider
import org.AimEx.data.tokens.Token

fun setCurrentToken(token: Token) {
    loadKoinModules(
            listOf(module(override = true) {
                single { CurrentTokenProvider(get()).apply {
                    setCurrent(token)
                } }
            })
    )
}