package br.com.shaft.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.shaft.orgs.R
import br.com.shaft.orgs.dao.ProdutosDao
import br.com.shaft.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.shaft.orgs.databinding.FormularioImagemBinding
import br.com.shaft.orgs.model.Produto
import coil3.load
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null

    private val dao by lazy {
        ProdutosDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.formularioProdutoImagem.setOnClickListener {

            val bindingFormularioImagem = FormularioImagemBinding.inflate(layoutInflater)
            bindingFormularioImagem.formularioImagemBotaoCarregar.setOnClickListener {
                val url = bindingFormularioImagem.formluarioImagemUrl.text.toString()
                bindingFormularioImagem.formularioImagemImageView.load(url)
            }

            AlertDialog.Builder(this)
                .setView(bindingFormularioImagem.root)
                .setPositiveButton("Confirmar", { _, _ ->
                    url = bindingFormularioImagem.formluarioImagemUrl.text.toString()
                    binding.formularioProdutoImagem.load(url)
                })
                .setNegativeButton("Cancelar", { _, _ ->
                    println("did tap on cancelar")
                })
                .show()
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.formularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adiciona(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.formularioProdutoNome
        val nome = campoNome.text.toString()

        val campoDescricao = binding.formularioProdutoDescricao
        val descricao = campoDescricao.text.toString()

        var campoValor = binding.formularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}