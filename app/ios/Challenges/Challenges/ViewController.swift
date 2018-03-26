//
//  ViewController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/25/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        let user = Usuario.sharedUser()
        
        user?.nome = "Rodolfo Roca Neto"
        user?.email = "meu@email.com.br"
        user?.tipo = NSNumber.init(value: 0)
        
        UsuarioDAO.sharedInstance.cadastrarUsuario(usuario: user!, senha: "123mudar", success: { (_) in
            print("deu certo")
        }) { (_) in
            print("deu erro")
        }
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

