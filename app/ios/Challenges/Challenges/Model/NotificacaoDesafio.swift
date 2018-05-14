//
//  NotificacaoDesafio.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/14/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseFirestore

class NotificacaoDesafio: FirestoreObject {

    var crianca: Crianca?
    var desafio: Desafio?
    var responsavel: Responsavel?

    override init() {
        super.init()
    }
}
