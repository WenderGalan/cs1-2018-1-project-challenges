//
//  Usuario.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class Usuario: FirestoreObject {

    @objc var nome: String?
    var email: String?
    var tipo: NSNumber?
    
    func createUser() {
        guard let ref = ref else {
            return
        }
        
        if let uid = objectId {
            ref.document(uid).setData(toDictionary())
        }
    }
    
    func createUserWithBlock(success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ()) {
        guard let ref = ref else {
            return
        }
        
        if let uid = objectId {
            
            ref.document(uid).setData(toDictionary()) { (error) in
                if let e = error {
                    print(e.localizedDescription)
                    failed(e)
                } else {
                    success(true)
                }
            }
        }
    }
    
    
}
