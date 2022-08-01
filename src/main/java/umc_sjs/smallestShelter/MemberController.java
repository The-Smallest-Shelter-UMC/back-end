//package umc_sjs.smallestShelter;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import umc_sjs.smallestShelter.domain.PrivateMember;
//import umc_sjs.smallestShelter.domain.Role;
//
//@Controller
//@RequiredArgsConstructor
//public class MemberController {
//
//    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @GetMapping("/joinForm")
//    public String joinForm() {
//        return "joinForm.html";
//    }
//
//    @GetMapping("/loginForm")
//    public String testLogin() {
//        return "loginForm.html";
//    }
//
//    @PostMapping("/join")
//    public String join(PrivateMember privateMember){
//        System.out.println("privateMember = " + privateMember);
//        privateMember.setRole(Role.PRIVATE);
//        String rawPassword = privateMember.getPassword();
//        String encodePassword = passwordEncoder.encode(rawPassword);
//
//        privateMember.setPassword(encodePassword);
//
//        memberRepository.saveMember(privateMember);
//        return "redirect:/loginForm";
//    }
//
//}
