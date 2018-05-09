//
//  CadastroCriancaController.swift
//  Challenges
//
//  Created by Rodolfo Roca on 3/28/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import MobileCoreServices


class CadastroCriancaController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var cadastrarDepoisButton: UIButton!
    
    var user: Responsavel!
    var senhaResponsavel: String!
    
    var crianca: Crianca?
    var foto: UIImage?
    var senha: String?
    var confirmaSenha: String?
    
    var editandoCadastro = false
    var fromPerfil = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        title = "Cadastre uma criança"

        senhaResponsavel = UserDefaults.standard.value(forKey: "Key") as! String
        
        navigationController?.isNavigationBarHidden = false
        
        if fromPerfil || editandoCadastro {
            cadastrarDepoisButton.isHidden = true
        }
        setupTableView()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func setupTableView() {
        
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 44
        tableView.tableFooterView = UIView.init(frame: .zero)
        
        tableView.register(UINib.init(nibName: "FotoPerfilCell", bundle: nil), forCellReuseIdentifier: FotoPerfilCell.defaultIdentifier())
        tableView.register(UINib.init(nibName: "BasicTextFieldCell", bundle: nil), forCellReuseIdentifier: BasicTextFieldCell.defaultIdentifier())
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: FotoPerfilCell.defaultIdentifier(), for: indexPath) as! FotoPerfilCell
            
            cell.fotoPerfilImageView.layer.masksToBounds  = true
            cell.fotoPerfilImageView.layer.cornerRadius = cell.fotoPerfilImageView.frame.size.height / 2

            if let foto = foto {
                cell.fotoPerfilImageView.image = foto
            } else {
                if editandoCadastro {
                    if let criancaFoto = crianca?.fotoURL {
                        let url = URL.init(string: criancaFoto)
                        cell.fotoPerfilImageView.setImageWith(url!, placeholderImage: #imageLiteral(resourceName: "addPhoto"))
                    }
                } else {
                    cell.fotoPerfilImageView.image = #imageLiteral(resourceName: "addPhoto")
                }
            }
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: BasicTextFieldCell.defaultIdentifier(), for: indexPath) as! BasicTextFieldCell
            cell.textField.delegate = self
            
            switch indexPath.row {
            
            case 1:
                cell.textField.text = crianca?.nome
                cell.textField.placeholder = "Nome completo"
                break
            case 2:
                cell.textField.text = crianca?.email
                cell.textField.placeholder = "E-mail"
                
                break
            case 3:
                if editandoCadastro {
                    cell.textField.placeholder = "Senha atual"
                } else {
                    cell.textField.placeholder = "Senha"
                }
                cell.textField.isSecureTextEntry = true

                break
            case 4:
                if editandoCadastro {
                    cell.textField.placeholder = "Nova senha"
                } else {
                    cell.textField.placeholder = "Confirme a senha"
                }
                cell.textField.isSecureTextEntry = true

                break
            default:
                break
            }
            
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if indexPath.row == 0 {
            adicionarFotoTapped()
        } else {
            let cell = tableView.cellForRow(at: indexPath) as! BasicTextFieldCell
            cell.textField.becomeFirstResponder()
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        if crianca == nil {
            crianca = Crianca.sharedInstance
        }
        
        let cell = textField.superview?.superview as! UITableViewCell
        let indexPath = tableView.indexPath(for: cell)
        
        switch indexPath!.row {
        case 1:
            crianca?.nome = textField.text
            break
        case 2:
            crianca?.email = textField.text
            
            break
        case 3:
            senha = textField.text
            
            break
        case 4:
            confirmaSenha = textField.text
            
            break
        default:
            break
        }
    }
    
    // MARK: - Camera Action
    func adicionarFotoTapped() {
        
        let imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        imagePickerController.allowsEditing = true
        imagePickerController.mediaTypes = [kUTTypeImage as String]
        
        let alert = UIAlertController.init(title: "Selecione uma opção", message: "", preferredStyle: .actionSheet)
        let cameraAction = UIAlertAction.init(title: "Câmera", style: .default) { (_) in
            imagePickerController.sourceType = .camera
            self.present(imagePickerController, animated: true, completion: nil)
        }
        
        let photoLibraryAction = UIAlertAction.init(title: "Biblioteca de fotos", style: .default) { (_) in
            imagePickerController.sourceType = .photoLibrary
            self.present(imagePickerController, animated: true, completion: nil)
        }
        
        let cancelAction = UIAlertAction.init(title: "Cancelar", style: .cancel, handler: nil)
        
        alert.addAction(cameraAction)
        alert.addAction(photoLibraryAction)
        alert.addAction(cancelAction)
        
        present(alert, animated: true, completion: nil)
    }
    
    // MARK: - ImagePicker Delegates
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        let image = info[UIImagePickerControllerOriginalImage] as? UIImage
        dismiss(animated:true, completion: nil)
        if let img = image {
            foto = img
            tableView.reloadData()
        }
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func concluirButtonTapped(_ sender: UIButton) {
        view.endEditing(true)
        
        if validarDados() {
            guard let crianca = crianca else {
                return
            }
            
            if editandoCadastro {
                crianca.saveInBackground(success: { (_) in
                    if let senha = self.senha, let nova = self.confirmaSenha {
                        UsuarioDAO.sharedInstance.mudarSenha(senha: senha, novaSenha: nova, success: { (_) in
                            if let foto = self.foto {
                                UsuarioDAO.sharedInstance.salvarFotoPerfil(crianca: crianca, image: foto, success: { (_) in
                                    self.navigationController?.popViewController(animated: true)
                                }, failed: { (error) in
                                    // TODO: Alert error login
                                })
                            } else {
                                self.navigationController?.popViewController(animated: true)
                            }
                        }, failed: { (error) in
                            // TODO: Alert error login
                        })
                    } else {
                        if let foto = self.foto {
                            UsuarioDAO.sharedInstance.salvarFotoPerfil(crianca: crianca, image: foto, success: { (_) in
                                self.navigationController?.popViewController(animated: true)
                            }, failed: { (error) in
                                // TODO: Alert error login
                            })
                        } else {
                            self.navigationController?.popViewController(animated: true)
                        }
                    }
                }) { (error) in
                    // TODO: Alert error login
                }
            } else {
                crianca.responsavel = user
                UsuarioDAO.sharedInstance.cadastrarCrianca(crianca: crianca, senha: senha!, foto: foto, success: { [unowned self] (_) in
                    if let c = self.crianca {
                        self.user.criancas.append(c)
                        self.user.save()
                    }
                    UsuarioDAO.sharedInstance.login(email: self.user.email!, senha: self.senhaResponsavel, success: { (_) in
                        if self.fromPerfil {
                            self.navigationController?.popViewController(animated: true)
                        } else {
                            self.performSegue(withIdentifier: "SeguePerfilResponsavel", sender: self)
                        }
                    }, failed: { (error) in
                        // TODO: Alert error login
                    })
                }) { (error) in
                    // TODO: Alert error cadastro
                }
            }
        } else {
            // TODO: Alert error validacao
        }
    }
    
    @IBAction func cadastrarDepoisButtonTapped(_ sender: UIButton) {
        self.performSegue(withIdentifier: "SeguePerfilResponsavel", sender: self)
    }
    
    func validarDados() -> Bool {
        return true
    }
    
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "SeguePerfilResponsavel" {
            
        }
    }

}
