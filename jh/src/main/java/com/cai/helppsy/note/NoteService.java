package com.cai.helppsy.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    // 쪽지 전송
    public void sendNote(String senderId, String receiverId, String title, String content) {
        Note note = new Note();
        note.setSenderId(senderId);
        note.setReceiverId(receiverId);
        note.setTitle(title);
        note.setContent(content);
        note.setSentAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    // 받은 쪽지 목록 가져오기
    public List<Note> getReceivedNotes(String receiverId) {
        return noteRepository.findByReceiverIdOrderBySentAtDesc(receiverId);
    }

    // 쪽지 하나 가져오기
    public Note getNote(Long id) {
        return noteRepository.findById(id).orElse(null);
    }
}