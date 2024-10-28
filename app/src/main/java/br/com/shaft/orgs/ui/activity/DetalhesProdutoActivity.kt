package br.com.shaft.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.shaft.orgs.R
import br.com.shaft.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.shaft.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.shaft.orgs.extensions.formataParaMoedaBrasileira
import br.com.shaft.orgs.extensions.tentaCarregarImagem
import br.com.shaft.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val produto = intent.getParcelableExtra<Produto>("produto")
        title = "Detalhes de produto"
        setContentView(binding.root)
        binding.detalhesProdutoNome.text = produto?.nome
        binding.detalhesProdutoDescricao.text = produto?.descricao
        binding.detalhesProdutoValor.text = produto?.valor?.formataParaMoedaBrasileira()
        binding.detalhesProdutoImageView.tentaCarregarImagem(produto?.imagem)
    }
}