//
//  Responsavel.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/30/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import Firebase
import FirebaseFirestore
import FirebaseAuth

class Responsavel: Usuario {
    var criancas: [DocumentReference]?
    
    private static var sharedInstance: Responsavel? = {
        
        if let currentUser = Auth.auth().currentUser {
            let sharedInstance = Responsavel.init(objectId: currentUser.uid)
            return sharedInstance
        }
        
        return Responsavel.init()
    }()
    
    private override init() {
        super.init()
        setReference()
    }
    
    private override init(objectId: String) {
        super.init(objectId: objectId)
        setReference()
    }
    
    class func sharedUser() -> Responsavel? {
        return sharedInstance
    }
    
    func setReference() {
        ref = db.collection("Usuarios")
    }
    
//    func save() {
//        if ref == nil {
//            setReference()
//        }
//
//        if objectId != nil {
//            ref!.document(objectId!).updateData(toDictionary())
//        } else {
//            ref!.addDocument(data: toDictionary())
//        }
//    }
//
//    func delete() {
//        if ref == nil {
//            setReference()
//        }
//
//        if let uid = objectId {
//            ref!.document(uid).delete()
//        }
//    }
    
    override func toDictionary() -> Dictionary<String, Any> {
        var data = [String : Any]()
        
        if let nome = nome {
            data["nome"] = nome
        }
        
        if let email = email {
            data["email"] = email
        }
        
        if let criancas = criancas {
            data["criancas"] = criancas
        }
        
        data["tipo"] = NSNumber(integerLiteral: 0)
        
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
        
        if let criancas = data["criancas"] as? [DocumentReference] {
            self.criancas = criancas
        }
        
        if let tipo = data["tipo"] as? NSNumber {
            self.tipo = tipo
        }
    }
    
    
    
}
