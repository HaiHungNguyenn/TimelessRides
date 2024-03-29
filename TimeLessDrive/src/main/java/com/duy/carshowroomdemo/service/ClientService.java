package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;

import com.duy.carshowroomdemo.dto.StaffDto;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.ClientRepository;
import com.duy.carshowroomdemo.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    private final MapperManager mapperManager = MapperManager.getInstance();

    private final ModelMapper modelMapper = new ModelMapper();

    public ClientDto findById(String id) {
        return mapperManager.getClientMapper().toDto(repository.findById(id).orElse(null));
    }

    public Client findEntityById(String id) {
        return repository.findById(id).orElse(null);
    }


    public ClientDto login(String email, String pass){

        Optional<Client> client = repository.findByEmail(email);
        if(client.isEmpty()){
            return null;
        }else {
            String encodedPW = client.get().getPassword();

            return Util.isValidPW(pass, encodedPW) ? mapperManager.getClientMapper().toDto(client.get())  : null;
        }
    }

    public void save(Client client) {
        repository.save(client);
    }

    public boolean isExist(String email) {
        return repository.existsByEmail(email);
    }

    public List<ClientDto> getClientList() {
        List <ClientDto> clienList = new ArrayList<>();
        repository.findAll().forEach(x->{clienList.add(mapperManager.getClientMapper().toDto(x));});
        return clienList;
    }
    public List<ClientDto> listByJoinDate(String startDate, String endDate){
        List<ClientDto> list = new ArrayList<>();
        // lay list tu repo, sau do parse sang dto
        repository.findByJoinDateBetween(LocalDate.parse(startDate),LocalDate.parse(endDate)).forEach(x->{list.add(mapperManager.getClientMapper().toDto(x));});
        return list;
    }

    public ClientDto getClientByEmailAndNameAndPhone(String email, String name, String phone){
        Client client = repository.findByEmailAndNameAndPhone(email, name, phone);
        if(client == null){
            return null;
        }
        return modelMapper.map(client, ClientDto.class);
    }

    public boolean changePassword(String id,String oldPass, String newPass) {
        Client client = repository.findById(id).get();
        try {
            if(Util.isValidPW(oldPass,client.getPassword())){
                client.setPassword(newPass);
                repository.save(client);
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;

    }

    public Client findEntityByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }


    public List<ClientDto> findAll(Pageable pageable) {
        List<ClientDto> list = new ArrayList<>();
        repository.findAll(pageable).forEach(x -> list.add(mapperManager.getClientMapper().toDto(x)));
        return list;
    }

    public ClientDto findByEmail(String email) {
        return mapperManager.getClientMapper().toDto(repository.findByEmail(email).orElse(null));
    }

    public void delete(Client client) {
        repository.delete(client);
    }

    public long getLastOffset(Pageable pageable) {
        return repository.findAll(pageable).getTotalPages();
    }

    public List<ClientDto> searchUser(Pageable pageable, String name) {
        List<ClientDto> clientList = new ArrayList<>();
        repository.findClientsByNameContainingIgnoreCase(pageable, name).forEach(client -> {
            clientList.add(mapperManager.getClientMapper().toDto(client));
        });

        return clientList;
    }

    public long getSearchUserLastOffset(Pageable pageable, String name) {
        return repository.findClientsByNameContainingIgnoreCase(pageable, name).getTotalPages();
    }

    public int getNumOfUser() {
        return repository.getNumberOfUser();
    }
}
