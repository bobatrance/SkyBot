package ml.duncte123.skybot.commands.guild.mod;

import ml.duncte123.skybot.objects.command.Command;
import ml.duncte123.skybot.utils.AirUtils;
import ml.duncte123.skybot.utils.Settings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class SoftbanCommand extends Command {

    /**
     * This is the executeCommand of the command, the thing you want the command to to needs to be in here
     * @param args The command agruments
     * @param event a instance of {@link net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent GuildMessageReceivedEvent}
     */
    @Override
    public void executeCommand(String[] args, GuildMessageReceivedEvent event) {

        Permission[] perms = {
                Permission.KICK_MEMBERS,
                Permission.BAN_MEMBERS
        };

        if (!event.getMember().hasPermission(perms)) {
            sendMsg(event, "You don't have permission to run this command");
            return;
        }

        if (event.getMessage().getMentionedUsers().size() < 1 || args.length < 2) {
            sendMsg(event, "Usage is " + Settings.prefix + getName() +" <@user> [Reason]");
            return;
        }

        try {
            final User toBan = event.getMessage().getMentionedUsers().get(0);
            if(toBan.equals(event.getAuthor()) &&
                    !event.getGuild().getMember(event.getAuthor()).canInteract(event.getGuild().getMember(toBan)) ) {
                sendMsg(event, "You are not permitted to perform this action.");
                return;
            }
            String reason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
            event.getGuild().getController().ban(toBan.getId(), 1, "Kicked by: " + event.getAuthor().getName() + "\nReason: " + reason).queue(
                    nothing -> {
                        AirUtils.modLog(event.getAuthor(), toBan, "kicked", reason, event.getGuild());
                        sendSuccess(event.getMessage());
                    }
            );
            event.getGuild().getController().unban(toBan.getId()).reason("(softban) Kicked by: " + event.getAuthor().getName()).queue();
        }
        catch (Exception e) {
            e.printStackTrace();
            sendMsg(event, "ERROR: " + e.getMessage());
        }
    }

    /**
     * The usage instructions of the command
     * @return a String
     */
    @Override
    public String help() {
        return "Kicks a user from the guild **(THIS WILL DELETE MESSAGES)**";
    }

    @Override
    public String getName() {
        return "softban";
    }
}