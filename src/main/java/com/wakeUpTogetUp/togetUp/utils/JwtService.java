package com.wakeUpTogetUp.togetUp.utils;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtService {
    @Value("${jwt.secret-key}")
    private  String key;
    @Value("${jwt.token-expired-time}")
    private  Long expiredTimeMs;

    public Boolean validate(String token, String userEmail, String key) {
        String useremailByToken = getUseremail(token, key);
        return useremailByToken.equals(userEmail) && !isTokenExpired(token, key);
    }

    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public Integer getUserId(String accessToken) {
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(Status.INVALID_JWT);
        }
        return claims.get("userId", Integer.class);
    }

    /**
     *
     * @return int
     * @throws BaseException
     */
    // TODO :getUserId 와 합치기
    public int getUserNum() throws BaseException{
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(Status.EMPTY_JWT);
        }

        // 2. JWT parsing
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Exception ignored) {
            throw new BaseException(Status.INVALID_JWT);
        }

        // 3. userId 추출
        return claims.get("userId",Integer.class);
    }

    public Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUseremail(String token, String key) {
        return extractAllClaims(token, key).get("username", String.class);
    }

    private  Key getSigningKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean verifyToken(String token) {
        try {
            final Claims claims = getBody(token);
            return true;
        } catch (RuntimeException e) {
            if (e instanceof ExpiredJwtException) {
                throw new BaseException(Status.INVALID_JWT);
            }
            return false;
        }
    }

    public Boolean isTokenExpired(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

//    public static String generateAccessToken(Integer userId, String key, long expiredTimeMs) {
//        return doGenerateToken(userId, expiredTimeMs, key);
//    }

    public  String generateAccessToken(Integer userId) {
        return doGenerateToken(userId, expiredTimeMs, key);
    }

    private String doGenerateToken(Integer userId, long expireTime, String key) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

