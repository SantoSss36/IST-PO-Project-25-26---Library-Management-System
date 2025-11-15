package bci;

import bci.user.*;

import bci.exceptions.*;
import bci.rules.CompositeRule;
import java.io.*;
import java.util.List;
/**
 * The façade class.
 */
public class LibraryManager {

    private CompositeRule _defaultRules = new CompositeRule();

    /** The object doing all the actual work. */
    private Library _library = new Library(_defaultRules);

    public void save() throws MissingFileAssociationException, IOException {
      String filename = _library.getFilename();
      if (filename == null) {
        throw new MissingFileAssociationException();
      }
      try (var oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
        oos.writeObject(_library);
        _library.setChanged(false);
      }
    }

    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
      _library.setFilename(filename);
      save();
    }

    public void load(String filename) throws UnavailableFileException {
      try (var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
        _library = (Library) in.readObject();
        _library.setFilename(filename); // update filename
        _library.setChanged(false);
      } catch (IOException | ClassNotFoundException e) {
        throw new UnavailableFileException(filename);
      }
    }

    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities represented in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      try {
        if (filename != null && !filename.isEmpty())
          _library.importFile(filename);
      } catch (IOException | UnrecognizedEntryException | UnavailableFileException | UnknownUserRegistrationException e) {
        throw new ImportFileException(filename, e);
      }
    }

    public boolean changed() {
      return _library.getChanged();
    }

    public int getDate() {
      return _library.getDate();
    }

    public void advanceDate(int days) {
      _library.advanceDate(days);
    }

    public Work displayWork(int identifier) throws UnknownWorkIdException {
      return _library.displayWork(identifier);
    }

    public User displayUser(int identifier) throws UnknownUserIdException {
      return _library.displayUser(identifier);
    }

    public List<Work> displayWorks() {
      return _library.displayWorks();
    }

    public List<User> displayUsers() {
      return _library.displayUsers();
    }

    public List<Work> displayCreatorWorks(String name) throws UnknownCreatorNameException {
      return _library.displayCreatorWorks(name);
    }

    public void createUser(String name, String email) throws UnknownUserRegistrationException {
      _library.createUser(name, email);
    } 

    public int getUserID() {
      return _library.getUserID();
    }

    public int getWorkID() {
      return _library.getWorkID();
    }

    public Creator searchCreator(String name) {
      return _library.searchCreator(name);
    }

    public List<Work> searchTerm(String term) {
      return _library.searchTerm(term);
    }

    public void updateStock(int identifier, int val) throws UnknownWorkIdException, InvalidValueException {
      _library.updateStock(identifier, val);
    }

    public void requestWork(int userId, int workId) throws InvalidRuleException, UnknownUserIdException, UnknownWorkIdException {
      _library.requestWork(userId, workId);
    }

    public int calcLimitDate(int userId, int workId){
      return _library.calcLimitDate(userId, workId);
    }

    public int getRequestLimitDate(int userId, int workId) throws UnknownRequestException {
      return _library.getRequestLimitDate(userId, workId);
    }

    public void calcFine(int userId, int workId) throws UnknownRequestException {
       _library.calcFine(userId, workId);
    }

    public int showFine(int userId) {
      return _library.showFine(userId);
    }

    public void updateUserFine(int userId, int fine) {
      _library.updateUserFine(userId, fine);
    }

    public Request searchRequest(int userId, int workId) throws UnknownRequestException {
      return _library.searchRequest(userId, workId);
    }
    public void returnWork(int userId, int workId)  throws UnknownUserIdException, UnknownWorkIdException, UnknownRequestException {
      _library.returnWork(userId, workId);
    }

    public void payFine(int userId) throws UnknownUserIdException, InvalidUserStatusException {
      _library.payFine(userId);
    }

    public void registerNotification(int userId, int workId) {
      _library.registerNotification(userId, workId);
    }

    public List<String> displayNotifications(int userId) throws UnknownUserIdException {
      return _library.displayNotifications(userId);
    }
}
