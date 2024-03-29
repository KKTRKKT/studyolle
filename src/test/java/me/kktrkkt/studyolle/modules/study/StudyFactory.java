package me.kktrkkt.studyolle.modules.study;

import lombok.RequiredArgsConstructor;
import me.kktrkkt.studyolle.modules.account.AccountRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyFactory {

    private final AccountRepository accounts;

    private final StudyRepository studys;

    public Study createStudy(String nickname, String path) {
        String title = "new-study";
        String bio = "bio";
        String explanation = "explanation";

        Study newStudy = new Study();
        newStudy.setPath(path);
        newStudy.setTitle(title);
        newStudy.setBio(bio);
        newStudy.setExplanation(explanation);
        newStudy.getManagers().add(accounts.findByNickname(nickname).orElseThrow());

        return studys.save(newStudy);
    }

    public Study createStudy(String nickname) {
        return createStudy(nickname, "new-study");
    }
}
