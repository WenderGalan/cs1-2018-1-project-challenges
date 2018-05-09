//
//  PickerTableViewCell.swift
//  Poink
//
//  Created by Adauto on 16/02/18.
//  Copyright © 2018 JustWorks. All rights reserved.
//

import UIKit

class PickerTableViewCell: UITableViewCell {

    static let kCellPickerID = "pickerCellIdentifier"

    @IBOutlet weak var iconImageView: UIImageView!
    @IBOutlet weak var pickerView: UIPickerView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    class func defaultIdentifier() -> String {
        return kCellPickerID
    }
}
