package org.eventer.entity;

import lombok.AllArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @JsonIgnore
    @OneToMany(targetEntity = Event.class, mappedBy = "account", cascade = CascadeType.ALL)
    private List<Event> events;

    @JsonIgnore
    @OneToMany(targetEntity = Stats.class, mappedBy = "account", cascade = CascadeType.ALL)
    private List<Stats> stats;

    public Account(){
        super();
    }

    public Account(String name) {
        super();
        this.name = name;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(events, account.events) && Objects.equals(stats, account.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, events, stats);
    }
}
