package com.example.data

import com.example.model.VocabWord

object VocabularyCatalog {

    val topics: List<String> = listOf(
        "Barchasi",
        "Salomlashish va Asoslar",
        "Oila va Insonlar",
        "Kundalik Fe'llar",
        "Oziq-ovqat va Ichimlik",
        "Sayohat va Yo'nalish",
        "Uy va Buyumlar",
        "Vaqt va Kunlar",
        "Sifatlar va His-tuyg'ular",
        "Ta'lim va Ish",
        "Salomatlik va Texnologiya"
    )

    val allWords: List<VocabWord> = listOf(
        // Topic 1: Salomlashish va Asoslar (12 A1 words)
        VocabWord("w001", "Hello", "Salom", "[he-ˈlō]", "Hello, my name is Sardor.", "Salom, mening ismim Sardor.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w002", "Good morning", "Xayrli tong", "[gʊd ˈmɔːr-nɪŋ]", "Good morning, teacher!", "Xayrli tong, ustoz!", "Salomlashish va Asoslar", "A1"),
        VocabWord("w003", "Good afternoon", "Xayrli kun", "[gʊd ˌæf-tər-ˈnuːn]", "Good afternoon, how can I help you?", "Xayrli kun, sizga qanday yordam bera olaman?", "Salomlashish va Asoslar", "A1"),
        VocabWord("w004", "Good evening", "Xayrli kech", "[gʊd ˈiːv-nɪŋ]", "Good evening, welcome to our home.", "Xayrli kech, uyimizga xush kelibsiz.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w005", "Goodbye", "Xayr", "[ˌgʊd-ˈbaɪ]", "Goodbye, see you tomorrow!", "Xayr, ertaga ko'rishguncha!", "Salomlashish va Asoslar", "A1"),
        VocabWord("w006", "Please", "Iltimos", "[pliːz]", "Please open the window.", "Iltimos, derazani oching.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w007", "Thank you", "Rahmat", "[ˈθæŋk juː]", "Thank you for your help.", "Yordamingiz uchun rahmat.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w008", "You are welcome", "Arzimaydi / Marhamat", "[jʊr ˈwel-kəm]", "Thank you! — You are welcome.", "Rahmat! — Arzimaydi.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w009", "Excuse me", "Kechirasiz (murojaat)", "[ɪk-ˈskjuːz miː]", "Excuse me, where is the station?", "Kechirasiz, bekat qayerda?", "Salomlashish va Asoslar", "A1"),
        VocabWord("w010", "Sorry", "Uzr / Kechirasiz", "[ˈsɑː-ri]", "I am sorry I am late.", "Kechikkanim uchun uzr so'rayman.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w011", "Nice to meet you", "Tanishganimdan xursandman", "[naɪs tə miːt juː]", "Hello Dilnoza, nice to meet you.", "Salom Dilnoza, tanishganimdan xursandman.", "Salomlashish va Asoslar", "A1"),
        VocabWord("w012", "How are you?", "Qalaysiz? / Ahvolingiz qanday?", "[haʊ ɑːr juː]", "How are you today? I am fine.", "Bugun ahvolingiz qanday? Yaxshiman.", "Salomlashish va Asoslar", "A1"),

        // Topic 2: Oila va Insonlar (12 A1 words)
        VocabWord("w013", "Family", "Oila", "[ˈfæ-mə-li]", "I have a big and happy family.", "Mening katta va baxtli oilam bor.", "Oila va Insonlar", "A1"),
        VocabWord("w014", "Mother", "Ona", "[ˈmʌ-ðər]", "My mother cooks delicious palov.", "Mening onam mazali palov pishiradi.", "Oila va Insonlar", "A1"),
        VocabWord("w015", "Father", "Ota", "[ˈfɑː-ðər]", "My father works at a hospital.", "Mening otam shifoxonada ishlaydi.", "Oila va Insonlar", "A1"),
        VocabWord("w016", "Parents", "Ota-ona", "[ˈpeə-rənts]", "My parents live in Samarkand.", "Ota-onam Samarqandda yashaydilar.", "Oila va Insonlar", "A1"),
        VocabWord("w017", "Brother", "Aka / Uka", "[ˈbrʌ-ðər]", "My brother is a university student.", "Akam universitet talabasi.", "Oila va Insonlar", "A1"),
        VocabWord("w018", "Sister", "Opa / Singil", "[ˈsɪs-tər]", "My younger sister likes drawing.", "Singlim rasm chizishni yaxshi ko'radi.", "Oila va Insonlar", "A1"),
        VocabWord("w019", "Son", "O'g'il farzand", "[sʌn]", "Their son is seven years old.", "Ularning o'g'li yetti yoshda.", "Oila va Insonlar", "A1"),
        VocabWord("w020", "Daughter", "Qiz farzand", "[ˈdɔː-tər]", "She has one daughter.", "Uning bitta qizi bor.", "Oila va Insonlar", "A1"),
        VocabWord("w021", "Friend", "Do'st", "[frend]", "Jasur is my best friend.", "Jasur mening eng yaqin do'stim.", "Oila va Insonlar", "A1"),
        VocabWord("w022", "Neighbor", "Qo'shni", "[ˈneɪ-bər]", "Our neighbor is very kind.", "Bizning qo'shnimiz juda mehribon.", "Oila va Insonlar", "A1"),
        VocabWord("w023", "Child", "Bola / Farzand", "[tʃaɪld]", "Every child loves playing games.", "Har bir bola o'yin o'ynashni yaxshi ko'radi.", "Oila va Insonlar", "A1"),
        VocabWord("w024", "People", "Odamlar / Insonlar", "[ˈpiː-pəl]", "Many people visit Bukhara every year.", "Har yili ko'p odamlar Buxoroga tashrif buyuradi.", "Oila va Insonlar", "A1"),

        // Topic 3: Kundalik Fe'llar (15 A1 words)
        VocabWord("w025", "Wake up", "Uyg'onmoq", "[weɪk ʌp]", "I wake up at six o'clock every day.", "Men har kuni soat oltida uyg'onaman.", "Kundalik Fe'llar", "A1"),
        VocabWord("w026", "Eat", "Yemoq / Ovqatlanmoq", "[iːt]", "We eat breakfast together.", "Biz nonushtani birga qilamiz.", "Kundalik Fe'llar", "A1"),
        VocabWord("w027", "Drink", "Ichmoq", "[drɪŋk]", "I drink green tea in the morning.", "Men ertalab ko'k choy ichaman.", "Kundalik Fe'llar", "A1"),
        VocabWord("w028", "Go", "Bormoq", "[gō]", "They go to school by bus.", "Ular maktabga avtobusda boradilar.", "Kundalik Fe'llar", "A1"),
        VocabWord("w029", "Come", "Kelmoq", "[kʌm]", "Please come to the classroom on time.", "Iltimos, darsxonaga vaqtida keling.", "Kundalik Fe'llar", "A1"),
        VocabWord("w030", "Work", "Ishlamoq", "[wɜːrk]", "I work from Monday to Friday.", "Men dushanbadan jumagacha ishlayman.", "Kundalik Fe'llar", "A1"),
        VocabWord("w031", "Study", "O'qimoq / O'rganmoq", "[ˈstʌ-di]", "I study English every evening.", "Men har kechqurun ingliz tilini o'rganaman.", "Kundalik Fe'llar", "A1"),
        VocabWord("w032", "Read", "O'qimoq (kitob, matn)", "[riːd]", "She likes to read interesting books.", "U qiziqarli kitoblarni o'qishni yoqtiradi.", "Kundalik Fe'llar", "A1"),
        VocabWord("w033", "Write", "Yozmoq", "[raɪt]", "Write new words in your notebook.", "Yangi so'zlarni daftaringizga yozing.", "Kundalik Fe'llar", "A1"),
        VocabWord("w034", "Speak", "Gapirmoq", "[spiːk]", "Do you speak English?", "Siz ingliz tilida gapirasizmi?", "Kundalik Fe'llar", "A1"),
        VocabWord("w035", "Listen", "Tinglamoq", "[ˈlɪ-sən]", "Listen carefully to the audio.", "Audioni diqqat bilan tinglang.", "Kundalik Fe'llar", "A1"),
        VocabWord("w036", "Understand", "Tushunmoq", "[ˌʌn-dər-ˈstænd]", "I understand this grammar rule.", "Men bu grammatik qoidani tushundim.", "Kundalik Fe'llar", "A1"),
        VocabWord("w037", "Help", "Yordam bermoq", "[help]", "Can you help me with my homework?", "Uy vazifamga yordam bera olasizmi?", "Kundalik Fe'llar", "A1"),
        VocabWord("w038", "Sleep", "Uxlamoq", "[sliːp]", "I usually sleep eight hours a night.", "Men odatda kechasi sakkiz soat uxlayman.", "Kundalik Fe'llar", "A1"),
        VocabWord("w039", "Buy", "Sotib olmoq", "[baɪ]", "We buy fresh bread every morning.", "Biz har ertalab issiq non sotib olamiz.", "Kundalik Fe'llar", "A1"),

        // Topic 4: Oziq-ovqat va Ichimlik (12 A1 words)
        VocabWord("w040", "Bread", "Non", "[bred]", "Uzbek bread is famous around the world.", "O'zbek noni butun dunyoda mashhur.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w041", "Water", "Suv", "[ˈwɔː-tər]", "Drink plenty of clean water.", "Ko'proq toza suv iching.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w042", "Tea", "Choy", "[tiː]", "Would you like a cup of hot tea?", "Bir piyola issiq choy xohlaysizmi?", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w043", "Coffee", "Qahva", "[ˈkɔː-fi]", "He drinks coffee without sugar.", "U qahvani shakarsiz ichadi.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w044", "Milk", "Sut", "[mɪlk]", "Children drink warm milk before bed.", "Bolalar uxlashdan oldin iliq sut ichadilar.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w045", "Apple", "Olma", "[ˈæ-pəl]", "This red apple is very sweet.", "Bu qizil olma juda shirin.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w046", "Meat", "Go'sht", "[miːt]", "We need meat and carrots for dinner.", "Kechki ovqat uchun bizga go'sht va sabzi kerak.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w047", "Rice", "Guruch", "[raɪs]", "Rice is the main ingredient in palov.", "Guruch palovning asosiy masallig'idir.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w048", "Vegetables", "Sabzavotlar", "[ˈvedʒ-tə-bəlz]", "Fresh vegetables are good for health.", "Yangi sabzavotlar sog'liq uchun foydali.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w049", "Fruit", "Meva", "[fruːt]", "Summer brings delicious fruit.", "Yoz fasli shirin mevalarni olib keladi.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w050", "Breakfast", "Nonushta", "[ˈbrek-fəst]", "Breakfast is the most important meal.", "Nonushta eng muhim taomdir.", "Oziq-ovqat va Ichimlik", "A1"),
        VocabWord("w051", "Dinner", "Kechki ovqat", "[ˈdɪ-nər]", "Dinner is ready at seven o'clock.", "Kechki ovqat soat yettida tayyor bo'ladi.", "Oziq-ovqat va Ichimlik", "A1"),

        // Topic 5: Sayohat va Yo'nalish (12 A1 words)
        VocabWord("w052", "Airport", "Aeroport", "[ˈeər-pɔːrt]", "We arrived at the airport early.", "Biz aeroportga erta yetib keldik.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w053", "Ticket", "Chipta", "[ˈtɪ-kɪt]", "I bought a train ticket to Tashkent.", "Men Toshkentga poyezd chiptasini sotib oldim.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w054", "Passport", "Pasport", "[ˈpæs-pɔːrt]", "Please show your passport at the gate.", "Iltimos, darvozada pasportingizni ko'rsating.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w055", "Hotel", "Mehmonxona", "[hō-ˈtel]", "The hotel is near the city center.", "Mehmonxona shahar markazi yaqinida joylashgan.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w056", "Street", "Ko'cha", "[striːt]", "Our school is on Navoi Street.", "Maktabimiz Navoiy ko'chasida joylashgan.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w057", "City", "Shahar", "[ˈsɪ-ti]", "Tashkent is a modern and green city.", "Toshkent — zamonaviy va yashil shahar.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w058", "Country", "Mamlakat / Davlat", "[ˈkʌn-tri]", "Uzbekistan is a beautiful country.", "O'zbekiston — go'zal mamlakat.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w059", "Left", "Chap", "[left]", "Turn left at the traffic light.", "Svetoforda chapga buriling.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w060", "Right", "O'ng", "[raɪt]", "The bank is on your right.", "Bank sizning o'ng tomoningizda.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w061", "Straight", "To'g'riga", "[streɪt]", "Go straight ahead for two blocks.", "Ikki daha to'g'riga yuring.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w062", "Train", "Poyezd", "[treɪn]", "The Afrosiyob train is very fast.", "Afrosiyob poyezdi juda tez yuradi.", "Sayohat va Yo'nalish", "A1"),
        VocabWord("w063", "Map", "Xarita", "[mæp]", "Can you show me the place on the map?", "Xaritadan joyni ko'rsata olasizmi?", "Sayohat va Yo'nalish", "A1"),

        // Topic 6: Uy va Buyumlar (10 A1 words)
        VocabWord("w064", "House", "Uy / Hovli", "[haʊs]", "They live in a comfortable house.", "Ular qulay uyda yashaydilar.", "Uy va Buyumlar", "A1"),
        VocabWord("w065", "Room", "Xona", "[ruːm]", "My room is clean and bright.", "Mening xonam toza va yorug'.", "Uy va Buyumlar", "A1"),
        VocabWord("w066", "Door", "Eshik", "[dɔːr]", "Please close the door quietly.", "Iltimos, eshikni sekin yoping.", "Uy va Buyumlar", "A1"),
        VocabWord("w067", "Window", "Deraza", "[ˈwɪn-dō]", "Look out of the window.", "Derazadan tashqariga qarang.", "Uy va Buyumlar", "A1"),
        VocabWord("w068", "Table", "Stol", "[ˈteɪ-bəl]", "The books are on the table.", "Kitoblar stol ustida turibdi.", "Uy va Buyumlar", "A1"),
        VocabWord("w069", "Chair", "Stul / Kursi", "[tʃeər]", "Please sit on this chair.", "Iltimos, mana bu stulga o'tiring.", "Uy va Buyumlar", "A1"),
        VocabWord("w070", "Bed", "Karavot / Joy", "[bed]", "I go to bed at ten o'clock.", "Men soat o'nda uxlashga yotaman.", "Uy va Buyumlar", "A1"),
        VocabWord("w071", "Key", "Kalit", "[kiː]", "Where is my house key?", "Uyimning kaliti qayerda?", "Uy va Buyumlar", "A1"),
        VocabWord("w072", "Bag", "Sumka", "[bæg]", "Her bag is heavy today.", "Bugun uning sumkasi og'ir.", "Uy va Buyumlar", "A1"),
        VocabWord("w073", "Book", "Kitob", "[bʊk]", "A good book is a great teacher.", "Yaxshi kitob — buyuk ustoz.", "Uy va Buyumlar", "A1"),

        // Topic 7: Vaqt va Kunlar (10 A1 words)
        VocabWord("w074", "Today", "Bugun", "[tə-ˈdeɪ]", "Today is a sunny day.", "Bugun quyoshli kun.", "Vaqt va Kunlar", "A1"),
        VocabWord("w075", "Tomorrow", "Ertaga", "[tə-ˈmɔː-rō]", "We have an English test tomorrow.", "Ertaga ingliz tilidan testimiz bor.", "Vaqt va Kunlar", "A1"),
        VocabWord("w076", "Yesterday", "Kecha", "[ˈjes-tər-deɪ]", "Yesterday was Sunday.", "Kecha yakshanba edi.", "Vaqt va Kunlar", "A1"),
        VocabWord("w077", "Morning", "Ertalab / Tong", "[ˈmɔːr-nɪŋ]", "I exercise in the morning.", "Men ertalab mashq qilaman.", "Vaqt va Kunlar", "A1"),
        VocabWord("w078", "Evening", "Kechqurun", "[ˈiːv-nɪŋ]", "We watch movies in the evening.", "Biz kechqurun kino ko'ramiz.", "Vaqt va Kunlar", "A1"),
        VocabWord("w079", "Week", "Hafta", "[wiːk]", "There are seven days in a week.", "Bir haftada yetti kun bor.", "Vaqt va Kunlar", "A1"),
        VocabWord("w080", "Month", "Oy (kalendar)", "[mʌnθ]", "May is a warm month.", "May — iliq oy.", "Vaqt va Kunlar", "A1"),
        VocabWord("w081", "Year", "Yil", "[jɪər]", "I want to learn English this year.", "Men bu yil ingliz tilini o'rganmoqchiman.", "Vaqt va Kunlar", "A1"),
        VocabWord("w082", "Always", "Har doim", "[ˈɔːl-weɪz]", "She always smiles and greets people.", "U har doim tabassum qiladi va salomlashadi.", "Vaqt va Kunlar", "A1"),
        VocabWord("w083", "Sometimes", "Ba'zan", "[ˈsʌm-taɪmz]", "Sometimes I walk to work.", "Ba'zan men ishga piyoda boraman.", "Vaqt va Kunlar", "A1"),

        // Topic 8: Sifatlar va His-tuyg'ular (10 A1 words)
        VocabWord("w084", "Happy", "Xursand / Baxtli", "[ˈhæ-pi]", "I am very happy to see you.", "Sizni ko'rganimdan juda xursandman.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w085", "Tired", "Charchagan", "[ˈtaɪərd]", "He feels tired after a long day.", "U uzoq kundan keyin o'zini charchagan his qilyapti.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w086", "Easy", "Oson", "[ˈiː-zi]", "This lesson is easy to understand.", "Bu darsni tushunish oson.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w087", "Difficult", "Qiyin", "[ˈdɪ-fɪ-kəlt]", "No language is too difficult with practice.", "Mashq qilinsa, hech bir til juda qiyin emas.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w088", "Important", "Muhim", "[ɪm-ˈpɔːr-tənt]", "Vocabulary is very important.", "Lug'at boyligi juda muhim.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w089", "Fast", "Tez", "[fæst]", "He speaks English quite fast.", "U ingliz tilida ancha tez gapiradi.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w090", "Slow", "Sekin", "[slō]", "Please speak a little slow.", "Iltimos, biroz sekinroq gapiring.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w091", "New", "Yangi", "[nuː]", "I learned ten new words today.", "Men bugun o'nta yangi so'z o'rgandim.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w092", "Good", "Yaxshi", "[gʊd]", "That is a very good idea.", "Bu juda yaxshi fikr.", "Sifatlar va His-tuyg'ular", "A1"),
        VocabWord("w093", "Ready", "Tayyor", "[ˈre-di]", "Are you ready for the quiz?", "Testga tayyormisiz?", "Sifatlar va His-tuyg'ular", "A1"),

        // Topic 9: Ta'lim va Ish (10 A1-B1 words)
        VocabWord("w094", "Teacher", "O'qituvchi / Ustoz", "[ˈtiː-tʃər]", "Our English teacher explains clearly.", "Ingliz tili o'qituvchimiz tushunarli tushuntiradi.", "Ta'lim va Ish", "A1"),
        VocabWord("w095", "Student", "Talaba / O'quvchi", "[ˈstuː-dənt]", "Every student has a goal.", "Har bir o'quvchining o'z maqsadi bor.", "Ta'lim va Ish", "A1"),
        VocabWord("w096", "Question", "Savol", "[ˈkwes-tʃən]", "May I ask a question?", "Savol bersam maylimi?", "Ta'lim va Ish", "A1"),
        VocabWord("w097", "Answer", "Javob / Javob bermoq", "[ˈæn-sər]", "Please check your answer.", "Iltimos, javobingizni tekshiring.", "Ta'lim va Ish", "A1"),
        VocabWord("w098", "Mistake", "Xato", "[mɪ-ˈsteɪk]", "We learn from every mistake.", "Biz har bir xatodan saboq olamiz.", "Ta'lim va Ish", "A1"),
        VocabWord("w099", "Job", "Ish / Kasb", "[dʒɑːb]", "She found a great job in IT.", "U IT sohasida ajoyib ish topdi.", "Ta'lim va Ish", "A1"),
        VocabWord("w100", "Office", "Idora / Ofis", "[ˈɔː-fɪs]", "The office opens at nine.", "Ofis soat to'qqizda ochiladi.", "Ta'lim va Ish", "A1"),
        VocabWord("w101", "Experience", "Tajriba", "[ɪk-ˈspɪə-ri-əns]", "He has five years of teaching experience.", "Uning besh yillik o'qituvchilik tajribasi bor.", "Ta'lim va Ish", "A2"),
        VocabWord("w102", "Opportunity", "Imkoniyat", "[ˌɑː-pər-ˈtuː-nə-ti]", "Learning English opens new opportunities.", "Ingliz tilini o'rganish yangi imkoniyatlar ochadi.", "Ta'lim va Ish", "B1"),
        VocabWord("w103", "Achievement", "Yutuq / Muvaffaqiyat", "[ə-ˈtʃiːv-mənt]", "Passing the exam was a big achievement.", "Imtihondan o'tish katta yutuq bo'ldi.", "Ta'lim va Ish", "B1"),

        // Topic 10: Salomatlik va Texnologiya (12 A1-C1 words)
        VocabWord("w104", "Doctor", "Shifokor", "[ˈdɑːk-tər]", "The doctor advised me to rest.", "Shifokor menga dam olishni maslahat berdi.", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w105", "Hospital", "Shifoxona", "[ˈhɑːs-pɪ-təl]", "Where is the nearest hospital?", "Eng yaqin shifoxona qayerda?", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w106", "Medicine", "Dori / Tibbiyot", "[ˈme-də-sən]", "Take this medicine after meals.", "Bu dorini ovqatdan keyin iching.", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w107", "Healthy", "Sog'lom", "[ˈhel-θi]", "Walking keeps your heart healthy.", "Piyoda yurish yuragingizni sog'lom saqlaydi.", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w108", "Phone", "Telefon", "[fōn]", "I use my phone to practice English.", "Men telefonimdan ingliz tilini mashq qilish uchun foydalanaman.", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w109", "Computer", "Kompyuter", "[kəm-ˈpjuː-tər]", "Every engineer works with a computer.", "Har bir muhandis kompyuter bilan ishlaydi.", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w110", "Message", "Xabar", "[ˈme-sɪdʒ]", "Did you receive my text message?", "Yozma xabarimni oldingizmi?", "Salomatlik va Texnologiya", "A1"),
        VocabWord("w111", "Environment", "Atrof-muhit / Muhit", "[ɪn-ˈvaɪ-rən-mənt]", "Protecting the environment is our duty.", "Atrof-muhitni asrash bizning burchimizdir.", "Salomatlik va Texnologiya", "B1"),
        VocabWord("w112", "Knowledge", "Bilim", "[ˈnɑː-lɪdʒ]", "Knowledge is a treasure that follows its owner everywhere.", "Bilim — egasiga har yerda hamroh bo'ladigan xazinadir.", "Salomatlik va Texnologiya", "B1"),
        VocabWord("w113", "Sustainable", "Barqaror / Uzoq muddatli", "[sə-ˈsteɪ-nə-bəl]", "Consistent daily study is more sustainable.", "Har kuni muntazam o'qish ancha barqarordir.", "Salomatlik va Texnologiya", "B2"),
        VocabWord("w114", "Comprehensive", "Keng qamrovli / Har tomonlama", "[ˌkɑːm-prɪ-ˈhen-sɪv]", "This course provides a comprehensive guide.", "Bu kurs keng qamrovli qo'llanmani taqdim etadi.", "Salomatlik va Texnologiya", "C1"),
        VocabWord("w115", "Articulate", "Fikrini ravon va aniq ifodalaydigan", "[ɑːr-ˈtɪk-jə-lət]", "She is an articulate speaker in both Uzbek and English.", "U ham o'zbek, ham ingliz tilida fikrini ravon ifodalaydi.", "Salomatlik va Texnologiya", "C1")
    )
}
