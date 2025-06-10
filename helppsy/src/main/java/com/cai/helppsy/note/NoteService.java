package com.cai.helppsy.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public void sendNote(String senderId, String receiverId, String title, String content) {
        Note note = new Note();
        note.setSenderId(senderId);
        note.setReceiverId(receiverId);
        note.setTitle(title);
        note.setContent(content);
        note.setSentAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    public List<Note> getReceivedNotes(String receiverId) {
        return noteRepository.findByReceiverIdOrderBySentAtDesc(receiverId);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }
}