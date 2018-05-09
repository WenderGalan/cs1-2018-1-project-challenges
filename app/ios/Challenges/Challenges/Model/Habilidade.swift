//
//  Habilidade.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/30/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import FirebaseFirestore

class Habilidade: FirestoreObject {

    var nome: String?
    var iconeGrande: UIImage!
    var iconePequeno: UIImage!
    
    override func from(document: DocumentSnapshot) {
        guard let data = document.data() else {
            return
        }
        
        if let nome = data["nome"] as? String {
            self.nome = nome
        }
        
        if nome?.lowercased() == "criatividade" {
            iconeGrande = #imageLiteral(resourceName: "criatividadeGroup")
            iconePequeno = #imageLiteral(resourceName: "criatividadeGroupSmall")
        } else if nome?.lowercased() == "intelectual" {
            iconeGrande = #imageLiteral(resourceName: "inteligenciaGroup")
            iconePequeno = #imageLiteral(resourceName: "inteligenciaGroupSmall")
        } else if nome?.lowercased() == "fisica" {
            iconeGrande = #imageLiteral(resourceName: "fisicaGroup")
            iconePequeno = #imageLiteral(resourceName: "fisicaGroupSmall")
        } else {
            iconeGrande = #imageLiteral(resourceName: "socialGroup")
            iconePequeno = #imageLiteral(resourceName: "socialGroupSmall")
        }
    }
    
    func setIcones() {
        if nome?.lowercased() == "criatividade" {
            iconeGrande = #imageLiteral(resourceName: "criatividadeGroup")
            iconePequeno = #imageLiteral(resourceName: "criatividadeGroupSmall")
        } else if nome?.lowercased() == "intelectual" {
            iconeGrande = #imageLiteral(resourceName: "inteligenciaGroup")
            iconePequeno = #imageLiteral(resourceName: "inteligenciaGroupSmall")
        } else if nome?.lowercased() == "fisica" {
            iconeGrande = #imageLiteral(resourceName: "fisicaGroup")
            iconePequeno = #imageLiteral(resourceName: "fisicaGroupSmall")
        } else {
            iconeGrande = #imageLiteral(resourceName: "socialGroup")
            iconePequeno = #imageLiteral(resourceName: "socialGroupSmall")
        }
    }
}
