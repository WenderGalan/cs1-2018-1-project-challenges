//
//  DesafioViewController.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MBProgressHUD

extension UIView {
    // Drop Shadow
    func addDefaultDropShadow() {
        let defaultOffset: CGSize = CGSize(width: 0, height: 0)
        let defaultRadius: CGFloat = 2.0
        let defaultOpacity: Float = 1.0
        
        self.addDropShadow(color: UIColor.lightGray, offSet: defaultOffset, radius: defaultRadius, opacity: defaultOpacity)
    }
    
    func addDropShadow(color: UIColor, offSet: CGSize, radius: CGFloat, opacity: Float) {
        self.layer.shadowColor = color.cgColor
        self.layer.shadowOffset = offSet
        self.layer.shadowRadius = radius
        self.layer.shadowOpacity = opacity
    }
    

    // Border
    func addDefaultUnderBorder() {
        let defaultWidth: CGFloat = 1.0
        let defaultColor = UIColor.init(red: 199/255, green: 199/255, blue: 199/255, alpha: 1.0)
        self.addBorder(color: defaultColor, width: defaultWidth, frame: CGRect(x: 0, y: self.frame.size.height - defaultWidth, width: self.frame.size.width, height: defaultWidth))
    }
    
    func addBorder(color: UIColor, width: CGFloat, frame: CGRect) {
        let border = CALayer.init()
        border.borderColor = color.cgColor
        border.frame = frame
        border.borderWidth = width
        
        self.layer.addSublayer(border)
        self.layer.masksToBounds = true
    }
}

class DesafioViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var tableView: UITableView!    
    @IBOutlet weak var holdingView: UIView!
    @IBOutlet weak var primeiroButton: UIButton!
    @IBOutlet weak var segundoButton: UIButton!
    
    var desafio: Desafio!
    var tipoDesafio: DesafioType!
    
    enum DesafioType {
        case padrao
        case aplicativo
        case responsavel
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // verde rgba(42,201,64,1)
        // azul rgba(74,144,226,1)
        // vermelho rgba(226,74,74,1)
        
        title = "Desafio"
        
        tabBarController?.tabBar.isHidden = true
        
        holdingView.addDefaultDropShadow()
        
        switch tipoDesafio {
        case .padrao:
            if desafio.checado != nil && desafio.checado! {
                self.primeiroButton.setTitle("Desafio Realizado", for: .normal)
            } else if desafio.completado != nil && desafio.completado! {
                self.primeiroButton.setTitle("Aguardando confirmação", for: .normal)
            } else {
                primeiroButton.setTitle("Completar desafio", for: .normal)
            }
            
            primeiroButton.backgroundColor = UIColor.init(red: 42/255, green: 201/255, blue: 64/255, alpha: 1.0)
            
            segundoButton.setTitle("Pedir ajuda", for: .normal)
            segundoButton.backgroundColor = UIColor.init(red: 74/255, green: 144/255, blue: 226/255, alpha: 1.0)
        case .aplicativo:
            primeiroButton.setTitle("Aceitar desafio", for: .normal)
            segundoButton.backgroundColor = UIColor.init(red: 74/255, green: 144/255, blue: 226/255, alpha: 1.0)

            segundoButton.isHidden = true
        case .responsavel:
            primeiroButton.setTitle("Editar desafio", for: .normal)
            primeiroButton.backgroundColor = UIColor.init(red: 74/255, green: 144/255, blue: 226/255, alpha: 1.0)

            segundoButton.setTitle("Apagar desafio", for: .normal)
            segundoButton.backgroundColor = UIColor.init(red: 226/255, green: 74/255, blue: 74/255, alpha: 1.0)
        default:
            break
        }
        
        setupTableView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.isNavigationBarHidden = false
        navigationController?.navigationBar.tintColor = UIColor.init(red: 142/255, green: 223/255, blue: 64/255, alpha: 1.0)
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey.foregroundColor : UIColor.init(red: 142/255, green: 223/255, blue: 64/255, alpha: 1.0), NSAttributedStringKey.font : UIFont.init(name: "DK Cool Crayon", size: 16)!]
        
        tableView.reloadData()
    }
    
    func setupTableView() {
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)

        tableView.register(UINib.init(nibName: "DefaultSectionHeaderView", bundle: nil), forHeaderFooterViewReuseIdentifier: DefaultSectionHeaderView.defaultIdentifier())
        tableView.register(UINib.init(nibName: "EstatisticaCell", bundle: nil), forCellReuseIdentifier: EstatisticaCell.defaultIdentifier())
        tableView.register(UINib.init(nibName: "HabilidadeDesafioCell", bundle: nil), forCellReuseIdentifier: HabilidadeDesafioCell.defaultIdentifier())
        tableView.register(UINib.init(nibName: "BasicLabelCell", bundle: nil), forCellReuseIdentifier: BasicLabelCell.defaultIdentifier())
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = tableView.dequeueReusableHeaderFooterView(withIdentifier: DefaultSectionHeaderView.defaultIdentifier()) as! DefaultSectionHeaderView
        
        headerView.plusButton.isHidden = true
        headerView.borderView.isHidden = true
        headerView.tituloLabel.textColor = UIColor.init(red: 142/255, green: 223/255, blue: 64/255, alpha: 1.0)
        
        switch section {
        case 0:
            headerView.tituloLabel.text = "DESAFIO"

        case 1:
            headerView.tituloLabel.text = "FREQUÊNCIA"

        case 2:
            headerView.tituloLabel.text = "HABILIDADE"

        case 3:
            headerView.tituloLabel.text = "RECOMPENSA"

        case 4:
            headerView.tituloLabel.text = "PROGRESSO"

        default:
            break
        }
        
        return headerView;
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 3 && desafio.recompensa != nil {
            return 2
        }
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 2 {
            let cell = tableView.dequeueReusableCell(withIdentifier: HabilidadeDesafioCell.defaultIdentifier(), for: indexPath) as! HabilidadeDesafioCell
            
            cell.nomeHabilidadeLabel.text = desafio.nome
            cell.iconeHabilidadeImageView.image = desafio.habilidade?.iconePequeno
            cell.holdingView.addDefaultUnderBorder()
            
            return cell
        } else if indexPath.section == 4 {
            let cell = tableView.dequeueReusableCell(withIdentifier: EstatisticaCell.defaultIdentifier(), for: indexPath) as! EstatisticaCell
            
            cell.tipoDadoUmLabel.text = "Pontos"
            cell.tipoDadoDoisLabel.text = "Ajuda"
            cell.tipoDadoTresLabel.text = "Não feito"
            cell.tipoDadoQuatroLabel.text = "Recompensas"
            
            cell.dadoUmLabel.text = "\(desafio.pontos!)"
            cell.dadoDoisLabel.text = "0"
            cell.dadoTresLabel.text = "0"
            cell.dadoQuatroLabel.text = "1"
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: BasicLabelCell.defaultIdentifier(), for: indexPath) as! BasicLabelCell

            if indexPath.section == 0 {
                cell.textoLabel.text = desafio.nome
                cell.textoLabel.addDefaultUnderBorder()
            } else if indexPath.section == 1 {
                cell.textoLabel.text = desafio.frequencia
                cell.textoLabel.addDefaultUnderBorder()
            } else {
                if indexPath.row == 0 {
                    cell.textoLabel.text = "Pontos: \(desafio.pontos!)"
                } else {
                    cell.textoLabel.text = "Recompensa: \(desafio.recompensa!)"
                    cell.textoLabel.addDefaultUnderBorder()
                }
            }
            
            return cell
        }
    }
    
    @IBAction func firstButtonTapped(_ sender: UIButton) {
        switch tipoDesafio {
        case .responsavel:
            let desafioController = self.storyboard?.instantiateViewController(withIdentifier: "CadastroDesafio") as! CadastroDesafioController
            desafioController.user = desafio.responsavel!
            desafioController.editandoCadastro = true
            desafioController.desafio = desafio
            navigationController?.pushViewController(desafioController, animated: true)
            
        case .padrao:
            if desafio.checado == nil {
                if desafio.completado == nil {
                    MBProgressHUD.showAdded(to: view, animated: true)

                    desafio.completado = true
                    desafio.saveInBackground(success: { (_) in
                        NotificacoesDAO.sharedInstance.notificarDesafioCompleto(desafio: self.desafio)
                        self.primeiroButton.setTitle("Aguardando confirmação", for: .normal)
                        MBProgressHUD.hide(for: self.view, animated: true)
                    }) { (error) in
                        MBProgressHUD.hide(for: self.view, animated: true)
                    }
                }
            }
            
        case .aplicativo:
            var user = Crianca.sharedInstance
            UsuarioDAO.sharedInstance.getUsuarioInfo(objectId: user!.objectId!, success: { (crianca) in
                NotificacoesDAO.sharedInstance.notificarInteresseDesafioApp(desafio: self.desafio, crianca: crianca as! Crianca)
                self.navigationController?.popViewController(animated: true)
            }) { (error) in

            }
            
        default:
            break
        }
    }
    
    @IBAction func secondButtonTapped(_ sender: UIButton) {
        switch tipoDesafio {
        case .responsavel:
            desafio.deleteInBackground(success: { (_) in
                self.navigationController?.popViewController(animated: true)
            }) { (error) in
                AlertHelper.sharedInstance.createFirestoreErrorAlert(error: error!, from: self)
            }
        
        
        default:
            break
        }
    }
}


