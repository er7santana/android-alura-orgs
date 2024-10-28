package br.com.shaft.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.formataParaMoedaBrasileira() : String? {
    val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    val valorEmMoeda = formatador.format(this)
    return valorEmMoeda
}