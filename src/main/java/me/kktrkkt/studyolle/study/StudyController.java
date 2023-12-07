package me.kktrkkt.studyolle.study;

import lombok.RequiredArgsConstructor;
import me.kktrkkt.studyolle.account.CurrentUser;
import me.kktrkkt.studyolle.account.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class StudyController {

    static final String STUDY_URL = "/study";
    static final String STUDY_VIEW= "study/view";
    static final String NEW_STUDY_URL = "/new-study";
    static final String NEW_STUDY_VIEW = "study/newStudySubmitForm";
    static final String MEMBERS_URL = "/members";
    static final String STUDY_MEMBERS_VIEW= "study/members";

    private final StudyRepository studys;

    private final StudyService studyService;

    @GetMapping(NEW_STUDY_URL)
    public String newStudyForm(Model model) {
        model.addAttribute(new StudyForm());
        return NEW_STUDY_VIEW;
    }

    @PostMapping(NEW_STUDY_URL)
    public String createStudy(@CurrentUser Account account, @Valid StudyForm studySubmitForm, Errors errors, RedirectAttributes ra) {
        if(errors.hasErrors()){
            return NEW_STUDY_VIEW;
        }
        else{
            Study study = studyService.create(account, studySubmitForm);
            ra.addFlashAttribute(study);
            return "redirect:/study/" + URLEncoder.encode(study.getPath(), StandardCharsets.UTF_8);
        }
    }

    @GetMapping(STUDY_URL + "/{path}")
    public String studyView(@PathVariable String path, Model model) {
        Study byPath = studys.findByPath(path).orElseThrow(()->new IllegalArgumentException("study is not found"));
        model.addAttribute(byPath);
        return STUDY_VIEW;
    }

    @GetMapping(STUDY_URL + "/{path}" + MEMBERS_URL)
    public String studyMembers(@PathVariable String path, Model model) {
        Study byPath = studys.findByPath(path).orElseThrow(()->new IllegalArgumentException("study is not found"));
        model.addAttribute(byPath);
        return STUDY_MEMBERS_VIEW;
    }
}
