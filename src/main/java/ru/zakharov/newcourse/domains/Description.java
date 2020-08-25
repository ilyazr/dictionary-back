package ru.zakharov.newcourse.domains;

import javax.persistence.*;

@Entity
@Table(name = "dictionary_description")
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    @Column(name = "description")
    private String description;

    public Description() {
    }

    @Override
    public String toString() {
        return "Description{" +
                "id=" + id +
                ", dictionary=" + dictionary +
                ", description='" + description + '\'' +
                '}';
    }

    public Description(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
