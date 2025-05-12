package security.copy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.korea.todo.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY = "fd19296800bfafc8bfcdc702ebceeb565d7750c0ed7cbe388ecb803d6193bb72d84de814d75e29a49262de549ec2f95e6329e0e8a99b6b10f61f4842299a787c583e147ad2d1b39323539b5d72b32a309eb620640e96ec9616919655db98b8abec0570a2de9c04096228390b407fb26cb3b3d9f7e68d988ae4628d87046b086ee0aab0794fa77fe0ed999c7e55a11712fe07be62c3418bd4a34717d195b8ed61e3b62c5f4db4015c60af6bd9693f1964c0804d7d0e74ddc0f7ba445edf971ebe1fdbbb64521afe7d8e72d4ed5c3f419691aaa09e79c3bbbcb5037f753c56d2898e2d0533f6523d2967acdc5db1196c3fcb8302cf6ec3c8a9e113a71095031c0e";

	public String create(UserEntity userEntity) {
		// 기한 지금으로부터 1일로 설정
		Date expiryDate = Date.from(
						Instant.now()
						.plus(1, ChronoUnit.DAYS));

		/*
		{ // header
		  "alg":"HS512"
		}.
		{ // payload
		  "sub":"40288093784915d201784916a40c0001",
		  "iss": "demo app",
		  "iat":1595733657,
		  "exp":1596597657
		}.
		// SECRET_KEY를 이용해 서명한 부분
		fd19296800bfafc8bfcdc702ebceeb565d7750c0ed7cbe388ecb803d6193bb72d84de814d75e29a49262de549ec2f95e6329e0e8a99b6b10f61f4842299a787c583e147ad2d1b39323539b5d72b32a309eb620640e96ec9616919655db98b8abec0570a2de9c04096228390b407fb26cb3b3d9f7e68d988ae4628d87046b086ee0aab0794fa77fe0ed999c7e55a11712fe07be62c3418bd4a34717d195b8ed61e3b62c5f4db4015c60af6bd9693f1964c0804d7d0e74ddc0f7ba445edf971ebe1fdbbb64521afe7d8e72d4ed5c3f419691aaa09e79c3bbbcb5037f753c56d2898e2d0533f6523d2967acdc5db1196c3fcb8302cf6ec3c8a9e113a71095031c0e
		 */
		// JWT Token 생성
		return Jwts.builder()
						// header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
						.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
						// payload에 들어갈 내용
						.setSubject(userEntity.getId()) // sub
						.setIssuer("demo app") // iss
						.setIssuedAt(new Date()) // iat
						.setExpiration(expiryDate) // exp
						.compact();
	}

	public String validateAndGetUserId(String token) {
		// parseClaimsJws메서드가 Base 64로 디코딩 및 파싱.
		// 즉, 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용 해 서명 후, token의 서명 과 비교.
		// 위조되지 않았다면 페이로드(Claims) 리턴
		// 그 중 우리는 userId가 필요하므로 getBody를 부른다.
		Claims claims = Jwts.parser()
						.setSigningKey(SECRET_KEY)
						.parseClaimsJws(token)
						.getBody();

		return claims.getSubject();
	}
}
