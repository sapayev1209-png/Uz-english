package com.example.data

import com.example.model.GrammarExample
import com.example.model.GrammarLesson
import com.example.model.GrammarMistake
import com.example.model.ListeningExercise
import com.example.model.QuestionType
import com.example.model.QuizQuestion
import com.example.model.ReadingPassage
import com.example.model.SpeakingScenario
import com.example.model.WordMeaning
import com.example.model.WritingPrompt

object GrammarAndSkillsCatalog {

    val placementQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "pl_1",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "Hello! My name ___ Aziz and I ___ from Uzbekistan.",
            promptUz = "Bo'sh joylarga mos 'to be' fe'l shakllarini tanlang:",
            options = listOf("is / am", "am / is", "are / am", "is / are"),
            correctAnswer = "is / am",
            explanationUz = "'My name' (3-shaxs birlik = it) bilan 'is', 'I' olmoshi bilan esa 'am' ishlatiladi.",
            errorCategory = "Verb to be",
            cefrLevel = "A1"
        ),
        QuizQuestion(
            id = "pl_2",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "She ___ to work by metro every morning.",
            promptUz = "Har kuni takrorlanadigan harakat (Present Simple) uchun to'g'ri shaklni toping:",
            options = listOf("go", "goes", "going", "is go"),
            correctAnswer = "goes",
            explanationUz = "Present Simple zamonida 'He / She / It' olmoshlaridan keyin fe'lga '-s/-es' qo'shiladi: 'goes'.",
            errorCategory = "Present Simple",
            cefrLevel = "A1"
        ),
        QuizQuestion(
            id = "pl_3",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "Look! It ___ outside right now.",
            promptUz = "Ayni paytda sodir bo'layotgan ish-harakat (Present Continuous):",
            options = listOf("rains", "is raining", "rained", "rain"),
            correctAnswer = "is raining",
            explanationUz = "'Look!' va 'right now' ayni damda davom etayotgan harakatni bildiradi: am/is/are + V-ing ('is raining').",
            errorCategory = "Present Continuous",
            cefrLevel = "A1"
        ),
        QuizQuestion(
            id = "pl_4",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "We ___ Samarkand last summer and saw Registan Square.",
            promptUz = "O'tgan zamonda (last summer) tugallangan harakatni tanlang:",
            options = listOf("visit", "visited", "have visited", "visiting"),
            correctAnswer = "visited",
            explanationUz = "'Last summer' aniq o'tgan vaqtni bildirgani uchun Past Simple ('visited') ishlatiladi.",
            errorCategory = "Past Simple",
            cefrLevel = "A2"
        ),
        QuizQuestion(
            id = "pl_5",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "Tashkent is ___ than Bukhara, but both cities are very historic.",
            promptUz = "Sifatlarni qiyoslash darajasi (Comparative):",
            options = listOf("big", "more big", "bigger", "the biggest"),
            correctAnswer = "bigger",
            explanationUz = "'than' so'zi bilan qiyosiy daraja ishlatiladi. Qisqa sifat 'big' -> 'bigger' bo'ladi.",
            errorCategory = "Comparatives",
            cefrLevel = "A2"
        ),
        QuizQuestion(
            id = "pl_6",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "I ___ this English book three times already.",
            promptUz = "Natijasi hozir bilan bog'liq harakat (Present Perfect):",
            options = listOf("read", "have read", "am reading", "was reading"),
            correctAnswer = "have read",
            explanationUz = "'Already' va hozirgacha necha marta bajarilganini aytishda Present Perfect ('have + V3') ishlatiladi.",
            errorCategory = "Present Perfect",
            cefrLevel = "B1"
        ),
        QuizQuestion(
            id = "pl_7",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "You ___ bring an umbrella; the sky is completely clear today.",
            promptUz = "Zarurat yo'qligini bildiruvchi modal fe'lni tanlang:",
            options = listOf("mustn't", "don't have to", "can't", "should"),
            correctAnswer = "don't have to",
            explanationUz = "'Don't have to' — 'shart emas / zarurati yo'q' degan ma'noni beradi ('mustn't' esa qat'iy taqiq).",
            errorCategory = "Modal Verbs",
            cefrLevel = "B1"
        ),
        QuizQuestion(
            id = "pl_8",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "If I ___ more free time now, I would learn a third language.",
            promptUz = "Hozirgi zamondagi noreal/xayoliy shart gap (Second Conditional):",
            options = listOf("have", "had", "will have", "would have"),
            correctAnswer = "had",
            explanationUz = "Second Conditional formulasida 'If + Past Simple (had), would + V1' ishlatiladi.",
            errorCategory = "Conditionals",
            cefrLevel = "B2"
        ),
        QuizQuestion(
            id = "pl_9",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "The new metro station ___ next month by the engineering team.",
            promptUz = "Majhul nisbat (Passive Voice) kelasi zamonda:",
            options = listOf("will open", "will be opened", "is opening", "has opened"),
            correctAnswer = "will be opened",
            explanationUz = "Metro bekati o'zi ochilmaydi, balki muhandislar tomonidan ochiladi: 'will be + V3 (opened)'.",
            errorCategory = "Passive Voice",
            cefrLevel = "B2"
        ),
        QuizQuestion(
            id = "pl_10",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "She asked me where I ___ the previous day.",
            promptUz = "O'zlashtirma gapda (Reported Speech) zamon moslashuvi:",
            options = listOf("have been", "was", "had been", "am"),
            correctAnswer = "had been",
            explanationUz = "Reported Speech'da 'the previous day' (yesterday) bilan Past Simple -> Past Perfect ('had been') ga o'zgaradi.",
            errorCategory = "Reported Speech",
            cefrLevel = "C1"
        ),
        QuizQuestion(
            id = "pl_11",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "Had we booked the tickets earlier, we ___ front-row seats.",
            promptUz = "O'tgan zamondagi inversiyali shart gap (Third Conditional Inversion):",
            options = listOf("would get", "would have got", "will get", "got"),
            correctAnswer = "would have got",
            explanationUz = "'Had we booked...' = 'If we had booked...' (3rd Conditional), bosh gapda 'would have + V3' keladi.",
            errorCategory = "Conditionals",
            cefrLevel = "C1"
        ),
        QuizQuestion(
            id = "pl_12",
            lessonId = "placement_test",
            type = QuestionType.MULTIPLE_CHOICE,
            promptEn = "Not only ___ fluent English, but she also translates classical poetry.",
            promptUz = "C2 darajadagi inkor ravishli inversiya (Negative Inversion):",
            options = listOf("she speaks", "does she speak", "speaks she", "she does speak"),
            correctAnswer = "does she speak",
            explanationUz = "Gap 'Not only' bilan boshlanganda so'roq tartibidagi inversiya ('does she speak') qo'llanadi.",
            errorCategory = "Advanced Inversion",
            cefrLevel = "C2"
        )
    )

    fun estimateCefrFromScore(percentage: Int): String = when {
        percentage < 25 -> "A1"
        percentage < 45 -> "A2"
        percentage < 65 -> "B1"
        percentage < 80 -> "B2"
        percentage < 92 -> "C1"
        else -> "C2"
    }

    val grammarLessons: List<GrammarLesson> = listOf(
        // 1. Verb to be (A1)
        GrammarLesson(
            id = "gram_a1_tobe",
            cefrLevel = "A1",
            titleEn = "Verb 'to be' (am / is / are)",
            titleUz = "'To be' fe'li — bo'lmoq (-man, -san, -dir)",
            explanationUz = "O'zbek tilida 'Men talabaman' desak, kesim oxiridagi '-man' qo'shimchasi orqali ifodalanadi. Ingliz tilida esa ot yoki sifatdan oldin albatta 'to be' (am / is / are) bog'lama fe'li qo'yilishi shart!",
            formula = "Subject + am / is / are + Noun / Adjective / Place",
            affirmativeForm = "I am | He/She/It is | We/You/They are",
            negativeForm = "I am not | He/She/It is not (isn't) | We/You/They are not (aren't)",
            questionForm = "Am I ...? | Is he/she/it ...? | Are we/you/they ...?",
            examples = listOf(
                GrammarExample("I am a student from Tashkent.", "Men toshkentlik talabaman."),
                GrammarExample("She is a doctor.", "U — shifokor."),
                GrammarExample("They are very happy today.", "Ular bugun juda xursand.")
            ),
            commonMistakes = listOf(
                GrammarMistake("I student.", "I am a student.", "Ingliz tilida ega va ot-kesim orasida 'am/is/are' tushib qolmasligi kerak."),
                GrammarMistake("He are my friend.", "He is my friend.", "'He' (3-shaxs birlik) bilan doim 'is' ishlatiladi.")
            ),
            guidedPracticeHintUz = "Eslab qoling: I -> am, He/She/It -> is, We/You/They -> are.",
            questions = listOf(
                QuizQuestion(
                    id = "g1_q1",
                    lessonId = "gram_a1_tobe",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "My brother ___ an engineer.",
                    promptUz = "'My brother' (He) uchun mos shaklni tanlang:",
                    options = listOf("am", "is", "are", "be"),
                    correctAnswer = "is",
                    explanationUz = "'My brother' = 'He' (3-shaxs birlik), shuning uchun 'is' qo'yiladi.",
                    errorCategory = "Verb to be",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g1_q2",
                    lessonId = "gram_a1_tobe",
                    type = QuestionType.TRUE_FALSE,
                    promptEn = "Is this sentence correct? 'We is ready for the lesson.'",
                    promptUz = "Ushbu gap grammatik jihatdan to'g'rimi?",
                    options = listOf("True", "False"),
                    correctAnswer = "False",
                    explanationUz = "Noto'g'ri! 'We' ko'plikda bo'lgani uchun 'We are ready' bo'lishi kerak.",
                    errorCategory = "Verb to be",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g1_q3",
                    lessonId = "gram_a1_tobe",
                    type = QuestionType.SENTENCE_ORDER,
                    promptEn = "you / from / Are / Samarkand?",
                    promptUz = "So'zlarni to'g'ri tartibda joylashtirib so'roq gap tuzing:",
                    options = listOf("Are you from Samarkand?", "You are from Samarkand?", "From Samarkand are you?", "Are from you Samarkand?"),
                    correctAnswer = "Are you from Samarkand?",
                    explanationUz = "So'roq gapda 'Are' egadan ('you') oldinga chiqadi: 'Are you from Samarkand?'",
                    errorCategory = "Verb to be",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g1_q4",
                    lessonId = "gram_a1_tobe",
                    type = QuestionType.FILL_BLANK,
                    promptEn = "I ___ very glad to learn English.",
                    promptUz = "'I' olmoshiga mos 'to be' shaklini yozing:",
                    options = listOf("am", "is", "are"),
                    correctAnswer = "am",
                    explanationUz = "'I' olmoshi bilan har doim 'am' ishlatiladi.",
                    errorCategory = "Verb to be",
                    cefrLevel = "A1"
                )
            )
        ),

        // 2. Present Simple (A1)
        GrammarLesson(
            id = "gram_a1_present_simple",
            cefrLevel = "A1",
            titleEn = "Present Simple Tense",
            titleUz = "Hozirgi oddiy zamon (Doimiy harakatlar)",
            explanationUz = "Present Simple har kuni takrorlanadigan odatlar, jadval asosidagi ishlar va umumiy haqiqatlarni ifodalash uchun ishlatiladi. Muhim qoida: He, She, It bilan fe'l oxiriga '-s' yoki '-es' qo'shiladi!",
            formula = "I/We/You/They + V1 | He/She/It + V1(-s/-es)",
            affirmativeForm = "I work / He works",
            negativeForm = "I do not (don't) work / He does not (doesn't) work",
            questionForm = "Do you work? / Does he work?",
            examples = listOf(
                GrammarExample("I drink green tea every morning.", "Men har ertalab ko'k choy ichaman."),
                GrammarExample("She speaks English very well.", "U ingliz tilida juda yaxshi gapiradi."),
                GrammarExample("Does he live in Bukhara?", "U Buxoroda yashaydimi?")
            ),
            commonMistakes = listOf(
                GrammarMistake("She work in a bank.", "She works in a bank.", "Darak gapda He/She/It dan keyin fe'lga '-s' qo'shilishi shart."),
                GrammarMistake("Does she works here?", "Does she work here?", "Savol va inkor gapda 'does / doesn't' kelganda asosiy fe'ldagi '-s' tushib qoladi!")
            ),
            guidedPracticeHintUz = "Savol yoki inkorda 'does/doesn't' qatnashsa, asosiy fe'lni oddiy (V1) shaklda qoldiring.",
            questions = listOf(
                QuizQuestion(
                    id = "g2_q1",
                    lessonId = "gram_a1_present_simple",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Dilshod ___ English every evening.",
                    promptUz = "To'g'ri fe'l shaklini tanlang:",
                    options = listOf("study", "studies", "studying", "is study"),
                    correctAnswer = "studies",
                    explanationUz = "Dilshod (He) 3-shaxs birlik. Undosh + 'y' bilan tugagan fe'lda 'y' -> 'ies' ga aylanadi: 'studies'.",
                    errorCategory = "Present Simple",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g2_q2",
                    lessonId = "gram_a1_present_simple",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "She doesn't ___ coffee at night.",
                    promptUz = "'doesn't' dan keyin fe'l qanday shaklda keladi?",
                    options = listOf("drinks", "drink", "drinking", "drank"),
                    correctAnswer = "drink",
                    explanationUz = "'Doesn't' o'ziga '-es' ni olgani uchun asosiy fe'l oddiy 'drink' shaklida qoladi.",
                    errorCategory = "Present Simple",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g2_q3",
                    lessonId = "gram_a1_present_simple",
                    type = QuestionType.FILL_BLANK,
                    promptEn = "___ they live near the metro station? (Do / Does)",
                    promptUz = "'they' uchun mos yordamchi fe'lni tanlang:",
                    options = listOf("Do", "Does", "Are"),
                    correctAnswer = "Do",
                    explanationUz = "'They' olmoshi bilan Present Simple so'roq gapida 'Do' ishlatiladi.",
                    errorCategory = "Present Simple",
                    cefrLevel = "A1"
                )
            )
        ),

        // 3. Present Continuous (A1)
        GrammarLesson(
            id = "gram_a1_present_continuous",
            cefrLevel = "A1",
            titleEn = "Present Continuous Tense",
            titleUz = "Hozirgi davomli zamon (-yapman, -yapti)",
            explanationUz = "Nutq aytilayotgan ayni daqiqada (now, right now, at the moment) davom etayotgan harakatlarni ifodalaydi. O'zbek tilidagi '-yapman / -yapti' qo'shimchasiga to'g'ri keladi.",
            formula = "Subject + am / is / are + Verb-ing",
            affirmativeForm = "I am reading | He is reading | We are reading",
            negativeForm = "I am not reading | He isn't reading | We aren't reading",
            questionForm = "Am I reading? | Is he reading? | Are you reading?",
            examples = listOf(
                GrammarExample("I am learning English right now.", "Men hozir ingliz tilini o'rganyapman."),
                GrammarExample("Listen! The teacher is speaking.", "Tinglang! O'qituvchi gapiryapti.")
            ),
            commonMistakes = listOf(
                GrammarMistake("I reading a book now.", "I am reading a book now.", "V-ing oldidan albatta 'am / is / are' yordamchi fe'li bo'lishi shart.")
            ),
            guidedPracticeHintUz = "Kalit so'zlar: now, right now, at the moment, Look!, Listen!",
            questions = listOf(
                QuizQuestion(
                    id = "g3_q1",
                    lessonId = "gram_a1_present_continuous",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Be quiet, please! The baby ___.",
                    promptUz = "Ayni damda davom etayotgan harakatni tanlang:",
                    options = listOf("sleeps", "is sleeping", "sleeping", "slept"),
                    correctAnswer = "is sleeping",
                    explanationUz = "Ayni vaqtda chaqaloq uxlayotgani uchun 'is sleeping' ishlatiladi.",
                    errorCategory = "Present Continuous",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g3_q2",
                    lessonId = "gram_a1_present_continuous",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "What ___ you doing at the moment?",
                    promptUz = "'you' uchun to'g'ri yordamchi fe'lni tanlang:",
                    options = listOf("do", "is", "are", "am"),
                    correctAnswer = "are",
                    explanationUz = "'You' bilan Present Continuous zamonida 'are' ishlatiladi.",
                    errorCategory = "Present Continuous",
                    cefrLevel = "A1"
                )
            )
        ),

        // 4. Articles & Pronouns (A1)
        GrammarLesson(
            id = "gram_a1_articles_pronouns",
            cefrLevel = "A1",
            titleEn = "Articles (a / an / the) & Pronouns",
            titleUz = "Artikllar (a / an / the) va Olmoshlar",
            explanationUz = "O'zbek tilida artikl tushunchasi yo'q, ammo ingliz tilida sanaladigan birlikdagi otlar oldidan noaniq ('a' — undosh tovush oldidan, 'an' — unli tovush oldidan) yoki aniq ('the' — tinglovchiga ma'lum narsa oldidan) artikl qo'yiladi.",
            formula = "a + consonant sound | an + vowel sound (a,e,i,o,u) | the + specific noun",
            affirmativeForm = "a book, an apple, an hour | the sun, the book on the table",
            negativeForm = "Plural/Uncountable general nouns take no article: Books are useful.",
            questionForm = "Can you pass me the salt? / Do you have a pen?",
            examples = listOf(
                GrammarExample("I bought a notebook and an umbrella.", "Men bitta daftar va bitta soyabon sotib oldim."),
                GrammarExample("The moon is bright tonight.", "Bugun kechasi oy yorug' (dunyoda yagona narsa — 'the').")
            ),
            commonMistakes = listOf(
                GrammarMistake("She is a honest person.", "She is an honest person.", "'Honest' so'zida 'h' o'qilmaydi va unli [ɒ] tovushi bilan boshlanadi, shuning uchun 'an' qo'yiladi.")
            ),
            guidedPracticeHintUz = "Harfga emas, so'zning birinchi TOVUSHIGA qarang: a university [ju:], an hour [aʊ].",
            questions = listOf(
                QuizQuestion(
                    id = "g4_q1",
                    lessonId = "gram_a1_articles_pronouns",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "It takes ___ hour to get there.",
                    promptUz = "'hour' so'zi oldidan qaysi artikl qo'yiladi?",
                    options = listOf("a", "an", "the", "no article"),
                    correctAnswer = "an",
                    explanationUz = "'Hour' so'zida 'h' talaffuz qilinmaydi va unli tovush bilan boshlangani uchun 'an hour' bo'ladi.",
                    errorCategory = "Articles",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "g4_q2",
                    lessonId = "gram_a1_articles_pronouns",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "This is my friend. ___ name is Kamola.",
                    promptUz = "Ayol kishiga nisbatan 'uning' egalik olmoshini tanlang:",
                    options = listOf("She", "Her", "His", "Hers"),
                    correctAnswer = "Her",
                    explanationUz = "Otdan ('name') oldin egalik olmoshi 'Her' (qiz bolaga nisbatan 'uning') ishlatiladi.",
                    errorCategory = "Pronouns",
                    cefrLevel = "A1"
                )
            )
        ),

        // 5. Past Simple (A2)
        GrammarLesson(
            id = "gram_a2_past_simple",
            cefrLevel = "A2",
            titleEn = "Past Simple Tense",
            titleUz = "O'tgan oddiy zamon (-di)",
            explanationUz = "O'tmishda aniq bir vaqtda (yesterday, last week, in 2022, two days ago) boshlanib, o'sha vaqtda tugagan ish-harakatlarni ifodalaydi. To'g'ri fe'llarga '-ed' qo'shiladi, noto'g'ri fe'llarning 2-shakli (V2) ishlatiladi.",
            formula = "Subject + V2 / V-ed | Did + Subject + V1?",
            affirmativeForm = "I worked / We went to Khiva.",
            negativeForm = "I did not (didn't) work / We didn't go.",
            questionForm = "Did you work? / Where did you go?",
            examples = listOf(
                GrammarExample("We visited Registan Square yesterday.", "Biz kecha Registon maydoniga bordik."),
                GrammarExample("I didn't see him at the meeting.", "Men uni yig'ilishda ko'rmadim.")
            ),
            commonMistakes = listOf(
                GrammarMistake("I didn't went there.", "I didn't go there.", "'Didn't' va 'Did' qatnashgan gapda fe'l o'zining 1-shakliga (V1) qaytadi!")
            ),
            guidedPracticeHintUz = "Darak gapdagina V2/V-ed ishlatiladi; savol va inkorda 'did/didn't + V1' bo'ladi.",
            questions = listOf(
                QuizQuestion(
                    id = "g5_q1",
                    lessonId = "gram_a2_past_simple",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Where did you ___ your phone yesterday?",
                    promptUz = "'did' ishtirok etgan so'roq gapda fe'lning to'g'ri shaklini tanlang:",
                    options = listOf("found", "find", "finding", "finds"),
                    correctAnswer = "find",
                    explanationUz = "Savolda 'did' yordamchi fe'li bo'lgani uchun asosiy fe'l V1 ('find') shaklida keladi.",
                    errorCategory = "Past Simple",
                    cefrLevel = "A2"
                ),
                QuizQuestion(
                    id = "g5_q2",
                    lessonId = "gram_a2_past_simple",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "She ___ a delicious cake for her mother's birthday last Sunday.",
                    promptUz = "'make' noto'g'ri fe'lining o'tgan zamon shaklini toping:",
                    options = listOf("maked", "made", "makes", "making"),
                    correctAnswer = "made",
                    explanationUz = "'Make' noto'g'ri fe'l bo'lib, uning Past Simple (V2) shakli — 'made'.",
                    errorCategory = "Past Simple",
                    cefrLevel = "A2"
                )
            )
        ),

        // 6. Past Continuous (A2)
        GrammarLesson(
            id = "gram_a2_past_continuous",
            cefrLevel = "A2",
            titleEn = "Past Continuous Tense",
            titleUz = "O'tgan davomli zamon (-ayotgan edi)",
            explanationUz = "O'tmishdagi ma'lum bir vaqtda yoki boshqa bir qisqa harakat sodir bo'lganda davom etayotgan jarayonni bildiradi ('men kitob o'qiyotgan edim').",
            formula = "Subject + was / were + Verb-ing",
            affirmativeForm = "I/He/She/It was studying | We/You/They were studying",
            negativeForm = "wasn't / weren't + Verb-ing",
            questionForm = "Was he studying? / Were you sleeping when I called?",
            examples = listOf(
                GrammarExample("I was reading a book when you called me.", "Sen qo'ng'iroq qilganingda men kitob o'qiyotgan edim."),
                GrammarExample("At 8 PM yesterday, we were having dinner.", "Kecha soat 20:00 da biz kechki ovqat yeyayotgan edik.")
            ),
            commonMistakes = listOf(
                GrammarMistake("We was watching TV.", "We were watching TV.", "'We / You / They' bilan doimo 'were' ishlatiladi.")
            ),
            guidedPracticeHintUz = "Uzun fon harakati — Past Continuous (was/were + V-ing), uni bo'lgan qisqa harakat — Past Simple (V2).",
            questions = listOf(
                QuizQuestion(
                    id = "g6_q1",
                    lessonId = "gram_a2_past_continuous",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "While I ___ down the street, it started to snow.",
                    promptUz = "'While' (davomida) bog'lovchisidan keyin qaysi shakl keladi?",
                    options = listOf("walked", "was walking", "am walking", "were walking"),
                    correctAnswer = "was walking",
                    explanationUz = "'While' davomli jarayonni bildiradi va 'I' bilan 'was walking' ishlatiladi.",
                    errorCategory = "Past Continuous",
                    cefrLevel = "A2"
                )
            )
        ),

        // 7. Prepositions & Comparatives (A2)
        GrammarLesson(
            id = "gram_a2_prepositions_comparatives",
            cefrLevel = "A2",
            titleEn = "Prepositions (in/on/at) & Comparatives",
            titleUz = "Predloglar (in/on/at) va Sifat darajalari",
            explanationUz = "Vaqt uchun: 'at' — aniq soatlar (at 7:00, at night), 'on' — hafta kunlari va sanalar (on Monday, on May 9th), 'in' — oylar, fasllar, yillar (in summer, in 2026). Sifatlarni qiyoslashda qisqa sifatlarga '-er / the -est', uzun sifatlarga 'more / the most' qo'shiladi.",
            formula = "at + clock time | on + days/dates | in + months/years | Adj-er / more Adj + than",
            affirmativeForm = "taller than, more interesting than, the best",
            negativeForm = "not as tall as",
            questionForm = "Which city is older, Samarkand or Tashkent?",
            examples = listOf(
                GrammarExample("Our class starts at 9:00 AM on Monday.", "Darsimiz dushanba kuni soat 9:00 da boshlanadi."),
                GrammarExample("Health is more important than money.", "Salomatlik puldan ko'ra muhimroqdir.")
            ),
            commonMistakes = listOf(
                GrammarMistake("See you in Friday!", "See you on Friday!", "Hafta kunlari oldidan har doim 'on' predlogi ishlatiladi."),
                GrammarMistake("more better", "better", "'Good -> better -> the best' noto'g'ri sifat bo'lib, 'more' qo'shilmaydi.")
            ),
            guidedPracticeHintUz = "Good -> better -> the best | Bad -> worse -> the worst.",
            questions = listOf(
                QuizQuestion(
                    id = "g7_q1",
                    lessonId = "gram_a2_prepositions_comparatives",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "My birthday is ___ October.",
                    promptUz = "Oy nomlari oldidan qaysi predlog ishlatiladi?",
                    options = listOf("at", "on", "in", "by"),
                    correctAnswer = "in",
                    explanationUz = "Oy, fasl va yillar oldidan 'in' predlogi qo'llanadi: 'in October'.",
                    errorCategory = "Prepositions",
                    cefrLevel = "A2"
                ),
                QuizQuestion(
                    id = "g7_q2",
                    lessonId = "gram_a2_prepositions_comparatives",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "This is ___ book I have ever read!",
                    promptUz = "Orttirma darajani (Superlative) tanlang:",
                    options = listOf("more interesting", "the most interesting", "interestinger", "most interesting"),
                    correctAnswer = "the most interesting",
                    explanationUz = "Ko'p bo'g'inli sifatning orttirma darajasi 'the most + adjective' orqali yasaladi.",
                    errorCategory = "Comparatives",
                    cefrLevel = "A2"
                )
            )
        ),

        // 8. Present Perfect (B1)
        GrammarLesson(
            id = "gram_b1_present_perfect",
            cefrLevel = "B1",
            titleEn = "Present Perfect Tense (have/has + V3)",
            titleUz = "Hozirgi tugallangan zamon (Natija va Tajriba)",
            explanationUz = "O'zbek tilida Present Perfect va Past Simple ko'pincha bir xil ('-ganman / -dim') tarjima qilinadi. Farqi shundaki: Present Perfect'da ish qachon bo'lgani aniq aytilmaydi, balki uning HOZIRGI NATIJASI yoki hayotiy tajriba muhim bo'ladi!",
            formula = "Subject + have / has + Past Participle (V3)",
            affirmativeForm = "I have finished my work. / She has visited Khiva.",
            negativeForm = "I haven't finished yet. / She hasn't arrived.",
            questionForm = "Have you ever been to London?",
            examples = listOf(
                GrammarExample("I have lost my keys (so I cannot open the door now).", "Men kalitimni yo'qotib qo'ydim (natijada hozir eshikni ocha olmayman)."),
                GrammarExample("She has lived here since 2020.", "U 2020-yildan beri shu yerda yashab kelyapti.")
            ),
            commonMistakes = listOf(
                GrammarMistake("I have seen him yesterday.", "I saw him yesterday.", "'Yesterday' kabi aniq o'tgan vaqt ko'rsatilsa, Present Perfect emas, Past Simple ('saw') ishlatiladi!")
            ),
            guidedPracticeHintUz = "Kalit so'zlar: already, just, yet, ever, never, since, for, recently.",
            questions = listOf(
                QuizQuestion(
                    id = "g8_q1",
                    lessonId = "gram_b1_present_perfect",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Have you finished the report ___?",
                    promptUz = "So'roq va inkor gap oxirida 'hali/allaqachon' ma'nosida keluvchi so'z:",
                    options = listOf("just", "already", "yet", "ever"),
                    correctAnswer = "yet",
                    explanationUz = "'Yet' so'roq va inkor gaplarning oxirida keladi ('Have you finished yet?').",
                    errorCategory = "Present Perfect",
                    cefrLevel = "B1"
                ),
                QuizQuestion(
                    id = "g8_q2",
                    lessonId = "gram_b1_present_perfect",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "We have known each other ___ ten years.",
                    promptUz = "Vaqt davomiyligi (10 yil davomida) oldidan qaysi so'z keladi?",
                    options = listOf("since", "for", "from", "ago"),
                    correctAnswer = "for",
                    explanationUz = "Vaqt oralig'i (ten years) bilan 'for', boshlanish nuqtasi (2016) bilan 'since' ishlatiladi.",
                    errorCategory = "Present Perfect",
                    cefrLevel = "B1"
                )
            )
        ),

        // 9. Future Forms & Modal Verbs (B1)
        GrammarLesson(
            id = "gram_b1_future_modals",
            cefrLevel = "B1",
            titleEn = "Future Forms & Modal Verbs",
            titleUz = "Kelasi zamon shakllari va Modal fe'llar",
            explanationUz = "'Will' — suhbat paytida qabul qilingan qaror va taxminlar uchun; 'be going to' — oldindan rejalashtirilgan niyat va aniq belgilari bor hodisalar uchun. Modal fe'llar (can, should, must, have to, might) dan keyin har doim 'to' siz oddiy fe'l (V1) keladi.",
            formula = "will + V1 | am/is/are going to + V1 | Modal + V1",
            affirmativeForm = "I am going to study abroad. / You should rest.",
            negativeForm = "I won't forget. / You mustn't smoke here.",
            questionForm = "Are you going to attend? / Should I call him?",
            examples = listOf(
                GrammarExample("Look at those black clouds! It is going to rain.", "Ana u qora bulutlarga qarang! Yomg'ir yog'adiganga o'xshaydi (aniq belgi)."),
                GrammarExample("You should practice English for 15 minutes every day.", "Har kuni 15 daqiqa ingliz tilini mashq qilishingiz kerak (maslahat).")
            ),
            commonMistakes = listOf(
                GrammarMistake("You should to learn these words.", "You should learn these words.", "Modal fe'llardan (can, must, should, may) keyin hech qachon 'to' qo'yilmaydi!")
            ),
            guidedPracticeHintUz = "Oldindan reja = 'be going to'; hozirgi qaror/va'da = 'will'; maslahat = 'should'.",
            questions = listOf(
                QuizQuestion(
                    id = "g9_q1",
                    lessonId = "gram_b1_future_modals",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "The phone is ringing! — Okay, I ___ answer it.",
                    promptUz = "Nutq paytida darhol qabul qilingan qaror uchun qaysi shakl mos?",
                    options = listOf("am going to", "will", "would", "must to"),
                    correctAnswer = "will",
                    explanationUz = "Gapirayotgan paytda qabul qilingan tezkor qaror uchun 'will' ishlatiladi.",
                    errorCategory = "Future Forms",
                    cefrLevel = "B1"
                ),
                QuizQuestion(
                    id = "g9_q2",
                    lessonId = "gram_b1_future_modals",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Passengers ___ fasten their seatbelts during takeoff.",
                    promptUz = "Majburiy qoida va qonuniyat uchun mos modal fe'l:",
                    options = listOf("might", "must", "could", "may"),
                    correctAnswer = "must",
                    explanationUz = "Rasmiy qoida va qat'iy majburiyatni ifodalashda 'must' qo'llanadi.",
                    errorCategory = "Modal Verbs",
                    cefrLevel = "B1"
                )
            )
        ),

        // 10. Conditionals & Passive Voice (B2)
        GrammarLesson(
            id = "gram_b2_conditionals_passive",
            cefrLevel = "B2",
            titleEn = "Conditionals (1st & 2nd) & Passive Voice",
            titleUz = "Shart ergash gaplar va Majhul nisbat",
            explanationUz = "1st Conditional (Real kelajak): If + Present Simple, will + V1. 2nd Conditional (Hozirgi xayoliy holat): If + Past Simple, would + V1. Passive Voice (Majhul nisbat) esa harakat kim tomonidan emas, balki nima ustida bajarilgani muhim bo'lganda 'be + V3' formulasi bilan yasaladi.",
            formula = "1st: If + V1, will + V1 | 2nd: If + V2, would + V1 | Passive: be + V3",
            affirmativeForm = "If it rains, we will stay home. / Cotton is grown in Uzbekistan.",
            negativeForm = "If I were you, I wouldn't worry.",
            questionForm = "What would you do if you won a scholarship?",
            examples = listOf(
                GrammarExample("If you study hard, you will pass the exam.", "Agar qunt bilan o'qisangiz, imtihondan o'tasiz."),
                GrammarExample("This historical mosque was built in the 15th century.", "Bu tarixiy masjid XV asrda qurilgan.")
            ),
            commonMistakes = listOf(
                GrammarMistake("If I will see him, I will tell him.", "If I see him, I will tell him.", "'If' qatnashgan shart qismida kelasi zamon uchun 'will' EMAS, Present Simple ishlatiladi!")
            ),
            guidedPracticeHintUz = "Eslab qoling: 'If' bo'lagida 'will/would' ishlatmang!",
            questions = listOf(
                QuizQuestion(
                    id = "g10_q1",
                    lessonId = "gram_b2_conditionals_passive",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "If she ___ earlier tomorrow, she will catch the morning train.",
                    promptUz = "First Conditional qoidasiga ko'ra 'If' qismiga mos fe'lni tanlang:",
                    options = listOf("will wake up", "wakes up", "woke up", "would wake up"),
                    correctAnswer = "wakes up",
                    explanationUz = "1st Conditional'da 'If' dan keyin Present Simple ('wakes up') keladi.",
                    errorCategory = "Conditionals",
                    cefrLevel = "B2"
                ),
                QuizQuestion(
                    id = "g10_q2",
                    lessonId = "gram_b2_conditionals_passive",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Thousands of new trees ___ in our city every spring.",
                    promptUz = "Majhul nisbat (Passive Voice) Present Simple shaklini toping:",
                    options = listOf("plant", "are planted", "planted", "is planted"),
                    correctAnswer = "are planted",
                    explanationUz = "Daraxtlar ekiladi (ko'plik + har bahor): 'are + V3 (planted)'.",
                    errorCategory = "Passive Voice",
                    cefrLevel = "B2"
                )
            )
        ),

        // 11. Reported Speech & Relative Clauses (C1)
        GrammarLesson(
            id = "gram_c1_reported_relative",
            cefrLevel = "C1",
            titleEn = "Reported Speech & Relative Clauses",
            titleUz = "O'zlashtirma gaplar va Aniqlovchi ergash gaplar",
            explanationUz = "Kimningdir gapini o'zlashtirib aytganda (He said that...), zamonlar bir pog'ona o'tmishga suriladi (Backshift: am/is -> was, have -> had, will -> would). Relative Clauses (who, which, that, whose, where) esa ikki gapni bog'lab, otga aniqlik kiritadi.",
            formula = "Said (that) + Backshifted Verb | Person + who/that | Thing + which/that | Possession + whose",
            affirmativeForm = "He said he was busy. / The scholar who wrote Algebra was Al-Khwarizmi.",
            negativeForm = "She told me not to be late.",
            questionForm = "He asked if I had finished the project.",
            examples = listOf(
                GrammarExample("Aziza said that she would call me the next day.", "Aziza menga ertasi kuni qo'ng'iroq qilishini aytdi."),
                GrammarExample("The student whose essay won first prize is from Fergana.", "Inshosi birinchi o'rinni olgan talaba Farg'onadan.")
            ),
            commonMistakes = listOf(
                GrammarMistake("He asked me where was the library.", "He asked me where the library was.", "O'zlashtirma so'roq gapda so'roq tartibi emas, darak gap tartibi (Subject + Verb) bo'ladi!")
            ),
            guidedPracticeHintUz = "Reported question'da 'do/does/did' tushib qoladi va ega kesimdan oldin keladi.",
            questions = listOf(
                QuizQuestion(
                    id = "g11_q1",
                    lessonId = "gram_c1_reported_relative",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "The doctor ___ treated my grandfather is very experienced.",
                    promptUz = "Insonga nisbatan aniqlovchi ergash gap bog'lovchisini tanlang:",
                    options = listOf("which", "who", "whose", "where"),
                    correctAnswer = "who",
                    explanationUz = "Shaxslarga nisbatan ega vazifasida 'who' (yoki 'that') ishlatiladi.",
                    errorCategory = "Relative Clauses",
                    cefrLevel = "C1"
                ),
                QuizQuestion(
                    id = "g11_q2",
                    lessonId = "gram_c1_reported_relative",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Sardor told me that he ___ his visa the day before.",
                    promptUz = "Reported Speech'da 'the day before' bilan qaysi zamon keladi?",
                    options = listOf("receives", "has received", "had received", "will receive"),
                    correctAnswer = "had received",
                    explanationUz = "O'tgan zamondagi harakat Reported Speech'da Past Perfect ('had received') ga o'tadi.",
                    errorCategory = "Reported Speech",
                    cefrLevel = "C1"
                )
            )
        ),

        // 12. Advanced Sentence Structures & Inversion (C2)
        GrammarLesson(
            id = "gram_c2_inversion_cleft",
            cefrLevel = "C2",
            titleEn = "Negative Inversion & Cleft Sentences",
            titleUz = "Inversiya va Kuchaytiruvchi (Cleft) gap qurilmalari",
            explanationUz = "Yuqori (C2 / IELTS 8.0+) darajada fikrga urg'u berish uchun gap boshiga inkor ravishlar (Never, Rarely, Seldom, Not only, Hardly... when) chiqariladi va yordamchi fe'l egadan oldinga o'tadi. Cleft sentences ('What I need is...', 'It was X that...') esa asosiy ma'lumotni ajratib ko'rsatadi.",
            formula = "Negative Adverbial + Auxiliary + Subject + Verb | What + Clause + is/was + Focus",
            affirmativeForm = "Rarely have I seen such dedication. / What really matters is consistency.",
            negativeForm = "Under no circumstances should you give up.",
            questionForm = "Little did they know how much the world would change.",
            examples = listOf(
                GrammarExample("Seldom do we witness such rapid technological progress.", "Biz bunday tez texnologik taraqqiyotga kamdan-kam guvoh bo'lamiz."),
                GrammarExample("What helped me master English was daily practice.", "Menga ingliz tilini mukammal o'rganishga yordam bergan narsa — kundalik mashq bo'ldi.")
            ),
            commonMistakes = listOf(
                GrammarMistake("Never I have seen such a view.", "Never have I seen such a view.", "'Never' gap boshiga chiqqanda 'have I seen' inversiya tartibi bo'lishi shart.")
            ),
            guidedPracticeHintUz = "Not only / Never / Rarely / Hardly gap boshida kelsa -> Auxiliary + Subject!",
            questions = listOf(
                QuizQuestion(
                    id = "g12_q1",
                    lessonId = "gram_c2_inversion_cleft",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Hardly ___ the office when my phone started ringing.",
                    promptUz = "'Hardly ... when' inversiyali qolipni to'ldiring:",
                    options = listOf("I had entered", "had I entered", "did I entered", "I entered"),
                    correctAnswer = "had I entered",
                    explanationUz = "'Hardly' gap boshida kelganda Past Perfect inversiyasi ('had I entered ... when') ishlatiladi.",
                    errorCategory = "Advanced Inversion",
                    cefrLevel = "C2"
                ),
                QuizQuestion(
                    id = "g12_q2",
                    lessonId = "gram_c2_inversion_cleft",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Under no circumstances ___ your password with strangers.",
                    promptUz = "'Under no circumstances' dan keyin to'g'ri inversiyani tanlang:",
                    options = listOf("you should share", "should you share", "you share", "do you shared"),
                    correctAnswer = "should you share",
                    explanationUz = "Inkor ibora gap boshida kelgani uchun modal fe'l egadan oldinga o'tadi: 'should you share'.",
                    errorCategory = "Advanced Inversion",
                    cefrLevel = "C2"
                )
            )
        )
    )

    val listeningExercises: List<ListeningExercise> = listOf(
        ListeningExercise(
            id = "list_a1_cafe",
            cefrLevel = "A1",
            titleEn = "At the Coffee Shop in Tashkent",
            titleUz = "Toshkentdagi qahvaxonada buyurtma berish",
            transcriptEn = "Barista: Good morning! Welcome to Silk Road Cafe. What would you like to drink today? Customer: Good morning! Can I have a medium green tea with lemon, please? Barista: Sure! Would you like anything to eat? We have fresh croissants and apple pie. Customer: One croissant, please. How much is that? Barista: That is five dollars in total. Customer: Here you are. Thank you!",
            summaryUz = "Mijoz qahvaxonada limonli ko'k choy va bitta kruassan buyurtma qiladi. Jami narxi 5 dollar bo'ladi.",
            questions = listOf(
                QuizQuestion(
                    id = "l1_q1",
                    lessonId = "list_a1_cafe",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "What drink did the customer order?",
                    promptUz = "Mijoz qanday ichimlik buyurtma qildi?",
                    options = listOf("Black coffee", "Green tea with lemon", "Orange juice", "Hot chocolate"),
                    correctAnswer = "Green tea with lemon",
                    explanationUz = "Dialogda mijoz: 'Can I have a medium green tea with lemon, please?' deb so'radi.",
                    errorCategory = "Listening Detail",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "l1_q2",
                    lessonId = "list_a1_cafe",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "How much did the order cost in total?",
                    promptUz = "Buyurtmaning umumiy narxi qancha bo'ldi?",
                    options = listOf("Three dollars", "Five dollars", "Seven dollars", "Ten dollars"),
                    correctAnswer = "Five dollars",
                    explanationUz = "Barista: 'That is five dollars in total' deb javob berdi.",
                    errorCategory = "Listening Numbers",
                    cefrLevel = "A1"
                )
            )
        ),
        ListeningExercise(
            id = "list_a2_train",
            cefrLevel = "A2",
            titleEn = "Announcement at the Train Station",
            titleUz = "Temir yo'l vokzalidagi e'lon",
            transcriptEn = "Attention passengers! The high-speed train to Samarkand will depart from Platform Three at eight fifteen AM. Previously, it was scheduled for Platform One, so please move to Platform Three now. Have your tickets and passports ready for inspection. We wish you a pleasant journey!",
            summaryUz = "Samarqandga ketuvchi tezyurar poyezd ertalab soat 8:15 da 1-platforma o'rniga 3-platformadan jo'nab ketishi e'lon qilindi.",
            questions = listOf(
                QuizQuestion(
                    id = "l2_q1",
                    lessonId = "list_a2_train",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Which platform will the train depart from now?",
                    promptUz = "Poyezd endi qaysi platformadan jo'naydi?",
                    options = listOf("Platform One", "Platform Two", "Platform Three", "Platform Eight"),
                    correctAnswer = "Platform Three",
                    explanationUz = "E'londa platforma 1 dan 3 ga o'zgargani ('move to Platform Three now') aytildi.",
                    errorCategory = "Listening Detail",
                    cefrLevel = "A2"
                ),
                QuizQuestion(
                    id = "l2_q2",
                    lessonId = "list_a2_train",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "What time does the train leave?",
                    promptUz = "Poyezd soat nechada jo'nab ketadi?",
                    options = listOf("8:00 AM", "8:15 AM", "8:50 AM", "9:15 AM"),
                    correctAnswer = "8:15 AM",
                    explanationUz = "'at eight fifteen AM' — ertalab soat 8:15.",
                    errorCategory = "Listening Numbers",
                    cefrLevel = "A2"
                )
            )
        ),
        ListeningExercise(
            id = "list_b1_remote_work",
            cefrLevel = "B1",
            titleEn = "Balancing Study and Remote Work",
            titleUz = "Masofaviy ish va o'qishni uyg'unlashtirish",
            transcriptEn = "Many young professionals in Uzbekistan now work remotely for international software companies. While working from home saves commuting time, it requires strong self-discipline. Experts recommend setting clear working hours, taking short walks during breaks, and dedicating thirty minutes every evening to improving English communication skills.",
            summaryUz = "Uydan turib xalqaro kompaniyalarda ishlash yo'l vaqtini tejaydi, biroq kuchli intizom va har kuni ingliz tilini rivojlantirishni talab qiladi.",
            questions = listOf(
                QuizQuestion(
                    id = "l3_q1",
                    lessonId = "list_b1_remote_work",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "What is the main advantage of working from home mentioned in the audio?",
                    promptUz = "Audioda uydan ishlashning qaysi asosiy afzalligi aytildi?",
                    options = listOf("Higher salary", "Saves commuting time", "Free meals", "No deadlines"),
                    correctAnswer = "Saves commuting time",
                    explanationUz = "'working from home saves commuting time' — yo'lga ketadigan vaqtni tejaydi.",
                    errorCategory = "Listening Main Idea",
                    cefrLevel = "B1"
                ),
                QuizQuestion(
                    id = "l3_q2",
                    lessonId = "list_b1_remote_work",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "How much time do experts recommend spending on English every evening?",
                    promptUz = "Mutaxassislar har kechqurun ingliz tiliga qancha vaqt ajratishni tavsiya qiladi?",
                    options = listOf("Ten minutes", "Thirty minutes", "Two hours", "Once a week"),
                    correctAnswer = "Thirty minutes",
                    explanationUz = "'dedicating thirty minutes every evening to improving English communication skills'.",
                    errorCategory = "Listening Detail",
                    cefrLevel = "B1"
                )
            )
        )
    )

    val speakingScenarios: List<SpeakingScenario> = listOf(
        SpeakingScenario(
            id = "spk_a1_intro",
            cefrLevel = "A1",
            titleEn = "Introducing Yourself",
            titleUz = "O'zini tanishtirish",
            situationUz = "Yangi xalqaro kursda o'zingiz, shahringiz va qiziqishlaringiz haqida 2-3 gap bilan ingliz tilida gapirib bering.",
            targetPhrasesEn = listOf("My name is...", "I am from...", "I work as / I am a student", "In my free time, I like..."),
            samplePromptEn = "Hello! Welcome to our class. Could you please introduce yourself and tell us why you are learning English?",
            samplePromptUz = "Salom! Kursimizga xush kelibsiz. O'zingizni tanishtirib, nima uchun ingliz tilini o'rganayotganingizni aytib bera olasizmi?",
            sampleResponseEn = "Hello! My name is Jasur and I am from Tashkent. I am learning English because I want to travel and work in an international company."
        ),
        SpeakingScenario(
            id = "spk_a1_food",
            cefrLevel = "A1",
            titleEn = "Ordering Food at a Restaurant",
            titleUz = "Restoranda ovqat buyurtma qilish",
            situationUz = "Ofitsiant sizdan nima buyurtma qilishingizni so'rayapti. Asosiy taom va ichimlik buyurtma qiling.",
            targetPhrasesEn = listOf("I would like to order...", "Could I have..., please?", "Can I get the bill, please?"),
            samplePromptEn = "Good evening! Are you ready to order, or do you need a few more minutes?",
            samplePromptUz = "Xayrli kech! Buyurtma berishga tayyormisiz yoki yana bir necha daqiqa kerakmi?",
            sampleResponseEn = "Yes, I am ready. I would like grilled chicken with rice and a glass of mineral water, please."
        ),
        SpeakingScenario(
            id = "spk_a2_travel",
            cefrLevel = "A2",
            titleEn = "At the Airport & Hotel Check-in",
            titleUz = "Aeroport va Mehmonxonada ro'yxatdan o'tish",
            situationUz = "Mehmonxona qabulxonasiga keldingiz. Xona band qilganingizni ayting va nonushta vaqtini so'rang.",
            targetPhrasesEn = listOf("I have a reservation under the name...", "What time is breakfast served?", "Is Wi-Fi included?"),
            samplePromptEn = "Welcome to Grand Hotel! How may I help you check in today?",
            samplePromptUz = "Grand Hotelga xush kelibsiz! Bugun joylashishingizga qanday yordam bera olaman?",
            sampleResponseEn = "Hello! I have a reservation for two nights under the name Karimov. Could you tell me what time breakfast starts?"
        ),
        SpeakingScenario(
            id = "spk_b1_shopping",
            cefrLevel = "B1",
            titleEn = "Shopping & Asking for Sizes",
            titleUz = "Do'konda xarid va o'lcham so'rash",
            situationUz = "Kiyim do'konida kurtka yoqdi, lekin sizga kattaroq o'lcham (large size) kerak va karta orqali to'lamoqchisiz.",
            targetPhrasesEn = listOf("Do you have this in a larger size?", "Where is the fitting room?", "Can I pay by credit card?"),
            samplePromptEn = "Hi there! Let me know if you need help finding your size.",
            samplePromptUz = "Salom! O'lchamingizni topishda yordam kerak bo'lsa, bemalol ayting.",
            sampleResponseEn = "Thank you! I really like this jacket, but it is a bit small. Do you have it in size large, and where is the fitting room?"
        ),
        SpeakingScenario(
            id = "spk_b2_interview",
            cefrLevel = "B2",
            titleEn = "Job Interview: Strengths & Experience",
            titleUz = "Ish suhbati (Job Interview)",
            situationUz = "Xalqaro kompaniyadagi suhbatda kuchli tomonlaringiz va jamoaviy ishlash tajribangiz haqida so'rashdi.",
            targetPhrasesEn = listOf("My greatest strength is...", "In my previous role, I successfully...", "I enjoy collaborating with..."),
            samplePromptEn = "Could you tell us about your main professional strengths and how you handle tight deadlines?",
            samplePromptUz = "Asosiy kasbiy kuchli tomonlaringiz va tig'iz muddatlarda qanday ishlashingiz haqida gapirib bera olasizmi?",
            sampleResponseEn = "My greatest strength is problem-solving under pressure. When facing tight deadlines, I prioritize key tasks and communicate clearly with my team."
        )
    )

    val readingPassages: List<ReadingPassage> = listOf(
        ReadingPassage(
            id = "read_a1_samarqand",
            cefrLevel = "A1",
            titleEn = "A Sunny Morning in Samarkand",
            titleUz = "Samarqandda quyoshli tong",
            passageEn = "Aziza lives in Samarkand with her family. Every Saturday morning, she walks to the famous Registan Square with her grandfather. The blue domes shine brightly under the morning sun. Many tourists from around the world visit the square and take photographs. Aziza likes practicing her English by greeting friendly visitors and telling them about Uzbek history.",
            translationUz = "Aziza oilasi bilan Samarqandda yashaydi. Har shanba kuni ertalab u bobosi bilan mashhur Registon maydoniga piyoda boradi. Moviy gumbazlar ertalabki quyosh ostida yorqin porlaydi. Dunyoning turli burchaklaridan ko'plab sayyohlar maydonga tashrif buyurib, suratga tushadilar. Aziza mehribon mehmonlar bilan salomlashib va ularga o'zbek tarixi haqida gapirib berib, ingliz tilini mashq qilishni yaxshi ko'radi.",
            glossary = listOf(
                WordMeaning("famous", "mashhur", "[ˈfeɪ-məs]"),
                WordMeaning("square", "maydon", "[skweər]"),
                WordMeaning("domes", "gumbazlar", "[dōmz]"),
                WordMeaning("shine", "porlamoq / nur sochmoq", "[ʃaɪn]"),
                WordMeaning("visitors", "mehmonlar / tashrif buyuruvchilar", "[ˈvɪ-zɪ-tərz]")
            ),
            questions = listOf(
                QuizQuestion(
                    id = "r1_q1",
                    lessonId = "read_a1_samarqand",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "Who does Aziza walk to Registan Square with?",
                    promptUz = "Aziza Registon maydoniga kim bilan boradi?",
                    options = listOf("Her sister", "Her grandfather", "Her teacher", "Her classmate"),
                    correctAnswer = "Her grandfather",
                    explanationUz = "Matnda: 'she walks to the famous Registan Square with her grandfather' deyilgan.",
                    errorCategory = "Reading Comprehension",
                    cefrLevel = "A1"
                ),
                QuizQuestion(
                    id = "r1_q2",
                    lessonId = "read_a1_samarqand",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "How does Aziza practice her English?",
                    promptUz = "Aziza ingliz tilini qanday mashq qiladi?",
                    options = listOf("By watching TV", "By greeting visitors and talking about history", "By writing letters", "By reading newspapers"),
                    correctAnswer = "By greeting visitors and talking about history",
                    explanationUz = "Aziza sayyohlar bilan salomlashib, ularga o'zbek tarixi haqida so'zlab berish orqali mashq qiladi.",
                    errorCategory = "Reading Comprehension",
                    cefrLevel = "A1"
                )
            )
        ),
        ReadingPassage(
            id = "read_b1_habits",
            cefrLevel = "B1",
            titleEn = "The Secret of Polyglots: Small Daily Habits",
            titleUz = "Poliglotlar siri: Kichik kundalik odatlar",
            passageEn = "People often think that learning a foreign language requires special talent. However, linguistic research shows that consistency is far more important than natural ability. Successful language learners—often called polyglots—rarely study for five hours in a single day. Instead, they practice for twenty minutes every day without fail. They listen to podcasts while commuting, review ten flashcards over breakfast, and speak aloud without fearing mistakes.",
            translationUz = "Odamlar ko'pincha chet tilini o'rganish alohida qobiliyat talab qiladi deb o'ylashadi. Biroq tilshunoslik tadqiqotlari shuni ko'rsatadiki, muntazamlik tug'ma qobiliyatdan ko'ra ancha muhimroqdir. Muvaffaqiyatli til o'rganuvchilar — poliglotlar kamdan-kam hollarda bir kunda besh soatlab o'qishadi. Buning o'rniga ular har kuni kanda qilmasdan yigirma daqiqadan mashq qiladilar. Ular yo'lda podkastlar tinglaydilar, nonushta paytida o'nta kartochkani takrorlaydilar va xato qilishdan qo'rqmasdan ovoz chiqarib gapiradilar.",
            glossary = listOf(
                WordMeaning("foreign", "xorijiy / chet", "[ˈfɔː-rən]"),
                WordMeaning("consistency", "muntazamlik / davomiylik", "[kən-ˈsɪs-tən-si]"),
                WordMeaning("ability", "qobiliyat", "[ə-ˈbɪ-lə-ti]"),
                WordMeaning("commuting", "ishga/o'qishga qatnash yo'li", "[kə-ˈmjuː-tɪŋ]"),
                WordMeaning("aloud", "ovoz chiqarib", "[ə-ˈlaʊd]")
            ),
            questions = listOf(
                QuizQuestion(
                    id = "r2_q1",
                    lessonId = "read_b1_habits",
                    type = QuestionType.MULTIPLE_CHOICE,
                    promptEn = "According to the passage, what is more important than natural talent?",
                    promptUz = "Matnga ko'ra, tug'ma qobiliyatdan ko'ra nima muhimroq?",
                    options = listOf("Studying 5 hours on Sundays", "Consistency and daily habits", "Living in another country", "Memorizing dictionaries"),
                    correctAnswer = "Consistency and daily habits",
                    explanationUz = "'consistency is far more important than natural ability' — muntazamlik eng muhim omildir.",
                    errorCategory = "Reading Main Idea",
                    cefrLevel = "B1"
                ),
                QuizQuestion(
                    id = "r2_q2",
                    lessonId = "read_b1_habits",
                    type = QuestionType.TRUE_FALSE,
                    promptEn = "Polyglots are afraid of making mistakes when speaking.",
                    promptUz = "Poliglotlar gapirayotganda xato qilishdan qo'rqishadimi?",
                    options = listOf("True", "False"),
                    correctAnswer = "False",
                    explanationUz = "Noto'g'ri ('False'). Matnda ular xato qilishdan qo'rqmasdan ('without fearing mistakes') gapirishi aytilgan.",
                    errorCategory = "Reading Inference",
                    cefrLevel = "B1"
                )
            )
        )
    )

    val writingPrompts: List<WritingPrompt> = listOf(
        WritingPrompt(
            id = "wrt_a1_daily",
            cefrLevel = "A1",
            category = "Gap tuzish va Kundalik hayot",
            titleUz = "Mening odatiy kunim (My Daily Routine)",
            promptEn = "Write 3 to 5 sentences about your daily routine. When do you wake up, what do you do during the day, and how do you study English?",
            instructionsUz = "Kundalik tartibingiz haqida ingliz tilida 3–5 ta gap yozing (Present Simple zamonidan foydalaning).",
            targetKeywords = listOf("wake up", "morning", "work", "study", "evening"),
            sampleAnswerEn = "I usually wake up at seven o'clock in the morning. After breakfast, I go to university by bus. In the evening, I study English for thirty minutes and read books."
        ),
        WritingPrompt(
            id = "wrt_a2_message",
            cefrLevel = "A2",
            category = "Xabar va Email yozish",
            titleUz = "Do'stga taklif xabari (Invitation Message)",
            promptEn = "Write a short message to your English-speaking friend inviting them to visit your hometown in Uzbekistan this weekend.",
            instructionsUz = "Ingliz tilida so'zlashuvchi do'stingizni dam olish kunlari o'z shahringizga taklif qilib qisqa xabar yozing.",
            targetKeywords = listOf("invite", "weekend", "visit", "delicious", "historic"),
            sampleAnswerEn = "Hi Alex! Are you free this weekend? I would love to invite you to visit my hometown, Bukhara. We can see historic monuments and eat delicious Uzbek palov together."
        ),
        WritingPrompt(
            id = "wrt_b1_email",
            cefrLevel = "B1",
            category = "Rasmiy Email (Formal Email)",
            titleUz = "Kurs yoki ish bo'yicha rasmiy xat",
            promptEn = "Write a polite email to an international training center asking for information about their online English course schedule and tuition fees.",
            instructionsUz = "Xalqaro o'quv markaziga onlayn ingliz tili kursi jadvali va narxi haqida so'rab rasmiy email yozing.",
            targetKeywords = listOf("Dear", "inquire", "schedule", "fee", "look forward to"),
            sampleAnswerEn = "Dear Admissions Team, I am writing to inquire about your B2 Business English online course. Could you please share the class schedule and tuition fees for next month? I look forward to hearing from you."
        ),
        WritingPrompt(
            id = "wrt_b2_essay",
            cefrLevel = "B2",
            category = "Qisqa Insho (Short Essay)",
            titleUz = "Texnologiya va Ta'lim (Technology in Education)",
            promptEn = "Write a short paragraph (5-7 sentences) discussing how mobile apps and AI tutors help students learn foreign languages faster.",
            instructionsUz = "Mobil ilovalar va AI ustozlar chet tillarini tezroq o'rganishga qanday yordam berishi haqida 5–7 gapdan iborat paragraf yozing.",
            targetKeywords = listOf("personalized", "accessible", "feedback", "practice", "effective"),
            sampleAnswerEn = "Modern technology has transformed how we learn foreign languages. Mobile applications make lessons accessible anytime and anywhere. Furthermore, AI tutors provide instant personalized feedback on grammar and writing mistakes. As a result, learners can practice consistently and build confidence much faster than before."
        )
    )
}
