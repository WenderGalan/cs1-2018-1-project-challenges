//
//  HabilidadeDesafioCell.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class HabilidadeDesafioCell: UITableViewCell {

    static let HabilidadeDesafioCellID = "HabilidadeDesafioCell"
    
    @IBOutlet weak var LabelHabilidade: UILabel!
    @IBOutlet weak var imagemHabilidade: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    class func defaultIdentifier() -> String {
        return HabilidadeDesafioCellID
    }

}
