package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;


@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public boolean checkUsernameIsExist(Client client){
        Optional<Client> op = clientRepository.findByUsername(client.getUsername());
        if(op.isPresent()) return true;
        else return false;
    }

    public boolean checkEmailIsExist(Client client){
        Optional<Client> op = clientRepository.findByEmail(client.getEmail());
        if(op.isPresent()) return true;
        else return false;
    }

    public boolean checkPhoneIsExist(Client client){
        Optional<Client> op = clientRepository.findByPhone(client.getPhone());
        if(op.isPresent()) return true;
        else return false;
    }

    public Client getClientByUsername(Client client){
        Optional<Client> op = clientRepository.findByUsername(client.getUsername());
        if(op.isPresent()) return op.get();
        else return null;
    }

    public void addNewClient(Client client){
        clientRepository.save(client);
    }

    public Client getClientById(Client client){
        Optional<Client> op = clientRepository.findById(client.getId());
        if(op.isPresent()) return op.get();
        else return null;
    }
}
