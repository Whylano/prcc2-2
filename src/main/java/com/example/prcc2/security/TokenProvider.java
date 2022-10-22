package com.example.prcc2.security;

import com.example.prcc2.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {
    //jwt를 전달할 때 헤더의 key 값을 정의
    public static final String AUTHORIZATION_HEADER = "Authorization";
    //jwt를 전달 할 때 token의 앞에 붙혀줄 문자열(일종의 규약)
    public static final String TOKEN_PREFIX = "Bearer";
    //jwt 내부의 claim에 key값으로 정의할 문자열
    private static final String AUTHORITY_KEY = "auth";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30분

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    //spring security 내부에서 사용하는 인증 객체인 authentication 객체를 받아 토큰을 생성
    //UsernamePasswordAuthenticationToken = authentication
    //generateToken은 로그인 할때 토큰이 있어야할때 사용하는 것
    public TokenDto generateToken(Authentication authentication){
        //ROLE_USER,ROLE_ADMIN
        //ROLE_USER.ROLE_ADMIN // 문자열로 만들어주는것
        //권한을 가져오는 코드
        String authorities= authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        //access Token, refresh Token 만료 시간 = 현재시간 + 제한 시간 // 토큰 만료시간 지정해주는 코드
        Date acceessTokenExpiresIn = new Date(new Date().getTime()+ACCESS_TOKEN_EXPIRE_TIME);

        //토큰을 지정하는 코드
        //Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) //반드시 고유한 값을 넣어줘야한다(Id와 같은 고유값)
                .claim(AUTHORITY_KEY,authorities)//"auth":"ROLE_MEMBER"/로그인을 해야만 할 수 있는 기능이 있기도하고 jwt는 session처럼 저장을 하지않기때문에 Token이 존재한다는 것만으로도 관리자인지 일반유저인지 알아낼 수 있다.
                .setExpiration(acceessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return new TokenDto(accessToken);
    }
    //JWT 토큰을 받아 spring security에서 사용하는 인증 객체인 authentication 객체로 변환
    //로그인을 해야만 쓸 수 있는 요청일때 사용하는 코드
    public Authentication getAuthentication(String accessToken){ //로그인 해야만 사용할 수 있는 기능을 쓸때
        //복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITY_KEY)==null) throw new RuntimeException("권한 정보가 없는 토큰입니다.");

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITY_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(),"",authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }
    //토큰을 검증하는 코드
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e){
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            log.error("지원되지 않는 JWT토큰입니다.");
        } catch (IllegalArgumentException e){
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
