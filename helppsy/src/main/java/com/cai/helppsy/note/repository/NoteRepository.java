package com.cai.helppsy.note.repository;

import com.cai.helppsy.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByReceiverIdOrderBySentAtDesc(String receiverId);
}
