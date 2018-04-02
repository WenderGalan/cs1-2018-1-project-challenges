//
//  LoginController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class LoginController: UIViewController {

    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var senhaField: UITextField!
    
    var user: Responsavel?
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.isNavigationBarHidden = true
    }
    
    @IBAction func entrarButtonTapped(_ sender: UIButton) {
        view.endEditing(true)
        UsuarioDAO.sharedInstance.login(email: emailField.text!, senha: senhaField.text!, success: { [unowned self] (usuario) in
            self.user = usuario as? Responsavel
            self.performSegue(withIdentifier: "SegueCadastroCrianca", sender: self)

        }) { (error) in
            print("erro de login")
        }
    }
    
    @IBAction func esqueciSenhaButtonTapped(_ sender: UIButton) {
        
    }
    
    @IBAction func criarContaButtonTapped(_ sender: UIButton) {
        self.performSegue(withIdentifier: "SegueCadastroResponsavel", sender: self)
    }
    
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "SegueCadastroCrianca" {
            let cc = segue.destination as! CadastroCriancaController
            cc.user = user
        }
    }
    

}
