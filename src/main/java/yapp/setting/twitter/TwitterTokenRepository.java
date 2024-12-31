package yapp.setting.twitter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterTokenRepository extends JpaRepository<TwitterToken, Long> {
}
