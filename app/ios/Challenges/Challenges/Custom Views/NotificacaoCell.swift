//
//  NotificacaoCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/14/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class NotificacaoCell: UITableViewCell {

    static let kNotificacaoCellID = "NotificacaoCellID"

    @IBOutlet weak var holdingView: UIView!
    @IBOutlet weak var avatarImageView: UIImageView!
    @IBOutlet weak var tituloLabel: UILabel!
    @IBOutlet weak var aceitarButton: UIButton!
    @IBOutlet weak var recusarButton: UIButton!
    
    @IBOutlet weak var avatarLeading: NSLayoutConstraint!
    @IBOutlet weak var avatarTop: NSLayoutConstraint!
    @IBOutlet weak var avatarTrailing: NSLayoutConstraint!
    @IBOutlet weak var avatarBottom: NSLayoutConstraint!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        aceitarButton.layer.masksToBounds = true
        recusarButton.layer.masksToBounds = true
        aceitarButton.layer.cornerRadius = 5
        recusarButton.layer.cornerRadius = 5
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    class func defaultIdentifier() -> String {
        return kNotificacaoCellID
    }
    
}
