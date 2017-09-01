package br.edu.ifpb.pdm.liguei.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.edu.ifpb.pdm.liguei.model.Contato

class ContatoDAO(context: Context) {

    var banco: SQLiteDatabase

    init {
        this.banco = DatabaseHelper(context).writableDatabase
    }

    fun insert(contato: Contato){
        var cv : ContentValues = ContentValues()
        cv.put("numero", contato.numero)
        cv.put("nome", contato.nome)
        cv.put("descricao", contato.descricao)
        this.banco.insert(DatabaseHelper.TB_NAME, null, cv)
    }

    fun get(index: Int) : Contato = this.get()[index]

    fun get() : List<Contato> {
        var lista = ArrayList<Contato>()
        val colunas = arrayOf("numero","nome","descricao")

        val c = this.banco.query(DatabaseHelper.TB_NAME, colunas, null, null, null, null, null)

        var contatoTemp : Contato

        if(c.count > 0) {

            c.moveToFirst()

            do {
                contatoTemp = Contato()
                contatoTemp.numero = c.getString(c.getColumnIndex("numero"))
                contatoTemp.nome = c.getString(c.getColumnIndex("nome"))
                contatoTemp.descricao = c.getString(c.getColumnIndex("descricao"))

                lista.add(contatoTemp)

            } while(c.moveToNext())
        }

        return lista
    }

    fun size() : Int{
        val colunas = arrayOf("numero","nome","descricao")
        val c = this.banco.query(DatabaseHelper.TB_NAME, colunas, null, null, null, null, null)
        return c.count
    }

    fun update(original: Contato, alterado: Contato) {
       var cv = ContentValues()
        cv.put("numero", alterado.numero)
        cv.put("nome", alterado.nome)
        cv.put("descricao", alterado.descricao)

        val where = arrayOf(original.numero)

        this.banco.update(DatabaseHelper.TB_NAME, cv, "numero = ?", where)
    }

    fun delete(contato: Contato){
        val where = arrayOf(contato.numero)
        this.banco.delete(DatabaseHelper.TB_NAME, "numero = ?", where)
    }
}
