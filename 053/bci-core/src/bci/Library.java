package bci;

import bci.exceptions.*;
import bci.notifications.NotificationType;
import bci.user.*;
import bci.rules.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/** Class that represents the library as a whole. */
class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private Map<Integer, Work> _works = new TreeMap<>();  

    private Map<String, Creator> _creators = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private Map<Integer, User> _users = new TreeMap<>();  

    private List<Request> _requests = new ArrayList<>();

    private String _filename = null;    // inicializes the filename String

    private boolean _changed = false;   // if the imported file has been changed

    private int _date = 1;   // inicializes the date

    private int _workId = 1;  // inicializes the identifier for each work

    private int _userId = 1;  // inicializes the identifier for each user

    private CompositeRule _rules;

    Library(CompositeRule rules) {
      _rules = rules;
    }

    /**
     * Getter for the filename
     * @return filename
     */
    public String getFilename() {
      return _filename;
    }

    /**
     * Setter for the filename
     * @param filename
     */
    public void setFilename(String filename) {
      _filename = filename;
      _changed = true;
    }

    /**
     * Getter for a boolean that indicates if there were changes to the saved file
     * @return boolean
     */
    public boolean getChanged() {
      return _changed;
    }

    /**
     * Setter for the boolean that indicates if there were changes to the saved file
     * @param changed
     */
    public void setChanged(boolean changed) {
      _changed = changed;
    }

    /**
     * Getter for the current date
     * @return date
     */
    public int getDate() {
      return _date;
    }

    public boolean workIdNotExists(int identifier) {
      return identifier > getWorkID() || identifier <= 0;
    }

    /**
     * Searches a work based on its identifier
     * @param identifier
     * @return work
     */
    public Work displayWork(int identifier) throws UnknownWorkIdException {
      verifyWorkId(identifier);

      return _works.get(identifier);
    }

    /**
     * Verifies if a user ID is valid.
     * @param identifier
     * @return
     */
    public boolean userIdNotExists(int identifier) {
      return identifier > getUserID() || identifier <= 0;
    }

    /**
     * Searches a user based on its identifier
     * @param identifier
     * @return user
     */
    public User displayUser(int identifier) throws UnknownUserIdException {
      if (userIdNotExists(identifier)) {
        throw new UnknownUserIdException();
      }
      return _users.get(identifier);
    }

    /**
     * Searches for all works
     * @return List<Work>
     */
    public List<Work> displayWorks() {
      return new ArrayList<>(_works.values());
    }

    /**
     * Searches for all users
     * @return List<User>
     */
    public List<User> displayUsers() {
      List<User> users = new ArrayList<>(_users.values());
      Collections.sort(users);
      return users;
    }

    /**
     * Searches for the works made by a creator
     * @param name
     * @return List<Work>
     */
    public List<Work> displayCreatorWorks(String name) throws UnknownCreatorNameException {
      Creator creator = searchCreator(name);
      if (creator == null) {
        throw new UnknownCreatorNameException();
      }
      return creator.getWorkList();
    }

    /**
     * Searches a creator based on its identifier
     * @param name
     * @return creator
     */
    public Creator searchCreator(String name) {
      return _creators.get(name);
    }

    /**
     * Advances the current date by the number of days given
     * @param days
     */
    public void advanceDate(int days) {
      if (days > 0) {
        _date = _date + days;
        
        updateStatus();
        _changed = true;
      }
    }

    /**
     * Creates an instance of Book
     * @param fields
     */

    public void createBook (String... fields) throws UnrecognizedEntryException {
      String title = fields[1], authors = fields[2], category = fields[4], isbn = fields[5];
      int price = Integer.parseInt(fields[3]), copies = Integer.parseInt(fields[6]);

      if (isbn.length() != 10 && isbn.length() != 13) {
        throw new UnrecognizedEntryException(isbn);
      }

      List<String> _cleanAuthors = cleanStringAuthors(authors);
      List<Creator> creators = createCreator(_cleanAuthors);

      Book book = new Book(_workId++, title, creators, price, Category.valueOf(category), isbn, copies);
      addWorkToCreator(book, creators);
      _works.put(book.getWorkId(), book);
      _changed = true;
    }

    /**
     * Cleans the String that contains the authors names, by removing the commas and spaces
     * between the names and separates them in separate Strings
     * @param authors
     * @return List<String>
     */
    public List<String> cleanStringAuthors(String authors) {
      List<String> _authorsClean = new ArrayList<>();
      
      String[] fields = authors.split(",");
      if (fields.length > 1) {
          for(String i : fields) {
              i.trim();
              _authorsClean.add(i);
          }
      }
      else {
          _authorsClean.add(fields[0]);
      }
      return _authorsClean;
    }

    /**
     * Creates an instance of DVD
     * @param fields
     */
    public void createDvd (String... fields) {
      String title = fields[1], category = fields[4], igac = fields[5];
      int price = Integer.parseInt(fields[3]), copies = Integer.parseInt(fields[6]);
      List<String> directorName = new ArrayList<>();
      directorName.add(fields[2]);

      List<Creator> creator = createCreator(directorName);
      DVD dvd = new DVD(_workId++, title, creator, price, Category.valueOf(category), igac, copies);
      addWorkToCreator(dvd, creator);
      _works.put(dvd.getWorkId(), dvd);
      _changed = true;
    }

    /**
     * Creates an instance of Creator
     * @param names
     */
    public List<Creator> createCreator (List<String> names) {
      List<Creator> _creatorsList = new ArrayList<>();

      if (names.size() > 0) {
        for (String i : names) {
          Creator creator;
          if (_creators.get(i) == null) {
            creator = new Creator(i);
            _creators.put(i, creator);
          }
          else{
            creator = _creators.get(i);
          }
          _creatorsList.add(creator);
        }
      }
      return _creatorsList;
    }

    /**
     * Adds a given work to a list of creator or multiple creators
     * @param work
     * @param creator
     */
    public void addWorkToCreator(Work work, List<Creator> creator) {
      for (Creator i : creator) {
        i.addToList(work);                                           // adds the work to the creator's work list
      }
    }

    /**
     * Checks if the name or email given already exists
     * @param name
     * @param email
     * @return boolean
     */
    public boolean nameEmailNotExists(String name, String email) {
      if (name.isEmpty() | email.isEmpty()) {                        // checks if the name or email is empty
        return true;
      }
      return false;
    }

    /**
     * Creates an instance of User
     * @param name
     * @param email
     */
    public void createUser(String name, String email) throws UnknownUserRegistrationException {
      if (nameEmailNotExists(name, email)) {
        throw new UnknownUserRegistrationException();
      }
      User user = new User(_userId++, name, email);                  // creates a new instance of User
      _users.put(user.getId(), user);                                // adds the user to the users map                      
      _changed = true;
    }

    public int getUserID() {
      return _userId - 1;
    }

    public int getWorkID() {
      return _workId - 1;
    }

    /**
     * Searchs for a title or a creator from a term
     * @param term
     * @return
     */
    public List<Work> searchTerm(String term) {
      String termLower = term.toLowerCase();
      
      return _works.values().stream()
            .filter(w -> w.getTitle().toLowerCase().contains(termLower) ||    // searchs for a title
            w.getCreatorName().toLowerCase().contains(termLower))             // searchs for a creator name
            .collect(Collectors.toList()); 
    }

    /**
     * Destroys an instance of Work
     * @param identifier
     */
    public void destroyWork(int identifier) {
      List<Creator> list = _works.get(identifier).getCreators();
      for (Creator creator : list) {
        creator.removeWorkFromCreator(_works.get(identifier));       // removes the work from the creator's work list
        destroyCreator(creator.getName());                           // removes the creator if he has no works left
      }
      _works.remove(identifier);
      _changed = true;
    }

    /**
     * Destroys an instance of Creator
     * @param name
     */
    public void destroyCreator(String name) {
      if (_creators.get(name).getWorkList().size() == 0) {           // checks if the creator has no works left
        _creators.remove(name);                                      // removes the creator from the creator's map
        _changed = true;
      }
    }

    /**
     * Updates the number of copies of a Work
     * @param identifier
     * @param val
     * @throws UnknownWorkIdException
     * @throws InvalidValueException
     */
    public void updateStock(int identifier, int val) throws UnknownWorkIdException, InvalidValueException {
      Work work = _works.get(identifier);
      verifyWorkId(identifier);                                      // verifies if the work ID is valid
      
      if (work.getAvailableCopies() + val < 0) {
        throw new InvalidValueException();
      }
      if (work != null) {
        work.changeCopies(val);
      }
      if (work.getCopies() == 0) {
        destroyWork(identifier);
      }

      if (work.getAvailableCopies() == val) {
        work.notifyObservers(NotificationType.valueOf("DISPONIBILIDADE"), work);
      }

      _changed = true;
    }

    /**
     * Request a Work
     * @param userId
     * @param workId
     * @throws InvalidRuleException
     * @throws UnknownWorkIdException
     * @throws UnknownUserIdException
     */
    public void requestWork(int userId, int workId) throws InvalidRuleException, UnknownWorkIdException, UnknownUserIdException{
      verifyWorkId(workId);                                          // verifies if the work ID is valid
      verifyUserId(userId);                                          // verifies if the user ID is valid

      User user = _users.get(userId);
      Work work = _works.get(workId);
      
      _rules.isValid(user, work);

      user.addBorrowedWork(work);
      int deadLine = calcLimitDate(userId, workId);
      _requests.add(new Request(user, work, deadLine));
      work.changeAvailableCopies(-1);

      user.removeNotification(NotificationType.valueOf("DISPONIBILIDADE"), work);

      _changed = true;
    }

    /**
     * Calculates the limit date of a request
     * @param userId
     * @param workId
     * @return
     */
    public int calcLimitDate(int userId, int workId){
      
      if (_works.get(workId).getCopies() == 1) {
        return _date + _users.get(userId).getBehaviour().borOneCopyPeriod();
      }
      else if (_works.get(workId).getCopies() <= 5) {
        return _date + _users.get(userId).getBehaviour().borFiveLessCopyPeriod();
      }
      else {
        return _date + _users.get(userId).getBehaviour().borFiveMoreCopyPeriod();
      }
    }

    /**
     * Searchs for a request
     * @param userId
     * @param workId
     * @return
     * @throws UnknownRequestException
     */
    public Request searchRequest(int userId, int workId) throws UnknownRequestException {

      for (Request req : _requests) {
        if (req.getUser().getId() == userId && req.getWork().getWorkId() == workId) {
          return req;
        }
      }
      throw new UnknownRequestException();
    }

    /**
     * Searches for a request made by a user for a specific work
     * @param userId
     * @param workId
     * @return
     * @throws UnknownRequestException
     */
    public int getRequestLimitDate(int userId, int workId) throws UnknownRequestException {

      Request req = searchRequest(userId, workId);
      return req.getLimitDate();
    }

    /**
     * Calculates the fine for a user who has returned a work late
     * @param userId
     * @param workId
     * @throws UnknownRequestException
     */
    public void calcFine(int userId, int workId) throws UnknownRequestException {
      User user = _users.get(userId);

      if (!requestIsInTime(userId, workId)) {
        Request req = searchRequest(userId, workId);
        int daysLate = _date - req.getLimitDate();                   // calculates the number of days late
        int fineAmount = daysLate * 5;                               // calculates the fine amount

        user.addFine(fineAmount);                                    // adds the fine to the user's account
        user.updateStreakRequests(-1);                               // user is penalized for late return
      } else {
        user.updateStreakRequests(1);                          // user is rewarded for timely return  
      }
    }

    /**
     * Shows the fine for a user
     * @param userId
     * @return
     */
    public int showFine(int userId) {
      return _users.get(userId).getFine();
    }

    /**
     * Updates the fine for a user
     * @param userId
     * @param fine
     */
    public void updateUserFine(int userId, int fine) {
      User user = _users.get(userId);
      user.payFine(fine);
      if (user.getFine() == 0 && requestsIsInTime(userId)) {
        user.active();
      }
    }

    /**
     * Searches for a request made by a user for a specific work
     * @param userId
     * @param workId
     * @throws UnknownUserIdException
     * @throws UnknownWorkIdException
     * @throws UnknownRequestException
     */
    public void returnWork(int userId, int workId) throws UnknownUserIdException, UnknownWorkIdException, UnknownRequestException {
      verifyUserId(userId);                                          // verifies if the user ID is valid
      verifyWorkId(workId);                                          // verifies if the work ID is valid

      User user = _users.get(userId);
      Work work = _works.get(workId);

      Request requestToRemove = searchRequest(userId, workId);

      calcFine(userId, workId);
      _requests.remove(requestToRemove);
      user.removeBorrowedWork(work);
      work.changeAvailableCopies(1);

      if (work.getAvailableCopies() == 1) {
        work.notifyObservers(NotificationType.valueOf("DISPONIBILIDADE"), work);
      }

      _changed = true;
    }

    /**
     * Checks if a request made by a user for a specific work is still within the time limit
     * @param userId
     * @param workId
     * @return
     */
    public boolean requestIsInTime(int userId, int workId) {
      for (Request req : _requests) {
        if (req.getUser().getId() == userId && req.getWork().getWorkId() == workId) {
          if (_date > req.getLimitDate()) {
            return false;
          }
        }
      }
      return true;
    }

    /**
     * Checks if a request made by a user for a specific work is still within the time limit
     * @param userId
     * @return
     */
    public boolean requestsIsInTime(int userId) {
      for (Request req : _requests) {
        if (req.getUser().getId() == userId) {
          if (_date > req.getLimitDate()) {
            return false;
          }
        }
      }
      return true;
    }
    
    /**
     * Verifies if a work ID is valid
     * @param workId
     * @throws UnknownWorkIdException
     */
    public void verifyWorkId(int workId) throws UnknownWorkIdException {
      if (_works.get(workId) == null) {
        throw new UnknownWorkIdException();
      }
    }

    /**
     * Verifies if a user ID is valid
     * @param userId
     * @throws UnknownUserIdException
     */
    public void verifyUserId(int userId) throws UnknownUserIdException {
      if (_users.get(userId) == null) {
        throw new UnknownUserIdException();
      }
    }

    /**
     * Updates the status of users based on their requests
     */
    public void updateStatus() {
      for (Request req : _requests) {
        int userId = req.getUser().getId();
        if (_date > req.getLimitDate()) {
          _users.get(userId).suspended();
        }
      }
    }

    /**
     * Pays the fine for a user
     * @param userId
     * @throws UnknownUserIdException
     * @throws InvalidUserStatusException
     */
    public void payFine(int userId) throws UnknownUserIdException, InvalidUserStatusException {
      verifyUserId(userId);                                          // verifies if the user ID is valid
      User user = _users.get(userId);
      
      if (user.getStatus().mustPayFine()) {
        user.payFine(user.getFine());
        if (user.getFine() == 0 && requestsIsInTime(userId)) {
        user.active();
        }
      }
      else {
        throw new InvalidUserStatusException();
      }
    }

    /**
     * Registers a notification for a user when a specific work becomes available
     * @param userId
     * @param workId
     */
    public void registerNotification(int userId, int workId) {
      User user = _users.get(userId);
      Work work = _works.get(workId);
      
      work.registerObserver(NotificationType.valueOf("DISPONIBILIDADE"), user);
    }

    /**
     * Displays the notifications for a user
     * @param userId
     * @return
     * @throws UnknownUserIdException
     */
    public List<String> displayNotifications(int userId) throws UnknownUserIdException {
      verifyUserId(userId);                                          // verifies if the user ID is valid
      User user = _users.get(userId);
      List<String> notif = new ArrayList<>(user.getNotifications());
      user.cleanNotifications();                                     // clears the user's notifications (list)
      return notif;
    }

    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users)
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException
     * @throws UnavailableFileException
     * @throws IOException
     * @throws UnknownUserRegistrationException
     */

    void importFile(String filename) throws UnrecognizedEntryException, UnavailableFileException, IOException, UnknownUserRegistrationException {
      try( var in = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = in.readLine()) != null) {
          String[] fields = line.split(":");
          switch(fields[0]) {                                               // process fields
            case "BOOK" -> createBook(fields);                              // create book
            case "DVD" -> createDvd(fields);                                // create DVD
            case "USER" -> createUser(fields[1], fields[2]);                // create user
            default -> throw new UnrecognizedEntryException(fields[0]);
          }
        }
        _changed = true;
        in.close();
      }
      catch (IOException e) {
        throw new UnavailableFileException(filename);
      }
    }
}
