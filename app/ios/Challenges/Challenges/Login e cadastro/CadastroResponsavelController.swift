//
//  CadastroResponsavelController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class CadastroResponsavelController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate {

    @IBOutlet weak var tableView: UITableView!
    
    var user: Responsavel?
    var senha: String?
    var confirmaSenha: String?
    
    var editandoCadastro = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationController?.isNavigationBarHidden = false
        title = "Crie sua conta"
        
        setupTableView()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func setupTableView() {
        
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)
        
        tableView.register(UINib.init(nibName: "BasicTextFieldCell", bundle: nil), forCellReuseIdentifier: BasicTextFieldCell.defaultIdentifier())
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
        cell.textField.delegate = self
        
        
        switch indexPath.row {
        case 0:
            cell.textField.placeholder = "Nome completo"
            break
        case 1:
            cell.textField.placeholder = "E-mail"

            break
        case 2:
            cell.textField.placeholder = "Senha"

            break
        case 3:
            cell.textField.placeholder = "Confirme a senha"

            break
        default:
            break
        }
        
        return cell
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        if user == nil {
            user = Responsavel.sharedUser()
        }
    
        let cell = textField.superview?.superview as! UITableViewCell
        let indexPath = tableView.indexPath(for: cell)
        
        switch indexPath!.row {
        case 0:
            user?.nome = textField.text
            break
        case 1:
            user?.email = textField.text
            
            break
        case 2:
            senha = textField.text
            
            break
        case 3:
            confirmaSenha = textField.text
            
            break
        default:
            break
        }
    }
    
    @IBAction func continuarButtonTapped(_ sender: UIButton) {
        view.endEditing(true)
        
        if validarDados() {
            if editandoCadastro {
                
            } else {                
                UsuarioDAO.sharedInstance.cadastrarResponsavel(responsavel: user!, senha: senha!, success: { [unowned self] (_)  in
                    self.performSegue(withIdentifier: "SegueCadastroCrianca", sender: self)
                }) { (_) in
                    // TODO: Alert error cadastro
                }
            }
        } else {
            // TODO: Alert error validacao
        }
        
    }
    
    func validarDados() -> Bool {
        return true
        
        
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
