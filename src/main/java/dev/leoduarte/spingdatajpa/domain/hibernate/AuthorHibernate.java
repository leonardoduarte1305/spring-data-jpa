package dev.leoduarte.spingdatajpa.domain.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NamedQueries(value = {
        @NamedQuery(name = "author_find_all", query = "FROM AuthorHibernate"),
        @NamedQuery(name = "author_find_by_name", query = "FROM AuthorHibernate a WHERE a.firstName = :first_name AND a.lastName = :last_name")
})
@Table(name = "AUTHOR_HIBERNATE")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuthorHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String firstName;
    private String lastName;

}
