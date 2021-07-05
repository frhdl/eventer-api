package org.eventer.entity;

import lombok.AllArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="createdAt")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="type")
    private String type;

    @Column(name="happenedAt")
    private Date happenedAt;

    @JsonIgnore
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", nullable = false)
    public Account account;

    public Event(){ super();}

    public Event(String type, Date happenedAt, Account account) {
        super();
        this.type = type;
        this.happenedAt = happenedAt;
        this.account = account;
    }

    public String getType(){
        return this.type;
    }

    public Account getAccount() { return this.account; }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getHappenedAt() {
        return happenedAt;
    }

    public Date getCreatedAt() {return createdAt;}

    @Override
    public String toString() {
        return String.format("Event{\"happenedAt\": \"%s\", \"type\": \"%s\"}", happenedAt, type);
    }
}
