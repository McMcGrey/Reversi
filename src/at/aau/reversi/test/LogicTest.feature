# language: de
Funktionalität: Testen der Programmlogik

Szenario: Korrektes Spielfeld anzeigen
Angenommen es existiert ein neues Einzelspielerspiel
Dann sollte ein "weißer" Stein auf "D4" liegen
Und es sollte ein "weißer Stein auf "E5" liegen
Und es sollte ein "schwarzer" Stein auf "D5" liegen
Und es sollte ein "schwarzer" Stein auf "E4" liegen

Szenario: Erster Spielzug
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "schwarzen" Stein auf "E6" lege
Dann sollte ich "Weiß ist am Zug" als Fehler bekommen
Wenn ich einen "weißen" Stein auf "E5" lege
Dann sollte ich "Ungülter Spielzug" als Fehler bekommen
Wenn ich einen "weißen" Stein auf "F4" lege
Dann sollte ein "weißer" Stein auf "F4" liegen
Und es sollte ein "weißer Stein auf "E4" liegen
