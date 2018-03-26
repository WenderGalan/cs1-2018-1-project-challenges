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
        
        Auth.auth().signIn(withEmail: email, password: senha) { (firUser, errorLogin) in
            if let authUser = firUser {
                let childRef = self.ref.document(authUser.uid)
                childRef.getDocument(completion: { (snapshot, error) in
                    let user = Usuario.sharedUser()
                    if let usuario = user {
                        if let snapshot = snapshot {
                            usuario.from(document: snapshot)
                            success(usuario)
                        } else {
                            failed(nil)
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
    
    func cadastrarUsuario(usuario: Usuario, senha: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        
        Auth.auth().createUser(withEmail: usuario.email!, password: senha) { (firUser, errorCadastro) in
            
            if let authUser = firUser {
                let dictionary = usuario.toDictionary()
                
                self.ref.document(authUser.uid).setData(dictionary, completion: { (error) in
                    if let error = error {
                        failed(error)
                    } else {
                        success(true)
                    }
                })
                
            } else {
                failed(errorCadastro)
            }
        }
    }
    
//    func salvarFotoPerfilUsuario(usuario: Usuario, image: UIImage, success: @escaping (Usuario) -> (), failed: @escaping (Error?) -> ()) {
//        
//        let storageRef = Storage.storage().reference(withPath: "imagens").child(String.init(format: "/%@/perfil.jpg", usuario.uid!))
//        
//        let imgData = UIImageJPEGRepresentation(image, 0.75)
//        
//        storageRef.putData(imgData!, metadata: nil) { (metadata, error) in
//            if let e = error {
//                failed(e)
//            } else {
//                if let data = metadata {
//                    if let url = data.downloadURL() {
//                        usuario.fotoURL = url.absoluteString
//                        self.atualizarUsuario(usuario: usuario, success: { (_) in
//                            success(usuario)
//                        }, failed: { (errorUpdate) in
//                            failed(errorUpdate)
//                        })
//                    } else {
//                        failed(error)
//                    }
//                } else {
//                    failed(error)
//                }
//            }
//        }
//    }
    
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
