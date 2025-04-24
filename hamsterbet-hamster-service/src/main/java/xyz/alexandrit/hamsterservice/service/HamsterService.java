package xyz.alexandrit.hamsterservice.service;

import xyz.alexandrit.common.dto.request.HamsterRegisterRequestDTO;
import xyz.alexandrit.common.dto.request.HamsterUpdateRequestDTO;
import xyz.alexandrit.common.dto.response.HamsterDeletedResponseDTO;
import xyz.alexandrit.common.dto.response.HamsterRegisterResponseDTO;
import xyz.alexandrit.common.dto.response.HamsterResponseDTO;

import java.util.List;

public interface HamsterService {
    HamsterRegisterResponseDTO save(HamsterRegisterRequestDTO hamsterRegisterRequestDTO);

    List<HamsterResponseDTO> findAll();

    HamsterResponseDTO findById(Long hamsterId);

    HamsterResponseDTO updateById(Long hamsterId, HamsterUpdateRequestDTO hamsterUpdateRequestDTO);

    HamsterDeletedResponseDTO deleteById(Long hamsterId);
}
