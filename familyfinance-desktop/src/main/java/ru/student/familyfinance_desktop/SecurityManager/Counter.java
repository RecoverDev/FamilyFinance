package ru.student.familyfinance_desktop.SecurityManager;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Setter;
import ru.student.familyfinance_desktop.Storage.Storage;

@Setter
@Component
public class Counter {
    
    @Value("${counter.max-count}")
    private int maxCount;

    private int currentCount = 0;

    @Value("${counter.timeout-minutes}")
    private int timeout = 0;

    private LocalDateTime blockTime = LocalDateTime.now();

    @Autowired
    private Storage storage;

    public Counter() {
        String store = "";
        store = storage.loadCreditional("currentCount");
        if (!store.isEmpty()) {
            this.currentCount = Integer.parseInt(store);
        }
        store = storage.loadCreditional("timeout");
        if (!store.isEmpty()) {
            this.blockTime = LocalDateTime.parse(store);
        }
    }

    public int inc() {
        if (this.currentCount < this.maxCount) {
            storage.saveCreditional("currentCount", Integer.toString(++this.currentCount));
            return this.currentCount;
        } else {
            this.blockTime = LocalDateTime.now().plusMinutes(timeout);
            return this.currentCount;
        }
    }

    public int remaining() {
        return this.maxCount - this.currentCount;
    }

    public boolean isBlocked() {
        boolean result =  LocalDateTime.now().isAfter(blockTime);
        if (result) {
            this.reset();
        }
        return !result;
    }

    public void reset() {
        storage.saveCreditional("currentCount", "0");
    }
}
