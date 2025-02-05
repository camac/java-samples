import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

import java.io.IOException;
import java.util.Collections;


public class AppDataSnippets {

  private Drive service;

  public AppDataSnippets(Drive service) {
    this.service = service;
  }

  public String uploadAppData() throws IOException {
    Drive driveService = this.service;
    // [START uploadAppData]
    File fileMetadata = new File();
    fileMetadata.setTitle("config.json");
    fileMetadata.setParents(Collections.singletonList(
        new ParentReference().setId("appDataFolder")));
    java.io.File filePath = new java.io.File("files/config.json");
    FileContent mediaContent = new FileContent("application/json", filePath);
    File file = driveService.files().insert(fileMetadata, mediaContent)
        .setFields("id")
        .execute();
    System.out.println("File ID: " + file.getId());
    // [END uploadAppData]
    return file.getId();
  }

  public FileList listAppData() throws IOException {
    Drive driveService = this.service;
    // [START listAppData]
    FileList files = driveService.files().list()
        .setSpaces("appDataFolder")
        .setFields("nextPageToken, items(id, title)")
        .setMaxResults(10)
        .execute();
    for (File file : files.getItems()) {
      System.out.printf("Found file: %s (%s)\n",
          file.getTitle(), file.getId());
    }
    // [END listAppData]
    return files;
  }

  public String fetchAppDataFolder() throws IOException {
    Drive driveService = this.service;
    // [START fetchAppDataFolder]
    File file = driveService.files().get("appDataFolder")
        .setFields("id")
        .execute();
    System.out.println("Folder ID: " + file.getId());
    // [END fetchAppDataFolder]
    return file.getId();
  }

}
