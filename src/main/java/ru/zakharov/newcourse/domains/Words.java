package ru.zakharov.newcourse.domains;

import javax.persistence.*;

@Entity
@Table(name = "words")
public class Words {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "word")
    private String word;

    @Column(name = "translate")
    private String translate;

    //Don't forget add list of word to the dictionary class!
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,
                                                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    public Words() {
    }

    public Words(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public Words(String word, String translate, Dictionary dictionary) {
        this.word = word;
        this.translate = translate;
        this.dictionary = dictionary;
    }

    @Override
    public String toString() {
        return "Words{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translate='" + translate + '\'' +
                ", dictionary=" + dictionary +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
