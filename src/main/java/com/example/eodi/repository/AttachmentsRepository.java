package com.example.eodi.repository;

import com.example.eodi.entity.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepository extends JpaRepository<Attachments, Long> {
}
