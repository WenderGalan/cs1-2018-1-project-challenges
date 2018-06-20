//
//  DesafioDAO.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/9/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseStorage
import FirebaseFirestore

class DesafioDAO: NSObject {

    static let sharedInstance = DesafioDAO()
    
    let ref = Firestore.firestore().collection("Desafios")
    
    // get habilidadesdesa
    
    func getDesafiosPara(responsavelID: String, success: @escaping ([Desafio]) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = self.ref.whereField("responsavel", isEqualTo: responsavelID)
        
        childRef.getDocuments { (snapshot, error) in
            if let e = error {
                failed(e)
            } else {
                if let snap = snapshot {
                    var desafios = [Desafio]()
                    for doc in snap.documents {
                        let desafio = Desafio.init(document: doc)
                        desafio.objectId = doc.documentID
                        desafios.append(desafio)
                    }
                    success(desafios)
                }
            }
            
        }

    }
    
    func getDesafiosPara(criancaID: String, success: @escaping ([Desafio]) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = self.ref.whereField("crianca", isEqualTo: criancaID)
        
        childRef.getDocuments { (snapshot, error) in
            if let e = error {
                failed(e)
            } else {
                if let snap = snapshot {
                    var desafios = [Desafio]()
                    for doc in snap.documents {
                        let desafio = Desafio.init(document: doc)
                        desafio.objectId = doc.documentID
                        desafios.append(desafio)
                    }
                    success(desafios)
                }
            }
            
        }
    }
    
    func getDesafiosApp(habilidadeID: String, success: @escaping ([Desafio]) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = Firestore.firestore().collection("DesafioApp").whereField("habilidadeID", isEqualTo: "9geWwaRduSwkgNmi6NPw")
        
        childRef.getDocuments { (snapshot, error) in
            if let e = error {
                failed(e)
            } else {
                if let snap = snapshot {
                    var desafios = [Desafio]()
                    for doc in snap.documents {
                        let desafio = Desafio.init(document: doc)
                        desafio.objectId = doc.documentID
                        desafio.ref = Firestore.firestore().collection("DesafioApp")
                        desafios.append(desafio)
                    }
                    success(desafios)
                }
            }
            
        }
        
    }
    
}
