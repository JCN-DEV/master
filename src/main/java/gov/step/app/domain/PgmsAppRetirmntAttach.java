package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PgmsAppRetirmntAttach.
 */
@Entity
@Table(name = "pgms_app_retirmnt_attach")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pgmsappretirmntattach")
public class PgmsAppRetirmntAttach implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "app_retirmnt_pen_id", nullable = false)
    private Long appRetirmntPenId;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;


    @Column(name = "attachment_content_type", nullable = false)
    private String attachmentContentType;
    @NotNull
    @Column(name = "attach_doc_name", nullable = false)
    private String attachDocName;

    @ManyToOne
    @JoinColumn(name = "attach_name_id")
    private PgmsRetirmntAttachInfo attachName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppRetirmntPenId() {
        return appRetirmntPenId;
    }

    public void setAppRetirmntPenId(Long appRetirmntPenId) {
        this.appRetirmntPenId = appRetirmntPenId;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachDocName() {
        return attachDocName;
    }

    public void setAttachDocName(String attachDocName) {
        this.attachDocName = attachDocName;
    }

    public PgmsRetirmntAttachInfo getAttachName() {
        return attachName;
    }

    public void setAttachName(PgmsRetirmntAttachInfo PgmsRetirmntAttachInfo) {
        this.attachName = PgmsRetirmntAttachInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgmsAppRetirmntAttach pgmsAppRetirmntAttach = (PgmsAppRetirmntAttach) o;

        if ( ! Objects.equals(id, pgmsAppRetirmntAttach.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PgmsAppRetirmntAttach{" +
            "id=" + id +
            ", appRetirmntPenId='" + appRetirmntPenId + "'" +
            ", attachment='" + attachment + "'" +
            ", attachmentContentType='" + attachmentContentType + "'" +
            ", attachDocName='" + attachDocName + "'" +
            '}';
    }
}
