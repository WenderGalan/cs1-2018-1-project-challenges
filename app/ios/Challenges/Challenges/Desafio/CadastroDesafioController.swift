//
//  CadastrarDesafioViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 03/05/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MZFormSheetPresentationController
import MBProgressHUD

class CadastroDesafioController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate, PickerResultProtocol {

    @IBOutlet weak var tableView: UITableView!
    
    var editandoCadastro = false
    var desafio: Desafio?
    var user: Responsavel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        title = "Cadastrar desafio"
        
        navigationController?.isNavigationBarHidden = false
        navigationController?.navigationBar.tintColor = UIColor.init(red: 250/255, green: 153/255, blue: 23/255, alpha: 1.0)
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.foregroundColor : UIColor.init(red: 250/255, green: 153/255, blue: 23/255, alpha: 1.0), NSAttributedStringKey.font : UIFont.init(name: "DK Cool Crayon", size: 16)!]
        
        setupTableView()
    }

    func setupTableView() {
        
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)
        
        tableView.register(UINib.init(nibName: "BasicLabelCell", bundle: nil), forCellReuseIdentifier: BasicLabelCell.defaultIdentifier())
        tableView.register(UINib.init(nibName: "BasicTextFieldCell", bundle: nil), forCellReuseIdentifier: BasicTextFieldCell.defaultIdentifier())
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 8
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if indexPath.row == 1 || indexPath.row == 2 || indexPath.row == 6 || indexPath.row == 7 {
            cell.separatorInset = UIEdgeInsets(top: 0, left: (cell.bounds.width / 2), bottom: 0, right: (cell.bounds.width / 2))
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.row {
            case 0:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
                cell.textoLabel.text = "Criança"
                cell.textoLabel.textColor = UIColor.init(red: 250/255, green: 153/255, blue: 23/255, alpha: 1.0)
                if let crianca = desafio?.crianca {
                    cell.textoLabel.text = crianca.nome
                }
                return cell
                
            case 1:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.delegate = self
                cell.textField.placeholder = "Nome do Desafio"
                cell.textField.text = desafio?.nome
                
                return cell
                
            case 2:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.delegate = self
                cell.textField.placeholder = "Recompensa"
                cell.textField.text = desafio?.recompensa
                
                return cell
                
            case 3:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.delegate = self
                cell.textField.placeholder = "Pontos"
                if let pontos = desafio?.pontos {
                    cell.textField.text = "\(pontos)"
                }
                return cell
                
            case 4:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
                cell.textoLabel.text = "Habilidade"
                cell.textoLabel.textColor = UIColor.init(red: 250/255, green: 153/255, blue: 23/255, alpha: 1.0)
                if let habilidade = desafio?.habilidade {
                    cell.textoLabel.text = habilidade.nome
                }
                
                return cell
                
            case 5:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
                cell.textoLabel.text = "Frequência"
                cell.textoLabel.textColor = UIColor.init(red: 250/255, green: 153/255, blue: 23/255, alpha: 1.0)
                if let frequencia = desafio?.frequencia {
                    cell.textoLabel.text = frequencia
                }
                
                return cell
                
            case 6:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.delegate = self
                cell.textField.placeholder = "Quantas Repetições?"
                if let repeticoes = desafio?.repeticoes {
                    cell.textField.text = "\(repeticoes)"
                }
                return cell
                
            case 7:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.delegate = self
                cell.textField.placeholder = "Observações"
                cell.textField.text = desafio?.observacoes
                
                return cell
                
            default:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.delegate = self
                cell.textField.placeholder = "Observações"
                return cell
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        switch indexPath.row {
        case 0:
            callPickerWithOption(option: .crianca)
            break
            
        case 4:
            callPickerWithOption(option: .habilidade)
            break
            
        case 5:
            callPickerWithOption(option: .frequencia)
            break
            
        default:
            break
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        if desafio == nil {
            desafio = Desafio.init()
        }
        
        let cell = textField.superview?.superview as! UITableViewCell
        let indexPath = tableView.indexPath(for: cell)
        
        switch indexPath!.row {
        case 1:
            desafio?.nome = textField.text
            break
        case 2:
            desafio?.recompensa = textField.text
            
            break
        case 3:
            let someString = textField.text!
            if let myInteger = Int(someString) {
                desafio?.pontos = Int(myInteger)
            }
            
            break
        case 6:
            let someString = textField.text!
            if let myInteger = Int(someString) {
                desafio?.repeticoes = Int(myInteger)
            }
            
            break
            
        case 7:
            desafio?.observacoes = textField.text
            
        default:
            break
        }
    }
    
    func callPickerWithOption(option: PickerController.PickerType) {
        if desafio == nil {
            desafio = Desafio.init()
        }
        
        let navigationController = self.storyboard?.instantiateViewController(withIdentifier: "PickerPopover") as! UINavigationController
        
        let formSheetController = MZFormSheetPresentationViewController.init(contentViewController: navigationController)
        formSheetController.presentationController?.contentViewSize = CGSize(width: 250, height: 300)
        formSheetController.contentViewControllerTransitionStyle = .fade
        formSheetController.presentationController?.shouldDismissOnBackgroundViewTap = true
        
        let appearance = formSheetController.presentationController
        appearance?.shouldCenterVertically = true
        appearance?.shouldCenterHorizontally = true
        
        formSheetController.willPresentContentViewControllerHandler = { (vc) in
            let presentedViewController = navigationController.viewControllers.first as! PickerController
            presentedViewController.tipoPicker = option
            if option == .crianca {
                presentedViewController.opcoes = self.user.criancas
            }
            presentedViewController.delegate = self
            presentedViewController.view.layoutIfNeeded()
        }
        self.present(formSheetController, animated: true, completion: nil)
    }
    
    func addCrianca(criancaSelecionada: Crianca) {
        desafio?.crianca = criancaSelecionada
        tableView.reloadData()
    }
    
    func addFrequencia(frequenciaSelecionada: String) {
        desafio?.frequencia = frequenciaSelecionada
        tableView.reloadData()
    }
    
    func addHabilidade(habilidadeSelecionada: String) {
        let habilidade = Habilidade.init()
        habilidade.nome = habilidadeSelecionada
        desafio?.habilidade = habilidade
        tableView.reloadData()
    }

    @IBAction func salvarButtonTapped(_ sender: UIButton) {
        view.endEditing(true)

        if let desafio = desafio {
            desafio.responsavel = user
            desafio.dataUpdate = Date.init(timeIntervalSinceNow: 0)
            
            let validacao = ValidationHelper.sharedInstance.validar(desafio: desafio)
            if validacao.status {
                if !editandoCadastro {
                    desafio.dataCriacao = Date.init(timeIntervalSinceNow: 0)
                }
                salvarDesafio()
            } else {
                AlertHelper.sharedInstance.createInternalErrorAlert(error: validacao.codigo!, camposObrigatorios: validacao.campos, from: self)
            }
        }
    }
    
    func salvarDesafio() {
        MBProgressHUD.showAdded(to: view, animated: true)
        desafio?.saveInBackground(success: { (_) in
            self.navigationController?.popViewController(animated: true)
            MBProgressHUD.hide(for: self.view, animated: true)
        }, failed: { (error) in
            AlertHelper.sharedInstance.createFirestoreErrorAlert(error: error!, from: self)
            MBProgressHUD.hide(for: self.view, animated: true)
        })
    }
}




