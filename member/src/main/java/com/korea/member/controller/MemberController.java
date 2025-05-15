package com.korea.member.controller;

import com.korea.member.dto.MemberDTO;
import com.korea.member.dto.ResponseDTO;
import com.korea.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<MemberDTO> dtos = memberService.findAll();
        ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        List<MemberDTO> dtos = memberService.findByEmail(email);
        ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MemberDTO dto) {
        try {
            List<MemberDTO> dtos = memberService.create(dto);
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{email}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String email, @RequestBody String newPassword) {
        MemberDTO updated = memberService.updatePassword(email, newPassword);
        if (updated != null) {
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(List.of(updated)).build();
            return ResponseEntity.ok().body(response);
        } else {
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().error("해당 이메일의 회원이 없습니다.").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        List<MemberDTO> dtos = memberService.deleteById(id);
        ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
}
