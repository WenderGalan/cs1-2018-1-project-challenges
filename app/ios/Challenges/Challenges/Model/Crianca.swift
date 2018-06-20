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
    lazy var amigos: [Crianca] = [Crianca]()
    lazy var pontos = 0
    lazy var desafios = 0
    lazy var recompensas = 0

    static var sharedInstance: Crianca? = {
        
        if let currentUser = Auth.auth().currentUser {
            let sharedInstance = Crianca.init(objectId: currentUser.uid)
            return sharedInstance
        }
        
        let sharedInstance = Crianca.init()
        return sharedInstance
    }()
    
    override init() {
        super.init()
    }
    
    override init(objectId: String) {
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
            data["responsavel"] = ref!.document(responsavel.objectId!)
        }
        
        if let habilidade = habilidade {
            data["habilidade"] = habilidade
        }
        
        
        data["pontos"] = pontos
        data["desafios"] = desafios
        data["recompensas"] = recompensas

        
        data["tipo"] = NSNumber(integerLiteral: 1)

        if amigos.count > 0 {
            var amigosRef: [DocumentReference] = [DocumentReference]()
            for amigo in amigos {
                amigosRef.append((amigo.ref?.document(amigo.objectId!))!)
            }
            data["amigos"] = amigosRef
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
        
        if let pontos = data["pontos"] as? Int {
            self.pontos = pontos
        }
        
        if let desafios = data["desafios"] as? Int {
            self.desafios = desafios
        }
        
        if let recompensas = data["recompensas"] as? Int {
            self.recompensas = recompensas
        }
        
        if let tipo = data["tipo"] as? NSNumber {
            self.tipo = tipo
        }
    }
}
