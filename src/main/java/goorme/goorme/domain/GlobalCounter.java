package goorme.goorme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
    public class GlobalCounter {
        @Id
        private String key;  // ex: "memberCnt"

        private int value;

        public void increment() {
            this.value += 1;
        }

        // getter, setter
    }
