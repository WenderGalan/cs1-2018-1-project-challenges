//
//  NotificationHeaderSectionView.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/6/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class NotificationHeaderSectionView: UITableViewCell {

    static let kNotificationHeaderCellID = "CellNotificationHeader"

    
    @IBOutlet weak var tituloLabel: UILabel!
    @IBOutlet weak var logoutButton: UIButton!
    @IBOutlet weak var notificationBadge: UILabel!
    @IBOutlet weak var notificationButton: UIButton!
    @IBOutlet weak var editarPerfilButton: UIButton!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    class func defaultIdentifier() -> String {
        return kNotificationHeaderCellID
    }
}
