package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DlContUpld.
 */
@Entity
@Table(name = "dl_cont_upld")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlcontupld")
public class DlContUpld implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "c_name")
    private String cName;

    @Column(name = "auth_name")
    private String authName;

    @Column(name = "edition")
    private String edition;

    @Column(name = "isbn_no")
    private String isbnNo;

    @Column(name = "copyright")
    private String copyright;



    @Column(name = "year_of_publication")
    private String yearOfPublication;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;


    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "status")
    private Integer status;

    @Column(name = "counter")
    private Integer counter;

    @Column(name = "content_name", nullable = true)
    private String contentName;

    @Lob
    @Column(name = "content", nullable = true)
    private byte[] content;

    @Column(name = "content_content_type", nullable = true)
    private String contentContentType;

    @Column(name = "content_image_name", nullable = true)
    private String contentImageName;

    @Lob
    @Column(name = "content_image", nullable = true)
    private byte[] contentImage;

    @Column(name = "content_image_content_type", nullable = true)
    private String contentImageContentType;



    @ManyToOne
    @JoinColumn(name = "dl_cont_type_set_id")
    private DlContTypeSet dlContTypeSet;


    @ManyToOne
    @JoinColumn(name = "dl_cont_cat_set_id")
    private DlContCatSet dlContCatSet;

    @ManyToOne
    @JoinColumn(name = "dl_cont_s_cat_set_id")
    private DlContSCatSet dlContSCatSet;

    @ManyToOne
    @JoinColumn(name = "dl_source_set_up_id")
    private DlSourceSetUp dlSourceSetUp;

    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "hr_employee_info_id")
    private InstEmployee hrEmployeeInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dl_file_type_id")
    private DlFileType dlFileType;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {this.counter = counter;}


    public DlContTypeSet getDlContTypeSet() {
        return dlContTypeSet;
    }

    public void setDlContTypeSet(DlContTypeSet dlContTypeSet) {
        this.dlContTypeSet = dlContTypeSet;
    }

    public DlContCatSet getDlContCatSet() {
        return dlContCatSet;
    }

    public void setDlContCatSet(DlContCatSet dlContCatSet) {
        this.dlContCatSet = dlContCatSet;
    }

    public DlContSCatSet getDlContSCatSet() {
        return dlContSCatSet;
    }

    public void setDlContSCatSet(DlContSCatSet dlContSCatSet) {
        this.dlContSCatSet = dlContSCatSet;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public DlFileType getDlFileType() {
        return dlFileType;
    }

    public void setDlFileType(DlFileType dlFileType) {
        this.dlFileType = dlFileType;
    }


    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentImageContentType() {
        return contentImageContentType;
    }

    public void setContentImageContentType(String contentImageContentType) {
        this.contentImageContentType = contentImageContentType;
    }

    public InstEmployee getHrEmployeeInfo() {
        return hrEmployeeInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHrEmployeeInfo(InstEmployee hrEmployeeInfo) {
        this.hrEmployeeInfo = hrEmployeeInfo;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public byte[] getContentImage() {
        return contentImage;
    }

    public void setContentImage(byte[] contentImage) {
        this.contentImage = contentImage;
    }

    public DlSourceSetUp getDlSourceSetUp() {
        return dlSourceSetUp;
    }

    public void setDlSourceSetUp(DlSourceSetUp dlSourceSetUp) {
        this.dlSourceSetUp = dlSourceSetUp;
    }

    public String getContentImageName() {
        return contentImageName;
    }

    public void setContentImageName(String contentImageName) {
        this.contentImageName = contentImageName;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlContUpld dlContUpld = (DlContUpld) o;

        if ( ! Objects.equals(id, dlContUpld.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlContUpld{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", authName='" + authName + "'" +
            ", edition='" + edition + "'" +
            ", isbnNo='" + isbnNo + "'" +
            ", copyright='" + copyright + "'" +
            ", publisher='" + publisher + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            ", status='" + counter + "'" +
            '}';
    }
}
