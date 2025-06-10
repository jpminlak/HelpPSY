package com.cai.helppsy.note;

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

    // 쪽지 작성 폼 (상대방 프로필에서 진입)
    @GetMapping("/note/{receiverId}")
    public String noteForm(@PathVariable String receiverId, Model model) {
        model.addAttribute("receiverId", receiverId);
        return "memberManager/note/note_form";
    }

    // 쪽지 전송
    @PostMapping("/note/send")
    public String sendNote(@RequestParam String receiverId,
                           @RequestParam String title,
                           @RequestParam String content,
                           HttpSession session) {
        String senderId = (String) session.getAttribute("userId");
        noteService.sendNote(senderId, receiverId, title, content);
        return "redirect:/profile/" + receiverId;
    }

    // 받은 쪽지 목록
    @GetMapping("/note/inbox")
    public String noteInbox(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        List<Note> notes = noteService.getReceivedNotes(userId);
        model.addAttribute("notes", notes);
        return "note/note_inbox";
    }

    // 쪽지 상세 보기
    @GetMapping("/note/view/{id}")
    public String viewNote(@PathVariable Long id, Model model, HttpSession session) {
        Note note = noteService.findById(id);
        if (note == null || !note.getReceiverId().equals(session.getAttribute("userId"))) {
            return "redirect:/note/inbox";
        }
        model.addAttribute("note", note);
        return "note/note_view";
    }

    // 쪽지 답장 작성 폼
    @GetMapping("/note/reply/{noteId}")
    public String replyForm(@PathVariable Long noteId, Model model, HttpSession session) {
        Note original = noteService.findById(noteId);
        if (original == null || !original.getReceiverId().equals(session.getAttribute("userId"))) {
            return "redirect:/note/inbox";
        }
        model.addAttribute("receiverId", original.getSenderId());
        model.addAttribute("originalNote", original);
        return "note/note_reply_form";
    }

    // 쪽지 답장 전송
    @PostMapping("/note/reply/send")
    public String sendReply(@RequestParam String receiverId,
                            @RequestParam String title,
                            @RequestParam String content,
                            HttpSession session) {
        String senderId = (String) session.getAttribute("userId");
        noteService.sendNote(senderId, receiverId, title, content);
        return "redirect:/note/inbox";
    }
}
