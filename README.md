# Лаборатори №10 — Өгөгдлийн урсгалын тест (Definition-Use Pairs)

**Төслийн хэл:** Java 17  
**Build tool:** Maven

Ашигласан Java хувилбарыг `java -version` командаар шалгасан.

```text
[heloise@arch lab08]$ java -version
openjdk version "17.0.21.0.3" 2026-10-24
OpenJDK Runtime Environment (build 17.0.21.0.3+1)
OpenJDK 64-Bit Server VM (build 17.0.21.0.3+1, mixed mode, sharing)
```

Ашигласан Maven болон Java орчны мэдээллийг `mvn -version` командаар шалгасан.

```text
[heloise@arch lab08]$ mvn -version
Apache Maven 3.10.0 (c43a36b8d67be7e0805a411bc0898af1a51f5472)
Maven home: /usr/share/java/maven
Java version: 17.0.21.0.3, vendor: Arch Linux, runtime: /usr/lib/jvm/java-17-openjdk
Default locale: en_US, platform encoding: UTF-8, time zone: Asia/Ulaanbaatar
OS name: "linux", version: "7.2.11-arch1-1", arch: "amd64", family: "unix"
```
## 1. Лабораторийн зорилго

Энэ лабораторийн ажлаар өгөгдсөн `DataFlowTargets` классын `gcd()` болон `bonus()` функцүүдэд өгөгдлийн урсгалын анализ хийж, хувьсагч бүрийн Definition (Def), Computational Use (C-use), Predicate Use (P-use), Kill болон Definition-Use (DU) хосуудыг тодорхойлно.

Мөн All-Defs болон All-DU-Pairs хамралтыг шалгах тестүүдийг боловсруулна.

## 2. Эх код

`src/main/java/mn/edu/must/sqat/DataFlowTargets.java`

**Анхааруулга:** Өгөгдсөн эх кодыг өөрчлөөгүй. DU анализад ашиглах мөрийн дугаарыг хэвээр хадгалсан.

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

`attendance == null || threshold < 0`

гэсэн логик OR ашигласан. Java хэлэнд эхний нөхцөл үнэн байвал хоёр дахь нөхцөлийг шалгахгүй. Иймээс `attendance == null` үед `threshold < 0` илэрхийлэл үнэлэгдэхгүй.

## 7. All-Defs тестүүд

Тестийн файл:

`src/test/java/mn/edu/must/sqat/BonusAllDefsTest.java`

| Тест | Оролт | Хүлээгдэж буй үр дүн | Зорилго |
|---|---|---:|---|
| B-AD1 | attendance = {1,1,0,1,1,1,1,1}, threshold = 7 | 8 | present, streak, best болон score-ийн эерэг definition-үүдийг хамруулах |
| B-AD2 | attendance = {}, threshold = 1 | 0 | present=0, best=0, score=0 definition-үүдийг хамруулах |

### 7.1 Туршилтын үр дүн

`mvn clean verify` командыг ажиллуулсан.

- Tests run: 4
- Failures: 0
- Errors: 0
- BUILD SUCCESS

JaCoCo All-Defs үр дүн:

| Үзүүлэлт | Covered | Missed | Нийт |
|---|---:|---:|---:|
| Instructions | 73 | 8 | 81 |
| Branches | 14 | 2 | 16 |
| Lines | 22 | 2 | 24 |
| Methods | 2 | 1 | 3 |

Branch coverage: 14 / 16 = 87.5%.

All-Defs болон Branch Coverage нь өөр өөр хамралтын шалгуур тул All-Defs тест амжилттай болсон ч бүх branch хамрагдах албагүй.

## 8. Үр дүнгийн файлууд

- `results/jacoco-alldefs.csv`
- `results/mvn-verify-alldefs.txt`

## 9. Дараагийн хийх ажлууд

- [ ] Bonus функцийн Control Flow Graph (`cfg-bonus.png`)
- [ ] All-DU-Pairs тестүүд
- [ ] Mutation testing (M1, M2, M3)
- [ ] Final JaCoCo coverage
- [ ] Final Maven test result
- [ ] Эцсийн Git commit болон push

## 10. Дүгнэлт

Энэ лабораторийн ажлаар `bonus()` функцийн хувьсагч бүрийн Definition болон Use цэгүүдийг тодорхойлж, def-clear замын дагуу DU хосуудыг гаргасан. Мөн All-Defs тестүүдийг ажиллуулж, Maven болон JaCoCo ашиглан тестийн үр дүн, хамралтыг шалгасан.

All-Defs тестүүд амжилттай ажилласан боловч Branch Coverage 87.5% байгаа тул үлдсэн branch-уудыг дараагийн тестүүдээр хамруулах шаардлагатай.
