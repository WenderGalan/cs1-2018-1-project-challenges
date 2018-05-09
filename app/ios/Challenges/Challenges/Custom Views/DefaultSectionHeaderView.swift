//
//  BasicLabelHeaderView.swift
//  Challenges
//
//  Created by Catwork on 04/05/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class DefaultSectionHeaderView: UITableViewHeaderFooterView {

    static let kDefaultHeaderID = "DefaultHeaderView"

    @IBOutlet weak var tituloLabel: UILabel!
    @IBOutlet weak var plusButton: UIButton!
    @IBOutlet weak var borderView: UIView!
    
    class func defaultIdentifier() -> String {
        return kDefaultHeaderID
    }
}
