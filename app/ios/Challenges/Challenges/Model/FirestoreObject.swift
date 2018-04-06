//
//  FirestoreObject.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import FirebaseFirestore

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
    
    func save() {
        guard let ref = ref else {
            return
        }
        
        if objectId != nil {
            ref.document(objectId!).setData(toDictionary())
        } else {
            ref.addDocument(data: toDictionary())
        }
    }
    
    func saveInBackground(success: @escaping (Bool) -> (), failed: @escaping (Error?) -> ())  {
        guard let ref = ref else {
            return
        }
        
        if objectId != nil {
            ref.document(objectId!).setData(toDictionary()) { (error) in
                if let erro = error {
                    failed(erro)
                } else {
                    success(true)
                }
            }
        } else {
            ref.addDocument(data: toDictionary()) { (error) in
                if let erro = error {
                    failed(erro)
                } else {
                    success(true)
                }
            }
        }
    }
    
    func delete() {
        guard let ref = ref else {
            return
        }
        
        if let uid = objectId {
            ref.document(uid).delete()
        }
    }
    
    func toDictionary() -> Dictionary<String, Any> {
        return Dictionary<String, Any>()
    }
    
    func from(document: DocumentSnapshot) {
        
    }

}
