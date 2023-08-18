package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.MedCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedCardRepository extends JpaRepository<MedCard, Long> {
}
