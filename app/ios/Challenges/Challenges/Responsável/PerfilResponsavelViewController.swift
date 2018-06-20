//
//  PerfilResponsavelViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MBProgressHUD

class PerfilResponsavelViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tableView: UITableView!
    
    var user: Responsavel?

    lazy var desafios: [Desafio] = [Desafio]()

    lazy var notificacoesDesafioCompleto: [NotificacaoDesafio] = [NotificacaoDesafio]()
    lazy var notificacoesDesafioApp: [NotificacaoDesafio] = [NotificacaoDesafio]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if user == nil {
            MBProgressHUD.showAdded(to: view, animated: true)
            user = Responsavel.sharedUser()
            UsuarioDAO.sharedInstance.getUsuarioInfo(objectId: user!.objectId!, success: { (responsavel) in
                self.user = responsavel as? Responsavel
                self.setupTableView()
                self.getDesafios()
            }) { (error) in
                MBProgressHUD.hide(for: self.view, animated: true)
            }
        } else {
            MBProgressHUD.showAdded(to: view, animated: true)
            getDesafios()
            setupTableView()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.isNavigationBarHidden = true
        getDesafios()
    }
    
    fileprivate func getDesafios() {
        DesafioDAO.sharedInstance.getDesafiosPara(responsavelID: user!.objectId!, success: { (array) in
            self.desafios = array
            self.tableView.reloadData()
            self.getNotificacoesCompletos()
            MBProgressHUD.hide(for: self.view, animated: true)
        }, failed: { (error) in
            MBProgressHUD.hide(for: self.view, animated: true)
        })
    }
    
    fileprivate func getNotificacoesCompletos() {
        NotificacoesDAO.sharedInstance.getNotificacoesDesafioCompleto(responsavelID: user!.objectId!, success: { (array) in
            self.notificacoesDesafioCompleto = array
            self.tableView.reloadData()
            self.getNotificacoesApp()
            MBProgressHUD.hide(for: self.view, animated: true)
        }, failed: { (error) in
            MBProgressHUD.hide(for: self.view, animated: true)
        })
    }
    
    fileprivate func getNotificacoesApp() {
        NotificacoesDAO.sharedInstance.getNotificacoesDesafioApp(responsavelID: user!.objectId!, success: { (array) in
            self.notificacoesDesafioApp = array
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
        return 4
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if section == 0 || section == 2 {
            return 0
        }
        return 50
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if section == 0 || section == 2 {
            return UIView.init()
        }
        
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: DefaultSectionHeaderView.defaultIdentifier()) as! DefaultSectionHeaderView
        
        headerView.plusButton.tag = section
        if section == 1 {
            headerView.tituloLabel.text = "CRIANÇAS"
            headerView.plusButton.addTarget(self, action: #selector(addCriancaButtonTapped(_:)), for: .touchUpInside)
            headerView.borderView.isHidden = true
        } else {
            headerView.tituloLabel.text = "DESAFIOS"
            headerView.plusButton.addTarget(self, action: #selector(addDesafioButtonTapped(_:)), for: .touchUpInside)
        }
        
        return headerView;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: NotificationHeaderSectionView.defaultIdentifier()) as! NotificationHeaderSectionView
            cell.tituloLabel.text = user?.nome
            
            let count = notificacoesDesafioCompleto.count + notificacoesDesafioApp.count
            cell.notificationBadge.isHidden = count == 0 ? true : false
            cell.notificationBadge.text = "\(count)"
            cell.notificationBadge.layer.masksToBounds = true
            cell.notificationBadge.layer.cornerRadius = cell.notificationBadge.frame.size.width / 2

            cell.logoutButton.addTarget(self, action: #selector(logoutButtonTapped(_:)), for: .touchUpInside)
            cell.notificationButton.setImage(#imageLiteral(resourceName: "notificationIconBlue"), for: .normal)
            cell.notificationButton.addTarget(self, action: #selector(notificacoesButtonTapped(_:)), for: .touchUpInside)
            cell.editarPerfilButton.addTarget(self, action: #selector(configuracoesButtonTapped(_:)), for: .touchUpInside)
            
            return cell
        } else if indexPath.section == 2 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "CellCheckmark", for: indexPath)
            
            let checkmarkImageView = cell.viewWithTag(1) as! UIImageView
            
            if (user?.permiteSocial)! {
                checkmarkImageView.image = #imageLiteral(resourceName: "checkmarkOn")
            } else {
                checkmarkImageView.image = #imageLiteral(resourceName: "checkmarkOff")
            }
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: ContainerTableViewCell.defaultIdentifier(), for: indexPath) as! ContainerTableViewCell
            let storyboard = UIStoryboard.init(name: "Main", bundle: nil)

            if indexPath.section == 1 {
                let ccvc = storyboard.instantiateViewController(withIdentifier: "CriancasCVC") as! CriancasCollectionViewController
                ccvc.criancas = user!.criancas
                ccvc.user = user!
                self.addChildViewController(ccvc)
                var frame = cell.containerView.frame
                frame.origin.y = 0
                ccvc.view.frame = frame
                cell.containerView.addSubview(ccvc.view)
                ccvc.didMove(toParentViewController: self)
            } else {
                let dcvc = storyboard.instantiateViewController(withIdentifier: "DesafiosCVC") as! DesafiosCollectionViewController
                dcvc.desafios = desafios
                dcvc.tipoDesafio = .responsavel
                self.addChildViewController(dcvc)
                cell.containerView.addSubview(dcvc.view)
                dcvc.didMove(toParentViewController: self)
                cell.heightConstraint.constant = (dcvc.collectionView?.frame.size.height)!
            }
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if indexPath.section == 2 {
            if indexPath.row == 0 {
                if (user?.permiteSocial)! {
                    user?.permiteSocial = false
                } else {
                    user?.permiteSocial = true
                }
                user?.save()
                tableView.reloadData()
            }
        }
    }

    @objc func logoutButtonTapped(_ sender: UIButton) {
        UsuarioDAO.sharedInstance.logOut()
        user = nil
        navigationController?.popToRootViewController(animated: true)
    }
    
    @objc func addCriancaButtonTapped(_ sender: UIButton) {
        if sender.tag == 1 {
            let criancaController = self.storyboard?.instantiateViewController(withIdentifier: "CadastroCrianca") as! CadastroCriancaController
            criancaController.user = user!
            criancaController.editandoCadastro = false
            criancaController.fromPerfil = true
            navigationController?.pushViewController(criancaController, animated: true)
        }
    }
    
    @objc func addDesafioButtonTapped(_ sender: UIButton) {
        if sender.tag == 2 {
            self.performSegue(withIdentifier: "SegueCadastroDesafio", sender: self)
        }
    }
    
    @objc func notificacoesButtonTapped(_ sender: UIButton) {
        self.performSegue(withIdentifier: "SegueNotificacoes", sender: true)
    }
    
    @objc func configuracoesButtonTapped(_ sender: UIButton) {
        let responsavelController = self.storyboard?.instantiateViewController(withIdentifier: "CadastroResponsavel") as! CadastroResponsavelController
        responsavelController.user = user!
        responsavelController.editandoCadastro = true
        navigationController?.pushViewController(responsavelController, animated: true)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "SegueCadastroDesafio" {
            let cadastroDesafio = segue.destination as! CadastroDesafioController
            cadastroDesafio.user = user!
        } else if segue.identifier == "SegueNotificacoes" {
            let notificacoesController = segue.destination as! NotificacoesResponsavelViewController
            notificacoesController.user = user!
            notificacoesController.notificacoesDesafioApp = notificacoesDesafioApp
            notificacoesController.notificacoesDesafioCompleto = notificacoesDesafioCompleto
        }
    }
}
