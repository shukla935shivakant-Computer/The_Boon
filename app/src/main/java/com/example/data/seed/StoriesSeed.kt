package com.example.data.seed

data class StoryItem(
    val id: String,
    val emoji: String,
    val readMinutes: Int,
    val titleEn: String,
    val titleHi: String,
    val titleEs: String,
    val titleFr: String,
    val titleZh: String,
    val moralEn: String,
    val moralHi: String,
    val moralEs: String,
    val moralFr: String,
    val moralZh: String,
    val categoryEn: String,
    val categoryHi: String,
    val categoryEs: String,
    val categoryFr: String,
    val categoryZh: String,
    val contentEn: String,
    val contentHi: String,
    val contentEs: String,
    val contentFr: String,
    val contentZh: String,
    var cheersCount: Int = 18
) {
    fun getTitle(lang: String): String = when (lang.lowercase()) {
        "hi" -> titleHi
        "es" -> titleEs
        "fr" -> titleFr
        "zh" -> titleZh
        else -> titleEn
    }

    fun getMoral(lang: String): String = when (lang.lowercase()) {
        "hi" -> moralHi
        "es" -> moralEs
        "fr" -> moralFr
        "zh" -> moralZh
        else -> moralEn
    }

    fun getCategory(lang: String): String = when (lang.lowercase()) {
        "hi" -> categoryHi
        "es" -> categoryEs
        "fr" -> categoryFr
        "zh" -> categoryZh
        else -> categoryEn
    }

    fun getContent(lang: String): String = when (lang.lowercase()) {
        "hi" -> contentHi
        "es" -> contentEs
        "fr" -> contentFr
        "zh" -> contentZh
        else -> contentEn
    }
}

object StoriesSeed {
    fun getStories(): List<StoryItem> = listOf(
        StoryItem(
            id = "story_hare_tortoise",
            emoji = "🐢",
            readMinutes = 3,
            titleEn = "The Steadfast Tortoise and the Flashy Hare",
            titleHi = "दृढ़निश्चयी कछुआ और घमंडी खरगोश",
            titleEs = "La Tortuga Constante y la Liebre Presumida",
            titleFr = "La Tortue Patiente et le Lièvre Vantard",
            titleZh = "坚韧不拔的乌龟与骄傲轻敌的白兔",
            moralEn = "Slow and steady with quiet determination wins the race every single time.",
            moralHi = "धैर्य, निरंतर प्रयास और शांत दृढ़ संकल्प हमेशा घमंड पर विजय पाते हैं।",
            moralEs = "La constancia serena y el paso firme siempre ganan a la soberbia.",
            moralFr = "Rien ne sert de courir ; il faut partir à point avec persévérance.",
            moralZh = "脚踏实地、持之以恒的毅力每一次都能战胜浮躁与骄傲自满。",
            categoryEn = "Aesop Fable",
            categoryHi = "ईसप की नीति कथा",
            categoryEs = "Fábula de Esopo",
            categoryFr = "Fable d'Ésope",
            categoryZh = "伊索寓言精选",
            contentEn = """Once in an emerald clearing beneath old cedar trees, a speedy hare named Swift boasted endlessly about his lightning paws. He laughed at Ollie the tortoise, whose heavy green shell made every step deliberate and calm.

"I could run circles around you backwards while whistling!" Swift bragged, kicking up dust.

Ollie smiled warmly. "Then let us race to the great white birch at the top of Whispering Hill."

The forest animals gathered. At the count of three, Swift vanished in a flurry of leaves, covering half the distance in seconds. Seeing Ollie barely past the starting stump, Swift grew arrogant. 

"I have plenty of time," he chuckled. "I will take a cozy nap under this clover patch."

Swift closed his eyes and drifted into deep sleep. Meanwhile, Ollie never stopped. Step by step, crunching over twigs and steadying himself over pebbles, he kept his eyes on the hilltop. The sun climbed high.

When Swift awoke with a jolt, the sky was tinted with sunset amber. He sprinted as fast as the wind, but as he breached the hill crest, Ollie had already crossed the finish ribbon! The animals cheered for Ollie's steady, persevering spirit. Slow, consistent effort triumphs over unfocused talent!""",
            contentHi = """एक हरे-भरे जंगल में, स्विफ्ट नाम का एक तेज़ खरगोश अपनी फुर्ती पर बहुत घमंड करता था। वह ओली नाम के शांत कछुए की धीमी चाल का रोज़ मज़ाक उड़ाता था।

"मैं आंख झपकाते ही तुमसे सौ गुना आगे निकल सकता हूँ!" खरगोश ने इतराते हुए कहा।

कछुआ मुस्कुराया और बोला: "चलो, उस ऊंची पहाड़ी के शिखर तक एक दौड़ लगाकर देखते हैं।"

दौड़ शुरू होते ही खरगोश हवा की तरह उड़ गया। आधी दूरी तय करके जब उसने पीछे देखा, तो कछुआ बहुत पीछे था। खरगोश ने सोचा, "कछुआ तो शाम तक भी यहाँ नहीं पहुंचेगा। क्यों न इस घनी छांव में थोड़ी देर सो लिया जाए?"

खरगोश गहरी नींद में सो गया। लेकिन कछुआ एक पल के लिए भी नहीं रुका। वह धीरे-धीरे, लगातार कदम बढ़ाता हुआ आगे बढ़ता रहा। जब खरगोश की आंख खुली, तो शाम ढल चुकी थी। वह पूरी ताकत से दौड़ा, लेकिन तब तक कछुआ फिनिश लाइन पार कर चुका था! सभी जानवरों ने कछुए की निरंतर मेहनत और धैर्य का सम्मान किया।""",
            contentEs = """En un claro esmeralda bajo viejos cedros, una veloz liebre llamada Swift presumía sin descanso de sus veloces patas. Se burlaba de Ollie, la tortuga, cuyo caparazón verde hacía que cada paso fuera tranquilo y pausado.

"¡Podría correr en círculos a tu alrededor mientras silbo!", se jactaba la liebre levantando polvo.

Ollie sonrió con calma: "Entonces corramos hasta el gran abedul en la cima de la colina."

Todos los animales del bosque se reunieron. A la de tres, la liebre salió disparada como el viento, dejando a Ollie apenas detrás de la salida. Al verse tan adelantada, la liebre pensó: "Tengo tiempo de sobra; dormiré una pequeña siesta bajo estos tréboles."

La liebre se quedó profundamente dormida. Mientras tanto, Ollie no se detuvo ni un solo segundo. Paso a paso, sobre ramitas y piedras, mantuvo la mirada en la meta. Cuando la liebre despertó sobresaltada, el cielo ya era de color atardecer. Corrió con todas sus fuerzas, ¡pero Ollie ya había cruzado la cinta de llegada! La constancia siempre vence a la soberbia.""",
            contentFr = """Sous les grands cèdres d'une clairière verdoyante, un lièvre nommé Swift ne cessait de vanter sa vitesse fulgurante. Il se moquait ouvertement d'Ollie, la tortue, dont la carapace rendait la démarche calme et mesurée.

"Je pourrais faire dix fois le tour de toi en sifflotant !", fanfaronnait le lièvre.

Ollie lui sourit avec bienveillance : "Faisons donc la course jusqu'au sommet de la Colline Dorée."

Les animaux de la forêt s'assemblèrent pour le départ. Au signal, le lièvre bondit comme l'éclair et disparut dans un nuage de feuilles. Voyant la tortue encore près de la ligne, il se crut déjà vainqueur : "J'ai tout le temps ! Je vais m'accorder une sieste douillette à l'ombre."

Il s'endormit profondément. Pendant ce temps, Ollie n'arrêta jamais de marcher. Un pas, puis un autre, avançant avec régularité vers le sommet. Lorsque le lièvre se réveilla brusquement, le soleil se couchait déjà. Il s'élança à toute vitesse, mais en atteignant la colline, Ollie venait tout juste de franchir le ruban d'arrivée ! La régularité triomphe toujours de l'arrogance.""",
            contentZh = """在一片郁郁葱葱的古老雪松森林里，住着一只跑得飞快的白兔斯威夫特，它整天向森林里的小动物夸耀自己如同闪电一般的神速。每当看到背着沉重甲壳缓慢爬行的乌龟奥利，它就忍不住大声讥笑。

“我就是倒着跑、一边吹口哨，也能围着你跑三圈！”白兔洋洋得意地扬起尘土。

小乌龟奥利温和地微笑着说：“既然如此，那我们就从这里赛跑，看谁先到达远方山丘顶端的那棵大白桦树吧。”

森林居民们纷纷围拢过来当裁判。口令一响，白兔嗖地一声窜了出去，转眼就跑完了一半路程。回头看到小乌龟才刚挪动了几步，白兔狂妄地打起了哈欠：“时间充裕得很，我先在三叶草树荫底下美美地睡个午觉再说！”

白兔呼呼大睡起来。然而，小乌龟奥利一步也没有停下。踩着松针、跨过小石子，它心中只有一个目标：前方的山顶。阳光从正午渐渐西斜。

当白兔猛然惊醒时，夕阳已经染红了半边天！它拼尽全力向山顶飞奔，可是当它气喘吁吁爬上山脊时，奥利早已经胸有成竹地冲过了终点红丝带！坚持不懈、脚踏实地的努力，永远能够击败骄傲自大的空谈！"""
        ),
        StoryItem(
            id = "story_lion_mouse",
            emoji = "🦁",
            readMinutes = 3,
            titleEn = "The Gentle Mouse and the King of the Forest",
            titleHi = "दयालु नन्हा चूहा और जंगल का राजा",
            titleEs = "El Ratón Compasivo y el Rey León",
            titleFr = "Le Petit Souris et le Roi Lion",
            titleZh = "善良的小田鼠与森林狮子王",
            moralEn = "No act of kindness, however small, is ever wasted. Even the tiniest creature can help a giant.",
            moralHi = "दयालुता का कोई भी कार्य कभी व्यर्थ नहीं जाता। छोटे से छोटा जीव भी बड़ा मददगार बन सकता है।",
            moralEs = "Ningún acto de bondad es en vano; hasta el más pequeño puede salvar a un gigante.",
            moralFr = "On a souvent besoin d'un plus petit que soi ; la bonté porte toujours ses fruits.",
            moralZh = "小小的善意永远不会白费，再微小的朋友也有拯救巨人的非凡力量。",
            categoryEn = "Aesop Fable",
            categoryHi = "ईसप की नीति कथा",
            categoryEs = "Fábula de Esopo",
            categoryFr = "Fable d'Ésope",
            categoryZh = "伊索寓言精选",
            contentEn = """A mighty golden-maned lion named Leo lay snoozing peacefully on a warm boulder after a morning stroll. A curious field mouse named Pip was chasing dandelion seeds and accidentally scampered right across the great lion's sleeping nose!

With a fierce rumble, Leo's massive paw came down, trapping the trembling mouse.

"How dare a speck like you wake the king?" Leo growled, raising his claws.

"Please, Great King!" squeaked Pip bravely. "I was careless, but spare my life! If you let me go, I promise that one day I will repay your kindness."

Leo chuckled at the thought of a tiny mouse helping a 400-pound lion. But amused and touched by Pip's courage, he lifted his paw and let him scamper free into the tall grass.

Days later, while patrolling his territory, Leo fell into a hunter's thick rope net. The cords bound his paws and chest tight. Leo roared in despair, his thunderous voice echoing across the valley.

Hearing the familiar roar, Pip rushed to the spot. Without hesitation, Pip set to work with his sharp teeth, gnawing furiously through the thickest hemp strands. Strand by strand, the knot loosened until Leo burst free!

Leo looked down with deep gratitude and bowed his head to the little hero. From that day on, the lion and the field mouse were inseparable friends, proving that heart and kindness matter far more than size!""",
            contentHi = """एक दोपहर एक विशाल शेर लियो धूप सेंकते हुए गहरी नींद में सो रहा था। तभी पिप नाम का एक नन्हा चंचल चूहा खेलते-खेलते अनजाने में शेर की नाक के ऊपर से दौड़ गया!

शेर ने भारी दहाड़ के साथ अपना भारी पंजा आगे बढ़ाया और चूहे को दबोच लिया।

"एक तिनके जैसे चूहे की यह हिम्मत कि वह जंगल के राजा की नींद खराब करे?" शेर गरजा।

नन्हे चूहे ने हाथ जोड़कर विनती की: "महाराज! मुझसे गलती हो गई, कृपया मुझे क्षमा कर दें। यदि आप मुझे जीवनदान देंगे, तो मैं किसी दिन आपके इस उपकार का बदला ज़रूर चुकाऊंगा।"

शेर चूहे की इस बात पर हंसा कि इतना छोटा चूहा जंगल के राजा की क्या मदद करेगा! लेकिन दया करके शेर ने अपना पंजा उठा लिया और चूहे को जाने दिया।

कुछ दिनों बाद, जंगल में शिकारियों ने एक मजबूत जाल बिछा रखा था। शेर उस जाल में बुरी तरह फंस गया। शेर दर्द और बेबसी से ज़ोर-ज़ोर से दहाड़ने लगा।

चूहे ने शेर की आवाज़ पहचान ली और तुरंत दौड़कर वहां पहुंचा। चूहे ने अपने तेज़ नुकीले दांतों से जाल की मोटी रस्सियों को काटना शुरू कर दिया। देखते ही देखते उसने पूरा फंदा काट डाला और शेर आज़ाद हो गया! शेर ने झुककर नन्हे चूहे को धन्यवाद दिया।""",
            contentEs = """Un imponente león de melena dorada llamado Leo dormía la siesta sobre una roca tibia. Un curioso ratoncito de campo llamado Pip perseguía semillas y, sin querer, corrió directo sobre la nariz del gran león.

Con un rugido atronador, la enorme garra del león cayó sobre el tembloroso ratón.

"¿Cómo te atreves a despertar al rey de la selva?", gruñó el león con las garras listas.

"¡Por favor, gran rey!", suplicó Pip con valentía. "Fue un accidente. Si me perdonas la vida, te prometo que algún día devolveré tu generosidad."

El león sonrió divertido ante la idea de que un ratón diminuto pudiera ayudar a una fiera de doscientos kilos. Conmovido por su valor, levantó la garra y lo dejó marchar.

Días después, el león cayó en una trampa de cuerdas de cazadores. La red lo ató con fuerza y Leo rugió desesperado por el valle.

Al escuchar el rugido, Pip corrió al rescate. Con sus afilados dientes, royó tenazmente las gruesas cuerdas hasta romper los nudos principales. ¡El león quedó libre! Agradecido de corazón, el león aprendió que la valentía y la lealtad no tienen tamaño.""",
            contentFr = """Un puissant lion à la crinière étincelante faisait la sieste sur un rocher tiède. Une petite souris des champs prénommée Pip poursuivait des aigrettes de pissenlit et passa malencontreusement sur le museau du félin endormi !

Dans un rugissement sourd, la patte colossale du lion s'abattit sur la pauvre souris terrorisée.

"Comment oses-tu réveiller le roi des animaux ?", gronda le fauve.

"Je vous en supplie, noble roi !", couina bravement Pip. "Épargnez-moi ! Si vous me laissez vivre, je vous promets qu'un jour je vous rendrai ce bienfait."

Le lion éclata de rire à l'idée qu'un si petit être puisse jamais lui venir en aide. Amusé par son audace, il leva sa patte et laissa filer la souris dans les hautes herbes.

Quelques jours plus tard, le lion tomba dans les filets solides de chasseurs. Pris au piège, il rugissait de détresse dans toute la vallée.

Entendant ce cri familier, Pip accourut aussitôt. De ses petites dents acérées, elle rongea les épaisses cordes de chanvre l'une après l'autre. Bientôt, le filet céda et le lion bondit, libre ! Ému et reconnaissant, le lion comprit que la grandeur d'âme ne dépend jamais de la taille.""",
            contentZh = """一天午后，威风凛凛的森林之王大狮子莱奥在一块温暖的大岩石上打盹。一只活泼可爱的小田鼠皮普追逐着蒲公英种子，不小心一溜烟踩到了大狮子沉睡的大鼻子上！

大狮子发出一声威严的咆哮，厚重的巨掌猛地落下，一下子将瑟瑟发抖的小田鼠按在掌心。

“大胆的小不点，竟敢惊扰万兽之王的酣睡？”狮子露出锋利的牙齿。

“尊敬的森林之王，请饶我一命吧！”小田鼠鼓起勇气拼命恳求，“我不是故意的！只要您放我走，日后我一定会报答您的仁慈大恩！”

狮子听了哈哈大笑：一只体重不到几两重的小老鼠，居然夸口要帮助几百斤重的森林之王？但看到小老鼠真诚无畏的眼神，狮子心生怜悯，抬起爪子放它钻进了草丛。

几天后，狮子在巡逻领地时不幸落入了猎人布下的粗绳铁网陷阱。绳索牢牢勒住它的身躯，狮子痛苦绝望地在山谷中大声咆哮。

听到熟悉的呼救声，小田鼠皮普立刻飞奔赶来。它毫不犹豫地用坚硬如凿的门牙，拼命啃咬撕扯最结实的绳结。一下、两下……绳结终于断开，狮子一跃重获自由！狮子深深地低下了头，向这位不起眼的小救命恩人致谢。从那天起，庞大的狮子与微小的小田鼠成了形影不离的知心伙伴！"""
        ),
        StoryItem(
            id = "joke_1",
            emoji = "😄",
            readMinutes = 1,
            titleEn = "Laugh Out Loud Nature Jokes",
            titleHi = "हँसी के फव्वारे: प्रकृति के लतीफे",
            titleEs = "Chistes Divertidos de la Naturaleza",
            titleFr = "Blagues Rigolotes sur la Nature",
            titleZh = "开怀大笑自然脑筋急转弯",
            moralEn = "Laughter is the best sunshine for a healthy growing mind!",
            moralHi = "हँसी और मुस्कान हमारे मस्तिष्क और स्वास्थ्य के लिए सबसे अच्छी धूप हैं!",
            moralEs = "¡La risa es el rayo de sol más saludable para una mente creativa!",
            moralFr = "Le rire est la plus belle des lumières pour faire grandir l'esprit !",
            moralZh = "欢声笑语是健康成长心灵最明媚温暖的阳光！",
            categoryEn = "Kid Jokes",
            categoryHi = "बच्चों के चुटकुले",
            categoryEs = "Chistes Infantiles",
            categoryFr = "Blagues pour Enfants",
            categoryZh = "儿童幽默笑话",
            contentEn = """Q: Why was the math book always looking so sad?
A: Because it had way too many problems to solve!

Q: Why did the computer squeak when it visited the doctor?
A: Because it caught a bad virus and had a squeaky mouse!

Q: What did the little tree say to the spring morning breeze?
A: 'Please leaf me alone, I'm trying to branch out today!'

Q: Why are ocean fish so smart?
A: Because they spend all their time swimming in schools!""",
            contentHi = """सवाल: गणित की किताब हमेशा इतनी उदास क्यों दिखाई देती थी?
जवाब: क्योंकि उसके पास हल करने के लिए बहुत सारी 'प्रॉब्लम्स' (समस्याएं) थीं!

सवाल: कंप्यूटर जब डॉक्टर के पास गया तो चीं-चीं क्यों कर रहा था?
जवाब: क्योंकि उसमें वायरस था और उसका माउस बहुत पुराना था!

सवाल: छोटे पेड़ ने सुबह की ठंडी हवा से क्या कहा?
जवाब: 'कृपया मुझे अकेला छोड़ दो (Leaf me alone), मैं आज नई शाखाएं फैला रहा हूँ!'

सवाल: समुद्र की मछलियां इतनी होशियार क्यों होती हैं?
जवाब: क्योंकि वे हमेशा 'स्कूल्स' (मछलियों के झुंड/विद्यालय) में रहती हैं!""",
            contentEs = """P: ¿Por qué el libro de matemáticas siempre estaba tan triste?
R: ¡Porque tenía demasiados problemas por resolver!

P: ¿Por qué chillaba la computadora cuando fue al médico?
R: ¡Porque tenía un virus y el ratón no paraba de moverse!

P: ¿Qué le dijo el arbolito a la brisa de primavera?
R: '¡Déjame en paz, que hoy estoy echando ramas!'

P: ¿Por qué los peces del mar son tan inteligentes?
R: ¡Porque siempre nadan en escuelas!""",
            contentFr = """Q : Pourquoi le livre de mathématiques avait-il toujours l'air si triste ?
R : Parce qu'il avait beaucoup trop de problèmes à résoudre !

Q : Pourquoi l'ordinateur couinait-il chez le médecin ?
R : Parce qu'il avait attrapé un virus et que sa souris était malade !

Q : Que dit le jeune arbre au vent du matin ?
R : 'Laisse-moi tranquille, je suis en train de faire de nouvelles branches !'

Q : Pourquoi les poissons sont-ils si savants ?
R : Parce qu'ils passent leurs journées dans des bancs (écoles) !""",
            contentZh = """问：为什么数学书整天都愁眉苦脸的？
答：因为它肚子里装满了数不清的“难题”呀！

问：电脑去看医生的时候为什么总是吱吱叫？
答：因为它的鼠标（老鼠）感冒中病毒啦！

问：春天早晨的小树苗对吹拂的微风说了什么？
答：‘请别摇晃我啦，我今天正忙着分枝发芽呢！’

问：为什么海里的小鱼都特别聪明有学问？
答：因为它们每天都在成群结队的“鱼群（Schools，双关学校）”里游历呀！"""
        )
    )
}
