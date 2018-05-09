//
//  Desafio.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseFirestore

class Desafio: FirestoreObject {

    var nome: String?
    var crianca: Crianca?
    var ajudante: Crianca?
    var responsavel: Responsavel?
    var dataCriacao: Date?
    var dataUpdate: Date?
    var recompensa: String?
    var pontos: Int?
    var habilidade: Habilidade?
    var frequencia: String?
    var repeticoes: Int?
    var observacoes: String?
    
    override init() {
        super.init()
        ref = Firestore.firestore().collection("Desafios")
    }
    
    convenience init(document: DocumentSnapshot) {
        self.init()
        self.from(document: document)
    }
    
    override func toDictionary() -> Dictionary<String, Any> {
        var data = [String : Any]()
        
        if let nome = nome {
            data["nome"] = nome
        }
        
        if let crianca = crianca {
            data["crianca"] = crianca.objectId
        }
        
        if let ajudante = ajudante {
            data["ajudante"] = ajudante.objectId
        }
        
        if let responsavel = responsavel {
            data["responsavel"] = responsavel.objectId
        }
        
        if let dataCriacao = dataCriacao {
            data["dataCriacao"] = dataCriacao.timeIntervalSince1970 * 1000
            
            let dateFormatter = DateFormatter.init()
            dateFormatter.dateFormat = "dd/MM/YYYY"
            
            data["data"] = dateFormatter.string(from: dataCriacao)
            
            dateFormatter.dateFormat = "HH:mm"
            data["hora"] = dateFormatter.string(from: dataCriacao)
        }
        
        if let dataUpdate = dataUpdate {
            data["dataUpdate"] = dataUpdate
        }
        
        if let recompensa = recompensa {
            data["recompensa"] = recompensa
        }
        
        if let habilidade = habilidade {
            data["habilidade"] = habilidade.nome?.lowercased()
//            data["habilidadeID"] = habilidade.objectId
        }
        
        if let frequencia = frequencia {
            data["frequencia"] = frequencia
        }
        
        if let repeticoes = repeticoes {
            data["repeticoes"] = repeticoes
        }
        if let pontos = pontos {
            data["pontos"] = pontos
        }
        
        if let observacoes = observacoes {
            data["observacoes"] = observacoes
        }
        
        return data
    }
    
    override func from(document: DocumentSnapshot) {
        guard let data = document.data() else {
            return
        }
        
        if let nome = data["nome"] as? String {
            self.nome = nome
        }
        
        if let crianca = data["crianca"] as? String {
            self.crianca = Crianca.init(objectId: crianca)
        }
        
        if let ajudante = data["ajudante"] as? String {
            self.ajudante = Crianca.init(objectId: ajudante)
        }
        
        if let responsavel = data["responsavel"] as? String {
            self.responsavel = Responsavel.init(objectId: responsavel)
        }
        
        if let dataCriacao = data["dataCriacao"] as? TimeInterval {
            self.dataCriacao = Date.init(timeIntervalSince1970: dataCriacao / 1000)
        }
        
        if let dataUpdate = data["dataUpdate"] as? TimeInterval {
            self.dataUpdate = Date.init(timeIntervalSince1970: dataUpdate / 1000)
        }
        
        if let recompensa = data["recompensa"] as? String {
            self.recompensa = recompensa
        }
        
        if let pontos = data["pontos"] as? Int {
            self.pontos = pontos
        }
        
        self.habilidade = Habilidade.init()
        if let habilidadeID = data["habilidadeID"] as? String {
            self.habilidade?.objectId = habilidadeID
        }
        
        if let habilidade = data["habilidade"] as? String {
            self.habilidade?.nome = habilidade
            self.habilidade?.setIcones()
        }
        
        if let frequencia = data["frequencia"] as? String {
            self.frequencia = frequencia
        }
        
        if let repeticoes = data["repeticoes"] as? Int {
            self.repeticoes = repeticoes
        }
        
        if let observacoes = data["observacoes"] as? String {
            self.observacoes = observacoes
        }
    }
}
