//
//  BasicLabelCell.swift
//  Challenges
//
//  Created by Paulo Renan on 27/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class BasicLabelCell: UITableViewCell {

    static let basicLabelCellID = "BasicLabelCell"
    
    @IBOutlet weak var textoLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

    class func defaultIdentifier() -> String {
        return basicLabelCellID
    }
    
}
