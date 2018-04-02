//
//  Usuario.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class Usuario: FirestoreObject {

    var nome: String?
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
    
    func logOut() {
        
    }
}
