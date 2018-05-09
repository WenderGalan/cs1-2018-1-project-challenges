//
//  EstatisticaCell.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/9/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class EstatisticaCell: UITableViewCell {

    static let kEstatisticaCellID = "EstatisticaCell"

    
    @IBOutlet weak var dadoUmLabel: UILabel!
    @IBOutlet weak var dadoDoisLabel: UILabel!
    @IBOutlet weak var dadoTresLabel: UILabel!
    @IBOutlet weak var dadoQuatroLabel: UILabel!
    
    @IBOutlet weak var tipoDadoUmLabel: UILabel!
    @IBOutlet weak var tipoDadoDoisLabel: UILabel!
    @IBOutlet weak var tipoDadoTresLabel: UILabel!
    @IBOutlet weak var tipoDadoQuatroLabel: UILabel!
    

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    class func defaultIdentifier() -> String {
        return kEstatisticaCellID
    }
    
}
