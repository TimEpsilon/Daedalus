# Daedalus
<p align="center">
    <img src="https://github.com/zelytra/Daedalus/blob/master/img/logo.png" width="800" height="100" align="center">
</p>

![release](https://img.shields.io/badge/release-v0.4-blueviolet)
![language](https://img.shields.io/badge/langage-JAVA-orange)
![license](https://img.shields.io/badge/license-GPL--3.0-blue)
# Presentation

-----------------

# Setup
### Server:
First,you need to install a [**spigot**](https://getbukkit.org/download/spigot) server in **1.16.4/1.16.5**. I advise you to allocate 4G ram:  
``java -Xms1G -Xmx4G -jar server.jar nogui``    
  
After settings up the server, download the latest [**release**](https://github.com/zelytra/Daedalus/releases) of the plugin and put it inside *plugins* folder of your server.  

**IMPORTANT** : you also need to install [**FAWE**](https://ci.athion.net/job/FastAsyncWorldEdit-1.16/) (FastAsyncWorldEdit). The plugin maze generation depend on it to generate the Labyrinth.

### On Start
At the first boot, the plugin will download the map of the mini-game (you cannot use custom map).
>The map size is about *85MB* so prepare the server before inviting your friends !  

`[Daedalus]>> Downloading map please wait...`  
`[Daedalus]>> Extracting files please wait...`  
At this point, the map is setup and ready to be played ! 

### Reset
To reset the map, you only need to reboot the server :
> /stop or /restart as you want

It will reset the map (the plugin is smart, it will not download the map again, only extracting files !)

-----------------
# Documentation
 * [**Règles (français)**](https://github.com/zelytra/Daedalus/wiki/Rules-%5BFrench%5D) 
 * [**Rules (english)**](https://github.com/zelytra/Daedalus/wiki/Rules-%5BFrench%5D) 
-----------------
# Credits
* **Spirit__10** : Project Founder & Builder
* **Ichabodt** : Artistic Director & Builder
* [**Zelytra**](https://github.com/zelytra) : Developer
-----------------
# Thanks
* [**Nicolas61x**](https://github.com/Nicolas62x) : Help on maze optimization
* [**punisher5**](https://github.com/JohnPoliakov) : Help on GameManager part



