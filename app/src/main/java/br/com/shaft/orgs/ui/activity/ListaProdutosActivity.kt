package br.com.shaft.orgs.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.shaft.orgs.R
import br.com.shaft.orgs.dao.ProdutosDao
import br.com.shaft.orgs.databinding.ActivityListaProdutosBinding
import br.com.shaft.orgs.databinding.ProdutoItemBinding
import br.com.shaft.orgs.model.Produto
import coil3.load
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

    private val adapter by lazy {
        ListaProdutosAdapter(this, produtos = dao.buscaTodos())
    }

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

    class ListaProdutosAdapter(
        private val context: Context,
        produtos: List<Produto> = listOf()
    ) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

        private val produtos = produtos.toMutableList()

        class ViewHolder(binding: ProdutoItemBinding): RecyclerView.ViewHolder(binding.root) {

            private val nome = binding.produtoItemNome
            private val descricao = binding.produtoItemDescricao
            private val valor = binding.produtoItemValor
            private val imageView = binding.produtoItemImagem

            fun vincula(produto: Produto) {
                nome.text = produto.nome
                descricao.text = produto.descricao
                valor.text = formataParaMoedaBrasileira(produto.valor)

                if (produto.imagem != null) {
                    imageView.load(produto.imagem)
                } else {
                    imageView.load(R.drawable.imagem_padrao)
                }
            }

            private fun formataParaMoedaBrasileira(valor: BigDecimal): String? {
                val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                val valorEmMoeda = formatador.format(valor)
                return valorEmMoeda
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