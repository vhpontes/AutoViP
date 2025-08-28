### AutoViP - Bukkit Plugin

**AutoViP** is a player management plugin for Minecraft servers that use the Bukkit API (and compatible platforms like Spigot and PaperMC). Its main goal is to automate the process of granting VIP privileges to players based on predefined criteria.

---

### 🚀 What the Project Does

This plugin was developed to simplify server administration, allowing VIP status to be assigned automatically. The source code indicates that the tool can promote a player to a specific VIP group (defined in the `cfg_ViPgroup` configuration) upon reaching a certain amount of money (`cfg_getvip_money`) and/or experience (`cfg_getvip_xp`).

The plugin's main features include:

* **Flexible Configuration**: All variables, such as the VIP group name, promotion requirements, and notification messages, are customizable through a `config.yml` file.
* **Database Support**: It offers the option to use a **MySQL** database to manage data persistence. This ensures that player information is not lost even after server restarts.
* **In-Game Commands**: The plugin registers the `/av` command, which serves as the entry point for all its functionalities, managed by the `Commands.java` class.

---

### 💻 How to Use It

Installing and configuring AutoViP is straightforward.

1.  **Installation**: Place the `AutoViP.jar` file in your server's `plugins` folder.
2.  **First Run**: Start the server. The plugin will automatically generate a configuration file (`config.yml`) in the `plugins/AutoViP/` folder.
3.  **Configuration**: Edit the `config.yml` to adjust the settings according to your server's needs. You can define the money and XP requirements, the VIP group name, and enable MySQL support if required.
4.  **Commands**: After configuration, the plugin will be ready for use. Players will be automatically promoted when they meet the requirements. The `/av` command can be used to interact with the plugin.

---

### 🛠️ Requirements

To run the AutoViP plugin, you need the following components:

* **Minecraft Server**: A server running a **Bukkit** API-compatible platform (e.g., Spigot, PaperMC).
* **Java Runtime Environment (JRE)**: Required to run the plugin on the server.
* **MySQL Database (Optional)**: If the data persistence option with MySQL is enabled in `config.yml`, an active and accessible MySQL server is necessary for it to function correctly.

---

### 📂 Project Structure

The project structure, deduced from the imports and class references in `AutoViP.java`, is organized as follows:

* `src/org/mcforge/vhpontes/`: The main source code directory.
    * `AutoViP.java`: The main plugin class, responsible for initialization and shutdown.
    * `Commands.java`: Contains the logic for processing in-game commands.
    * `utils/`: Subdirectory for utility classes.
        * `ConfigFileUtils.java`: Handles the creation and management of the configuration file.
        * `DatabaseUtils.java`: Manages the database connection and related operations.
* `plugin.yml`: The plugin's manifest file, crucial for the server to recognize and load AutoViP.

---

### ⚖️ License

This project is licensed under the MIT License - see the [LICENSE](/LICENSE) file for details.
