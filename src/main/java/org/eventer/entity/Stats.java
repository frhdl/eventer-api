package org.eventer.entity;

import lombok.AllArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;

@Entity(name = "AccountStats")
@AllArgsConstructor
@Table(name = "account_stats", uniqueConstraints = { @UniqueConstraint(columnNames = { "day", "event_type" }) })
@SQLInsert(sql="INSERT INTO account_stats (account_id, count, day, event_type) values (?, ?, ?, ?) " +
        "ON CONFLICT (day,event_type) DO UPDATE SET count = account_stats.count + EXCLUDED.count")
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
    private Account account;

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

    private Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    private Account getAccount() {
        return account;
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
