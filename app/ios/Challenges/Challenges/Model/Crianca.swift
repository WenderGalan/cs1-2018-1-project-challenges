//
//  Crianca.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/30/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseAuth
import FirebaseFirestore

class Crianca: Usuario {

    var fotoURL: String?
    var responsavel: Responsavel?
    var habilidade: Habilidade?
    var amigos: [Crianca]?
    var pontos: NSNumber?
    
    private static var sharedInstance: Crianca? = {
        
        if let currentUser = Auth.auth().currentUser {
            let sharedInstance = Crianca.init(objectId: currentUser.uid)
            return sharedInstance
        }
        
        return Crianca.init()
    }()
    
    private override init() {
        super.init()
    }
    
    private override init(objectId: String) {
        super.init(objectId: objectId)
        setReference()
    }
    
    class func sharedUser() -> Crianca? {
        return sharedInstance
    }
    
    func setReference() {
        ref = db.collection("Usuarios")
    }
    
    override func toDictionary() -> Dictionary<String, Any> {
        var data = [String : Any]()
        
        if let nome = nome {
            data["nome"] = nome
        }
        
        if let email = email {
            data["email"] = email
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
        
        if let pontos = pontos {
            data["pontos"] = pontos
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
        
        if let email = data["email"] as? String {
            self.email = email
        }
        
        if let fotoURL = data["fotoURL"] as? String {
            self.fotoURL = fotoURL
        }
        
        if let responsavel = data["responsavel"] as? Responsavel {
            self.responsavel = responsavel
        }
        
        if let habilidade = data["habilidade"] as? Habilidade {
            self.habilidade = habilidade
        }
        
        if let amigos = data["amigos"] as? [Crianca] {
            self.amigos = amigos
        }
        
        if let pontos = data["pontos"] as? NSNumber {
            self.pontos = pontos
        }
    }
}
