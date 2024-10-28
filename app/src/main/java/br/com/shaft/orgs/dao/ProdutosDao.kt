package br.com.shaft.orgs.dao

import br.com.shaft.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto("Salada de frutas", "Laranja, maçãs e uva", BigDecimal(10.99))
        )
    }
}