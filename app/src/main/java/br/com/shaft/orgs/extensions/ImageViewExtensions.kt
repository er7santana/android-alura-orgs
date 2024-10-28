package br.com.shaft.orgs.extensions

import android.widget.ImageView
import br.com.shaft.orgs.R
import coil3.load
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder

fun ImageView.tentaCarregarImagem(url: String? = null) {
    load(url) {
        error(R.drawable.erro)
        fallback(R.drawable.imagem_padrao)
        placeholder(R.drawable.placeholder)
    }
}