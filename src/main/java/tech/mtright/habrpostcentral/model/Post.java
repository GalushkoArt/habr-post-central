package tech.mtright.habrpostcentral.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@ToString
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(indexes = {@Index(columnList = "postId")})
public class Post {
    @Id
    @GeneratedValue
    @JsonIgnore
    int id;
    int postId;
    @Temporal(TemporalType.TIMESTAMP)
    Date date;
    String title;
    String link;
    String author;
    int votes;
    int views;
    @Nullable
    String company;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Tag> tags;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Hub> hubs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postId == post.postId &&
                date.equals(post.date) &&
                title.equals(post.title) &&
                link.equals(post.link) &&
                Objects.equals(author, post.author) &&
                Objects.equals(company, post.company) &&
                Objects.equals(tags, post.tags) &&
                Objects.equals(hubs, post.hubs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, date, title, link, author, company, tags, hubs);
    }
}
