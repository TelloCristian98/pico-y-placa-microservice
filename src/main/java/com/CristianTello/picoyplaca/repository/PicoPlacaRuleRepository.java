package com.CristianTello.picoyplaca.repository;
import com.CristianTello.picoyplaca.model.PicoPlacaRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PicoPlacaRuleRepository extends JpaRepository<PicoPlacaRule, Long> {
    Optional<PicoPlacaRule> findByDayOfWeek(String dayOfWeek);
}
