package xyz.alexandrit.hamsterservice.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import xyz.alexandrit.common.dto.request.HamsterRegisterRequestDTO;
import xyz.alexandrit.common.dto.request.HamsterUpdateRequestDTO;
import xyz.alexandrit.common.dto.response.HamsterDeletedResponseDTO;
import xyz.alexandrit.common.dto.response.HamsterRegisterResponseDTO;
import xyz.alexandrit.common.dto.response.HamsterResponseDTO;
import xyz.alexandrit.hamsterservice.entity.Hamster;
import xyz.alexandrit.hamsterservice.exception.HamsterNotFoundException;
import xyz.alexandrit.hamsterservice.repository.HamsterRepository;
import xyz.alexandrit.hamsterservice.service.HamsterService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HamsterServiceImpl implements HamsterService {

    private final HamsterRepository hamsterRepository;

    @Override
    public HamsterRegisterResponseDTO save(HamsterRegisterRequestDTO hamsterRegisterRequestDTO) {
        var hamster = new Hamster();

        hamster.setName(hamsterRegisterRequestDTO.getName());
        hamster.setAge(hamsterRegisterRequestDTO.getAge());
        hamster.setWeightInGrams(hamsterRegisterRequestDTO.getWeightInGrams());

        var hamsterInDB = hamsterRepository.save(hamster);

        var responseDTO = new HamsterRegisterResponseDTO();
        responseDTO.setHamsterId(hamsterInDB.getId());

        return responseDTO;
    }

    @Override
    public List<HamsterResponseDTO> findAll() {
        var hamsters = hamsterRepository.findAll();
        return hamsters.stream().map(hamster->{

            var responseDTO = new HamsterResponseDTO();

            responseDTO.setHamsterId(hamster.getId());
            responseDTO.setName(hamster.getName());
            responseDTO.setAge(hamster.getAge());
            responseDTO.setWeightInGrams(hamster.getWeightInGrams());
            responseDTO.setLossesCount(hamster.getLossesCount());
            responseDTO.setWinsCount(hamster.getWinsCount());
            responseDTO.setStatus(hamster.getStatus());

            return responseDTO;

        }).toList();

    }

    @Override
    public HamsterResponseDTO findById(Long hamsterId) {
        var hamsterContainer = hamsterRepository.findById(hamsterId);

        if (hamsterContainer.isEmpty()) {
            throw new HamsterNotFoundException(String.format("Hamster with id %s not found", hamsterId));
        }

        var hamster = hamsterContainer.get();
        var responseDTO = new HamsterResponseDTO();

        responseDTO.setHamsterId(hamster.getId());
        responseDTO.setName(hamster.getName());
        responseDTO.setAge(hamster.getAge());
        responseDTO.setWeightInGrams(hamster.getWeightInGrams());
        responseDTO.setLossesCount(hamster.getLossesCount());
        responseDTO.setWinsCount(hamster.getWinsCount());
        responseDTO.setStatus(hamster.getStatus());

        return responseDTO;
    }

    @Override
    public HamsterResponseDTO updateById(Long hamsterId, HamsterUpdateRequestDTO hamsterUpdateRequestDTO) {

        var hamsterContainer = hamsterRepository.findById(hamsterId);

        if (hamsterContainer.isEmpty()) {
            throw new HamsterNotFoundException(String.format("Hamster with id %s not found", hamsterId));
        }

        var hamster = hamsterContainer.get();

        hamster.setName(hamsterUpdateRequestDTO.getName()  != null ? hamsterUpdateRequestDTO.getName() : hamster.getName());
        hamster.setAge(hamsterUpdateRequestDTO.getAge() != null ? hamsterUpdateRequestDTO.getAge() : hamster.getAge());
        hamster.setWeightInGrams(hamsterUpdateRequestDTO.getWeightInGrams() != null ? hamsterUpdateRequestDTO.getWeightInGrams() : hamster.getWeightInGrams());
        hamster.setStatus(hamsterUpdateRequestDTO.getStatus() != null ? hamsterUpdateRequestDTO.getStatus() : hamster.getStatus());

        var hamsterInDB = hamsterRepository.save(hamster);

        var responseDTO = new HamsterResponseDTO();

        responseDTO.setHamsterId(hamsterInDB.getId());
        responseDTO.setName(hamsterInDB.getName());
        responseDTO.setAge(hamsterInDB.getAge());
        responseDTO.setWeightInGrams(hamsterInDB.getWeightInGrams());
        responseDTO.setLossesCount(hamsterInDB.getLossesCount());
        responseDTO.setWinsCount(hamsterInDB.getWinsCount());
        responseDTO.setStatus(hamsterInDB.getStatus());

        return responseDTO;
    }

    @Override
    public HamsterDeletedResponseDTO deleteById(Long hamsterId) {
        HamsterDeletedResponseDTO response = new HamsterDeletedResponseDTO();
        response.setHamsterId(hamsterId);


        if (!hamsterRepository.existsById(hamsterId)) {
            response.setDeleted(false);
        } else {
            hamsterRepository.deleteById(hamsterId);
            response.setDeleted(true);
        }

        return response;
    }
}
