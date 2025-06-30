package com.cai.helppsy.note;

import com.cai.helppsy.memberManager.SignupEntity; // SignupEntity 임포트
import com.cai.helppsy.memberManager.signupService; // signupService 임포트
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final signupService signupService; // signupService 주입


    /*쪽지 작성 폼 이동 */
    @GetMapping("/note/{receiverId}")
    public String noteForm(@PathVariable String receiverId, HttpSession session, Model model) {
        String senderId = (String) session.getAttribute("userId");
        if (senderId == null) return "redirect:/login";

        model.addAttribute("receiverId", receiverId);
        // 답장 폼을 열 때 수신자 (원래 쪽지의 발신자)의 현재 별명을 가져와 표시
        SignupEntity receiverUser = signupService.login(receiverId);
        String receiverAlias = (receiverUser != null) ? receiverUser.getAlias() : receiverId;
        model.addAttribute("receiverAliasForDisplay", receiverAlias); // 화면 표시용 별명

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
        if (userId == null) {
            return "redirect:/login"; // 로그인하지 않았으면 로그인 페이지로 리다이렉트
        }

        List<Note> notes = noteService.getReceivedNotes(userId); // 현재 수신자의 쪽지 목록 가져오기
        List<String> senderAliases = new ArrayList<>(); // 발신자 별명을 저장할 리스트

        for (Note note : notes) {
            // 각 쪽지의 발신자 (senderId)를 기반으로 최신 SignupEntity를 조회
            SignupEntity senderUser = signupService.login(note.getSenderId());
            // 발신자 별명이 변경되었을 수 있으므로, SignupEntity에서 최신 별명을 가져옵니다.
            // 만약 사용자를 찾을 수 없다면 (탈퇴 등), 원래의 senderId를 별명으로 사용합니다.
            String alias = (senderUser != null) ? senderUser.getAlias() : note.getSenderId();
            senderAliases.add(alias); // 조회된 최신 별명을 리스트에 추가
        }

        model.addAttribute("notes", notes); // 원래 Note 엔티티 리스트 (쪽지 ID, 내용 등 원본 데이터)
        model.addAttribute("senderAliases", senderAliases); // 조회된 최신 발신자 별명 리스트
        return "memberManager/note_inbox";
    }


    /*쪽지 상세 보기 */
    @GetMapping("/note/view/{noteId}")
    public String viewNote(@PathVariable Long noteId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null)
            return "redirect:/login";

        Note note = noteService.getNote(noteId);
        // 쪽지가 없거나, 현재 로그인한 사용자가 해당 쪽지의 수신자가 아니라면 접근 거부
        if (note == null || !userId.equals(note.getReceiverId())) {
            return "redirect:/note_inbox";
        }

        // 쪽지 상세 보기에서도 발신자 별명을 동적으로 가져와야 합니다.
        SignupEntity senderUser = signupService.login(note.getSenderId());
        String senderAlias = (senderUser != null) ? senderUser.getAlias() : note.getSenderId();

        model.addAttribute("note", note); // 원래 Note 엔티티는 그대로 전달
        model.addAttribute("senderAlias", senderAlias); // 최신 발신자 별명을 별도의 속성으로 추가
        return "memberManager/note_view";
    }

    /*쪽지 답장 폼 */
    @GetMapping("/note/reply/{noteId}")
    public String replyForm(@PathVariable Long noteId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Note original = noteService.getNote(noteId);
        // 답장할 원래 쪽지가 없거나, 현재 로그인한 사용자가 해당 쪽지의 수신자가 아니라면 접근 거부
        if (original == null || !userId.equals(original.getReceiverId())) {
            return "redirect:/note_inbox";
        }

        // 답장 시 수신자 (원래 쪽지 발신자)의 현재 별명을 가져와 표시
        SignupEntity originalSender = signupService.login(original.getSenderId());
        String receiverAliasForReply = (originalSender != null) ? originalSender.getAlias() : original.getSenderId();

        model.addAttribute("receiverId", original.getSenderId()); // 실제 답장을 보낼 userId
        model.addAttribute("receiverAliasForDisplay", receiverAliasForReply); // 화면에 표시될 별명
        model.addAttribute("originalTitle", original.getTitle()); // 원래 쪽지 제목
        return "memberManager/note_form";
    }

    @PostMapping("/note/reply/send")
    public String sendReply(@RequestParam String receiverId,
                            @RequestParam String title,
                            @RequestParam String content,
                            HttpSession session) {

        String senderId = (String) session.getAttribute("userId");
        if (senderId == null || senderId.isEmpty()) {
            return "redirect:/login";  // 세션 만료 또는 미로그인 시
        }
        // 답장 쪽지 전송 (senderId는 현재 로그인 사용자, receiverId는 원래 쪽지 발신자 userId)
        noteService.sendNote(senderId, receiverId, title, content);
        return "redirect:/note_inbox";
    }

}