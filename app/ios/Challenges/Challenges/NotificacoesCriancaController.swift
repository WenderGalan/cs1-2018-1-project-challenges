//
//  NotificacoesCriancaController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 6/18/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class NotificacoesCriancaController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var tableView: UITableView!
    
    var user: Crianca!
    var notificacoesAmizade: [NotificacaoAmizade]!
    lazy var notificacoesAjuda: [NotificacaoAmizade] = [NotificacaoAmizade]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Notificações"
        
        navigationController?.isNavigationBarHidden = false
        navigationController?.navigationBar.tintColor = UIColor.init(red: 123/255, green: 103/255, blue: 255/255, alpha: 1.0)
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.foregroundColor : UIColor.init(red: 123/255, green: 103/255, blue: 255/255, alpha: 1.0), NSAttributedStringKey.font : UIFont.init(name: "DK Cool Crayon", size: 16)!]
        
        setupTableView()
        // Do any additional setup after loading the view.
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
        
        tableView.register(UINib.init(nibName: "DefaultSectionHeaderView", bundle: nil), forHeaderFooterViewReuseIdentifier: DefaultSectionHeaderView.defaultIdentifier())
        tableView.register(UINib.init(nibName: "NotificacaoCell", bundle: nil), forCellReuseIdentifier: NotificacaoCell.defaultIdentifier())
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: DefaultSectionHeaderView.defaultIdentifier()) as! DefaultSectionHeaderView
        
        headerView.plusButton.isHidden = true
        headerView.tituloLabel.textColor = UIColor.init(red: 123/255, green: 103/255, blue: 255/255, alpha: 1.0)
        headerView.borderView.isHidden = false
        
        if section == 0 {
            headerView.tituloLabel.text = "PEDIDOS DE\nAMIZADE"
        } else {
            headerView.tituloLabel.text = "PEDIDOS DE\nAJUDA"
        }
        return headerView;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return notificacoesAmizade.count
        }
        return notificacoesAjuda.count
        
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: NotificacaoCell.defaultIdentifier(), for: indexPath) as! NotificacaoCell
        
        if indexPath.section == 0 {
            let notificacao = notificacoesAmizade[indexPath.row]
            
            cell.avatarLeading.constant = 0
            cell.avatarTop.constant = 0
            cell.avatarTrailing.constant = 0
            cell.avatarBottom.constant = 0
            
            if let fotoURL = notificacao.crianca?.fotoURL {
                cell.avatarImageView.setImageWith(URL.init(string: fotoURL)!, placeholderImage: #imageLiteral(resourceName: "profilePlaceholderSmall"))
            }
            
            cell.avatarImageView.layer.masksToBounds = true
            cell.avatarImageView.layer.cornerRadius = cell.avatarImageView.frame.size.width / 2
            
            cell.tituloLabel.text = notificacao.crianca?.nome
            
            cell.aceitarButton.tag = indexPath.row
            cell.recusarButton.tag = indexPath.row
            
            cell.aceitarButton.addTarget(self, action: #selector(aceitarButtonTapped(_:)), for: .touchUpInside)
            cell.recusarButton.addTarget(self, action: #selector(recusarButtonTapped(_:)), for: .touchUpInside)
        } else {
//            let notificacao = notificacoesAmizade[indexPath.row]
//
//            cell.avatarLeading.constant = 0
//            cell.avatarTop.constant = 0
//            cell.avatarTrailing.constant = 0
//            cell.avatarBottom.constant = 0
//
//            cell.avatarImageView.image = notificacao.desafio?.habilidade?.iconePequeno
//            cell.tituloLabel.text = notificacao.desafio?.nome
        }
        

        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    @objc func aceitarButtonTapped(_ sender: UIButton) {
        let index = sender.tag

        let note = notificacoesAmizade[index]
        user.amigos.append(note.crianca!)
        user.save()
        
        note.crianca?.amigos.append(user)
        note.crianca?.save()
        
        note.delete()
        
        notificacoesAmizade.remove(at: index)
        tableView.reloadData()
    }
    
    @objc func recusarButtonTapped(_ sender: UIButton) {
        let index = sender.tag
        
        let note = notificacoesAmizade[index]
        note.delete()
        notificacoesAmizade.remove(at: index)
        tableView.reloadData()
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
