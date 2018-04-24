//
//  NotificacaoResponsavelTableViewCell.swift
//  Challenges
//
//  Created by Catwork on 23/04/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class NotificacaoResponsavelTableViewCell: UITableViewCell {

    @IBOutlet weak var imagem: UIImageView!
    @IBOutlet weak var descricao: UILabel!
    @IBOutlet weak var botaoAceitar: UIButton!
    @IBOutlet weak var botaoRecusar: UIButton!
    
    @IBAction func aceitar(_ sender: UIButton) {
        
    }
    @IBAction func recusar(_ sender: UIButton) {
        
    }
    
    override func layoutSubviews() {
        botaoAceitar.layer.cornerRadius = 2
        botaoRecusar.layer.cornerRadius = 2
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
