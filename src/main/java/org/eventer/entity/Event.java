package org.eventer.entity;

import lombok.AllArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Event")
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
    private Account account;

    public Event(){ super();}

    public Event(String type, Date happenedAt, Account account) {
        super();
        this.type = type;
        this.happenedAt = happenedAt;
        this.account = account;
    }

    private Long getId(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    private Account getAccount() { return this.account; }

    public Date getHappenedAt() {
        return happenedAt;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setHappenedAt(Date happenedAt) {
        this.happenedAt = happenedAt;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Event))
            return false;
        Event event = (Event) o;
        return Objects.equals(this.id, event.id) && Objects.equals(this.type, event.type) && Objects.equals(this.createdAt, event.createdAt) && Objects.equals(this.account, event.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type, this.account);
    }

    @Override
    public String toString() {
        return String.format("Event{\"happenedAt\": \"%s\", \"type\": \"%s\"}", this.happenedAt, this.type);
    }
}
