package bci.app.main;

import java.io.IOException;
import bci.LibraryManager;
import bci.app.exceptions.FileOpenFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import bci.exceptions.*;
//FIXME maybe import classes

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() throws FileOpenFailedException {
    	try {
			_receiver.save();
		} catch (MissingFileAssociationException e) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} catch (MissingFileAssociationException | IOException ex) {
				throw new FileOpenFailedException(ex);
			}
		} catch (IOException e) {
			throw new FileOpenFailedException(e);
		}
    }

}