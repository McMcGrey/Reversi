# language: de
Funktionalität: Testen der Programmlogik

Szenario: Korrektes Spielfeld anzeigen
Angenommen es existiert ein neues Einzelspielerspiel
Dann sollte ein "wei�er" Stein auf "D4" liegen
Und es sollte ein "wei�er" Stein auf "E5" liegen
Und es sollte ein "schwarzer" Stein auf "D5" liegen
Und es sollte ein "schwarzer" Stein auf "E4" liegen


Szenario: Falsches Spielfeld anzeigen
Angenommen es existiert ein neues Einzelspielerspiel
Dann sollte ein "wei�er" Stein auf "D4" liegen
Und es sollte ein "wei�er" Stein auf "H5" liegen
Und es sollte ein "schwarzer" Stein auf "A5" liegen
Und es sollte ein "schwarzer" Stein auf "E4" liegen


Szenario: Spielbeginn (wei� am Zug)
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "schwarzen" Stein auf "E6" lege


Szenario: Stein richtig setzen 1
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "wei�en" Stein auf "F4" lege
Dann sollte ein "wei�er" Stein auf "F4" liegen
Und es sollte ein "wei�er" Stein auf "E4" liegen
Wenn ich einen "schwarzen" Stein auf "D3" lege
Dann sollte ein "schwarzer" Stein auf "D3" liegen
Und es sollte ein "schwarzer" Stein auf "D4" liegen

Szenario: Stein falsch setzen 1
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "wei�en" Stein auf "D3" lege
Dann sollte ich "Ungueltiger Zug" als Fehler bekommen


Szenario: Stein falsch setzen 2
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "wei�en" Stein auf "F4" lege
Dann sollte ein "wei�er" Stein auf "F4" liegen
Und es sollte ein "wei�er" Stein auf "E4" liegen
Wenn ich einen "schwarzen" Stein auf "E3" lege
Dann sollte ich "Ungueltiger Zug" als Fehler bekommen


Szenario: Stein falsch setzen 3
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "wei�en" Stein auf "D6" lege
Wenn ich einen "schwarzen" Stein auf "C4" lege
Wenn ich einen "wei�en" Stein auf "C3" lege
Wenn ich einen "schwarzen" Stein auf "C6" lege
Wenn ich einen "wei�en" Stein auf "C7" lege
Dann sollte ich "Ungueltiger Zug" als Fehler bekommen


Szenario: Auf ein belegtes fremdes Feld setzen
Angenommen es existiert ein neues Einzelspielerspiel
Wenn ich einen "wei�en" Stein auf "E4" lege
Und es sollte ein "schwarzer" Stein auf "E4" liegen 




