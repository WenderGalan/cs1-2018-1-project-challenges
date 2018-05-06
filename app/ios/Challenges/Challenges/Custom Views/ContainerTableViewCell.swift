//
//  ContainerTableViewCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class ContainerTableViewCell: UITableViewCell {

    static let kContainerCellID = "CellContainer"

    @IBOutlet weak var containerView: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    class func defaultIdentifier() -> String {
        return kContainerCellID
    }
}
