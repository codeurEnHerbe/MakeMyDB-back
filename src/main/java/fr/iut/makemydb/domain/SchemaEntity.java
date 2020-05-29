package fr.iut.makemydb.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity
@Getter @Setter
public class SchemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "seq_schema", allocationSize = 1)
    @Setter(AccessLevel.PRIVATE)
    private Integer id;

    @Version
    private int version;

    private String name;

    @ManyToOne
    @JoinColumn(name="username")
    private UserInfosEntity user;

    @Lob
    @Column(columnDefinition="CLOB")
    private String schemaData;
}