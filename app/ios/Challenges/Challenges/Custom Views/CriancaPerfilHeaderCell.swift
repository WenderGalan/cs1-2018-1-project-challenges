//
//  CriancaPerfilHeaderCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/9/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class CriancaPerfilHeaderCell: UITableViewCell {

    static let kPerfilHeaderCellID = "PerfilHeaderCellID"

    
    @IBOutlet weak var nomeLabel: UILabel!
    @IBOutlet weak var sairContaButton: UIButton!
    @IBOutlet weak var habilidadeLabel: UILabel!
    @IBOutlet weak var editarPerfilImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    class func defaultIdentifier() -> String {
        return kPerfilHeaderCellID
    }
}
