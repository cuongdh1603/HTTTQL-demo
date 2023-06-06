package com.example.demo.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Contract;
import com.example.demo.repository.ContractRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    

    public Contract setDetailedInfor(Contract contract){
        List<String> codes = contractRepository.findAllCode();
        String genCode = new String();
        do{
            genCode = generateRandomCode(4);
            log.info("Ma code : " + genCode);
            contract.setCode(genCode);
        }while(codes.contains(genCode));
        contract.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        contract.setStatus(0);
        return contract;
    }

    public void saveContract(Contract contract){
        contractRepository.save(contract);
    }

    public List<Contract> getAllContracts(){
        return contractRepository.findAll();
    }
    /*------------------------------------------------------------------------ */
    // public List<Contract> getUnhandledContracts(){
    //     return contractRepository.findUnhandledContracts();
    // }

    public List<Contract> getAssignedContracts(){
        return contractRepository.findAssignedContracts();
    }

    public List<Contract> getInstalledContracts(){
        return contractRepository.findInstalledContracts();
    }

    public List<Contract> getPaidContracts(){
        return contractRepository.findPaidContracts();
    }
    /*------------------------------------------------------------------------ */
    public List<Contract> getProcessingContract(Integer technicianId){
        return contractRepository.findProcessingContracts().stream()
                .filter(ct -> (ct.getTechnician().getId()==technicianId))
                .collect(Collectors.toList());
        
    }

    public void setHandledContract(Integer contractId, Integer managerId, Integer technicianId){
        contractRepository.setHandleContract(contractId, managerId, technicianId);
    }

    public void setConfirmedContract(Integer contractId){
        contractRepository.setConfirmContract(contractId);
    }

    public void setInstalledContract(Integer contractId){
        contractRepository.setInstallContract(contractId);
    }
    public void setPaidContract(Integer contractId, Integer accountantId, Float paidAmount){
        contractRepository.setPaidContract(contractId, accountantId, paidAmount, new Timestamp(System.currentTimeMillis()));
    }
    public Contract getContractById(Integer id){
        Optional<Contract> optional = contractRepository.findById(id);
        if(optional.isPresent()) return optional.get();
        else return null;
    }

    // public Integer getContractAmountByMonthAndYear(int month, int year){

    // }
    
    /*----------------------------------- utilities ------------------------------------ */
    public static String generateRandomCode(int len)
    {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String numbers = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder char_sb = new StringBuilder();
        StringBuilder num_sb = new StringBuilder();
        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            char_sb.append(chars.charAt(randomIndex));
        }
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(numbers.length());
            num_sb.append(numbers.charAt(randomIndex));
        }
        char_sb.append('-');
        char_sb.append(num_sb);
        return char_sb.toString();
    }
}
