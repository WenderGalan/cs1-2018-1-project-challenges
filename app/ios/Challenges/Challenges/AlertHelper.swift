//
//  AlertHelper.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/9/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit
import Firebase
import FirebaseFirestore
import FirebaseAuth

class AlertHelper: NSObject {

    static let sharedInstance = AlertHelper()

    enum InternalErrorType: Int {
        case semInternet
        case camposObrigatorios
        case esqueciSenha
        case emailInvalido
        case senhasDiferentes
        case desconhecido
    }
    
    func createInternalErrorAlert(error: InternalErrorType, camposObrigatorios: [String]?, from controller: UIViewController) {
        let alert = UIAlertController.init(title: "", message: "", preferredStyle: .alert)
        let action = UIAlertAction.init(title: "Ok", style: .default, handler: nil)
        
        alert.addAction(action)
        
        switch error {
        case .semInternet:
            alert.title = "Sem conexão"
            alert.message = "O aplicativo não detectou uma conexão com a internet, por favor, conecte-se e tente novamente."
            break;
            
        case .camposObrigatorios:
            alert.title = "Campos obrigatórios"
            alert.message = "A tentativa de conexão com o servidor falhou, por favor, verifique sua conexão com a internet e tente novamente em alguns minutos."
            
            if let campos = camposObrigatorios {
                var campo = ""
                for i in 0..<campos.count {
                    if i == campos.count - 1 {
                        campo.append(campos[i])
                    } else {
                        campo.append(", \(campos[i])")
                    }
                }
                alert.message = "Os campos \(campo) são obrigatórios, por favor, preencha-os e tente novamente."
            } else {
                alert.message = "Todos os campos são obrigatórios, por favor, preencha-os e tente novamente."
            }
            break;
            
        case .esqueciSenha:
            alert.title = "Ocorreu um erro"
            alert.message = "E-mail digitado incorretamente, por favor tente novamente."
            break;
            
        case .emailInvalido:
            alert.title = "Email inválido"
            alert.message = "O endereço de email digitado não é valido, por favor, corrija e tente novamente."
            break;
            
        case .senhasDiferentes:
            alert.title = "Senhas diferentes"
            alert.message = "A senha e a confirmação de senha estão diferentes, digite senhas iguais e tente novamente."
            break;
            
        case .desconhecido:
            alert.title = "Erro ao realizar operação"
            alert.message = "Ocorreu um erro ao tentar realizar esta operação, por favor, tente novamente em alguns minutos ou entre em contato com nosso suporte."
            break;
        
        default:
            break
        }
    }
    
    func createFirestoreErrorAlert(error: Error, from controller: UIViewController) {
        let nsError = error as NSError
        
        let alert = UIAlertController.init(title: "", message: "", preferredStyle: .alert)
        let action = UIAlertAction.init(title: "Ok", style: .default, handler: nil)
        
        alert.addAction(action)
        
        switch nsError.code {
        case AuthErrorCode.networkError.rawValue:
            alert.title = "Erro de conexão"
            alert.message = "A tentativa de conexão com o servidor falhou, por favor, verifique sua conexão com a internet e tente novamente em alguns minutos."
            break;
            
        case AuthErrorCode.internalError.rawValue:
            alert.title = "Erro do servidor"
            alert.message = "Ocorreu um erro em nosso servidor que impediu esta operação, por favor, tente novamente em alguns minutos"
            break;
            
        case AuthErrorCode.emailAlreadyInUse.rawValue:
            alert.title = "Email já existe"
            alert.message = "O endereço de email digitado já está cadastrado, tente outro email ou vá em \"Esqueci minha senha\"."
            break;
            
        case AuthErrorCode.userNotFound.rawValue:
            alert.title = "Usuário não existe"
            alert.message = "Não existe um usuário cadastrado com este e-mail, por favor, faça seu cadastro."
            break;
            
        case AuthErrorCode.invalidEmail.rawValue:
            alert.title = "Email invalido"
            alert.message = "O email digitado é invalido ou não existe."
            break;
        
        case AuthErrorCode.weakPassword.rawValue:
            alert.title = "Senha muito fraca"
            alert.message = "A senha digitada é muito fraca, por favor, digite uma senha de pelo menos 6 caracteres que inclua letras e números e tente novamente."
            break;
            
        case AuthErrorCode.wrongPassword.rawValue:
            alert.title = "Senha incorreta"
            alert.message = "A senha digitada está incorreta."
            break;
            
        default:
            alert.title = "Erro desconhecido"
            alert.message = "Não foi possível realizar esta operação, tente novamente em alguns minutos ou contacte nosso suporte caso o erro persista. - \(nsError.code)"
            break;
        }
        
        controller.present(alert, animated: true, completion: nil)
    }
}
