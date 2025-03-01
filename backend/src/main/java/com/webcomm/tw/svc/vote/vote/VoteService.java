package com.webcomm.tw.svc.vote.vote;

import com.webcomm.tw.svc.vote.cache.CacheKey;
import com.webcomm.tw.svc.vote.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {
    
    private static final String RECORDS = "records";

    @Autowired 
    private VoteParams voteParams;
    
    @Autowired
    private CacheService cacheService;
    
    public VoteParams getParams() {
        return voteParams;
    }

    @SuppressWarnings("unchecked")
    public List<VoteRecord> getRecords() {
        return cacheService.getValue(CacheKey.VOTE, List.class, RECORDS)
                .orElseGet(ArrayList::new);
    }
    
    public List<VoteRecord> getRecords(String userId) {
        return getRecords()
                .stream()
                .filter(record -> userId.equals(((VoteRecord)record).getUserId()))
                .collect(Collectors.toList());
    }

    public void vote(VoteRecord record) {
        List<VoteRecord> records = getRecords();
        records.add(record);
        cacheService.setValue(CacheKey.VOTE, RECORDS, records);
    }

    public List<VoteResult> getResult() {
        List<VoteRecord> records = getRecords();
        List<VoteResult> result = new ArrayList<>();
        for(VoteOption option : getParams().getOptions()){
            long count = records
                    .stream()
                    .filter(record -> option.getOptionId().equals(((VoteRecord)record).getOptionId()))
                    .count();
            result.add(new VoteResult(option.getOptionId(), count));
        }
        return result;
    }
}