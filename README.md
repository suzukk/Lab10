# Лаборатори №10 — Өгөгдлийн урсгалын тест (Definition-Use Pairs)

**Төслийн хэл:** Java 17  
**Build tool:** Maven  
**Үйлдлийн систем:** Arch Linux

Ашигласан Java хувилбар:

```text
openjdk version "17.0.21.0.3" 2026-10-24
```

Ашигласан Maven хувилбар:

```text
Apache Maven 3.10.0
Java version: 17.0.21.0.3
OS: Arch Linux
```

## 1. Лабораторийн зорилго

Энэ лабораторийн ажлаар өгөгдсөн `DataFlowTargets` классын `gcd()` болон `bonus()` функцүүдэд өгөгдлийн урсгалын анализ хийж, хувьсагч бүрийн Definition (Def), Computational Use (C-use), Predicate Use (P-use), Kill болон Definition-Use (DU) хосуудыг тодорхойлсон.

Мөн дараах тестийн аргуудыг хэрэгжүүлсэн:

- All-Defs testing
- All-DU-Pairs testing
- Mutation testing
- JaCoCo coverage analysis

## 2. Эх код

`src/main/java/mn/edu/must/sqat/DataFlowTargets.java`

**Анхааруулга:** Өгөгдсөн эх кодын мөрийн дугаарыг DU анализад ашигласан. Mutation testing хийх үед түр өөрчлөлт оруулсан боловч туршилт бүрийн дараа эх кодыг сэргээсэн.

## 2.1 Bonus функцийн Control Flow Graph

Bonus функцийн Control Flow Graph-ийг Graphviz ашиглан үүсгэсэн.

- Эх файл: `results/cfg-bonus.dot`
- Зураг: `results/cfg-bonus.png`

![Bonus Control Flow Graph](results/cfg-bonus.png)

## 3. B2 — Definition / Use анализ

### 3.1 Нэр томьёо

- **Def:** Хувьсагчид шинэ утга оноох.
- **C-use:** Тооцоолол болон илэрхийлэлд хувьсагч ашиглах.
- **P-use:** Нөхцөл шалгахад хувьсагч ашиглах.
- **Kill:** Өмнөх definition-ийг шинэ definition-ээр хүчингүй болгох.
- **DU pair:** Нэг definition-ээс тухайн хувьсагчийн use хүртэл өөр definition-ээр таслагдаагүй зам.

### 3.2 `bonus()` функцийн Def/Use жагсаалт

| Мөр | Код / үйлдэл | Definition | Use |
|---:|---|---|---|
| 31 | `bonus(int[] attendance, int threshold)` | attendance, threshold | — |
| 32 | `attendance == null || threshold < 0` | — | attendance, threshold (P-use) |
| 35 | `int present = 0` | present | — |
| 36 | `int streak = 0` | streak | — |
| 37 | `int best = 0` | best | — |
| 38 | `for (int a : attendance)` | a | attendance (C-use) |
| 39 | `if (a > 0)` | — | a (P-use) |
| 40 | `present = present + 1` | present | present, a (C-use) |
| 41 | `streak = streak + 1` | streak | streak (C-use) |
| 42 | `if (streak > best)` | — | streak, best (P-use) |
| 43 | `best = streak` | best | streak (C-use) |
| 46 | `streak = 0` | streak | — |
| 49 | `int score = 0` | score | — |
| 50 | `if (present >= threshold)` | — | present, threshold (P-use) |
| 51 | `score = 5` | score | — |
| 53 | `if (best >= 5)` | — | best (P-use) |
| 54 | `score = score + 3` | score | score (C-use) |
| 56 | `return score` | — | score (C-use) |

### 3.3 Хувьсагч тус бүрийн Def/Use

| Хувьсагч | Definition мөрүүд | Use мөрүүд |
|---|---|---|
| attendance | 31 | 32 (P), 38 (C) |
| threshold | 31 | 32 (P), 50 (P) |
| a | 38 | 39 (P), 40 (C) |
| present | 35, 40 | 40 (C), 50 (P) |
| streak | 36, 41, 46 | 41 (C), 42 (P), 43 (C) |
| best | 37, 43 | 42 (P), 53 (P) |
| score | 49, 51, 54 | 54 (C), 56 (C) |

## 4. B3 — Definition-Use (DU) хосууд

DU хос нь тухайн definition-ээс use хүртэлх замд ижил хувьсагчийн өөр definition ороогүй байх шаардлагатай.

### 4.1 attendance

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 31 | 32 | P-use | 31 → 32 |
| 31 | 38 | C-use | 31 → 38 |

### 4.2 threshold

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 31 | 32 | P-use | 31 → 32 |
| 31 | 50 | P-use | 31 → 50 |

### 4.3 a

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 38 | 39 | P-use | 38 → 39 |
| 38 | 40 | C-use | 38 → 40 |

### 4.4 present

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 35 | 40 | C-use | 35 → 40 |
| 35 | 50 | P-use | 35 → 50 |
| 40 | 40 | C-use | 40 → 40 |
| 40 | 50 | P-use | 40 → 50 |

### 4.5 streak

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 36 | 41 | C-use | 36 → 41 |
| 41 | 41 | C-use | 41 → 41 |
| 41 | 42 | P-use | 41 → 42 |
| 41 | 43 | C-use | 41 → 43 |
| 46 | 41 | C-use | 46 → 41 |

### 4.6 best

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 37 | 42 | P-use | 37 → 42 |
| 37 | 53 | P-use | 37 → 53 |
| 43 | 42 | P-use | 43 → 42 |
| 43 | 53 | P-use | 43 → 53 |

### 4.7 score

| Def | Use | Төрөл | Зам |
|---:|---:|---|---|
| 49 | 54 | C-use | 49 → 54 |
| 49 | 56 | C-use | 49 → 56 |
| 51 | 54 | C-use | 51 → 54 |
| 51 | 56 | C-use | 51 → 56 |
| 54 | 56 | C-use | 54 → 56 |

## 5. Kill анализ

| Мөр | Definition | Kill хийх үйлдэл |
|---:|---|---|
| 40 | `present = present + 1` | Өмнөх present definition-ийг шинэчилнэ |
| 41 | `streak = streak + 1` | Өмнөх streak definition-ийг шинэчилнэ |
| 43 | `best = streak` | Өмнөх best definition-ийг дарна |
| 46 | `streak = 0` | Өмнөх streak definition-ийг дарна |
| 51 | `score = 5` | Өмнөх score definition-ийг дарна |
| 54 | `score = score + 3` | Өмнөх score definition-ийг ашиглаад шинэчилнэ |

### 5.1 Def-clear замын жишээ

- `streak` 36 → 41: эхний ирц эерэг үед эхний утгыг ашиглана.
- `streak` 41 → 41: дараалсан эерэг ирцийн үед өмнөх утгыг дараагийн тооцоололд ашиглана.
- `streak` 46 → 41: тасалсны дараа дахин ирсэн үед тэг утгыг ашиглана.
- `score` 49 → 56: хоёр нөхцөл хоёулаа худал үед эхний тэг утга return хүртэл хадгалагдана.

### 5.2 DU хос болохгүй жишээнүүд

| Хослол | Шалтгаан |
|---|---|
| streak 36 → 42 | 41-р мөрөнд streak дахин тодорхойлогдоно |
| streak 36 → 43 | 41-р мөрөнд streak дахин тодорхойлогдоно |
| best 37 → 53 (43-р мөрөөр дамжсан) | 43-р мөр best-ийн шинэ definition үүсгэнэ |
| score 49 → 56 (51 эсвэл 54-р мөрөөр дамжсан) | Score дахин тодорхойлогдсон байна |

## 6. Short-circuit нөхцөлийн тайлбар

32-р мөрөнд:

```java
attendance == null || threshold < 0
```

гэсэн логик OR ашигласан. Java хэлэнд эхний нөхцөл үнэн байвал хоёр дахь нөхцөлийг шалгахгүй. Иймээс `attendance == null` үед `threshold < 0` илэрхийлэл үнэлэгдэхгүй.

## 7. All-Defs тестүүд

Тестийн файл:

`src/test/java/mn/edu/must/sqat/BonusAllDefsTest.java`

| Тест | Оролт | Хүлээгдэж буй үр дүн | Зорилго |
|---|---|---:|---|
| B-AD1 | attendance = `{1,1,0,1,1,1,1,1}`, threshold = 7 | 8 | present, streak, best болон score-ийн эерэг definition-үүдийг хамруулах |
| B-AD2 | attendance = `{}`, threshold = 1 | 0 | present=0, best=0, score=0 definition-үүдийг хамруулах |

### 7.1 All-Defs туршилтын үр дүн

`mvn clean verify` командаар All-Defs тестүүдийг шалгасан.

| Үзүүлэлт | Covered | Missed | Нийт |
|---|---:|---:|---:|
| Instructions | 73 | 8 | 81 |
| Branches | 14 | 2 | 16 |
| Lines | 22 | 2 | 24 |
| Methods | 2 | 1 | 3 |

Branch coverage: 14 / 16 = 87.5%.

All-Defs болон Branch Coverage нь өөр өөр хамралтын шалгуур тул All-Defs тест амжилттай болсон ч бүх branch хамрагдах албагүй.

## 8. All-DU-Pairs тестүүд

Тестийн файл:

`src/test/java/mn/edu/must/sqat/BonusAllDUPairsTest.java`

All-DU-Pairs тестүүдээр хувьсагчдын definition-ээс use хүртэлх замуудыг шалгасан.

| Тест | Шалгах нөхцөл | Хүлээгдэж буй үр дүн |
|---|---|---:|
| DU1 | Null attendance | IllegalArgumentException |
| DU2 | Negative threshold | IllegalArgumentException |
| DU3 | Empty attendance, threshold 0 | 5 |
| DU4 | Empty attendance, threshold 1 | 0 |
| DU5 | One positive attendance | 5 |
| DU6 | Streak reset and shorter new streak | 0 |
| DU7 | Five scattered positive days | 5 |
| DU8 | Five consecutive days below threshold | 3 |

### 8.1 All-DU-Pairs туршилтын үр дүн

| Үзүүлэлт | Covered | Missed | Нийт |
|---|---:|---:|---:|
| Instructions | 78 | 3 | 81 |
| Branches | 16 | 0 | 16 |
| Lines | 23 | 1 | 24 |
| Methods | 2 | 1 | 3 |

- Branch coverage: 16 / 16 = 100%.
- Line coverage: 23 / 24 = 95.8%.
- Instruction coverage: 78 / 81 = 96.3%.

JaCoCo-ийн Branch Coverage 100% болсон нь бүх DU pair хамрагдсан гэсэн шууд баталгаа биш. Эдгээр нь тусдаа хэмжүүрүүд юм.

## 9. Mutation Testing

Mutation testing нь програмын кодод зориудаар жижиг өөрчлөлт оруулж, тестүүд тухайн алдааг илрүүлж чадаж байгаа эсэхийг шалгах арга юм.

Энэ лабораторид гурван mutation туршилт хийсэн.

### 9.1 M1 — Missing Update

**Өөрчлөлт:** `streak = streak + 1;` мөрийг устгасан.

Үр дүнд нь дараалсан ирцийн тоо нэмэгдэхээ больсон.

| Унасан тест | Expected | Actual |
|---|---:|---:|
| `BonusAllDUPairsTest.fiveConsecutiveBelowThreshold` | 3 | 0 |
| `BonusAllDefsTest.bonusAllDefsCoversEveryDefinition` | 8 | 5 |

**Үр дүн:** Mutation тестүүдээр илэрсэн.

### 9.2 M2 — Wrong Variable

**Өөрчлөлт:** `best = streak;` мөрийг `best = present;` болгож сольсон.

Эхний 12 тест mutation-ийг илрүүлээгүй тул тусгай тест нэмсэн.

Тестийн файл:

`src/test/java/mn/edu/must/sqat/BonusM2MutationTest.java`

Тестийн оролт:

```java
int[] attendance = {1, 1, 1, 0, 1, 1, 1, 1};
```

`threshold = 7` үед нийт ирц 7 боловч хамгийн урт дараалсан ирц 4 байна.

| Үзүүлэлт | Утга |
|---|---:|
| Expected | 5 |
| Actual | 8 |
| Унасан тест | `BonusM2MutationTest.totalAttendanceMustNotReplaceBestStreak` |

**Үр дүн:** Нэмэлт тестээр mutation илэрсэн.

### 9.3 M3 — Dead Definition

**Өөрчлөлт:** Давталтын дараа дараах ашиглагдахгүй хувьсагчийг нэмсэн.

```java
int total = present + best;
```

`total` хувьсагчид утга оноосон боловч програмын цаашдын ажиллагаанд ашиглаагүй.

| Үзүүлэлт | Үр дүн |
|---|---|
| Tests run | 13 |
| Failures | 0 |
| Errors | 0 |
| Build | BUILD SUCCESS |

**Дүгнэлт:** Одоогийн тестүүд ашиглагдахгүй definition-ийг илрүүлээгүй. Энэ mutation нь тестээр илрэхгүй үлдсэн.

### 9.4 Mutation Testing-ийн нэгтгэл

| Mutation | Өөрчлөлт | Үр дүн |
|---|---|---|
| M1 | streak update мөрийг устгасан | 2 тестээр илэрсэн |
| M2 | best = streak → best = present | Нэмэлт тестээр илэрсэн |
| M3 | Ашиглагдахгүй total хувьсагч нэмсэн | Тестүүд илрүүлээгүй |

Mutation туршилт бүрийн дараа `DataFlowTargets.java` эх кодыг `git restore` командаар сэргээсэн.

## 10. Эцсийн туршилтын үр дүн

Эцсийн шалгалтыг дараах командаар ажиллуулсан:

```bash
mvn clean verify
```

Үр дүн:

```text
Tests run: 13
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

Эцсийн шалгалтаар All-Defs, All-DU-Pairs, GCD болон M2 mutation-ийг илрүүлэх нэмэлт тестүүд бүгд амжилттай ажилласан.

## 11. Үр дүнгийн файлууд

| Файл | Тайлбар |
|---|---|
| `results/cfg-bonus.dot` | Control Flow Graph эх файл |
| `results/cfg-bonus.png` | Control Flow Graph зураг |
| `results/jacoco-alldefs.csv` | All-Defs coverage |
| `results/mvn-verify-alldefs.txt` | All-Defs Maven үр дүн |
| `results/jacoco-alldupairs.csv` | All-DU-Pairs coverage |
| `results/mvn-verify-alldupairs.txt` | All-DU-Pairs Maven үр дүн |
| `results/mvn-test-M1.txt` | M1 mutation үр дүн |
| `results/mvn-test-M2.txt` | M2 эхний туршилтын үр дүн |
| `results/mvn-test-M2-kill.txt` | M2 нэмэлт тестээр илэрсэн үр дүн |
| `results/mvn-test-M3.txt` | M3 mutation үр дүн |
| `results/mvn-verify-final.txt` | Эцсийн Maven шалгалт |
| `results/jacoco-final.csv` | Эцсийн JaCoCo coverage |

## 12. Дүгнэлт

Энэ лабораторийн ажлаар `gcd()` болон `bonus()` функцүүдийн өгөгдлийн урсгалын анализ хийж, хувьсагчдын Definition, Use, Kill болон DU хосуудыг тодорхойлсон.

All-Defs болон All-DU-Pairs тестүүдийг боловсруулж, JaCoCo ашиглан кодын хамралтыг шалгасан. Мөн Mutation Testing ашиглан тестийн чанарыг шалгахад M1 болон M2 алдааг илрүүлж чадсан бол M3 dead definition тестээр илрээгүй.

Эцсийн `mvn clean verify` шалгалтаар 13 тест бүгд амжилттай ажиллаж, `BUILD SUCCESS` үр дүн гарсан.
