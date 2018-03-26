//
//  FirestoreObject.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import Firebase

class FirestoreObject: NSObject {
    let db = Firestore.firestore()
    
    var ref: CollectionReference? 
    var objectId: String?
    
    override init() {
        super.init()
    }
    
    init(reference: CollectionReference) {
        ref = reference
    }
    
    init(reference: CollectionReference, objectId: String) {
        ref = reference
        
        self.objectId = objectId
    }
    
    init(objectId: String) {        
        self.objectId = objectId
    }
}
