package br.edu.ifpb.pdm.liguei.model

import java.io.Serializable

class Contato() : Serializable{
    var numero: String = ""
    var nome: String = ""
    var descricao: String = "Descrição do contato de emergência"

    override fun toString(): String {
        return "Contato: '$nome' - '$numero'"
    }

}