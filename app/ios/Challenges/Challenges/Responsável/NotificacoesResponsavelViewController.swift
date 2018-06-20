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
    @IBOutlet weak var tableView: UITableView!
    
    var user: Responsavel!
    var notificacoesDesafioCompleto: [NotificacaoDesafio]!
    var notificacoesDesafioApp: [NotificacaoDesafio]!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Notificações"
        
        navigationController?.isNavigationBarHidden = false
        navigationController?.navigationBar.tintColor = UIColor.init(red: 123/255, green: 103/255, blue: 255/255, alpha: 1.0)
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.foregroundColor : UIColor.init(red: 123/255, green: 103/255, blue: 255/255, alpha: 1.0), NSAttributedStringKey.font : UIFont.init(name: "DK Cool Crayon", size: 16)!]
        
        setupTableView()
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
        
        tableView.register(UINib.init(nibName: "DefaultSectionHeaderView", bundle: nil), forHeaderFooterViewReuseIdentifier: DefaultSectionHeaderView.defaultIdentifier())
        tableView.register(UINib.init(nibName: "NotificacaoCell", bundle: nil), forCellReuseIdentifier: NotificacaoCell.defaultIdentifier())
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: DefaultSectionHeaderView.defaultIdentifier()) as! DefaultSectionHeaderView
        
        headerView.plusButton.isHidden = true
        headerView.tituloLabel.textColor = UIColor.init(red: 123/255, green: 103/255, blue: 255/255, alpha: 1.0)
        headerView.borderView.isHidden = false
        
        if segmentControl.selectedSegmentIndex == 0 {
            headerView.tituloLabel.text = "PEDIDOS DE\nAMIZADE"

        } else if segmentControl.selectedSegmentIndex == 1 {
            headerView.tituloLabel.text = "DESAFIOS\nSUGERIDOS"

        } else {
            headerView.tituloLabel.text = "DESAFIOS\nCOMPLETOS"
        }
        return headerView;
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if segmentControl.selectedSegmentIndex == 0 {
            return 0
        } else if segmentControl.selectedSegmentIndex == 1 {
            return notificacoesDesafioApp.count
        }
        return notificacoesDesafioCompleto.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            
        let cell = tableView.dequeueReusableCell(withIdentifier: NotificacaoCell.defaultIdentifier(), for: indexPath) as! NotificacaoCell
        
        if segmentControl.selectedSegmentIndex == 0 {
            
        } else if segmentControl.selectedSegmentIndex == 1 {
            let notificacao = notificacoesDesafioApp[indexPath.row]
            
            cell.avatarLeading.constant = 0
            cell.avatarTop.constant = 0
            cell.avatarTrailing.constant = 0
            cell.avatarBottom.constant = 0
            
            cell.avatarImageView.image = notificacao.desafio?.habilidade?.iconePequeno
            cell.tituloLabel.text = notificacao.desafio?.nome
        } else if segmentControl.selectedSegmentIndex == 2 {
            let notificacao = notificacoesDesafioCompleto[indexPath.row]
            
            cell.avatarLeading.constant = 0
            cell.avatarTop.constant = 0
            cell.avatarTrailing.constant = 0
            cell.avatarBottom.constant = 0
            
            cell.avatarImageView.image = notificacao.desafio?.habilidade?.iconePequeno
            cell.tituloLabel.text = notificacao.desafio?.nome
        }
        
        cell.aceitarButton.tag = indexPath.row
        cell.recusarButton.tag = indexPath.row
        
        cell.aceitarButton.addTarget(self, action: #selector(aceitarButtonTapped(_:)), for: .touchUpInside)
        cell.recusarButton.addTarget(self, action: #selector(recusarButtonTapped(_:)), for: .touchUpInside)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    @IBAction func segmentedControlValueDidChange(_ sender: UISegmentedControl) {
        tableView.reloadData()
    }
    
    @objc func aceitarButtonTapped(_ sender: UIButton) {
        let index = sender.tag
        if segmentControl.selectedSegmentIndex == 0 {
            
        } else if segmentControl.selectedSegmentIndex == 1 {
            let notificacao = notificacoesDesafioApp[index]

            let meuDesafio = Desafio.init()
            meuDesafio.dataCriacao = Date.init(timeIntervalSinceNow: 0)
            meuDesafio.dataUpdate = Date.init(timeIntervalSinceNow: 0)
            meuDesafio.completado = false
            meuDesafio.checado = false
            meuDesafio.crianca = notificacao.crianca
            meuDesafio.responsavel = user
            meuDesafio.frequencia = notificacao.desafio?.frequencia
            meuDesafio.habilidade = notificacao.desafio?.habilidade
            meuDesafio.nome = notificacao.desafio?.nome
            meuDesafio.pontos = notificacao.desafio?.pontos
            meuDesafio.recompensa = notificacao.desafio?.recompensa
            
            meuDesafio.saveInBackground(success: { (_) in
                notificacao.delete()
                self.tableView.reloadData()
                self.navigationController?.popViewController(animated: true)
            }) { (error) in
                
            }
        } else if segmentControl.selectedSegmentIndex == 2 {
            let notificacao = notificacoesDesafioCompleto[index]
            notificacao.desafio?.checado = true
            notificacao.desafio?.saveInBackground(success: { (_) in
                self.notificacoesDesafioCompleto.remove(at: index)
                let somaPontos = (notificacao.crianca?.pontos)! + (notificacao.desafio?.pontos)!
                notificacao.crianca?.pontos = somaPontos
                notificacao.crianca?.desafios = (notificacao.crianca?.desafios)! + 1
                notificacao.crianca?.recompensas = (notificacao.crianca?.recompensas)! + 1
                notificacao.crianca?.save()
                notificacao.delete()
                self.tableView.reloadData()
            }, failed: { (error) in
                
            })
        }
    }
    
    @objc func recusarButtonTapped(_ sender: UIButton) {
        let index = sender.tag
        if segmentControl.selectedSegmentIndex == 0 {
            
        } else if segmentControl.selectedSegmentIndex == 1 {
            let notificacao = notificacoesDesafioApp[index]

            notificacao.delete()
            self.tableView.reloadData()
            self.navigationController?.popViewController(animated: true)
        } else if segmentControl.selectedSegmentIndex == 2 {
            let notificacao = notificacoesDesafioCompleto[index]
            notificacao.desafio?.checado = false
            notificacao.desafio?.completado = false
            notificacao.desafio?.saveInBackground(success: { (_) in
                self.notificacoesDesafioCompleto.remove(at: index)
                notificacao.delete()
                self.tableView.reloadData()
            }, failed: { (error) in
                
            })
        }
    }
}
