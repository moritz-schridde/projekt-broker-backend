package com.example.financeapp.modules.share;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShareServieImpl implements ShareService {

    private final ShareRepository shareRepository;

    @Autowired
    public ShareServieImpl(ShareRepository shareRepository){
        this.shareRepository= shareRepository;
    }


    @Override
    public List<Share> findAllShare() throws Exception {

        return shareRepository.findAll();
    }
}
