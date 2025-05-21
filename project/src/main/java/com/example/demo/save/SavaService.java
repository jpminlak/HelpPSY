package com.example.demo.save;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SavaService implements IF_SaveService{

   private final SaveRepository saverepository;

    @Override
    public void save(Save save) {
        save.setCreateDate(LocalDateTime.now());
        saverepository.save(save);
    }
}
