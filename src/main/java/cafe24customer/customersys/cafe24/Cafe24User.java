package cafe24customer.customersys.cafe24;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cafe24User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String sessionId;
	
    @JsonProperty("access_token")
    @Column
    private String accessToken;

    @JsonProperty("expires_at")
    @Column
    private LocalDateTime expiresAt;

    @JsonProperty("refresh_token")
    @Column
    private String refreshToken;

    @JsonProperty("refresh_token_expires_at")
    @Column
    private LocalDateTime refreshTokenExpiresAt;

    @JsonProperty("client_id")
    @Column
    private String clientId;

    @JsonProperty("mall_id")
    @Column
    private String mallId;

    @JsonProperty("user_id")
    @Column(unique = true)
    private String userId;

    @JsonProperty("scopes")
    @Column
    private String[] scopes;

    @JsonProperty("issued_at")
    @Column
    private LocalDateTime issuedAt;

    @JsonProperty("shop_no")
    @Column
    private String shopNo;

	
}
