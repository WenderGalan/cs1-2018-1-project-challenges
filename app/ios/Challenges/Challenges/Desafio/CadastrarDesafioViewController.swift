//
//  CadastrarDesafioViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 03/05/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class CadastrarDesafioViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    override func viewDidLoad() {
        super.viewDidLoad()
        
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
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        switch indexPath.row {
        case 0: //Criança
            
            
            break
        case 4: //Habilidade
            
            
            break
        case 5: //Frequência
            
            
            break
        default:
            break
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        switch indexPath.row {
            case 0:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
                cell.TextoLabel.text = "Criança"
                //cell.TextoLabel.textColor = UIColor.orange
                return cell
                
                break
            case 1:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.placeholder = "Nome do Desafio"
                return cell
                
                break
            case 2:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.placeholder = "Recompensa"
                return cell
                
                break
            case 3:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.placeholder = "Pontos"
                return cell
                
                break
            case 4:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
                cell.TextoLabel.text = "Habilidade"
                //cell.TextoLabel.textColor = UIColor.orange
                return cell
                
                break
            case 5:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
                cell.TextoLabel.text = "Frequência"
                //cell.TextoLabel.textColor = UIColor.orange
                return cell
                
                break
            case 6:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.placeholder = "Quantas Repetições?"
                return cell
                
                break
            case 7:
                let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
                cell.textField.placeholder = "Observações"
                return cell
                
                break
            default:
                break
        }
    }

}




