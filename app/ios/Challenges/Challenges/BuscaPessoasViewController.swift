//
//  BuscaPessoasViewController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 6/3/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import AFNetworking

class BuscaPessoasViewController: UIViewController, UISearchBarDelegate, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!
    
    var user: Crianca!
    lazy var contatos: [Crianca] = Array<Crianca>.init()
    lazy var iniciais: [String] = Array<String>()
    lazy var filteredContacts: [Dictionary<String, Any>] = Array<Dictionary<String, Any>>.init()
    lazy var filteredContactsCopy: [Dictionary<String, Any>] = Array<Dictionary<String, Any>>.init()
    var isSearching = false
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.title = "Novo Chat"
//        filtrarPorInicial()
        
        searchBar.backgroundImage = UIImage()
        searchBar.barTintColor = UIColor.init(red: 241/255, green: 241/255, blue: 241/255, alpha: 1.0)
        searchBar.backgroundColor = UIColor.init(red: 241/255, green: 241/255, blue: 241/255, alpha: 1.0)
        searchBar.addDefaultDropShadow()
        searchBar.delegate = self
        self.setupTableView()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func setupTableView() {
        self.tableView.dataSource = self
        self.tableView.delegate = self
        self.tableView.estimatedRowHeight = 44
        self.tableView.rowHeight = UITableViewAutomaticDimension
        tableView.register(UINib.init(nibName: "NotificacaoCell", bundle: nil), forCellReuseIdentifier: NotificacaoCell.defaultIdentifier())
        self.tableView.tableFooterView = UIView.init(frame: .zero)
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
//        if isSearching {
//            return filteredContactsCopy.count
//        }
//        return filteredContacts.count
        return 1
    }
    
//    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
//        return 44
//    }
//
//    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
//        var dict: Dictionary<String, Any>
//        if isSearching {
//            dict = filteredContactsCopy[section]
//        } else {
//            dict = filteredContacts[section]
//        }
//        return dict["inicial"] as? String
//    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        var dict: Dictionary<String, Any>
//        if isSearching {
//            dict = filteredContactsCopy[section]
//        } else {
//            dict = filteredContacts[section]
//        }
//        let contacts = dict["contato"] as! [Crianca]
        
        return contatos.count
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.separatorInset = .zero
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: NotificacaoCell.defaultIdentifier(), for: indexPath) as! NotificacaoCell

//        let dict = filteredContacts[indexPath.section] as! Dictionary<String, Any>
//        let contacts = dict["contato"] as! [Crianca]

        let crianca = contatos[indexPath.row]
        
//        cell.avatarLeading.constant = 0
//        cell.avatarTop.constant = 0
//        cell.avatarTrailing.constant = 0
//        cell.avatarBottom.constant = 0


        if let foto = crianca.fotoURL {
            cell.avatarImageView.setImageWith(URL.init(string: foto)!)
        }
        cell.avatarImageView.layer.masksToBounds = true
        cell.avatarImageView.layer.cornerRadius = cell.avatarImageView.frame.size.width / 2
        
        cell.avatarImageView.clipsToBounds = true
        cell.tituloLabel.text = crianca.nome
        
        cell.aceitarButton.tag = indexPath.row
        
        cell.aceitarButton.setTitle("Adicionar como amigo", for: .normal)
        cell.aceitarButton.addTarget(self, action: #selector(adicionarButtonTapped(_:)), for: .touchUpInside)
        
        cell.recusarButton.isHidden = true
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        
    }
    
//    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
//        if searchText.count == 0 {
//            isSearching = false
//            filteredContactsCopy.removeAll()
//        } else {
//            isSearching = true
//            filteredContactsCopy.removeAll()
//            for dict in filteredContacts {
//                searchContacts(dict, searchText)
//            }
//        }
//        self.tableView.reloadData()
//    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        if let searchText = searchBar.text {
            contatos.removeAll()
            if searchText != "" {
                searchContacts(searchText)
            }
        }
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        isSearching = false
        filteredContactsCopy.removeAll()
        self.tableView.reloadData()
    }
    
    fileprivate func searchContacts(_ searchText: String) {
        UsuarioDAO.sharedInstance.buscarPessoas(nome: searchText, success: { (array) in
            
            self.contatos = array
            self.tableView.reloadData()
        }) { (error) in
            
        }
        
        
        
//        let contacts = dict["contato"] as! [Amigo]
//        let filtered = contacts.filter({ (contact) -> Bool in
//            let match = contact.nome.lowercased().range(of: searchText.lowercased())
//            return match != nil ? true : false
//        })
//        if filtered.count > 0 {
//            let filteredDict = ["inicial" : dict["inicial"]!, "contato" : filtered]  as [String : Any]
//            filteredContactsCopy.append(filteredDict)
//        }
    }
    
    fileprivate func filtrarPorInicial() {
        filteredContacts.removeAll()
        self.iniciais.removeAll()
        var inicialSet = Set<String>()
        for contato in self.contatos {
            let inicial = contato.nome?.substring(to: (contato.nome?.index((contato.nome?.startIndex)!, offsetBy: 1))!)
            inicialSet.insert((inicial?.uppercased())!)
        }
        
        self.iniciais.append(contentsOf: inicialSet)
        self.iniciais.sort()
        
        for inicial in self.iniciais {
            let predicate = NSPredicate.init(format: "nome BEGINSWITH %@", inicial)
            
            var contacts = (self.contatos as NSArray).filtered(using: predicate) as! [Crianca]
            
            contacts = contacts.sorted(by: { $0.nome! < $1.nome! })
            
            let dict = ["inicial" : inicial, "contato" : contacts] as [String : Any]
            
            self.filteredContacts.append(dict)
            self.filteredContacts.sort(by: { (dictOne, dictTwo) -> Bool in
                return (dictOne["inicial"] as! String) < (dictTwo["inicial"] as! String)
            })
        }
    }
    
    @objc func adicionarButtonTapped(_ sender: UIButton) {
//        let dict = filteredContacts[indexPath.section] as! Dictionary<String, Any>
//        let contacts = dict["contato"] as! [Crianca]
//
        let crianca = contatos[sender.tag]
        
        NotificacoesDAO.sharedInstance.notificarPedidoAmizade(amigo: crianca, crianca: user)
        sender.setTitle("Pedido enviado", for: .normal)
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
