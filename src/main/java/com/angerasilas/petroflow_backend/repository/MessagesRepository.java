package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.angerasilas.petroflow_backend.entity.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
}