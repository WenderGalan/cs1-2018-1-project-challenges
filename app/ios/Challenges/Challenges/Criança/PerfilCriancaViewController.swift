//
//  PerfilCriancaViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MBProgressHUD
import AFNetworking

class PerfilCriancaViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tableView: UITableView!
    
    var userResponsavel: Responsavel?
    var user: Crianca?
    lazy var amigos: [Crianca] = [Crianca]()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        if user == nil {
            MBProgressHUD.showAdded(to: view, animated: true)
            user = Crianca.sharedUser()
            UsuarioDAO.sharedInstance.getUsuarioInfo(objectId: user!.objectId!, success: { (crianca) in
                self.user = crianca as? Crianca
                self.setupTableView()
                MBProgressHUD.hide(for: self.view, animated: true)

            }) { (error) in
                MBProgressHUD.hide(for: self.view, animated: true)
            }
        } else {
            setupTableView()
        }
    }

    func setupTableView() {
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)
        
        tableView.register(UINib.init(nibName: "EstatisticaCell", bundle: nil), forCellReuseIdentifier: EstatisticaCell.defaultIdentifier())
        tableView.register(UINib.init(nibName: "CriancaPerfilHeaderCell", bundle: nil), forCellReuseIdentifier: CriancaPerfilHeaderCell.defaultIdentifier())
        tableView.register(UINib.init(nibName: "DefaultSectionHeaderView", bundle: nil), forHeaderFooterViewReuseIdentifier: DefaultSectionHeaderView.defaultIdentifier())
        tableView.register(UINib.init(nibName: "ContainerTableViewCell", bundle: nil), forCellReuseIdentifier: ContainerTableViewCell.defaultIdentifier())
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if section == 0 {
            return 0
        }
        return 50
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if section == 0 {
            return UIView.init()
        }
        
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: DefaultSectionHeaderView.defaultIdentifier()) as! DefaultSectionHeaderView
        
        headerView.plusButton.tag = section
        if section == 1 {
            headerView.tituloLabel.text = "AMIGOS"
            headerView.plusButton.addTarget(self, action: #selector(addAmigoButtonTapped(_:)), for: .touchUpInside)
            headerView.borderView.isHidden = true
        }
        
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
                let cell = tableView.dequeueReusableCell(withIdentifier: CriancaPerfilHeaderCell.defaultIdentifier(), for: indexPath) as! CriancaPerfilHeaderCell
                
                cell.nomeLabel.text = user?.nome
                cell.sairContaButton.addTarget(self, action: #selector(logoutButtonTapped(_:)), for: .touchUpInside)
                cell.habilidadeLabel.text = "Intelectual"
                
                if let fotoURL = user?.fotoURL {
                    cell.editarPerfilImageView.setImageWith(URL.init(string: fotoURL)!, placeholderImage: #imageLiteral(resourceName: "profilePlaceholderBig"))
                }
                cell.editarPerfilImageView.layer.masksToBounds  = true
                cell.editarPerfilImageView.layer.cornerRadius = cell.editarPerfilImageView.frame.size.height / 2
                
                
                let tap = UITapGestureRecognizer.init(target: self, action: #selector(configuracoesButtonTapped(_:)))
                cell.editarPerfilImageView.addGestureRecognizer(tap)
                
                return cell
            } else {
                let cell = tableView.dequeueReusableCell(withIdentifier: EstatisticaCell.defaultIdentifier(), for: indexPath) as! EstatisticaCell
                
                cell.tipoDadoUmLabel.text = "Desafios"
                cell.tipoDadoDoisLabel.text = "Pontos"
                cell.tipoDadoTresLabel.text = "Amigos"
                cell.tipoDadoQuatroLabel.text = "Recompensas"
                
                cell.dadoUmLabel.text = "\(user!.desafios)"
                cell.dadoDoisLabel.text = "\(user!.pontos)"
                cell.dadoTresLabel.text = "0"
                cell.dadoQuatroLabel.text = "\(user!.recompensas)"
                
                return cell
            }
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: ContainerTableViewCell.defaultIdentifier(), for: indexPath) as! ContainerTableViewCell
            let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
            
            if indexPath.section == 1 {
                let ccvc = storyboard.instantiateViewController(withIdentifier: "CriancasCVC") as! CriancasCollectionViewController
                ccvc.criancas = user!.amigos!
                ccvc.userCrianca = user!
                self.addChildViewController(ccvc)
                var frame = cell.containerView.frame
                frame.origin.y = 0
                ccvc.view.frame = frame
                cell.containerView.addSubview(ccvc.view)
                ccvc.didMove(toParentViewController: self)
                
            }
            return cell
        }
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    @objc func logoutButtonTapped(_ sender: UIButton) {
        UsuarioDAO.sharedInstance.logOut()
        user = nil
        tabBarController?.navigationController?.popToRootViewController(animated: true)
    }
    
    @objc func addAmigoButtonTapped(_ sender: UIButton) {
        if sender.tag == 1 {
//            let criancaController = self.storyboard?.instantiateViewController(withIdentifier: "CadastroCrianca") as! CadastroCriancaController
//            criancaController.user = user!
//            criancaController.editandoCadastro = false
//            criancaController.fromPerfil = true
//            navigationController?.pushViewController(criancaController, animated: true)
        }
    }
    
    @objc func configuracoesButtonTapped(_ sender: UITapGestureRecognizer) {
        if let responsavel = userResponsavel {
            let criancaController = self.storyboard?.instantiateViewController(withIdentifier: "CadastroCrianca") as! CadastroCriancaController
            criancaController.user = responsavel
            criancaController.crianca = user!
            criancaController.editandoCadastro = true
            criancaController.fromPerfil = true
            navigationController?.pushViewController(criancaController, animated: true)
        }

    }
}
