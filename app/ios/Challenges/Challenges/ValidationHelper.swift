//
//  ValidationHelper.swift
//  Challenges
//
//  Created by Rodolfo Roca on 5/9/18.
//  Copyright © 2018 RocaCorp. All rights reserved.
//

import UIKit

class ValidationHelper: NSObject {

    typealias Tuple = (status: Bool, codigo: AlertHelper.InternalErrorType?, campos: [String]?)
    
    static let sharedInstance = ValidationHelper()
    
    func validar(responsavel: Responsavel) -> Tuple {
        var valido = true
        var codigo: AlertHelper.InternalErrorType?
        var campos = [String]()
        
        if responsavel.nome == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("nome")
        }
        
        if responsavel.email == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("email")
        }
        
        return (valido, codigo, campos)
    }
    
    func validar(crianca: Crianca) -> Tuple {
        var valido = true
        var codigo: AlertHelper.InternalErrorType?
        var campos = [String]()
        
        if crianca.nome == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("nome")
        }
        
        if crianca.email == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("email")
        }
        
        return (valido, codigo, campos)
    }
    
    func validar(desafio: Desafio) -> Tuple {
        var valido = true
        var codigo: AlertHelper.InternalErrorType?
        var campos = [String]()
        
        if desafio.crianca == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("criança")
        }
        
        if desafio.nome == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("nome")
        }
        
        if desafio.pontos == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("pontos")
        }
        
        if desafio.habilidade == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("habilidade")
        }
        
        if desafio.frequencia == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("frequencia")
        }
        
        if desafio.repeticoes == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("repeticoes")
        }
        
        return (valido, codigo, campos)
    }
    
    func validarCadastroSenha(senha: String?, confirmaSenha: String?) -> Tuple {
        var valido = true
        var codigo: AlertHelper.InternalErrorType?
        var campos = [String]()

        guard let senha = senha else {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("senha")
            return (valido, codigo, campos)
        }
        
        guard let confirmaSenha = confirmaSenha else {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("confirmar senha")
            return (valido, codigo, campos)
        }
        
        if senha != confirmaSenha {
            valido = false
            codigo = AlertHelper.InternalErrorType.senhasDiferentes
            return (valido, codigo, campos)
        }
        
        return (valido, codigo, campos)
    }
    
    func validarMudarSenha(senha: String?, novaSenha: String?) -> Tuple {
        var valido = true
        var codigo: AlertHelper.InternalErrorType?
        var campos = [String]()
        
        if senha == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("senha")
        }
        
        if novaSenha == nil {
            valido = false
            codigo = AlertHelper.InternalErrorType.camposObrigatorios
            campos.append("nova senha")
        }
        
        return (valido, codigo, campos)
    }
}
