package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.UnknownCreatorNameException;

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("name", Prompt.creatorId());
    }

    @Override
    protected final void execute() throws NoSuchCreatorException {
        String name = stringField("name");
        try {
            _receiver.displayCreatorWorks(name).stream()
                .forEach(w -> _display.addLine(w));
        } catch (UnknownCreatorNameException e) {
            throw new NoSuchCreatorException(name);
        }
        
    }

}
