package com.studere.studerejava.treinere.services;

import org.springframework.stereotype.Service;

@Service("setService")
public class SetService {
//    private final SetRepository setRepository;
//    private final TreinereUserRepository treinereUserRepository;
//
//    public SetService(SetRepository setRepository, TreinereUserRepository treinereUserRepository, TreinereUserRepository treinereUserRepository1) {
//        this.setRepository = setRepository;
//        this.treinereUserRepository = treinereUserRepository1;
//    }
//
//    // List sets for given user id
//    public List<Set> listSetsByUserId(UUID userId) {
//        return setRepository.findByUserId(userId);
//    }
//
//    // Create set
//    public Set createSet(SetCreateOrUpdateDTO setCreateOrUpdateDTO, UUID userId) {
//        TreinereUser user = treinereUserRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//
//        Set set = new Set();
//        set.setRepetitions(setCreateOrUpdateDTO.getRepetitions());
//        set.setWeight(setCreateOrUpdateDTO.getWeight());
//
//
//        return setRepository.save(set);
//    }
//
//    // Get set by id
//    public Set getSetById(UUID id, UUID userId) {
//        return setRepository.findByIdAndUserId(id, userId)
//                .orElseThrow(() -> new NotFoundException("Set not found"));
//    }
//
//    // Delete set by id
//    public void deleteSetById(UUID id, UUID userId) {
//        setRepository.deleteByIdAndUserId(id, userId);
//    }
//
//    // Update set by id
//    public Set updateSetById(UUID id, SetCreateOrUpdateDTO setCreateOrUpdateDTO, UUID userId) {
//        Set set = setRepository.findByIdAndUserId(id, userId)
//                .orElseThrow(() -> new NotFoundException("Set not found"));
//
//        set.setName(setCreateOrUpdateDTO.getName());
//        set.setStartDate(setCreateOrUpdateDTO.getStartDate());
//        set.setEndDate(setCreateOrUpdateDTO.getEndDate());
//
//        return setRepository.save(set);
//    }

}
