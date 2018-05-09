//
//  HabilidadeDesafioCell.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class HabilidadeDesafioCell: UITableViewCell {

    static let kHabilidadeDesafilCellID = "HabilidadeDesafioCell"
    
    @IBOutlet weak var holdingView: UIView!
    @IBOutlet weak var nomeHabilidadeLabel: UILabel!
    @IBOutlet weak var iconeHabilidadeImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    class func defaultIdentifier() -> String {
        return kHabilidadeDesafilCellID
    }

}
