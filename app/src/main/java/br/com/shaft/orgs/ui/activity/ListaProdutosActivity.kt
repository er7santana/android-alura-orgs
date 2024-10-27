package br.com.shaft.orgs.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.shaft.orgs.R
import br.com.shaft.orgs.R.id.recycler_view
import br.com.shaft.orgs.dao.ProdutosDao
import br.com.shaft.orgs.model.Produto
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity: AppCompatActivity(R.layout.activity_lista_produtos) {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(this, produtos = dao.buscaTodos())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configuraFab()
        configuraRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun configuraFab() {
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
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
        produtos: List<Produto>
    ) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

        private val produtos = produtos.toMutableList()

        class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            fun vincula(produto: Produto) {
                val nome = itemView.findViewById<TextView>(R.id.nome)
                nome.text = produto.nome

                val descricao = itemView.findViewById<TextView>(R.id.descricao)
                descricao.text = produto.descricao

                val valor = itemView.findViewById<TextView>(R.id.valor)
                valor.text = produto.valor.toPlainString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.produto_item, parent, false)
            return ViewHolder(view)
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