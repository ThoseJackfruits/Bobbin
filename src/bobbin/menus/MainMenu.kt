package bobbin.menus

import bobbin.boundaries.Room
import bobbin.characters.GameCharacter
import bobbin.characters.PlayerCharacter
import bobbin.constants.Actions
import bobbin.interaction.ConsolePrompt
import bobbin.interaction.ExitToException
import bobbin.interaction.Printers
import bobbin.interaction.actions.ActionList
import bobbin.interaction.console.Console
import bobbin.io.gamedata.SaveGameSerial
import bobbin.io.settings.Settings
import bobbin.items.BaseGameEntity
import java.io.File
import java.io.IOException
import java.util.*

open class MainMenu : Menu(Settings.MESSAGES_BUNDLE.getString("MainMenu.name"), "") {
    internal class DummyPlayerCharacter(name: String?, description: String?, location: Room?) : PlayerCharacter(name, description, location)

    @JvmOverloads
    fun saveGame(playerCharacter: PlayerCharacter, console: Console? = null) {
        Objects.requireNonNull(playerCharacter, "PlayerCharacter must be provided to save the game")
        try {
            SaveGameSerial(playerCharacter.name
                    .replace(' ', '_')
                    .replace(File.separatorChar, '.'))
                    .saveData(playerCharacter)
            if (console != null) {
                Printers.printMessage(console, "MainMenu.gameSaved")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    class ExitToMainMenuException : ExitToException()

    override fun actions(actor: GameCharacter, from: BaseGameEntity): ActionList {
        val actions = ActionList()
        if (SaveGameSerial.hasActiveSave()) {
            val activeSave = SaveGameSerial.loadActiveSave()
            if (activeSave != null && activeSave.loadData() !is DummyPlayerCharacter) {
                // Only allow continue if the current player isn't a dummy
                actions.add(Actions.CONTINUE)
            }
        }
        actions.add(Actions.NEW_GAME)
        actions.add(Actions.EXIT_MENU)
        return actions
    }

    @Throws(ExitToException::class)
    override fun respondToInteraction(
            actor: PlayerCharacter, from: BaseGameEntity, console: Console): Int {
        currentPlayerCharacter = actor
        Printers.println(console)
        Printers.print(console, this)
        Printers.println(console)
        val next = ConsolePrompt.getChoice(console, actions(actor, from), null)
        if (next == Actions.CONTINUE) {
            val saveGame = SaveGameSerial.loadActiveSave() ?: throw IllegalStateException("SaveGame is null")
            val playerCharacter = saveGame.loadData()
            currentPlayerCharacter = playerCharacter
            return playerCharacter.interact(playerCharacter, this, console)
        }
        return next.apply(actor).interact(actor, this, console)
    }

    companion object {
        var currentPlayerCharacter: PlayerCharacter? = null
        fun dummyPlayerCharacter(): PlayerCharacter {
            val fakeRoom = Room("Fake Room", "For dummy character to exist in")
            return DummyPlayerCharacter("A Dummy Character", "To be used in the main menu", fakeRoom)
        }

        /**
         * Build a room, allowing the player to configure their character.
         *
         * @param console player input/output
         *
         * @return [PlayerCharacter] in the new game.
         */
        @JvmStatic
        fun buildNewGame(console: Console?): PlayerCharacter {
            val startingRoom = Room("Starting Room", "A Whole New Room")
            val playerCharacter = PlayerCharacter(
                    ConsolePrompt.getResponseString(console, "Character Name"),
                    ConsolePrompt.getResponseString(console, "Character Tagline"),
                    startingRoom)
            currentPlayerCharacter = playerCharacter
            return playerCharacter
        }
    }

    init {
        Runtime.getRuntime().addShutdownHook(Thread(Runnable {
            if (currentPlayerCharacter is PlayerCharacter && currentPlayerCharacter !is DummyPlayerCharacter) {
                saveGame(currentPlayerCharacter!!)
                println(Settings.MESSAGES_BUNDLE.getString("MainMenu.gameSaved"))
            }
        }))
    }
}