package com.netcracker.databasemodul.entity;

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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "test_group", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestGroup.findAll", query = "SELECT t FROM TestGroup t"),
    @NamedQuery(name = "TestGroup.findById", query = "SELECT t FROM TestGroup t WHERE t.id = :id"),
    @NamedQuery(name = "TestGroup.findByTestGroupType", query = "SELECT t FROM TestGroup t WHERE t.testGroupType = :testGroupType"),
    @NamedQuery(name = "TestGroup.findByTestsQuantity", query = "SELECT t FROM TestGroup t WHERE t.testsQuantity = :testsQuantity"),
    @NamedQuery(name = "TestGroup.findByPointsForTest", query = "SELECT t FROM TestGroup t WHERE t.pointsForTest = :pointsForTest")})
public class TestGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "test_group_type")
    private String testGroupType;
    @Basic(optional = false)
    @Column(name = "tests_quantity")
    private short testsQuantity;
    @Basic(optional = false)
    @Column(name = "points_for_test")
    private short pointsForTest;
    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Problem problemId;

    public TestGroup() {
    }

    public TestGroup(Integer id) {
        this.id = id;
    }

    public TestGroup(Integer id, String testGroupType, short testsQuantity, short pointsForTest) {
        this.id = id;
        this.testGroupType = testGroupType;
        this.testsQuantity = testsQuantity;
        this.pointsForTest = pointsForTest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestGroupType() {
        return testGroupType;
    }

    public void setTestGroupType(String testGroupType) {
        this.testGroupType = testGroupType;
    }

    public short getTestsQuantity() {
        return testsQuantity;
    }

    public void setTestsQuantity(short testsQuantity) {
        this.testsQuantity = testsQuantity;
    }

    public short getPointsForTest() {
        return pointsForTest;
    }

    public void setPointsForTest(short pointsForTest) {
        this.pointsForTest = pointsForTest;
    }

    public Problem getProblemId() {
        return problemId;
    }

    public void setProblemId(Problem problemId) {
        this.problemId = problemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestGroup)) {
            return false;
        }
        TestGroup other = (TestGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.databasemodul.entity.TestGroup[ id=" + id + " ]";
    }

}