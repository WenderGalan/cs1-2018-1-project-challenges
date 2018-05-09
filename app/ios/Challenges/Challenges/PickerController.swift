//
//  PickerController.swift
//  Poink
//
//  Created by Rodolfo Roca on 21/02/18.
//  Copyright © 2018 JustWorks. All rights reserved.
//

import UIKit
import MZFormSheetPresentationController

protocol PickerResultProtocol {
    func addCrianca(criancaSelecionada: Crianca)
    func addFrequencia(frequenciaSelecionada: String)
    func addHabilidade(habilidadeSelecionada: String)
}

class PickerController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    var delegate: PickerResultProtocol?
    
    enum PickerType: Int {
        case crianca
        case frequencia
        case habilidade
    }

    @IBOutlet weak var tableView: UITableView!
    
    var tipoPicker: PickerType!
    var opcoes: [Any]?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        if tipoPicker == .crianca {
            title = "Selecione a criança"
        } else if tipoPicker == .frequencia {
            title = "Selecione a frequencia"
            opcoes = ["Uma única vez", "Diário", "Semanal", "Mensal"]
        } else if tipoPicker == .habilidade {
            title = "Selecione a habilidade"
            opcoes = ["Física", "Intelectual", "Criatividade", "Social"]
        }
        setupTableView()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    fileprivate func setupTableView() {
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let options = opcoes {
            return options.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellOpcao", for: indexPath)
        let textLabel = cell.viewWithTag(1) as? UILabel
        
        if let options = opcoes {
            if tipoPicker == .crianca {
                let opcao = options[indexPath.row] as! Crianca
                textLabel?.text = opcao.nome
            } else {
                let opcao = options[indexPath.row] as! String
                textLabel?.text = opcao
            }
            
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if let options = opcoes {
            let opcao = options[indexPath.row]
            if tipoPicker == .crianca {
                delegate?.addCrianca(criancaSelecionada: opcao as! Crianca)
                dismiss(animated: true, completion: nil)
            } else if tipoPicker == .frequencia {
                delegate?.addFrequencia(frequenciaSelecionada: opcao as! String)
                dismiss(animated: true, completion: nil)
            } else if tipoPicker == .habilidade {
                    delegate?.addHabilidade(habilidadeSelecionada: opcao as! String)
                    dismiss(animated: true, completion: nil)
            }
        }
    }
    
    @IBAction func dismissTapped(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
