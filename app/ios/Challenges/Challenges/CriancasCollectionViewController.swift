//
//  CriancasCollectionViewController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import AFNetworking

class CriancasCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    var user: Responsavel!
    lazy var criancas: [Crianca] = [Crianca]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.collectionView!.register(UINib.init(nibName: "CriancaCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: CriancaCollectionViewCell.defaultIdentifier())
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: UICollectionViewDataSource
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return criancas.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let numberOfSets = CGFloat(4)
        let padding = CGFloat(10)
        let paddingSpaces = CGFloat(5)
        
        let width = (collectionView.frame.size.width - (padding * paddingSpaces))/numberOfSets
        
        let height = width + (width / 3)
        
        return CGSize(width: width, height: height)
    }
    
    // UICollectionViewDelegateFlowLayout method
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout,
                        insetForSectionAt section: Int) -> UIEdgeInsets {
        
        let cellWidthPadding = CGFloat(10)
        let cellHeightPadding = CGFloat(10)
        
        return UIEdgeInsets(top: cellHeightPadding,left: cellWidthPadding, bottom: cellHeightPadding,right: cellWidthPadding)
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CriancaCollectionViewCell.defaultIdentifier(), for: indexPath) as! CriancaCollectionViewCell
        
        let crianca = criancas[indexPath.row]
        if let foto = crianca.fotoURL {
            let url = URL.init(string: foto)
            cell.avatarImageView.setImageWith(url!, placeholderImage: #imageLiteral(resourceName: "profilePlaceholderSmall"))
        }

        cell.holdingView.clipsToBounds = true
        cell.holdingView.layer.borderWidth = 1;
        cell.holdingView.layer.borderColor = UIColor.init(red: 236/255, green: 236/255, blue: 236/255, alpha: 1.0).cgColor
        cell.holdingView.layer.cornerRadius = cell.holdingView.frame.size.height / 2

        cell.avatarImageView.layer.masksToBounds  = true
        cell.avatarImageView.layer.cornerRadius = cell.avatarImageView.frame.size.height / 2

        cell.nomeLabel.text = crianca.nome!
        
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        
        let criancaController = self.storyboard?.instantiateViewController(withIdentifier: "CadastroCrianca") as! CadastroCriancaController
        criancaController.user = user!
        criancaController.crianca = criancas[indexPath.row]
        criancaController.editandoCadastro = true
        criancaController.fromPerfil = true
        navigationController?.pushViewController(criancaController, animated: true)
    }

    // MARK: UICollectionViewDelegate

    /*
    // Uncomment this method to specify if the specified item should be highlighted during tracking
    override func collectionView(_ collectionView: UICollectionView, shouldHighlightItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment this method to specify if the specified item should be selected
    override func collectionView(_ collectionView: UICollectionView, shouldSelectItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment these methods to specify if an action menu should be displayed for the specified item, and react to actions performed on the item
    override func collectionView(_ collectionView: UICollectionView, shouldShowMenuForItemAt indexPath: IndexPath) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, canPerformAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, performAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) {
    
    }
    */

}
