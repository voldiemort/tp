package seedu.address.logic.parser.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERS;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.group.CreateGroupCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for Commands of type CreateGroup.
 */
public class CreateGroupCommandParser implements Parser<CreateGroupCommand> {
    @Override
    public CreateGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME, PREFIX_MEMBERS);

        if (argMultimap.getValue(PREFIX_GROUP_NAME).isEmpty() || argMultimap.getValue(PREFIX_MEMBERS).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GROUP_NAME, PREFIX_MEMBERS);
        String groupName = argMultimap.getValue(PREFIX_GROUP_NAME).orElse("");
        String membersString = argMultimap.getValue(PREFIX_MEMBERS).orElse("");
        List<Index> members;
        try {
            members = Arrays.stream(membersString.split(" ")).map(
                    i -> Index.fromOneBased(Integer.parseInt(i))).toList();
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE),
                    e);
        }
        return new CreateGroupCommand(groupName, members);
    }
}