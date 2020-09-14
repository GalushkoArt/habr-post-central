package tech.mtright.habrcrawler.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue
    int id;
    int postId;
    @Temporal(TemporalType.TIMESTAMP)
    Date date;
    String title;
    String link;
    String author;
    String company;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Tag> tags;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Hub> hubs;
}
