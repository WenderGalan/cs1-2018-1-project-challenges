//
//  HomeCriancaViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MBProgressHUD

class HomeCriancaViewController: UIViewController, UITableViewDelegate, UITableViewDataSource  {
    
    var user: Crianca?
    lazy var desafios: [Desafio] = [Desafio]()
    lazy var desafiosAplicativo: [Desafio] = [Desafio]()

    lazy var notificacoes: [Any] = [Any]()
    
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
     
        
        if user == nil {
            MBProgressHUD.showAdded(to: view, animated: true)
            user = Crianca.sharedUser()
            UsuarioDAO.sharedInstance.getUsuarioInfo(objectId: user!.objectId!, success: { (crianca) in
                self.user = crianca as? Crianca
                self.setupTableView()
                self.getDesafios()
            }) { (error) in
                MBProgressHUD.hide(for: self.view, animated: true)
            }
        } else {
            MBProgressHUD.showAdded(to: view, animated: true)
            getDesafios()
            self.setupTableView()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        tabBarController?.tabBar.isHidden = false
        navigationController?.isNavigationBarHidden = true
        getDesafios()
    }
    
    fileprivate func getDesafios() {
        DesafioDAO.sharedInstance.getDesafiosPara(criancaID: user!.objectId!, success: { (array) in
            self.desafios = array
            self.desafiosAplicativo = array
            
            self.tableView.reloadData()
            MBProgressHUD.hide(for: self.view, animated: true)
        }, failed: { (error) in
            MBProgressHUD.hide(for: self.view, animated: true)
        })
    }
    
    func setupTableView() {
        
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)
        
        tableView.register(UINib.init(nibName: "NotificationHeaderSectionView", bundle: nil), forCellReuseIdentifier: NotificationHeaderSectionView.defaultIdentifier())
        tableView.register(UINib.init(nibName: "DefaultSectionHeaderView", bundle: nil), forHeaderFooterViewReuseIdentifier: DefaultSectionHeaderView.defaultIdentifier())
        tableView.register(UINib.init(nibName: "ContainerTableViewCell", bundle: nil), forCellReuseIdentifier: ContainerTableViewCell.defaultIdentifier())
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if section == 0 {
            return 0
        }
        return 100
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if section == 0 {
            return UIView.init()
        }
        
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: DefaultSectionHeaderView.defaultIdentifier()) as! DefaultSectionHeaderView
        
        headerView.plusButton.isHidden = true
        headerView.tituloLabel.textColor = UIColor.init(red: 255/255, green: 79/255, blue: 114/255, alpha: 1.0)
        headerView.tituloLabel.text = "SUGESTÕES\nDE DESAFIOS"

        return headerView;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return 2
        }
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            if indexPath.row == 0 {
                let cell = tableView.dequeueReusableCell(withIdentifier: NotificationHeaderSectionView.defaultIdentifier()) as! NotificationHeaderSectionView
                cell.tituloLabel.text = "MEUS\nDESAFIOS"
                cell.tituloLabel.textColor = UIColor.init(red: 255/255, green: 79/255, blue: 114/255, alpha: 1.0)
                
                cell.notificationBadge.isHidden = notificacoes.count == 0 ? true : false
                cell.notificationBadge.text = "\(notificacoes.count)"
                cell.notificationButton.addTarget(self, action: #selector(notificacoesButtonTapped(_:)), for: .touchUpInside)
                
                cell.logoutButton.isHidden = true
                cell.editarPerfilButton.isHidden = true
                
                return cell
            } else {
                let cell = tableView.dequeueReusableCell(withIdentifier: ContainerTableViewCell.defaultIdentifier(), for: indexPath) as! ContainerTableViewCell
                let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
                
                let dcvc = storyboard.instantiateViewController(withIdentifier: "DesafiosCVC") as! DesafiosCollectionViewController
                dcvc.desafios = desafios
                dcvc.tipoDesafio = .padrao
                self.addChildViewController(dcvc)
                cell.containerView.addSubview(dcvc.view)
                dcvc.didMove(toParentViewController: self)
                cell.heightConstraint.constant = 195
                
                return cell
            }
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: ContainerTableViewCell.defaultIdentifier(), for: indexPath) as! ContainerTableViewCell
            let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
            
            let dcvc = storyboard.instantiateViewController(withIdentifier: "DesafiosCVC") as! DesafiosCollectionViewController
            dcvc.desafios = desafios
            dcvc.tipoDesafio = .padrao
            self.addChildViewController(dcvc)
            cell.containerView.addSubview(dcvc.view)
            dcvc.didMove(toParentViewController: self)
            cell.heightConstraint.constant = (dcvc.collectionView?.frame.size.height)!
            
            return cell
        }
    }
    
    @objc func notificacoesButtonTapped(_ sender: UIButton) {
        
    }
    
}
