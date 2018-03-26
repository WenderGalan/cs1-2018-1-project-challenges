//
//  FirestoreService.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import Foundation
import FirebaseFirestore

protocol FirestoreService {
    func save()
    func delete()
    func toDictionary() -> Dictionary<String, Any>
    func from(document: DocumentSnapshot)
}
