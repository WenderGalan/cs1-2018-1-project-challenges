//
//  LoginController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MBProgressHUD

class LoginController: UIViewController {

    @IBOutlet weak var emailField: UITextField!
    @IBOutlet weak var senhaField: UITextField!
    
    var userResponsavel: Responsavel?
    var userCrianca: Crianca?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        senhaField.isSecureTextEntry = true
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.isNavigationBarHidden = true
    }
    
    @IBAction func entrarButtonTapped(_ sender: UIButton) {
        view.endEditing(true)
        MBProgressHUD.showAdded(to: view, animated: true)
        UsuarioDAO.sharedInstance.login(email: emailField.text!, senha: senhaField.text!, success: { [unowned self] (usuario) in
            if usuario.tipo == 0 {
                self.userResponsavel = usuario as? Responsavel
                self.performSegue(withIdentifier: "SegueLoginResponsavel", sender: self)
            } else {
                self.userCrianca = usuario as? Crianca
                self.performSegue(withIdentifier: "SegueLoginCrianca", sender: self)
            }
            MBProgressHUD.hide(for: self.view, animated: true)
        }) { (error) in
            MBProgressHUD.hide(for: self.view, animated: true)
            AlertHelper.sharedInstance.createFirestoreErrorAlert(error: error!, from: self)
        }
    }
    
    @IBAction func esqueciSenhaButtonTapped(_ sender: UIButton) {
        let alertController = UIAlertController(title: "Insira seu e-mail", message: "", preferredStyle: .alert)
        alertController.addTextField { (textField : UITextField!) -> Void in
            textField.placeholder = "E-mail"
        }
        let saveAction = UIAlertAction(title: "Enviar", style: .default, handler: { alert -> Void in
            let firstTextField = alertController.textFields![0] as UITextField
            if let email = firstTextField.text {
                UsuarioDAO.sharedInstance.requisitarNovaSenha(email: email, success: { (_) in
                    //TODO: success message
                }, failed: { (error) in
                    AlertHelper.sharedInstance.createFirestoreErrorAlert(error: error!, from: self)
                })
            }
        })
        let cancelAction = UIAlertAction(title: "Cancel", style: .default, handler: nil)
        
        alertController.addAction(saveAction)
        alertController.addAction(cancelAction)
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    @IBAction func criarContaButtonTapped(_ sender: UIButton) {
        self.performSegue(withIdentifier: "SegueCadastroResponsavel", sender: self)
    }
    
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "SegueCadastroResponsavel" {
            let cc = segue.destination as! CadastroResponsavelController
            cc.user = userResponsavel
        }
    }
    

}
