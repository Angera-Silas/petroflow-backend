package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.angerasilas.petroflow_backend.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
}
