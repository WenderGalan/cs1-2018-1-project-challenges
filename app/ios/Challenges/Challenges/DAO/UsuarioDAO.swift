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
                            UserDefaults.standard.set(senha, forKey: "Key")
                            UserDefaults.standard.synchronize()
                            
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
    
    func requisitarNovaSenha(email: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
    
        Auth.auth().sendPasswordReset(withEmail: email) { (error) in
            if let e = error {
                failed(e)
            } else {
                success(true)
            }
        }
    }
    
    
    func cadastrarResponsavel(responsavel: Responsavel, senha: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        
        Auth.auth().createUser(withEmail: responsavel.email!, password: senha) { (firUser, errorCadastro) in
            
            if let authUser = firUser {
                responsavel.objectId = authUser.uid
                responsavel.createUser()
                
                UserDefaults.standard.set(senha, forKey: "Key")
                UserDefaults.standard.synchronize()
                
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
                crianca.createUserWithBlock(success: { (_) in
                    if let foto = foto {
                        self.salvarFotoPerfil(crianca: crianca, image: foto, success: { (criancaFoto) in
                            crianca.fotoURL = criancaFoto.fotoURL
                            success(true)
                        }, failed: { (_) in
                            success(true)
                        })
                    } else {
                        success(true)
                    }
                }, failed: { (error) in
                    failed(error)
                })
                
            } else {
                failed(errorCadastro)
            }
        }
    }
    
    func salvarFotoPerfil(crianca: Crianca, image: UIImage, success: @escaping (Crianca) -> (), failed: @escaping (Error?) -> ()) {
        
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
    
    func getUsuarioInfo(objectId: String, success: @escaping (Usuario) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = self.ref.document(objectId)

        childRef.getDocument(completion: { (snapshot, error) in
            
            if let snapshot = snapshot {
                guard let snapshotData = snapshot.data() else {
                    failed(nil)
                    return
                }
                
                if (snapshotData["tipo"] as! Int) == 0 {
                    let user = Responsavel.sharedUser()
                    if let usuario = user {
                        usuario.from(document: snapshot)
                        if let criancasData = snapshotData["criancas"] as? [DocumentReference] {
                            if criancasData.count == 0 {
                                success(user!)
                            } else {
                                for criancaRef in criancasData {
                                    criancaRef.getDocument(completion: { (criancaSnapshot, error) in
                                        if let criancaSnapshot = criancaSnapshot {
                                            let crianca = Crianca.init(objectId: criancaSnapshot.documentID)
                                            crianca.from(document: criancaSnapshot)
                                            user?.criancas.append(crianca)
                                            if criancasData.count == user?.criancas.count {
                                                success(user!)
                                            }
                                        }
                                    })
                                }
                            }
                            
                        } else {
                            success(usuario)
                        }
                    }
                } else {
                    let user = Crianca.sharedUser()
                    if let usuario = user {
                        usuario.from(document: snapshot)
                        let data = snapshot.data()
                        if let responsavelRef = data!["responsavel"] as? DocumentReference {
                            responsavelRef.getDocument(completion: { (responsavelSnapshot, errorResponsavel) in
                                let responsavel = Responsavel.init(objectId: (responsavelSnapshot?.documentID)!)
                                responsavel.from(document: responsavelSnapshot!)
                                usuario.responsavel = responsavel
                                success(usuario)
                            })
                        }
                        
                        
                    }
                }
            } else {
                failed(nil)
            }
            
        })
    }
    
    func logOut() {
        do {
            try Auth.auth().signOut()
        } catch {
            print("error")
        }
    }
    
    func reautenticarUsuario(senha: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        let user = Auth.auth().currentUser
        let credential = EmailAuthProvider.credential(withEmail: (user?.email)!, password: senha)
        user?.reauthenticate(with: credential, completion: { (error) in
            if let e = error {
                print(e.localizedDescription)
                failed(e)
            } else {
                success(true)
            }
        })
    }
    
    func mudarSenha(senha: String, novaSenha: String, success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        let user = Auth.auth().currentUser
        
        self.reautenticarUsuario(senha: senha, success: { (_) in
            user?.updatePassword(to: senha, completion: { (errorChange) in
                if let ec = errorChange {
                    print(ec.localizedDescription)
                    failed(ec)
                } else {
                    success(true)
                }
            })
        }) { (errorAuth) in
            failed(errorAuth)
        }
    }
    
    func buscarPessoas(nome: String, success: @escaping ([Crianca]) -> (), failed: @escaping (Error?) -> ()) {
        let childRef = Firestore.firestore().collection("Usuarios")
            
        let query = childRef.whereField("nome", isGreaterThanOrEqualTo: nome).order(by: "nome")
    
//            query.whereField("tipo", isEqualTo: 1)
//            .whereField("nome_lower", isGreaterThanOrEqualTo: nome.lowercased())
//            .order(by: "nome_lower")
        
        query.getDocuments { (snapshot, error) in
            if let e = error {
                failed(e)
            } else {
                if let snap = snapshot {
                    var criancas = [Crianca]()
                    for doc in snap.documents {
                        let crianca = Crianca.init(objectId: doc.documentID)
                        crianca.from(document: doc)
                        criancas.append(crianca)
                    }
                    success(criancas)
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
