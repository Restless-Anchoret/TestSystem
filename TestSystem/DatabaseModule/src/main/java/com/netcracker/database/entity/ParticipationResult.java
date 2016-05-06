package com.netcracker.database.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.netcracker.database.entity.CompetitionProblem;
import javax.persistence.FetchType;

@Entity
@Table(name = "participation_result", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParticipationResult.findByCompetitionId", query = "SELECT p FROM ParticipationResult p WHERE p.competitionProblemId.competitionId.id = :competitionId ORDER BY p.id"),
    @NamedQuery(name = "ParticipationResult.findByCompetitionIdAndUserIdAndCompetitionProblemId", query = "SELECT p FROM ParticipationResult p WHERE p.competitionProblemId.competitionId.id = :competitionId AND p.userId.id = :userId AND p.competitionProblemId.id = :competitionProblemId ORDER BY p.id"),
    @NamedQuery(name = "ParticipationResult.findById", query = "SELECT p FROM ParticipationResult p WHERE p.id = :id")})
public class ParticipationResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "points")
    private short points;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fine")
    private int fine;
    @JoinColumn(name = "competition_problem_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompetitionProblem competitionProblemId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public ParticipationResult() {
    }

    public ParticipationResult(Integer id) {
        this.id = id;
    }

    public ParticipationResult(Integer id, short points, int fine) {
        this.id = id;
        this.points = points;
        this.fine = fine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public CompetitionProblem getCompetitionProblemId() {
        return competitionProblemId;
    }

    public void setCompetitionProblemId(CompetitionProblem competitionProblemId) {
        this.competitionProblemId = competitionProblemId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParticipationResult)) {
            return false;
        }
        ParticipationResult other = (ParticipationResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entity.ParticipationResult[ id=" + id + " ]";
    }
    
}
