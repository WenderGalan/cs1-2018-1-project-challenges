//
//  Usuario.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseAuth
import FirebaseFirestore

class Usuario: FirestoreObject, FirestoreService {

    var nome: String?
    var email: String?
    var tipo: NSNumber?
    var fotoURL: String?
    var responsavel: DocumentReference?
    var habilidade: DocumentReference?
    var amigos: [DocumentReference]?
    var criancas: [DocumentReference]?
    var pontos: NSNumber?
    
    private static var sharedInstance: Usuario? = {
        
        if let currentUser = Auth.auth().currentUser {
            let sharedInstance = Usuario.init(objectId: currentUser.uid)
            return sharedInstance
        }
        
        return Usuario.init()
    }()
    
    private override init() {
        super.init()
    }
    
    private override init(objectId: String) {
        super.init(objectId: objectId)
        setReference()
    }
    
    class func sharedUser() -> Usuario? {
        return sharedInstance
    }
    
    func setReference() {
        ref = db.collection("Usuarios")
    }
    
    func save() {
        if ref == nil {
            setReference()
        }
        
        if objectId != nil {
            ref!.document(objectId!).updateData(toDictionary())
        } else {
            ref!.addDocument(data: toDictionary())
        }
    }
    
    func delete() {
        if ref == nil {
            setReference()
        }
        
        if let uid = objectId {
            ref!.document(uid).delete()
        }
    }
    
    func toDictionary() -> Dictionary<String, Any> {
        var data = [String : Any]()
        
        if let nome = nome {
            data["nome"] = nome
        }
        
        if let email = email {
            data["email"] = email
        }
        
        if let tipo = tipo {
            data["tipo"] = tipo
        }
        
        if let fotoURL = fotoURL {
            data["fotoURL"] = fotoURL
        }
        
        if let responsavel = responsavel {
            data["responsavel"] = responsavel
        }
        
        if let habilidade = habilidade {
            data["habilidade"] = habilidade
        }
        
        if let amigos = amigos {
            data["amigos"] = amigos
        }
        
        if let criancas = criancas {
            data["criancas"] = criancas
        }
        
        if let pontos = pontos {
            data["pontos"] = pontos
        }

        return data
    }
    
    func from(document: DocumentSnapshot) {
        guard let data = document.data() else {
            return
        }
        
        if let nome = data["nome"] as? String {
            self.nome = nome
        }
        
        if let email = data["email"] as? String {
            self.email = email
        }
        
        if let tipo = data["tipo"] as? NSNumber {
            self.tipo = tipo
        }
        
        if let fotoURL = data["fotoURL"] as? String {
            self.fotoURL = fotoURL
        }
        
        if let responsavel = data["responsavel"] as? DocumentReference {
            self.responsavel = responsavel
        }
        
        if let habilidade = data["habilidade"] as? DocumentReference {
            self.habilidade = habilidade
        }
        
        if let amigos = data["amigos"] as? [DocumentReference] {
            self.amigos = amigos
        }
        
        if let criancas = data["criancas"] as? [DocumentReference] {
            self.criancas = criancas
        }
        
        if let pontos = data["pontos"] as? NSNumber {
            self.pontos = pontos
        }
    }

}
