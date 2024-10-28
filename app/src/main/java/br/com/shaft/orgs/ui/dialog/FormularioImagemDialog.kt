package br.com.shaft.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.shaft.orgs.databinding.FormularioImagemBinding
import br.com.shaft.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {
    fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (imagem: String?) -> Unit) {
        val binding = FormularioImagemBinding.inflate(LayoutInflater.from(context))

        urlPadrao?.let {
            binding.formluarioImagemUrl.setText(it)
            binding.formularioImagemImageView.tentaCarregarImagem(it)
        }

        binding.formularioImagemBotaoCarregar.setOnClickListener {
            val url = binding.formluarioImagemUrl.text.toString()
            binding.formularioImagemImageView.tentaCarregarImagem(url)
        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar", { _, _ ->
                val url = binding.formluarioImagemUrl.text.toString()
                quandoImagemCarregada(url)
            })
            .setNegativeButton("Cancelar", { _, _ ->
                println("did tap on cancelar")
            })
            .show()
    }
}