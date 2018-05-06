//
//  DesafioCollectionViewCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class DesafioCollectionViewCell: UICollectionViewCell {
    
    static let kCellDesafioID = "CellDesafio"

    @IBOutlet weak var iconeImageView: UIImageView!
    @IBOutlet weak var nomeLabel: UILabel!
    
    class func defaultIdentifier() -> String {
        return kCellDesafioID
    }
}
