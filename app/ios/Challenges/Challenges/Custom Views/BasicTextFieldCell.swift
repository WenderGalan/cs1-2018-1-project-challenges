//
//  BasicTextFieldCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class BasicTextFieldCell: UITableViewCell {

    static let kCellBasicTextFieldID = "CellTextfield"

    
    @IBOutlet weak var textField: UITextField!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
    class func defaultIdentifier() -> String {
        return kCellBasicTextFieldID
    }
}
