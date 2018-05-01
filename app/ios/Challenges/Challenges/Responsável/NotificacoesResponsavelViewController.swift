//
//  NotificacoesResponsavelViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 23/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class NotificacoesResponsavelViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var segmentControl: UISegmentedControl!
    @IBOutlet weak var tableViewNotificacoes: UITableView!
    
    var arrayNotificacoes: NSArray = NSArray()
    
    @IBAction func segmentControlAction(_ sender: UISegmentedControl) {
        if(sender.selectedSegmentIndex == 0){
            
        } else if(sender.selectedSegmentIndex == 1){
            
        } else if(sender.selectedSegmentIndex == 2){
            
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Notificações"
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arrayNotificacoes.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            
        let cell = tableView.dequeueReusableCell(withIdentifier: "NotificacaoResponsavelCell", for: indexPath) as! NotificacaoResponsavelTableViewCell
        
        return cell

    }

}
