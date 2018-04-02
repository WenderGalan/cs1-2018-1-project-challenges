//
//  UsuarioDAO.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseStorage
import FirebaseFirestore
import FirebaseAuth

class UsuarioDAO: NSObject {
    
    static let sharedInstance = UsuarioDAO()
    
    let ref = Firestore.firestore().collection("Usuarios")
    
    
    func login(email: String, senha: String, success: @escaping (Usuario) -> (), failed:  @escaping (Error?) -> ()) {
        do {
            try Auth.auth().signOut()
        } catch {
            print("erro logout")
        }
        
        Auth.auth().signIn(withEmail: email, password: senha) { (firUser, errorLogin) in
            if let authUser = firUser {
                let childRef = self.ref.document(authUser.uid)
                childRef.getDocument(completion: { (snapshot, error) in
                    
                    if let snapshot = snapshot {
                        if (snapshot.data()!["tipo"] as! Int) == 0 {
                            let user = Responsavel.sharedUser()
                            if let usuario = user {
                                usuario.from(document: snapshot)
                                success(usuario)
                            }
                        } else {
                            
                        }
                    } else {
                        failed(nil)
                    }
                    
                })
            } else {
                failed(errorLogin)
            }
        }
    }
    
    func cadastrarResponsavel(responsavel: Responsavel, senha: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        
        Auth.auth().createUser(withEmail: responsavel.email!, password: senha) { (firUser, errorCadastro) in
            
            if let authUser = firUser {
                responsavel.objectId = authUser.uid
                responsavel.createUser()
                success(true)
            } else {
                failed(errorCadastro)
            }
        }
    }
    
    func cadastrarCrianca(crianca: Crianca, senha: String, foto: UIImage?, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        
        do {
            try Auth.auth().signOut()
        } catch {
            print("erro logout")
        }
        
        Auth.auth().createUser(withEmail: crianca.email!, password: senha) { [unowned self] (firUser, errorCadastro) in
            
            if let authUser = firUser {
                crianca.objectId = authUser.uid
                crianca.createUser()
                crianca.responsavel!.ref!.document(crianca.responsavel!.objectId!).collection("criancas").addDocument(data: ["0" : crianca.objectId])
                if let foto = foto {
                    self.salvarFotoPerfil(crianca: crianca, image: foto, success: { (criancaFoto) in
                        crianca.fotoURL = criancaFoto.fotoURL
                        success(true)
                    }, failed: { (_) in
                        success(true)
                    })
                }
            } else {
                print(errorCadastro?.localizedDescription)
                failed(errorCadastro)
            }
        }
    }
    
    private func salvarFotoPerfil(crianca: Crianca, image: UIImage, success: @escaping (Crianca) -> (), failed: @escaping (Error?) -> ()) {
        
        let storageRef = Storage.storage().reference(withPath: String.init(format: "/%@/perfil/fotoPerfil.jpg", crianca.objectId!))
        
        let imgData = UIImageJPEGRepresentation(image, 0.75)
        
        storageRef.putData(imgData!, metadata: nil) { (metadata, error) in
            if let e = error {
                failed(e)
            } else {
                if let data = metadata {
                    if let url = data.downloadURL() {
                        crianca.fotoURL = url.absoluteString
                        crianca.saveInBackground(success: { (_) in
                            success(crianca)
                        }, failed: { (_) in
                            success(crianca)
                        })
                    } else {
                        failed(error)
                    }
                } else {
                    failed(error)
                }
            }
        }
    }
    
//    func checkEmailAlreadyExist(email: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
//        let q = refUserUsernames.queryOrdered(byChild: "email").queryEqual(toValue: email.lowercased())
//        q.observeSingleEvent(of: .value, with: { (snapshot) in
//            if snapshot.value is Dictionary<AnyHashable, Any> {
//                success(true)
//            } else {
//                success(false)
//            }
//        }) { (error) in
//            failed(error)
//        }
//    }
//
//    func checkUsernameAlreadyExist(username: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
//        let q = refUserUsernames.queryOrdered(byChild: "username").queryEqual(toValue: username.lowercased())
//        q.observeSingleEvent(of: .value, with: { (snapshot) in
//            if snapshot.value is Dictionary<AnyHashable, Any> {
//                success(true)
//            } else {
//                success(false)
//            }
//        }) { (error) in
//            failed(error)
//        }
//    }
}
