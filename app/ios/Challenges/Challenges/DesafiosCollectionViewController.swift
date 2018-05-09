//
//  DesafiosCollectionViewController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit


class DesafiosCollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    lazy var desafios: [Desafio] = [Desafio]()
    var tipoDesafio: DesafioViewController.DesafioType!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.collectionView!.register(UINib.init(nibName: "DesafioCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: DesafioCollectionViewCell.defaultIdentifier())
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
        return desafios.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let numberOfSets = CGFloat(2)
        let padding = CGFloat(10)
        let paddingSpaces = CGFloat(3)
        
        let width = (collectionView.frame.size.width - (padding * paddingSpaces))/numberOfSets
        
        let height = width
        
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
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: DesafioCollectionViewCell.defaultIdentifier(), for: indexPath) as! DesafioCollectionViewCell
        
        let desafio = desafios[indexPath.row]
        cell.iconeImageView.image = desafio.habilidade?.iconeGrande
        cell.iconeImageView.layer.masksToBounds  = true
        cell.iconeImageView.layer.cornerRadius = 5
        
        cell.nomeLabel.text = desafio.nome
        
        return cell
    }

    override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        let dvc = storyboard.instantiateViewController(withIdentifier: "DesafioVC") as! DesafioViewController
        dvc.desafio = desafios[indexPath.row]
        dvc.tipoDesafio = .responsavel
        navigationController?.pushViewController(dvc, animated: true)
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
