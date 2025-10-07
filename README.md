# 🍕 Mario's Pizzabar – Order Management System

Dette projekt er udviklet som en del af Datamatikeruddannelsen (1. semester).  
Formålet er at designe og implementere et simpelt system til håndtering af pizzabestillinger, som kan køre på en standalone laptop uden netværksadgang.

---

## 🎯 Projektmål
- Gøre det nemt at oprette og håndtere ordrer.
- Give pizzabageren (Mario) et overblik over aktive ordrer sorteret efter afhentningstid.
- Mulighed for at afslutte ordrer og gemme dem i en fil, så der senere kan laves statistik over omsætning og mest populære pizzaer.

---

## ⚙️ Funktionalitet
- **Opret ordre** via konsol (kundeoplysninger, pizzaer, afhentningstid).
- **Validering af input** (ugyldig tid, tomme felter osv.).
- **Visning af aktive ordrer**, sorteret efter afhentningstid.
- **Afslut ordre**, fjern fra aktiv liste og gem i arkivfil (CSV).
- **Grundlag for statistik** (eks. omsætning, top 5 pizzaer).

---

## 🧩 Teknologi
- **Sprog:** Java (console application)
- **Paradigmer:** Objektorienteret programmering (indkapsling, arv, polymorfi)
- **Datahåndtering:** Collections (`ArrayList`), enums (pizza-menu), filhåndtering (IO)
- **Fejlhåndtering:** `try/catch` og inputvalidering
- **Versionsstyring:** Git + GitHub

---

## 👥 Team

- Zander  - Developer, Product Owner. 
- Oswald  - Developer, Scrum Master, Projektleder / mødekoordinator. 
- Marcus  - Developer, GitHub-ansvarlig, Test- og dokumentationsansvarlig. 
- Namirah - Developer, Kodeansvarlig. 

---
## 🚀 Kørsel
1. Klon repository:
   ```bash
   git clone https://github.com/Brammern/Marios-Pizzabar.git
