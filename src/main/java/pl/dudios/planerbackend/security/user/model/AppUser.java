package pl.dudios.planerbackend.security.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dudios.planerbackend.invoice.model.Invoice;
import pl.dudios.planerbackend.task.model.Task;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private List<Role> authorities;
    private String image;
    private String hash;
    private LocalDateTime HashDate;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userid")
    private List<Task> tasks;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userid")
    private List<Invoice> invoices;


}
