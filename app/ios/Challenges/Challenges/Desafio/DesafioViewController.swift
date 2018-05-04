//
//  DesafioViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class DesafioViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {


    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        switch (section) {
        case 0:
            let headerView = BasicLabelHeaderView()
            headerView.labelTitulo = "DESAFIO"
            headerView.plusButton.isHidden = true
            return headerView
            
            break
        case 1:
            let headerView = BasicLabelHeaderView()
            headerView.labelTitulo = "FREQUÊNCIA"
            headerView.plusButton.isHidden = true
            return headerView
            
            break
        case 2:
            let headerView = BasicLabelHeaderView()
            headerView.labelTitulo = "HABILIDADE"
            headerView.plusButton.isHidden = true
            return headerView
            
            break
        case 3:
            let headerView = BasicLabelHeaderView()
            headerView.labelTitulo = "RECOMPENSA"
            headerView.plusButton.isHidden = true
            return headerView
            
            break
        case 4:
            let headerView = BasicLabelHeaderView()
            headerView.labelTitulo = "PROGRESSO"
            headerView.plusButton.isHidden = true
            return headerView
            
            break
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch (indexPath.section) {
        case 0:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
            // cell.TextoLabel.text =
            return cell
            
            break
        case 1:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
            // cell.TextoLabel.text =
            return cell
            
            break
        case 2:
            let cell = tableView.dequeueReusableCell(withIdentifier: HabilidadeDesafioCell.defaultIdentifier(), for: indexPath) as! HabilidadeDesafioCell
            // cell.TextoLabel.text =
            return cell
            
            break
        case 3:
            let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell
            // cell.TextoLabel.text =
            return cell
            
            break
        case 4:
            
            break
        default:
            break
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
}


