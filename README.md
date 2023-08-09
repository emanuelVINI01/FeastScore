# FeastScore
A scoreboard plugin with configurations and hooks.

# [Click to see demo video.](https://youtu.be/gdYQ47cKQlE])

![](https://i.imgur.com/JGSneqb.gif)

![](https://i.imgur.com/9Xsftok.gif)
    
# Configuration of scoreboard

```yaml
# Suporte atual aos hooks: PlotSquared e SimpleClans
required-hook: "PlotSquared" #Requer que o jogador esteja em um terreno dominado.
required-world: "" #Mundo ao qual o jogador deve estar para ativar ela
required-permission: "" #Se for vazio não requer
weight: 2 #Usado para definir se ela será sobreescrita a outra scoreboard

scoreboard:
  title: "&5&lFEAST PLUGINS"
  lines:
    - "&r"
    - " &fTPS: &a%server_tps%"
    - " &fNome: &a%player_name%"
    - "&r"
    - " &fPing: &a%player_ping%"
    - ' &fUptime: &a%server_uptime%'
    - ""
    - " &dfeastplugins.com"

```
