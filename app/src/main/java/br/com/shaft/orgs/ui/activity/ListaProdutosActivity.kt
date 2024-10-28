package br.com.shaft.orgs.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.shaft.orgs.R
import br.com.shaft.orgs.dao.ProdutosDao
import br.com.shaft.orgs.databinding.ActivityListaProdutosBinding
import br.com.shaft.orgs.databinding.ProdutoItemBinding
import br.com.shaft.orgs.extensions.formataParaMoedaBrasileira
import br.com.shaft.orgs.extensions.tentaCarregarImagem
import br.com.shaft.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ListaProdutosActivity: AppCompatActivity(R.layout.activity_lista_produtos) {

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        ProdutosDao()
    }

    private val adapter =
        ListaProdutosAdapter(this, produtos = dao.buscaTodos())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraFab()
        configuraRecyclerView()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listaProdutosRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItemListener = { produto ->
            vaiParaDetalhesProduto(produto)
        }
    }

    private fun configuraFab() {
        val fab = binding.floatingActionButton
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun vaiParaDetalhesProduto(produto: Produto) {
        val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
            putExtra("produto", produto)
        }
        startActivity(intent)
    }

    class ListaProdutosAdapter(
        private val context: Context,
        produtos: List<Produto> = listOf(),
        var quandoClicaNoItemListener: (produto: Produto) -> Unit = {}
    ) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

        private val produtos = produtos.toMutableList()

        inner class ViewHolder(binding: ProdutoItemBinding): RecyclerView.ViewHolder(binding.root) {

            private lateinit var produto: Produto

            init {
                itemView.setOnClickListener {
                    if(::produto.isInitialized) {
                        quandoClicaNoItemListener(produto)
                    }
                }
            }

            private val nome = binding.produtoItemNome
            private val descricao = binding.produtoItemDescricao
            private val valor = binding.produtoItemValor
            private val imageView = binding.produtoItemImagem

            fun vincula(produto: Produto) {
                this.produto = produto
                nome.text = produto.nome
                descricao.text = produto.descricao
                valor.text = produto.valor.formataParaMoedaBrasileira()

                imageView.tentaCarregarImagem(produto.imagem)
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int = produtos.count()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val produto = produtos[position]
            holder.vincula(produto)
        }

        fun atualiza(produtos: List<Produto>) {
            this.produtos.clear()
            this.produtos.addAll(produtos)
            notifyDataSetChanged()
        }

    }
}