package br.com.shaft.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.shaft.orgs.R
import br.com.shaft.orgs.dao.ProdutosDao
import br.com.shaft.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.shaft.orgs.extensions.tentaCarregarImagem
import br.com.shaft.orgs.model.Produto
import br.com.shaft.orgs.ui.dialog.FormularioImagemDialog
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

        title = "Cadastrar produto"
        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.formularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.formularioProdutoImagem.tentaCarregarImagem(url)
            }
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