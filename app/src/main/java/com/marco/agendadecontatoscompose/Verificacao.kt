package com.marco.agendadecontatoscompose

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.marco.agendadecontatoscompose.dao.ContatoDao
import com.marco.agendadecontatoscompose.model.Contato
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var contatoDao: ContatoDao

class Verificacao(
    val listaDeContatos: MutableList<Contato> = mutableListOf()
) {

    fun criarContato(
        nome: String,
        sobreNome: String,
        idade: String,
        celular: String,
        context: Context
    ): Boolean {

        return if (nome.isEmpty() || sobreNome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
            false
        } else {
            val contato =
                Contato(nome = nome, sobrenome = sobreNome, idade = idade, celular = celular)
            listaDeContatos.add(contato)
            contatoDao = AppDatabase.getInstance(context).contatoDao()
            contatoDao.gravar(listaDeContatos)
            true
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun ReadContato(context: Context) {
        val scope = rememberCoroutineScope()
        scope.launch(Dispatchers.IO) {
            contatoDao = AppDatabase.getInstance(context).contatoDao()
            val contatos = contatoDao.getContatos()

            for (contato in contatos) {
                listaDeContatos.add(contato)
            }
        }
    }

    fun atualizarContato(
        uid: String,
        nome: String,
        sobreNome: String,
        idade: String,
        celular: String,
        context: Context
    ): Boolean {

        return if (nome.isEmpty() || sobreNome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
            false
        } else {
            val contato =
                Contato(nome = nome, sobrenome = sobreNome, idade = idade, celular = celular)
            listaDeContatos.add(contato)
            contatoDao = AppDatabase.getInstance(context).contatoDao()
            contatoDao.atualizar(uid.toInt(), nome, sobreNome, idade, celular)
            true
        }
    }
}