package com.korea.member.service;

import com.korea.member.model.MemberEntity;
import com.korea.member.repository.MemberRepository;
import com.korea.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberDTO> findAll() {
        List<MemberEntity> entities = memberRepository.findAll();
        return entities.stream().map(MemberDTO::new).collect(Collectors.toList());
    }

    public List<MemberDTO> findByEmail(String email) {
        Optional<MemberEntity> optional = memberRepository.findByEmail(email);
        if (optional.isPresent()) {
            MemberDTO dto = new MemberDTO(optional.get());
            return List.of(dto);
        }
        return List.of();
    }

    public List<MemberDTO> create(MemberDTO dto) {
        MemberEntity entity = MemberDTO.toEntity(dto);
        memberRepository.save(entity);
        return findAll();
    }

    public MemberDTO updatePassword(String email, String newPassword) {
        Optional<MemberEntity> optional = memberRepository.findByEmail(email);
        if (optional.isPresent()) {
            MemberEntity member = optional.get();
            member.setPassword(newPassword);
            memberRepository.save(member);
            return new MemberDTO(member);
        }
        return null;
    }

    public List<MemberDTO> deleteById(int id) {
        memberRepository.deleteById(id);
        return findAll();
    }
}
