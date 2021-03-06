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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "test_group", catalog = "test_system", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestGroup.findById", query = "SELECT t FROM TestGroup t WHERE t.id = :id")})
public class TestGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "test_group_type")
    private String testGroupType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tests_quantity")
    private short testsQuantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "points_for_test")
    private short pointsForTest;

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
        return "com.netcracker.entity.TestGroup[ id=" + id + " ]";
    }
    
}
