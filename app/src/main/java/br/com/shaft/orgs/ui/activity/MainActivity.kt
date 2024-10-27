package br.com.shaft.orgs.ui.activity

import android.app.Activity
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
import java.math.BigDecimal

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val recyclerView = findViewById<RecyclerView>(recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val dao = ProdutosDao()
        recyclerView.adapter = ListaProdutosAdapter(this, produtos = dao.buscaTodos())
    }

    class ListaProdutosAdapter(
        private val context: Context,
        private val produtos: List<Produto>
    ) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaProdutosAdapter.ViewHolder {

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.produto_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = produtos.count()

        override fun onBindViewHolder(holder: ListaProdutosAdapter.ViewHolder, position: Int) {
            val produto = produtos[position]
            holder.vincula(produto)
        }

    }
}