//
//  NotificacoesDAO.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/14/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseFirestore

class NotificacoesDAO: NSObject {

    static let sharedInstance = NotificacoesDAO()
    
    let refCompleto = Firestore.firestore().collection("NotificacoesDesafioCompleto")
    let refApp = Firestore.firestore().collection("NotificacaoDesafioApp")
    
    // get habilidadesdesa
    
    func getNotificacoesDesafioCompleto(responsavelID: String, success: @escaping ([NotificacaoDesafio]) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = self.refCompleto.whereField("responsavel", isEqualTo: responsavelID)
        
        childRef.getDocuments { (snapshot, error) in
            if let e = error {
                failed(e)
            } else {
                if let snap = snapshot {
                    var notificacoes = [NotificacaoDesafio]()
                    for doc in snap.documents {
                        let notificacao = NotificacaoDesafio.init()
                        notificacao.objectId = doc.documentID
                        notificacao.ref = Firestore.firestore().collection("NotificacoesDesafioCompleto")
                        let data = doc.data()
                        let criancaRef = data["crianca"] as! DocumentReference
                        criancaRef.getDocument(completion: { (criancaSnap, errorCrianca) in
                            if let ec = errorCrianca {
                                print(ec.localizedDescription)
                            } else {
                                if let criancaSnap = criancaSnap {
                                    notificacao.crianca = Crianca.init(objectId: criancaSnap.documentID)
                                    notificacao.crianca?.from(document: criancaSnap)
                                }
                            }
                        })
                        
                        let desafioRef = data["desafio"] as! DocumentReference
                        desafioRef.getDocument(completion: { (desafioSnapshot, errorDesafio) in
                            if let ed = errorDesafio {
                                print(ed.localizedDescription)
                            } else {
                                if let desafioSnap = desafioSnapshot {
                                    notificacao.desafio = Desafio.init(document: desafioSnap)
                                    notificacao.desafio?.objectId = desafioSnap.documentID
                                }
                            }
                        })
                        
                        notificacoes.append(notificacao)
                    }
                    success(notificacoes)
                }
            }
            
        }
        
    }
    
    func getNotificacoesDesafioApp(responsavelID: String, success: @escaping ([NotificacaoDesafio]) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = self.refApp.whereField("responsavel", isEqualTo: responsavelID)
        
        childRef.getDocuments { (snapshot, error) in
            if let e = error {
                failed(e)
            } else {
                if let snap = snapshot {
                    var notificacoes = [NotificacaoDesafio]()
                    for doc in snap.documents {
                        print(doc.data())
                        let notificacao = NotificacaoDesafio.init()
                        notificacao.objectId = doc.documentID
                        notificacao.ref = Firestore.firestore().collection("NotificacaoDesafioApp")
                        let data = doc.data()
                        let criancaRef = data["crianca"] as! DocumentReference
                        print(criancaRef.documentID)
                        criancaRef.getDocument(completion: { (criancaSnap, errorCrianca) in
                            if let ec = errorCrianca {
                                print(ec.localizedDescription)
                            } else {
                                if let criancaSnap = criancaSnap {
                                    print(criancaSnap.documentID)
                                    print(criancaSnap.data())
                                    notificacao.crianca = Crianca.init(objectId: criancaSnap.documentID)
                                    notificacao.crianca?.from(document: criancaSnap)
                                }
                            }
                        })
                        
                        let desafioRef = data["desafio"] as! DocumentReference
                        print(desafioRef.documentID)
                        desafioRef.getDocument(completion: { (desafioSnapshot, errorDesafio) in
                            if let ed = errorDesafio {
                                print(ed.localizedDescription)
                            } else {
                                if let desafioSnap = desafioSnapshot {
                                    print(desafioSnap.data())
                                    notificacao.desafio = Desafio.init(document: desafioSnap)
                                    notificacao.desafio?.ref = Firestore.firestore().collection("DesafioApp")
                                    notificacao.desafio?.objectId = desafioSnap.documentID
                                }
                            }
                        })
                        
                        notificacoes.append(notificacao)
                    }
                    success(notificacoes)
                }
            }
            
        }
    }
    
    func notificarDesafioCompleto(desafio: Desafio) {
        let desafioRef = desafio.ref?.document(desafio.objectId!)
        let criancaRef = desafio.crianca?.ref?.document(desafio.crianca!.objectId!)
        
        let post = ["responsavel" : desafio.responsavel!.objectId!, "crianca" : criancaRef!, "desafio" : desafioRef!] as [String : Any]
        
        refCompleto.addDocument(data: post)
    }
    
    func notificarInteresseDesafioApp(desafio: Desafio) {
        let desafioRef = desafio.ref?.document(desafio.objectId!)
        let criancaRef = desafio.crianca?.ref?.document(desafio.crianca!.objectId!)
        
        let post = ["responsavel" : desafio.responsavel!.objectId!, "crianca" : criancaRef!, "desafio" : desafioRef!] as [String : Any]
        
        refApp.addDocument(data: post)
    }
    
}
