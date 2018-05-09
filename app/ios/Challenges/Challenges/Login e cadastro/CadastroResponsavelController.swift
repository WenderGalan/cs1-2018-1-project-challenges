//
//  CadastroResponsavelController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MBProgressHUD

class CadastroResponsavelController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate {

    @IBOutlet weak var tableView: UITableView!
    
    var user: Responsavel?
    var senha: String?
    var confirmaSenha: String?
    
    var editandoCadastro = false
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationController?.isNavigationBarHidden = false
        
        if editandoCadastro {
            title = "Editar seus dados"
        } else {
            title = "Crie sua conta"
        }
        
        navigationController?.navigationBar.tintColor = UIColor.init(red: 74/255, green: 144/255, blue: 226/255, alpha: 1.0)
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.foregroundColor : UIColor.init(red: 74/255, green: 144/255, blue: 226/255, alpha: 1.0), NSAttributedStringKey.font : UIFont.init(name: "DK Cool Crayon", size: 16)!]
        
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
        if editandoCadastro {
            return 2
        }
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
        cell.textField.delegate = self
        
        
        switch indexPath.row {
        case 0:
            cell.textField.text = user?.nome
            cell.textField.placeholder = "Nome completo"
            break
        case 1:
            cell.textField.text = user?.email
            cell.textField.placeholder = "E-mail"

            break
        case 2:
            if editandoCadastro {
                cell.textField.placeholder = "Senha atual"
            } else {
                cell.textField.placeholder = "Senha"
            }
            
            cell.textField.isSecureTextEntry = true
            
            break
        case 3:
            if editandoCadastro {
                cell.textField.placeholder = "Nova senha"
            } else {
                cell.textField.placeholder = "Confirme a senha"
            }
            
            cell.textField.isSecureTextEntry = true
            
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
        
        if let usuario = user {
            let validacao = ValidationHelper.sharedInstance.validar(responsavel: usuario)
            if validacao.status {
                if editandoCadastro {
                    editarUsuario()
                } else {
                    let validacaoSenha = ValidationHelper.sharedInstance.validarCadastroSenha(senha: senha, confirmaSenha: confirmaSenha)
                    
                    if validacaoSenha.status {
                        cadastrarUsuario()
                    } else {
                        AlertHelper.sharedInstance.createInternalErrorAlert(error: validacao.codigo!, camposObrigatorios: validacao.campos, from: self)
                    }
                }
            } else {
                AlertHelper.sharedInstance.createInternalErrorAlert(error: validacao.codigo!, camposObrigatorios: validacao.campos, from: self)
            }
        }
    }

    func cadastrarUsuario() {
        MBProgressHUD.showAdded(to: view, animated: true)
        UsuarioDAO.sharedInstance.cadastrarResponsavel(responsavel: user!, senha: senha!, success: { [unowned self] (_)  in
            self.performSegue(withIdentifier: "SegueCadastroCrianca", sender: self)
            MBProgressHUD.hide(for: self.view, animated: true)
        }) { (error) in
            AlertHelper.sharedInstance.createFirestoreErrorAlert(error: error!, from: self)
            MBProgressHUD.hide(for: self.view, animated: true)
        }
    }
    
    func editarUsuario() {
        MBProgressHUD.showAdded(to: view, animated: true)
        user?.saveInBackground(success: { (_) in
            self.navigationController?.popViewController(animated: true)
        }, failed: { (error) in
            AlertHelper.sharedInstance.createFirestoreErrorAlert(error: error!, from: self)
            MBProgressHUD.hide(for: self.view, animated: true)
        })
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
