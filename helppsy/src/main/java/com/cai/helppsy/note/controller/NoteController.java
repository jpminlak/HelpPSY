package com.cai.helppsy.note.controller;

import com.cai.helppsy.note.service.NoteService;
import com.cai.helppsy.note.entity.Note;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    // B의 프로필에서 쪽지 작성창 진입
    @GetMapping("/note/{receiverId}")
    public String noteForm(@PathVariable String receiverId, Model model) {
        model.addAttribute("receiverId", receiverId);
        return "note_form";
    }

    // 쪽지 저장
    @PostMapping("/note/send")
    public String sendNote(@RequestParam String receiverId,
                           @RequestParam String title,
                           @RequestParam String content,
                           HttpSession session) {

        String senderId = (String) session.getAttribute("userId");
        noteService.sendNote(senderId, receiverId, title, content);
        return "redirect:/profile/" + receiverId;
    }

    // 내가 받은 쪽지함
    @GetMapping("/note/inbox")
    public String noteInbox(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        List<Note> notes = noteService.getReceivedNotes(userId);
        model.addAttribute("notes", notes);
        return "note_inbox";
    }
}