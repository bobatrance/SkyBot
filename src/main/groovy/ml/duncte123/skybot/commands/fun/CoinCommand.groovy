/*
 * Skybot, a multipurpose discord bot
 *      Copyright (C) 2017  Duncan "duncte123" Sterken & Ramid "ramidzkh" Khan & Maurice R S "Sanduhr32"
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ml.duncte123.skybot.commands.fun

import ml.duncte123.skybot.objects.command.Command
import ml.duncte123.skybot.objects.command.CommandCategory
import ml.duncte123.skybot.utils.AirUtils
import ml.duncte123.skybot.utils.EmbedUtils
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent

import java.util.concurrent.TimeUnit

class CoinCommand extends Command {

    def imagesArr = ["heads.png", "tails.png"]

    CoinCommand() {
        this.category = CommandCategory.FUN
    }

    @Override
    void executeCommand(String invoke, String[] args, GuildMessageReceivedEvent event) {

        event.channel.sendTyping().queue{
            event.channel.sendMessage("*Flips a coin*").queueAfter(500, TimeUnit.MILLISECONDS, {
                sendEmbed(event, EmbedUtils.embedImage("https://dshelmondgames.ml/img/coin/" + imagesArr[AirUtils.rand.nextInt(2)]))
            })
        }

    }

    @Override
    String help() {
        return "flips a coin.\nUsage: `$PREFIX$name`"
    }

    @Override
    String getName() {
        return "coin"
    }
}