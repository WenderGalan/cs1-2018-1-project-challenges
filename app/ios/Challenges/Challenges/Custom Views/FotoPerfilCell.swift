//
//  FotoPerfilCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class FotoPerfilCell: UITableViewCell {
    static let kCellBasicTextFieldID = "CellAddFoto"

    @IBOutlet weak var fotoPerfilImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
    }

    class func defaultIdentifier() -> String {
        return kCellBasicTextFieldID
    }
}
