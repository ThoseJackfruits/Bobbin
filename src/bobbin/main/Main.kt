package bobbin.main

import bobbin.characters.PlayerCharacter
import bobbin.interaction.ExitToException
import bobbin.interaction.Interactive
import bobbin.interaction.Interactive.ExitToSystemException
import bobbin.interaction.Interactive.ResetStackException
import bobbin.interaction.console.Console
import bobbin.interaction.console.SystemConsole
import bobbin.menus.MainMenu
import bobbin.menus.MainMenu.ExitToMainMenuException

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        start(MainMenu.dummyPlayerCharacter(), SystemConsole())
    }

    private fun start(
            playerCharacter: PlayerCharacter, console: Console) {
        var actor = playerCharacter
        val mainMenu = MainMenu()
        var next: Interactive = mainMenu
        while (true) {
            try {
                next.interact(actor, actor, console)
            } catch (e: ResetStackException) {
                next = e.then
                actor = e.actor
            } catch (e: ExitToMainMenuException) {
                mainMenu.saveGame(playerCharacter, console)
                next = mainMenu
            } catch (e: ExitToSystemException) {
                println("Goodbye!")
                break
            } catch (e: ExitToException) {
                System.err.println("Unhandled ExitToException: " + e.javaClass.name)
                break
            }
        }
    }
}