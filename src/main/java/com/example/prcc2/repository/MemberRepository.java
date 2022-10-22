package com.example.prcc2.repository;

import com.example.prcc2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);
//db에서 닉네임이 있는지 존재의 유무를 확인할 수 있는 메소드 쿼리 작성(existsByNickname)
    boolean existsByNickname(String nickname);

}
