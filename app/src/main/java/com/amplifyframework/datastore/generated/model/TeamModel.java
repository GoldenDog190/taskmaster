package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TeamModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TeamModels")
public final class TeamModel implements Model {
  public static final QueryField ID = field("TeamModel", "id");
  public static final QueryField NAME = field("TeamModel", "name");
  public static final QueryField TITLE = field("TeamModel", "title");
  public static final QueryField BODY = field("TeamModel", "body");
  public static final QueryField ASSIGNED = field("TeamModel", "assigned");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  public final @ModelField(targetType="String") String name;
  public final @ModelField(targetType="String") String title;
  public final @ModelField(targetType="String") String body;
  public final @ModelField(targetType="String") String assigned;
  public final @ModelField(targetType="Task") @HasMany(associatedWith = "task", type = Task.class) List<Task> teamModels = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getAssigned() {
      return assigned;
  }
  
  public List<Task> getTeamModels() {
      return teamModels;
  }
  
  private TeamModel(String id, String name, String title, String body, String assigned) {
    this.id = id;
    this.name = name;
    this.title = title;
    this.body = body;
    this.assigned = assigned;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TeamModel teamModel = (TeamModel) obj;
      return ObjectsCompat.equals(getId(), teamModel.getId()) &&
              ObjectsCompat.equals(getName(), teamModel.getName()) &&
              ObjectsCompat.equals(getTitle(), teamModel.getTitle()) &&
              ObjectsCompat.equals(getBody(), teamModel.getBody()) &&
              ObjectsCompat.equals(getAssigned(), teamModel.getAssigned());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getTitle())
      .append(getBody())
      .append(getAssigned())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TeamModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("assigned=" + String.valueOf(getAssigned()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static TeamModel justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new TeamModel(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      title,
      body,
      assigned);
  }
  public interface BuildStep {
    TeamModel build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep title(String title);
    BuildStep body(String body);
    BuildStep assigned(String assigned);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String title;
    private String body;
    private String assigned;
    @Override
     public TeamModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TeamModel(
          id,
          name,
          title,
          body,
          assigned);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep title(String title) {
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep assigned(String assigned) {
        this.assigned = assigned;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String title, String body, String assigned) {
      super.id(id);
      super.name(name)
        .title(title)
        .body(body)
        .assigned(assigned);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder assigned(String assigned) {
      return (CopyOfBuilder) super.assigned(assigned);
    }
  }
  
}
