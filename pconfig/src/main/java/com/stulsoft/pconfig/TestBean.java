package com.stulsoft.pconfig;

/**
 * @author Yuriy Stul
 */
public class TestBean {
    private Integer p1;
    private String p2;

    public TestBean() {
    }

    public Integer getP1() {
        return p1;
    }

    public void setP1(Integer p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestBean testBean = (TestBean) o;

        if (p1 != null ? !p1.equals(testBean.p1) : testBean.p1 != null) return false;
        return p2 != null ? p2.equals(testBean.p2) : testBean.p2 == null;
    }

    @Override
    public int hashCode() {
        int result = p1 != null ? p1.hashCode() : 0;
        result = 31 * result + (p2 != null ? p2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "p1=" + p1 +
                ", p2='" + p2 + '\'' +
                '}';
    }
}
