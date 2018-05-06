//
//  CriancaCollectionViewCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class CriancaCollectionViewCell: UICollectionViewCell {
    
    static let kCellCriancaID = "CellCrianca"
    
    @IBOutlet weak var holdingView: UIView!
    @IBOutlet weak var avatarImageView: UIImageView!
    @IBOutlet weak var nomeLabel: UILabel!
    
    class func defaultIdentifier() -> String {
        return kCellCriancaID
    }
    
}
