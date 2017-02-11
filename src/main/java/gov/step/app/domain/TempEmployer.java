package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.TempEmployerStatus;

/**
 * A TempEmployer.
 */
@Entity
@Table(name = "temp_employer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tempemployer")
public class TempEmployer implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="temp_employer_seq")
    @SequenceGenerator(name="temp_employer_seq", sequenceName="temp_employer_seq")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alt_company_name")
    private String alternativeCompanyName;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "person_designation")
    private String personDesignation;

    //@NotNull
   // @Size(min = 5)
    @Column(name = "contact_number")
    private String contactNumber;

   /* @NotNull
    @Size(min = 10)*/
    @Column(name = "company_info")
    private String companyInformation;

    //@NotNull
    //@Size(min = 10)
    @Column(name = "address")
    private String address;

    //@NotNull
    @Column(name = "city")
    private String city;

    /*@NotNull
    @Size(min = 4)*/
    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "company_website")
    private String companyWebsite;

   // @NotNull
    @Column(name = "industry_type")
    private String industryType;

    /*@NotNull
    @Size(min = 10)*/
    @Column(name = "business_description")
    private String businessDescription;

    @Lob
    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "company_logo_name")
    private String companyLogoName;

    @Column(name = "company_logo_content_type")
    private String companyLogoContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TempEmployerStatus status;

    @Column(name = "comments")
    private String comments;


    @Column(name = "date_created")
    private LocalDate dateCrated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "date_rejected")
    private LocalDate dateRejected;

    @Column(name = "date_approved")
    private LocalDate dateApproved;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "organization_category_id")
    private OrganizationCategory organizationCategory;

    @ManyToOne
    @JoinColumn(name = "organization_type_id")
    private OrganizationType organizationType;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternativeCompanyName() {
        return alternativeCompanyName;
    }

    public void setAlternativeCompanyName(String alternativeCompanyName) {
        this.alternativeCompanyName = alternativeCompanyName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getPersonDesignation() {
        return personDesignation;
    }

    public void setPersonDesignation(String personDesignation) {
        this.personDesignation = personDesignation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoName() {
        return companyLogoName;
    }

    public void setCompanyLogoName(String companyLogoName) {
        this.companyLogoName = companyLogoName;
    }

    public String getCompanyLogoContentType() {
        return companyLogoContentType;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public LocalDate getDateRejected() {
        return dateRejected;
    }

    public void setDateRejected(LocalDate dateRejected) {
        this.dateRejected = dateRejected;
    }

    public LocalDate getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(LocalDate dateApproved) {
        this.dateApproved = dateApproved;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public TempEmployerStatus getStatus() {
        return status;
    }

    public void setStatus(TempEmployerStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    public OrganizationCategory getOrganizationCategory() {
        return organizationCategory;
    }

    public void setOrganizationCategory(OrganizationCategory organizationCategory) {
        this.organizationCategory = organizationCategory;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TempEmployer tempEmployer = (TempEmployer) o;

        if ( ! Objects.equals(id, tempEmployer.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TempEmployer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", alternativeCompanyName='" + alternativeCompanyName + "'" +
            ", contactPersonName='" + contactPersonName + "'" +
            ", personDesignation='" + personDesignation + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", companyInformation='" + companyInformation + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", zipCode='" + zipCode + "'" +
            ", companyWebsite='" + companyWebsite + "'" +
            ", industryType='" + industryType + "'" +
            ", businessDescription='" + businessDescription + "'" +
            ", companyLogo='" + companyLogo + "'" +
            ", companyLogoContentType='" + companyLogoContentType + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
