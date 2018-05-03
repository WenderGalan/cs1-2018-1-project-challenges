//
//  PerfilResponsavelViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class PerfilResponsavelViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var nomeResponsavelLabel: UILabel!
    
    @IBOutlet weak var notificacaoBadgeLabel: UILabel!
    
    @IBOutlet weak var tableView: UITableView!
    
    var user: Responsavel?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if user == nil {
            user = Responsavel.sharedUser()
//            UsuarioDAO.sharedInstance.
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
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
        return 2
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return UIView.init()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        return UITableViewCell.init()
    }
    
    @IBAction func notificacoesButtonTapped(_ sender: UIButton) {
        
    }
    
    @IBAction func configuracoesButtonTapped(_ sender: UIButton) {
        
    }
}
