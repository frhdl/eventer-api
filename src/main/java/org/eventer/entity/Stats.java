package org.eventer.entity;

import lombok.AllArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Table(name = "account_stats", uniqueConstraints = { @UniqueConstraint(columnNames = { "account_id", "day", "event_type" }) })
@SQLInsert(sql="INSERT INTO account_stats (account_id, count, day, event_type) values (?, ?, ?, ?) " +
        "ON CONFLICT (account_id,day,event_type) DO UPDATE SET count = account_stats.count + EXCLUDED.count")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="day")
    private String day;

    @Column(name="event_type")
    private String eventType;

    @Column(name="count")
    private Long count;

    @JsonIgnore
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    public Account account;

    public Stats() {
        super();
    }

    public Stats(String day, String eventType, Long count, Account account) {
        super();
        this.day = day;
        this.eventType = eventType;
        this.count = count;
        this.account = account;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {

        return String.format("AccountStats{\"day\":\"%s\",\"type\":\"%s\",\"count\":\"%s\"}",
                day, eventType, count);
    }
}
