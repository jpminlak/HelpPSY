package com.cai.helppsy.note;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    /*쪽지 작성 폼 이동 */
    @GetMapping("/note/{receiverId}")
    public String noteForm(@PathVariable String receiverId, HttpSession session, Model model) {
        String senderId = (String) session.getAttribute("userId");
        if (senderId == null) return "redirect:/login";

        model.addAttribute("receiverId", receiverId);
        return "memberManager/note_form";
    }

    /*쪽지 전송 */
    @PostMapping("/note/send")
    public String sendNote(@RequestParam String receiverId,
                           @RequestParam String title,
                           @RequestParam String content,
                           HttpSession session) {
        String senderId = (String) session.getAttribute("userId");
        if (senderId == null) return "redirect:/login";

        noteService.sendNote(senderId, receiverId, title, content);
        return "redirect:/main";
    }

    /*받은 쪽지함 보기 */
    @GetMapping("/note_inbox")
    public String noteInbox(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        List<Note> notes = noteService.getReceivedNotes(userId);
        model.addAttribute("notes", notes);
        return "memberManager/note_inbox";
    }


    /*쪽지 상세 보기 */
    @GetMapping("/note/view/{noteId}")
    public String viewNote(@PathVariable Long noteId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null)
            return "redirect:/login";

        Note note = noteService.getNote(noteId);
        if (!userId.equals(note.getReceiverId()))
            return "redirect:/note/inbox";  // 권한 체크

        model.addAttribute("note", note);
        return "memberManager/note_view";
    }

    /*쪽지 답장 폼 */
    @GetMapping("/note/reply/{noteId}")
    public String replyForm(@PathVariable Long noteId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Note original = noteService.getNote(noteId);
        if (!userId.equals(original.getReceiverId())) return "redirect:/note/inbox";

        model.addAttribute("receiverId", original.getSenderId());
        model.addAttribute("originalTitle", original.getTitle());
        return "memberManager/note_form";
    }

    @PostMapping("/note/reply/send")
    public String sendReply(@RequestParam String receiverId,
                            @RequestParam String title,
                            @RequestParam String content,
                            HttpSession session,
                            Model model) {

        String senderId = (String) session.getAttribute("userId");
        if (senderId == null || senderId.isEmpty()) {
            return "redirect:/login";  // 세션 만료 또는 미로그인 시
        }
        model.addAttribute("senderId", senderId);
        model.addAttribute("senderAlias", session.getAttribute("userAlias")); // 로그인할 때 세션에 저장했던 별명

        noteService.sendNote(senderId, receiverId, title, content);
        return "redirect:/note/inbox";
    }

}
