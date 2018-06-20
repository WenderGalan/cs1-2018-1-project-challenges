//
//  NotificacaoAmizade.swift
//  Challenges
//
//  Created by Rodolfo Roca on 6/18/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class NotificacaoAmizade: FirestoreObject {

    var crianca: Crianca?
    var amigo: Crianca?
    
    override init() {
        super.init()
    }
}
