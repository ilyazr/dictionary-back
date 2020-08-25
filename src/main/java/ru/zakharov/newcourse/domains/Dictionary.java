package ru.zakharov.newcourse.domains;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "dictionary")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Calendar createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Calendar updateDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;

    @OneToMany(mappedBy = "dictionary",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Words> words;

    @OneToMany(mappedBy = "dictionary", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments;

    @Column(name = "dictionary_name")
    private String dictionaryName;

    @OneToOne(mappedBy = "dictionary", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Description description;

    public Dictionary() {
    }

    public Dictionary(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", user=" + user +
                '}';
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void addDescription(Description description) {
        this.description = description;
        description.setDictionary(this);
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setDictionary(this);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Words> getWords() {
        return words;
    }

    public void addWord(Words word) {
        if (words == null) {
            words = new ArrayList<>();
        }
        words.add(word);
        word.setDictionary(this);
    }

    public void setWords(List<Words> words) {
        this.words = words;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
