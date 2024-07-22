package dev.leoduarte.spingdatajpa.domain.hibernate;

import jakarta.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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

    @CreationTimestamp
    @Column(name = "CREATED_DATE", updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "LAST_MODIFIED_DATE", updatable = false)
    private Timestamp modifiedDate;

    private String firstName;
    private String lastName;

    public AuthorHibernate(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
